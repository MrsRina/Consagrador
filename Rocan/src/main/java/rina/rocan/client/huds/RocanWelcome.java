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
 * 30/08/2020.
 *
 **/
public class RocanWelcome extends RocanHUD {
	RocanSetting rgb_effect = addSetting(new String[] {"RGB", "WelcomeRGB", "RGB effect."}, false);

	public RocanWelcome() {
		super(new String[] {"Welcome", "Welcome", "Pretty welcome message to user!."}, Docking.LEFT_UP);
	}

	@Override
	public void onRenderHUD() {
		String welcome = "Welcome " + Rocan.getGrayColor() + mc.player.getName() + Rocan.setDefaultColor() + " to " + Rocan.getClientName() + Rocan.getGrayColor() + " v" + Rocan.getClientVersion();

		if (rgb_effect.getBoolean()) {
			renderString(welcome, 0, 0, rgb_r, rgb_g, rgb_b);
		} else {
			renderString(welcome, 0, 0);
		}

		this.rect.setWidth(getStringWidth(welcome));
		this.rect.setHeight(getStringHeight(welcome));
	}
}