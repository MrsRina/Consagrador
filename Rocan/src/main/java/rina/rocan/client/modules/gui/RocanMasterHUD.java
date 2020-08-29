package rina.rocan.client.modules.gui;

// Client.
import rina.rocan.client.RocanModule.Category;
import rina.rocan.client.RocanModule.Define;
import rina.rocan.client.RocanSetting;
import rina.rocan.client.RocanModule;

// Util.
import rina.rocan.util.RocanUtilClient;

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
@Define(name = "HUD", tag = "HUD", description = "Draw HUD.", category = Category.ROCAN_GUI)
public class RocanMasterHUD extends RocanModule {
	RocanSetting string_color_red   = createSetting(new String[] {"String Red", "StringRed", "Change color red."}, 255, 0, 255);
	RocanSetting string_color_blue  = createSetting(new String[] {"String Green", "StringGreen", "Change color blue."}, 0, 0, 255);
	RocanSetting string_color_green = createSetting(new String[] {"String Blue", "StringBlue", "Change color green."}, 0, 0, 255);
}