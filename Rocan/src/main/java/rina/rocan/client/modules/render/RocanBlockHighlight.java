package rina.rocan.client.modules.render;

// Minecraft.
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.BlockPos;

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
 * 06/09/2020.
 *
 **/
public class RocanBlockHighlight extends RocanModule {
	// RGB effect.
	RocanSetting rgb_effect = createSetting(new String[] {"RGB", "BlockHighlightRGB", "RGB effect to render."}, false);
	
	// Color stuffs.
	RocanSetting render_red_color   = createSetting(new String[] {"Render Red", "BlockHighlightRenderRed", "Change color red."}, 255, 0, 255);
	RocanSetting render_green_color = createSetting(new String[] {"Render Green", "BlockHighlightRenderGreen", "Change color green."}, 0, 0, 255);
	RocanSetting render_blue_color  = createSetting(new String[] {"Render Blue", "BlockHighlightRenderBlue", "Change color blue."}, 0, 0, 255);
	RocanSetting render_alpha_color = createSetting(new String[] {"Render Alpha", "BlockHighlightRenderAlpha", "Change color alpha."}, 150, 0, 255);
	
	// things...
	RocanSetting alpha_outline_color = createSetting(new String[] {"Alpha Outline", "BlockHighlightAlphaOutline", "Change color ALPHA to outline."}, 255, 0, 255);
	RocanSetting line_outline_width  = createSetting(new String[] {"Line Outline Width", "BlockHighlightOutlineWidth", "Width outline for render outline."}, 1.0, 1.0, 3.0);

	int r;
	int g;
	int b;

	public RocanBlockHighlight() {
		super(new String[] {"Block Highlight", "BlockHighlight", "Force render block."}, Category.ROCAN_RENDER);
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

		RayTraceResult result = mc.objectMouseOver;

		if (result != null && result.typeOfHit == RayTraceResult.Type.BLOCK) {
			BlockPos blockpos = result.getBlockPos();

			TurokRenderHelp.render3DSolid(getICamera(), blockpos, r, g, b, render_alpha_color.getInteger());
			TurokRenderHelp.render3DOutline(getICamera(), blockpos, r, g, b, alpha_outline_color.getInteger(), (float) line_outline_width.getDouble());
		}
	}
}