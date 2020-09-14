package rina.rocan.client.modules.render;

// Minecraft.
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.renderer.OpenGlHelper;
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

// Pomelo.
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

// Turok.
import rina.turok.TurokRenderHelp;

// Client.
import rina.rocan.client.RocanSetting;
import rina.rocan.client.RocanModule;

// Util.
import rina.rocan.util.RocanUtilRendererEntity2D3D;

// Event.
import rina.rocan.event.render.RocanEventRenderLivingBase;
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
	// Settings range, minimum, modes...
	RocanSetting range                = createSetting(new String[] {"Range", "PlayerESPRange", "Area range to able render."}, 200, 0, 200);
	RocanSetting range_3d_stop_render = createSetting(new String[] {"Range Stop Render 3D", "PlayerESPRangeStopRender3D", "Offset to stop render."}, 6, 0, 10);
	RocanSetting render_2d_mode       = createSetting(new String[] {"Render 2D", "PlayerESPRender2D", "Modes to render 2D."}, "States", new String[] {"States", "CSGO", "Rect", "none"});
	RocanSetting render_3d_mode       = createSetting(new String[] {"Render 3D", "PlayerESPRender3D", "Modes to render 3D."}, "Predador", new String[] {"Predador", "Hacker", "Chams", "Outline", "none"});
	RocanSetting render_friend_color  = createSetting(new String[] {"Render Friend Color", "PlayerESPRenderFriendColor", "Enable color HUD to render."}, true);

	// RGB retarddded effect.
	RocanSetting rgb_effect = createSetting(new String[] {"RGB", "PlayerESPRGB", "RGB effect to render."}, false);

	// Render stuff to ESP.
	RocanSetting render_color_red   = createSetting(new String[] {"Render Red", "PlayerESPRenderRed", "Player render color red."}, 190, 0, 255);
	RocanSetting render_color_green = createSetting(new String[] {"Render Green", "PlayerESPrenderGreen", "Player render color green."}, 190, 0, 255);
	RocanSetting render_color_blue  = createSetting(new String[] {"Render Blue", "PlayerESPrenderBlue", "Player render color blue."}, 190, 0, 255);
	
	// Blockhighlight.	
	RocanSetting block_highlight_alpha         = createSetting(new String[] {"BlockH Alpha", "PlayerESPBlockHighlightAlpha", "Block highlight from players."}, 200, 0, 255);
	RocanSetting block_highlight_outline_alpha = createSetting(new String[] {"BlockH Outline Alpha", "PlayerESPBlockHighlightOutlineAlpha", "Block highlight from players."}, 100, 0, 255);
	RocanSetting block_highlight_width_outline = createSetting(new String[] {"BlockH Line Width", "PlayerESPBlockhighlightLineWidth", "Width outline for block highlight."}, 1.0, 1.0, 3.0);

	float distance_player = 0.0f;

	int r;
	int g;
	int b;

	public RocanPlayerESP() {
		super(new String[] {"Player ESP", "PlayerESP", "Make you see players on area."}, Category.ROCAN_RENDER);
	}

	@Listener
	public void listenRender(RocanEventRenderLivingBase event) {
		if (mc.player == null) {
			return;
		}

		if (!(event.getEntityLivingBase() instanceof EntityPlayer)) {
			return;
		}

		if ((EntityPlayer) event.getEntityLivingBase() == mc.player) {
			return;
		}

		if (event.getStage() == RocanEventRenderLivingBase.EventStage.PRE) {
			switch (render_3d_mode.getString()) {
				case "Chams" : {
					GlStateManager.pushMatrix();

					OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);

					GL11.glEnable(GL11.GL_POLYGON_OFFSET_FILL);
					GL11.glPolygonOffset(1.0f, -1100000.0f);

					GlStateManager.popMatrix();

					break;
				}
			}
		} else if (event.getStage() == RocanEventRenderLivingBase.EventStage.POST) {
			switch (render_3d_mode.getString()) {
				case "Chams" : {
					GlStateManager.pushMatrix();

					GL11.glDisable(GL11.GL_POLYGON_OFFSET_FILL);
					GL11.glPolygonOffset(1.0f, 1100000.0f);
					GL11.glEnable(GL11.GL_TEXTURE_2D);

					GlStateManager.popMatrix();

					break;
				}
			}
		}
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

			if (mc.player.getDistance(entity) > range.getInteger()) {
				continue;
			}

			EntityPlayer player = (EntityPlayer) entity;

			renderTarget2D(player);
			renderBlockHighlight(player);
		}
	}

	public void renderBlockHighlight(EntityPlayer player) {
		RayTraceResult result = player.rayTrace(6, mc.getRenderPartialTicks());

		int veirfy_red   = (render_friend_color.getBoolean() && Rocan.getFriendManager().isFriend(player.getName())) ? Rocan.getClientHUDRed() : r;
		int veirfy_green = (render_friend_color.getBoolean() && Rocan.getFriendManager().isFriend(player.getName())) ? Rocan.getClientHUDGreen() : g;
		int veirfy_blue  = (render_friend_color.getBoolean() && Rocan.getFriendManager().isFriend(player.getName())) ? Rocan.getClientHUDBlue() : b;

		if (result != null && result.typeOfHit == RayTraceResult.Type.BLOCK) {
			BlockPos blockpos = result.getBlockPos();

			TurokRenderHelp.render3DSolid(getICamera(), blockpos, veirfy_red, veirfy_green, veirfy_blue, block_highlight_alpha.getInteger());
			TurokRenderHelp.render3DOutline(getICamera(), blockpos, veirfy_red, veirfy_green, veirfy_blue, block_highlight_outline_alpha.getInteger(), (float) block_highlight_width_outline.getDouble());
		}
	}

	public void renderTarget2D(EntityPlayer player) {
		RocanUtilRendererEntity2D3D.prepare2D((Entity) player);

		if (render_2d_mode.getString().equals("States")) {

		}

		RocanUtilRendererEntity2D3D.release2D();
	}
}