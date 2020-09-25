package rina.rocan.client.modules.render;

// Minecraft.
import net.minecraft.util.math.BlockPos;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

// Java.
import java.util.*;

// Turok.
import rina.turok.TurokRenderHelp;

// Client.
import rina.rocan.client.RocanSetting;
import rina.rocan.client.RocanModule;

// Util.
import rina.rocan.util.RocanUtilMath;

// Event.
import rina.rocan.event.render.RocanEventRender;

// Rocan.
import rina.rocan.Rocan;

/**
 *
 * @author Rina!
 *
 * Created by Rina!
 * 11/09/2020.
 *
 **/
public class RocanHoleESP extends RocanModule {
	// Configs.
	RocanSetting range       = createSetting(new String[] {"Range", "HoleESPRange", "Area range to able render."}, 6, 0, 16);
	//RocanSetting double_hole = createSetting(new String[] {"Double Hole", "HoleESPDoubleHole", "Double render hole."}, false);

	// RGB effect.
	RocanSetting rgb_effect = createSetting(new String[] {"RGB", "HoleESPRGB", "RGB effect to render."}, false);
	
	// Color stuffs.
	RocanSetting render_red_color   = createSetting(new String[] {"Red", "HoleESPRed", "Change color hole red."}, 255, 0, 255);
	RocanSetting render_green_color = createSetting(new String[] {"Green", "HoleESPGreen", "Change color hole green."}, 0, 0, 255);
	RocanSetting render_blue_color  = createSetting(new String[] {"Blue", "HoleESPBlue", "Change color hole blue."}, 0, 0, 255);
	RocanSetting render_alpha_color = createSetting(new String[] {"Alpha", "HoleESPAlpha", "Change color hole alpha."}, 150, 0, 255);
	
	// things...
	RocanSetting alpha_outline_color = createSetting(new String[] {"Alpha Outline", "HoleESPAlphaOutline", "Change color hole alpha to outline."}, 255, 0, 255);
	RocanSetting line_outline_width  = createSetting(new String[] {"Line Outline Width", "HoleESPOutlineWidth", "Width outline hole for render outline."}, 1.0, 1.0, 3.0);
	RocanSetting offset_y            = createSetting(new String[] {"Offset Y", "HoleESP", "Offset y to hole."}, 0.2d, 0.0d, 1.0d);

	int r;
	int g;
	int b;

	private ArrayList<BlockPos> hole_list;

	private boolean safe = false;

	private BlockPos[] hole_matrix = {
		// Down holes.
		new BlockPos(0, -1, 0),

		// Positives.
		new BlockPos(1, 0, 0),
		new BlockPos(0, 0, 1),

		// Negtives.
		new BlockPos(-1, 0, 0),
		new BlockPos(0, 0, -1)
	};

	private BlockPos[] double_hole_matrix = {
		// Down holes.
		new BlockPos(0, -1, 0),

		// Positives.
		new BlockPos(1, 0, 0),
		new BlockPos(0, 0, 1),

		// Negtives.
		new BlockPos(-1, 0, 0),
		new BlockPos(0, 0, -1)
	};

	private BlockPos[] current_hole_matrix = {};

	public RocanHoleESP() {
		super(new String[] {"Hole ESP", "HoleESP", "Render holes bedrock or obisidian."}, Category.ROCAN_RENDER);
	}

	@Override
	public void onUpdate() {
		if (hole_list == null) {
			hole_list = new ArrayList<>();
		} else {
			hole_list.clear();
		}

		if (mc.player != null || mc.world != null) {
			//if (double_hole.getBoolean()) {
				//current_hole_matrix = double_hole_matrix;
			//} else {
				current_hole_matrix = hole_matrix;
			//}

			int ceil_range = (int) Math.ceil(range.getInteger());

			List<BlockPos> sphere_list = RocanUtilMath.getSphereList(RocanUtilMath.getPlayerBlockpos(), ceil_range, ceil_range, false, true);

			for (BlockPos blockpos : sphere_list) {
				if (!mc.world.getBlockState(blockpos).getBlock().equals(Blocks.AIR)) {
					continue;
				}

				if (!mc.world.getBlockState(blockpos.add(0, 1, 0)).getBlock().equals(Blocks.AIR)) {
					continue;
				}

				if (!mc.world.getBlockState(blockpos.add(0, 2, 0)).getBlock().equals(Blocks.AIR)) {
					continue;
				}

				boolean possible = true;

				for (BlockPos blockpos_matrix : current_hole_matrix) {
					Block block = mc.world.getBlockState(blockpos.add(blockpos_matrix)).getBlock();


					if (block != Blocks.BEDROCK && block != Blocks.OBSIDIAN && block != Blocks.ENDER_CHEST && block != Blocks.ANVIL) {
						possible = false;

						break;
					}
				}

				if (possible) {
					hole_list.add(blockpos);
				}
			}
		}
	}

	@Override
	public void onRender(RocanEventRender event) {	
		if (rgb_effect.getBoolean()) {
			render_red_color.setInteger(Rocan.getEventManager().getRGBEffect()[0]);
			render_green_color.setInteger(Rocan.getEventManager().getRGBEffect()[1]);
			render_blue_color.setInteger(Rocan.getEventManager().getRGBEffect()[2]);

			r = render_red_color.getInteger();
			g = render_green_color.getInteger();
			b = render_blue_color.getInteger();
		} else {
			r = render_red_color.getInteger();
			g = render_green_color.getInteger();
			b = render_blue_color.getInteger();
		}

		if (hole_list != null && !hole_list.isEmpty() || safe != false) {
			for (BlockPos blockpos : hole_list) {
				TurokRenderHelp.render3DSolid(getICamera(), blockpos.x, blockpos.y, blockpos.z, 1, offset_y.getDouble(), 1, r, g, b, render_alpha_color.getInteger());
				TurokRenderHelp.render3DOutline(getICamera(), blockpos.x, blockpos.y, blockpos.z, 1, offset_y.getDouble(), 1, r, g, b, alpha_outline_color.getInteger(), (float) line_outline_width.getDouble());
			}
		}
	}
}