package rina.rocan.client.huds;

// Minecraft.
import net.minecraft.util.math.MathHelper;

// Client.
import rina.rocan.client.RocanSetting;
import rina.rocan.client.RocanModule;
import rina.rocan.client.RocanHUD;

// Rocan.
import rina.rocan.Rocan;

/**
 *
 * @author Rina!
 *
 * Created by Rina!
 * 03/09/2020.
 *
 **/
public class RocanVelocitySpeed extends RocanHUD {
	RocanSetting rgb_effect = addSetting(new String[] {"RGB", "VelocitySpeedRGB", "RGB effect."}, false);
	RocanSetting mode_calc  = addSetting(new String[] {"Calcule", "VelocityCalcule", "Modes for calcule."}, "KM/h", new String[] {"KM/h", "B/s"});

	public RocanVelocitySpeed() {
		super(new String[] {"Velocity Speed", "VelocitySpeed", "Show speed values."});
	}

	@Override
	public void onRenderHUD() {
		if (mc.player == null || mc.world == null) {
			return;
		}

		String speed = getSpeed();

		if (rgb_effect.getBoolean()) {
			renderString(speed, 0, 0, rgb_r, rgb_g, rgb_b);
		} else {
			renderString(speed, 0, 0);
		}

		this.rect.setWidth(getStringWidth(speed));
		this.rect.setHeight(getStringHeight(speed));
	}

	public String getSpeed() {
		String speed = "";

		if (mode_calc.getString().equals("KM/h")) {
			double delta_x = mc.player.posX - mc.player.prevPosX;
			double detal_z = mc.player.posZ - mc.player.prevPosZ;

			float tick_rate = (mc.timer.tickLength / 1000.0f);

			double km_convert = (MathHelper.sqrt(delta_x * delta_x + detal_z * detal_z) / tick_rate) * 3.6d;

			speed = "Speed " + Rocan.getGrayColor() + String.format("%.1f", (double) km_convert) + "KM/h";

		} else if (mode_calc.getString().equals("B/s")) {
			double delta_x = mc.player.posX - mc.player.prevPosX;
			double detal_z = mc.player.posZ - mc.player.prevPosZ;

			float tick_rate = (mc.timer.tickLength / 1000.0f);

			double bps_convert = (MathHelper.sqrt(delta_x * delta_x + detal_z * detal_z) / tick_rate);

			speed = "Speed " + Rocan.getGrayColor() + String.format("%.1f", (double) bps_convert) + "B/s";

		}

		return speed;
	}
}