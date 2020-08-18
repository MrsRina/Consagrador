package rina.rocan.client.modules.render;

// Minecraft.
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.BlockPos;

// Turok.
import rina.turok.TurokRenderHelp;

// Pomelo.
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

// Event.
import rina.rocan.event.render.RocanEventRender;

// Module.
import rina.rocan.client.RocanModule.Category;
import rina.rocan.client.RocanModule.Define;
import rina.rocan.client.RocanModule;

/**
 *
 * @author Rina!
 *
 * Created by Rina!
 * 17/08/2020.
 *
 **/
@Define(name = "Block Highlight", tag = "BlockHighlight", description = "Util highlight block.", category = Category.ROCAN_RENDER)
public class RocanBlockHighlight extends RocanModule {
	@Override
	public void onRender(RocanEventRender event) {
		if (mc.player != null && mc.world != null) {
			RayTraceResult result = mc.objectMouseOver;

			if (result != null) {
				if (result.typeOfHit == RayTraceResult.Type.BLOCK) {
					BlockPos block_pos = result.getBlockPos();

					TurokRenderHelp.prepare("quads");
					TurokRenderHelp.drawCube(block_pos, 255, 255, 255, 255, "all");
					TurokRenderHelp.release();
				}
			}
		}
	}
}