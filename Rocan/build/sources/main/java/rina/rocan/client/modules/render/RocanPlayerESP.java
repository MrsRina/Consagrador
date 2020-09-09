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
 * 07/09/2020.
 *
 **/
public class RocanPlayerESP extends RocanModule {
	RocanSetting range                = createSetting(new String[] {"Range", "PlayerESPRange", "Area range to able render."}, 200, 0, 200);
	RocanSetting range_3d_stop_render = createSetting(new String[] {"Range Stop Render 3D", "PlayerESPRangeStopRender3D", "Offset to stop render."}, 6, 0, 10);
	RocanSetting render_2d_mode       = createSetting(new String[] {"Render 2D", "PlayerESPRender2D", "Modes to render 2D."}, "States", new String[] {"States", "CSGO", "Rect", "none"});
	RocanSetting render_3d_mode       = createSetting(new String[] {"Render 3D", "PlayerESPRender3D", "Modes to render 3D."}, "Predador", new String[] {"Predador", "Hacker", "Chams", "Outline", "none"});
	RocanSetting block_highlight      = createSetting(new String[] {"Block Highlight Alpha", "PlayerESPBlockHighlightAlpha", "Block highlight from players."}, 255, 0, 255);
	RocanSetting tracer_util          = createSetting(new String[] {"Tracer Alpha", "PlayerESPTracerAlpha", "Tracer util."}, 0, 0, 255);

	public static float distance_player = 0.0f;

	int r;
	int g;
	int b;

	public RocanPlayerESP() {
		super(new String[] {"Player ESP", "PlayerESP", "Make you see players on area."}, Category.ROCAN_RENDER);
	}

	@Override
	public void onRender(RocanEventRender event) {
		if (mc.player == null || mc.world == null) {
			return;
		}

		r = 190;
		g = 190;
		b = 190;

		for (Entity entity : mc.world.loadedEntityList) {
			if (!(entity instanceof EntityLivingBase && entity instanceof EntityPlayer)) {
				continue;
			}

			if (mc.player == entity) {
				continue;
			}

			if (mc.player.getDistance(entity) > range.getInteger()) {
				continue;
			}

			EntityPlayer player = (EntityPlayer) entity;

			renderTarget2D(player);
			renderBlockHighlight(player);
			renderTracer(player);
		}
	}

	public void renderBlockHighlight(EntityPlayer player) {
		RayTraceResult result = player.rayTrace(6, mc.getRenderPartialTicks());

		if (result != null && result.typeOfHit == RayTraceResult.Type.BLOCK) {
			BlockPos blockpos = result.getBlockPos();

			TurokRenderHelp.render3DSolid(getICamera(), blockpos, r, g, b, block_highlight.getInteger());
		}
	}

	public void renderTracer(EntityPlayer player) {
		GlStateManager.pushMatrix();

		TurokRenderHelp.renderTracer(player, r, g, b, tracer_util.getInteger());

		GlStateManager.popMatrix();
	}

	public void renderTarget2D(EntityPlayer player) {
		RocanUtilRendererEntity2D3D.prepare2D((Entity) player);

		if (render_2d_mode.getString().equals("States")) {
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

			int x = 0;
			int y = 0;

			int width  = x + 10;
			int height = y + 10; 

			GL11.glBegin(GL11.GL_QUADS);
			{
				GL11.glVertex2d(width, y);
				GL11.glVertex2d(x, y);
				GL11.glVertex2d(x, height);
				GL11.glVertex2d(width, height);
			}

			GL11.glEnd();
		}

		RocanUtilRendererEntity2D3D.release2D();
	}
}