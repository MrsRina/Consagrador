package rina.rocan.manager;

// Minecraft.
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraft.network.play.server.SPacketPlayerListItem;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraftforge.client.event.*;
import net.minecraft.client.Minecraft;

// OpenGL.
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

// Java.
import java.awt.Color;
import java.util.*;

// Turok.
import rina.turok.TurokRenderGL;

// Client.
import rina.rocan.client.RocanCommand;

/// Util.
import rina.rocan.util.RocanUtilClient;

// Rocan.
import rina.rocan.Rocan;

/**
 *
 * @author Rina!
 *
 * Created by Rina!
 * 15/08/2020.
 *
 **/
public class RocanEventManager {
	private Minecraft mc = Minecraft.getMinecraft();

	public int tickness_rgb_effect_red;
	public int tickness_rgb_effect_green;
	public int tickness_rgb_effect_blue;

	public RocanEventManager() {
		this.tickness_rgb_effect_red   = 0;
		this.tickness_rgb_effect_green = 0;
		this.tickness_rgb_effect_blue  = 0;
	}

	@SubscribeEvent
	public void onUpdate(LivingEvent.LivingUpdateEvent event) {
		if (event.isCanceled()) {
			return;
		}
	}

	@SubscribeEvent
	public void onTick(TickEvent.ClientTickEvent event) {
		if (mc.player == null) {
			return;
		}

		Rocan.getModuleManager().onUpdateModuleList();

		// We call here to event listener, maybe I create a system mode to Rocan.
		Rocan.getChatManager().listenChatNotifier();
	}

	@SubscribeEvent
	public void onWorldRender(RenderWorldLastEvent event) {
		if (event.isCanceled()) {
			return;
		}

		Rocan.getModuleManager().onRenderModuleList(event);

		float[] tick_color = {
			(System.currentTimeMillis() % (360 * 32)) / (360f * 32)
		};
	
		int color_process = Color.HSBtoRGB(tick_color[0], 1, 1);

		// We update.
		this.tickness_rgb_effect_red   = ((color_process >> 16) & 0xFF);
		this.tickness_rgb_effect_green = ((color_process >> 8) & 0xFF);
		this.tickness_rgb_effect_blue  = ((color_process) & 0xFF);
	}

	@SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
	public void onKeyInput(InputEvent.KeyInputEvent event) {
		if (Keyboard.getEventKeyState()) {
			Rocan.getSettingManager().onKeyEvent(Keyboard.getEventKey());
		}
	}

	@SubscribeEvent
	public void onRender(RenderGameOverlayEvent.Post event) {
		if (event.isCanceled()) {
			return;
		}

		RenderGameOverlayEvent.ElementType target = RenderGameOverlayEvent.ElementType.ALL;

		if (!mc.player.isCreative() && mc.player.getRidingEntity() instanceof AbstractHorse) {
			target = RenderGameOverlayEvent.ElementType.HEALTHMOUNT;
		}

		if (event.getType() == target) {
			Rocan.getModuleManager().onRenderModuleList();

			// Prepare to render.
			TurokRenderGL.prepare2D();
			TurokRenderGL.release2D();
		}
	}

	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void onChat(ClientChatEvent event) {
		String   message      = event.getMessage();
		String[] message_args = Rocan.getCommandManager().getArgs(event.getMessage());

		boolean true_command = false;

		if (message_args.length > 0) {
			event.setCanceled(true);

			mc.ingameGUI.getChatGUI().addToSentMessages(event.getMessage());

			for (RocanCommand commands : Rocan.getCommandManager().getCommandList()) {
				try {
					if (Rocan.getCommandManager().getArgs(event.getMessage())[0].equalsIgnoreCase(commands.getCommand())) {
						true_command = commands.onRequested(Rocan.getCommandManager().getArgs(event.getMessage()));
					}
				} catch (Exception exc) {}
			}

			if (!true_command && Rocan.getCommandManager().hasPrefix(event.getMessage())) {
				RocanUtilClient.sendNotifyClient("An error ocurred, try type help command.");

				true_command = false;
			}
		}
	}

	public int[] getRGBEffect() {
		return new int[] {this.tickness_rgb_effect_red, this.tickness_rgb_effect_green, this.tickness_rgb_effect_blue};
	}
}