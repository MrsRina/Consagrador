package rina.rocan.client.modules.render;

// Client.
import rina.rocan.client.RocanSetting;
import rina.rocan.client.RocanModule;

// Rocan.
import rina.rocan.Rocan;

/**
 *
 * @author Rina!
 *
 * Created by Rina!
 * 17/08/2020. // 23:49 pm.
 *
 **/
public class RocanHUDRender extends RocanModule {
	RocanSetting string_color_red   = createSetting(new String[] {"String Red", "HUDStringRed", "Change color red."}, 255, 0, 255);
	RocanSetting string_color_blue  = createSetting(new String[] {"String Green", "HUDStringGreen", "Change color blue."}, 0, 0, 255);
	RocanSetting string_color_green = createSetting(new String[] {"String Blue", "HUDStringBlue", "Change color green."}, 0, 0, 255);

	public RocanHUDRender() {
		super(new String[] {"HUD", "HUD", "Enable for render HUD components."}, Category.ROCAN_RENDER);
	}

	@Override
	public void onUpdate() {
		Rocan.getModuleManager().syncHUD();
	}
}