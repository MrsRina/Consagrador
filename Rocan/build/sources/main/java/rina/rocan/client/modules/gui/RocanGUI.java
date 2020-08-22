package rina.rocan.client.modules.gui;

// Module.
import rina.rocan.client.RocanModule.Category;
import rina.rocan.client.RocanModule.Define;
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
@Define(name = "GUI", tag = "GUI", description = "GUI click.", category = Category.ROCAN_GUI)
public class RocanGUI extends RocanModule {
	boolean show_gui = true;

	@Override
	public void onEnable() {
		show_gui = true;

		RocanUtilClient.sendNotifyClient("true");
	}

	@Override
	public void onDisable() {
		show_gui = true;

		RocanUtilClient.sendNotifyClient("false");
	}

	@Override
	public void onUpdate() {
		if (show_gui) {
			mc.displayGuiScreen(Rocan.rocan_gui);

			show_gui = false;
		}
	}
}