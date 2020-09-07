package rina.rocan.client.modules.render;

// Minecraft.
import net.minecraft.network.play.server.SPacketBlockBreakAnim;
import net.minecraft.util.math.BlockPos;
import net.minecraft.init.Blocks;

// Java.
import java.util.*;

// Turok.
import rina.turok.TurokRenderHelp;

// Pomelo.
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

// Client.
import rina.rocan.client.RocanSetting;
import rina.rocan.client.RocanModule;

// Event.
import rina.rocan.event.network.RocanEventPacketReceive;
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
public class RocanBreakHighlight extends RocanModule {
	RocanSetting rgb_effect          = createSetting(new String[] {"RGB", "BreakHighlightRGB", "RGB effect to render."}, false);
	RocanSetting red_color           = createSetting(new String[] {"Red", "BreakHighlightRed", "Change color RED."}, 255, 0, 255);
	RocanSetting green_color         = createSetting(new String[] {"Green", "BreakHighlightGreen", "Change color GREEN."}, 0, 0, 255);
	RocanSetting blue_color          = createSetting(new String[] {"Blue", "BreakHighlightBlue", "Change color BLUE."}, 0, 0, 255);
	RocanSetting alpha_color         = createSetting(new String[] {"Alpha", "BreakHighlightAlpha", "Change color ALPHA."}, 150, 0, 255);
	RocanSetting alpha_outline_color = createSetting(new String[] {"Alpha Outline", "BreakHighlightAlphaOutline", "Change color ALPHA to outline."}, 255, 0, 255);

	int r;
	int g;
	int b;

	SPacketBlockBreakAnim packet_break;

	public RocanBreakHighlight() {
		super(new String[] {"Break Highlight", "BreakHighlight", "Render break block level."}, Category.ROCAN_RENDER);
	}

	@Listener
	public void sendPacket(RocanEventPacketReceive event) {
		if (event.getPacket() instanceof SPacketBlockBreakAnim) {
			if (mc.world.getBlockState(((SPacketBlockBreakAnim) event.getPacket()).getPosition()).getBlock() == Blocks.AIR) {
				return;
			}

			packet_break = (SPacketBlockBreakAnim) event.getPacket();
		}
	}

	@Override
	public void onRender(RocanEventRender event) {
		if (rgb_effect.getBoolean()) {
			red_color.setInteger(Rocan.getEventManager().getRGBEffect()[0]);
			green_color.setInteger(Rocan.getEventManager().getRGBEffect()[1]);
			blue_color.setInteger(Rocan.getEventManager().getRGBEffect()[2]);

			r = red_color.getInteger();
			g = green_color.getInteger();
			b = blue_color.getInteger();
		} else {
			r = red_color.getInteger();
			g = green_color.getInteger();
			b = blue_color.getInteger();
		}

		if (packet_break == null) {
			return;
		}

		TurokRenderHelp.prepare("quads");
		TurokRenderHelp.drawCube(packet_break.getPosition(), r, g, b, alpha_color.getInteger(), "all");
		TurokRenderHelp.release();

		TurokRenderHelp.prepare("lines");
		TurokRenderHelp.drawOutlineCube(packet_break.getPosition(), r, g, b, alpha_outline_color.getInteger(), "all");
		TurokRenderHelp.release();
	}
}