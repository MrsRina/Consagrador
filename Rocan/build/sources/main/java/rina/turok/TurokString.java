package rina.turok;

// Minecraft util.
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.Minecraft;

// Font.
import rina.util.CFontRenderer;

// Java.
import java.util.*;
import java.awt.*;

/**
 * @author Rina!
 *
 * Created by Rina in 27/07/2020.
 *
 **/
public class TurokString {
	public static CFontRenderer custom_font     = new CFontRenderer(new Font("Tahoma", 0, 16), true, true);
	public static CFontRenderer custom_font_hud = new CFontRenderer(new Font("Tahoma", 0, 19), true, true);
	public static FontRenderer font_renderer    = Minecraft.getMinecraft().fontRenderer;

	// Main Render.
	public static void renderString(String string, int x, int y, int r, int g, int b, boolean shadow, boolean custom) {
		TurokRenderGL.prepareToRenderString();
		TurokRenderGL.color(r, g, b, 255);

		if (custom) {
			if (shadow) {
				custom_font.drawStringWithShadow(string, x, y, new TurokColor(r, g, b).hex());
			} else {
				custom_font.drawString(string, x, y, new TurokColor(r, g, b).hex());
			}
		} else {
			if (shadow) {
				font_renderer.drawStringWithShadow(string, x, y, new TurokColor(r, g, b).hex());
			} else {
				font_renderer.drawString(string, x, y, new TurokColor(r, g, b).hex());
			}
		}

		TurokRenderGL.releaseRenderString();
	}

	public static int getStringWidth(String string, boolean custom) {
		if (custom) {
			return (int) custom_font.getStringWidth(string);
		} else {
			return (int) (font_renderer.getStringWidth(string) * 1);
		}
	}

	public static int getStringHeight(String string, boolean custom) {
		if (custom) {
			return (int) custom_font.getStringHeight(string);
		} else {
			return (int) (font_renderer.FONT_HEIGHT * 1);
		}
	}

	// Custom HUD.
	public static void renderStringHUD(String string, int x, int y, int r, int g, int b, boolean shadow, boolean custom) {
		TurokRenderGL.prepareToRenderString();
		TurokRenderGL.color(r, g, b, 255);

		if (custom) {
			if (shadow) {
				custom_font_hud.drawStringWithShadow(string, x, y, new TurokColor(r, g, b).hex());
			} else {
				custom_font_hud.drawString(string, x, y, new TurokColor(r, g, b).hex());
			}
		} else {
			if (shadow) {
				font_renderer.drawStringWithShadow(string, x, y, new TurokColor(r, g, b).hex());
			} else {
				font_renderer.drawString(string, x, y, new TurokColor(r, g, b).hex());
			}
		}

		TurokRenderGL.releaseRenderString();
	}

	public static int getStringHUDWidth(String string, boolean custom) {
		if (custom) {
			return (int) custom_font_hud.getStringWidth(string);
		} else {
			return (int) (font_renderer.getStringWidth(string) * 1);
		}
	}

	public static int getStringHUDHeight(String string, boolean custom) {
		if (custom) {
			return (int) custom_font_hud.getStringHeight(string);
		} else {
			return (int) (font_renderer.FONT_HEIGHT * 1);
		}
	}

	public static class TurokColor extends Color {
		public TurokColor(int r, int g, int b, int a) {
			super(r, g, b, a);
		}

		public TurokColor(int r, int g, int b) {
			super(r, g, b);
		}

		public int hex() {
			return getRGB();
		}
	}
}
