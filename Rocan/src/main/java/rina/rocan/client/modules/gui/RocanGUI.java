package rina.rocan.client.modules.gui;

// Module.
import rina.rocan.client.RocanModule.Category;
import rina.rocan.client.RocanModule.Define;
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
@Define(name = "GUI", tag = "GUI", description = "GUI click.", category = Category.ROCAN_GUI)
public class RocanGUI extends RocanModule {
	boolean e;

	@Override
	public void onEnable() {
		mc.displayGuiScreen(Rocan.rocan_gui);

		e = true;
	}

	@Override
	public void onDisable() {
		mc.displayGuiScreen(null);
	}

	@Override
	public void onUpdate() {
		if (e) {
			mc.displayGuiScreen(Rocan.rocan_gui);

			e = false;
		}
	}
}