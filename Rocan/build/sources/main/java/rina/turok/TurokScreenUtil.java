package rina.turok;

// Minecraft.
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

// Java.
import java.awt.*;

/**
 * @author Rina!
 *
 * Created by Rina in 27/07/2020.
 *
 **/
public class TurokScreenUtil {
	public static ScaledResolution scl_minecraft_screen = new ScaledResolution(Minecraft.getMinecraft());

	public static int getScreenWidth() {
		return scl_minecraft_screen.getScaledWidth();
	}

	public static int getScreenHeight() {
		return scl_minecraft_screen.getScaledHeight();
	}

	public static void drawGUIRect(int x, int y, int w, int h, int r, int g, int b, int a) {
		Gui.drawRect(x, y, w, h, new TurokColor(r, g, b, a).hex());
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