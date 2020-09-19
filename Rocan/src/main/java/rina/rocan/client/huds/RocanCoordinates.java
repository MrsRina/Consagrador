package rina.rocan.client.huds;

// Miencraft.
import net.minecraft.util.EnumFacing;

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
 * 28/08/2020.
 *
 **/
public class RocanCoordinates extends RocanHUD {
	RocanSetting rgb_effect = addSetting(new String[] {"RGB", "CoordinatesRGB", "RGB effect."}, false);
	RocanSetting direction  = addSetting(new String[] {"Direction", "Direction", "Direction view."}, "XZ", new String[] {"XZ", "NSWE", "none"});

	private String coordinates;

	public RocanCoordinates() {
		super(new String[] {"Coordinates", "Coordinates", "Draw position player."}, Docking.LEFT_UP);
	}

	@Override
	public void onRenderHUD() {
		String x = String.format("%.1f", (double) (mc.player.posX));
		String y = String.format("%.1f", (double) (mc.player.posY));
		String z = String.format("%.1f", (double) (mc.player.posZ));

		float value = mc.world.getBiome(mc.player.getPosition()).getBiomeName().equals("Hell") == true ? 8 : 0.125f;

		String x_nether = String.format("%.1f", (Double) (mc.player.posX * value));
		String z_nether = String.format("%.1f", (Double) (mc.player.posZ * value));

		String dirc = "";

		if (direction.getString().equals("XZ")) {
			dirc = getFaceDirect(true, false);
		} else if (direction.getString().equals("NSWE")) {
			dirc = getFaceDirect(false, true);
		} else {
			dirc = "";
		}

		coordinates = dirc + "XYZ " + Rocan.getGrayColor() + x + ", " + y + ", " + z + Rocan.setDefaultColor() + " [" + Rocan.getGrayColor() + x_nether + ", " + z_nether + Rocan.setDefaultColor() + "]";

		if (rgb_effect.getBoolean()) {
			renderString(coordinates, 0, 0, rgb_r, rgb_g, rgb_b);
		} else {
			renderString(coordinates, 0, 0);
		}

		this.rect.setWidth(getStringWidth(coordinates));
		this.rect.setHeight(getStringHeight(coordinates));
	}

	public String getFaceDirect(boolean xz, boolean nswe) {
		EnumFacing enum_facing = mc.getRenderViewEntity().getHorizontalFacing();

		String value = "Invalid";

		String l = Rocan.setDefaultColor() + "[" + Rocan.getGrayColor();
		String r = Rocan.setDefaultColor() + "]";

		switch (enum_facing) {
			case NORTH: value = xz ? l + "-Z" + r : nswe ? l + "N" + r : "North " + l + "-Z" + r; break;
			case SOUTH: value = xz ? l + "+Z" + r : nswe ? l + "S" + r : "South " + l + "+Z" + r; break;
			case WEST: value  = xz ? l + "-X" + r : nswe ? l + "W" + r : "West " + l + "-X" + r; break;
			case EAST: value  = xz ? l + "+X" + r : nswe ? l + "E" + r : "East " + l + "+X" + r;
		}

		return value + " ";
	}
}