package rina.rocan.client.modules.render;

// Minecraft.
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.IMob;
import net.minecraft.util.math.BlockPos;
import net.minecraft.client.renderer.*;
import net.minecraft.util.math.Vec3d;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.Entity;
import net.minecraft.init.Items;

// OpenGL.
import org.lwjgl.opengl.GL11;

// Java.
import java.awt.*;

// Turok.
import rina.turok.TurokRenderHelp;

// Client.
import rina.rocan.client.RocanSetting;
import rina.rocan.client.RocanModule;

// Util.
import rina.rocan.util.RocanUtilRendererEntity2D3D;

// Event.
import rina.rocan.event.render.RocanEventRender;

// Rocan.
import rina.rocan.Rocan;

/**
 *
 * @author Rina!
 *
 * Created by Rina!
 * 10/09/2020.
 *
 **/
public class RocanPlayerTracer extends RocanModule {
	// Stuff...
	RocanSetting range                = createSetting(new String[] {"Range", "PlayerTracerRange", "Range to render."}, 200, 0, 200);
	RocanSetting range_to_stop_render = createSetting(new String[] {"Range To Stop Render", "PlayerTracerToStopRender", "Range to stop] render."}, 1, 0, 10);

	RocanSetting render_friend_color = createSetting(new String[] {"Render Friend Color", "PlayerTracerRenderFriendColor", "Enable color HUD to render."}, true);

	// RGB effecttt.
	RocanSetting rgb_effect = createSetting(new String[] {"RGB", "PlayerTracerRGB", "RGB effect to render."}, false);

	// Color.
	RocanSetting render_color_red   = createSetting(new String[] {"Red", "PlayerTracerRed", "Render color red."}, 190, 0, 255);
	RocanSetting render_color_green = createSetting(new String[] {"Green", "PlayerTracerGreen", "Render color green."}, 190, 0, 255);
	RocanSetting render_color_blue  = createSetting(new String[] {"Blue", "PlayerTracerBlue", "Render color blue."}, 190, 0, 255);
	RocanSetting render_color_alpha = createSetting(new String[] {"Alpha", "PlayerTracerAlpha", "Render color alpha."}, 255, 0, 255);

	int r;
	int g;
	int b;

	public RocanPlayerTracer() {
		super(new String[] {"Tracer Util", "TracerUtil", "Tracer util for render."}, Category.ROCAN_RENDER);
	}

	@Override
	public void onRender(RocanEventRender event) {
		if (mc.player == null || mc.world == null) {
			return;
		}

		if (rgb_effect.getBoolean()) {
			render_color_red.setInteger(Rocan.getEventManager().getRGBEffect()[0]);
			render_color_green.setInteger(Rocan.getEventManager().getRGBEffect()[1]);
			render_color_blue.setInteger(Rocan.getEventManager().getRGBEffect()[2]);

			r = render_color_red.getInteger();
			g = render_color_green.getInteger();
			b = render_color_blue.getInteger();
		} else {
			r = render_color_red.getInteger();
			g = render_color_green.getInteger();
			b = render_color_blue.getInteger();
		}

		for (Entity entity : mc.world.loadedEntityList) {
			if (!(entity instanceof EntityLivingBase && entity instanceof EntityPlayer)) {
				continue;
			}

			if (mc.player == entity) {
				continue;
			}

			if (mc.player.getDistance(entity) > range.getInteger() || mc.player.getDistance(entity) < range_to_stop_render.getInteger()) {
				continue;
			}

			EntityPlayer player = (EntityPlayer) entity;

			TurokRenderHelp.renderTracer((Entity) player, r, g, b, render_color_alpha.getInteger());
		}
	}
}