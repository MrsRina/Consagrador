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
import java.util.*;

// Command.
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

	public RocanEventManager() {}

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
	}

	@SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
	public void onKeyInput(InputEvent.KeyInputEvent event) {
		if (Keyboard.getEventKeyState()) {
			Rocan.getModuleManager().onPressedKeyBind(Keyboard.getEventKey());
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

			if (!true_command && event.getMessage().startsWith(Rocan.getCommandManager().getPrefix())) {
				RocanUtilClient.sendNotifyClient("An error ocurred.");

				true_command = false;
			}
		}
	}
}