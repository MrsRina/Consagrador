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
 * 07/09/2020.
 *
 **/
public class RocanEntityBlockHighlight extends RocanModule {
	RocanSetting rgb_effect          = createSetting(new String[] {"RGB", "EntityBlockHighlightRGB", "RGB effect to render."}, false);
	RocanSetting red_color           = createSetting(new String[] {"Red", "EntityBlockHighlightRed", "Change color RED."}, 255, 0, 255);
	RocanSetting green_color         = createSetting(new String[] {"Green", "EntityBlockHighlightGreen", "Change color GREEN."}, 0, 0, 255);
	RocanSetting blue_color          = createSetting(new String[] {"Blue", "EntityBlockHighlightBlue", "Change color BLUE."}, 0, 0, 255);
	RocanSetting alpha_color         = createSetting(new String[] {"Alpha", "EntityBlockHighlightAlpha", "Change color ALPHA."}, 150, 0, 255);
	RocanSetting alpha_outline_color = createSetting(new String[] {"Alpha Outline", "EntityBlockHighlightAlphaOutline", "Change color ALPHA to outline."}, 255, 0, 255);

	int r;
	int g;
	int b;

	public RocanEntityBlockHighlight() {
		super(new String[] {"Entity Block Highlight", "EntityBlockHighlight", "Find eye and simule a block highlight"}, Category.ROCAN_RENDER);
	}
}