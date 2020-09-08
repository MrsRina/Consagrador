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

// Java.
import java.awt.*;

// Turok.
import rina.turok.TurokRenderHelp;

// Client.
import rina.rocan.client.RocanSetting;
import rina.rocan.client.RocanModule;

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
	RocanSetting render_2d_mode       = createSetting(new String[] {"Render 2D", "PlayerESPRender2D", "Modes to render 2D."}, "Predador", new String[] {"Predador", "Hacker", "CSGO", "Rect", "none"});
	RocanSetting render_3d_mode       = createSetting(new String[] {"Render 3D", "PlayerESPRender3D", "Modes to render 3D."}, "Chams", new String[] {"Chams", "Outline", "none"});
	RocanSetting block_highlight      = createSetting(new String[] {"Block Highlight Alpha", "PlayerESPBlockHighlightAlpha", "Block highlight from players."}, 255, 0, 255);
	RocanSetting tracer_util          = createSetting(new String[] {"Tracer", "PlayerESPTracerAlpha", "Tracer util."}, 0, 0, 255);

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

		for (Entity entities : mc.world.loadedEntityList) {
			if (!(entities instanceof EntityLivingBase && entities instanceof EntityPlayer)) {
				continue;
			}

			if (mc.player.getDistance(entities) > range.getInteger()) {
				continue;
			}

			renderBlockHighlight((EntityPlayer) entities);
			renderTracer((EntityPlayer) entities);
			renderTarget2D((EntityPlayer) entities);
		}
	}

	public void renderBlockHighlight(EntityPlayer player) {
		RayTraceResult result = player.rayTrace(6, mc.getRenderPartialTicks());

		if (result != null && result.typeOfHit == RayTraceResult.Type.BLOCK) {
			BlockPos blockpos = result.getBlockPos();

			TurokRenderHelp.prepare("quads");
			TurokRenderHelp.drawCube(blockpos, r, g, b, block_highlight.getInteger(), "all");
			TurokRenderHelp.release();
		}
	}

	public void renderTracer(EntityPlayer player) {}

	public void renderTarget2D(EntityPlayer player) {}
}