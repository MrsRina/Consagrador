package rina.rocan.client.modules.gui;

// Client.
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
 * 29/08/2020.
 *
 **/
public class RocanHUDEditor extends RocanModule {
	boolean show_gui;

	public RocanHUDEditor() {
		super(new String[] {"HUD Editor", "HUDEditor", "Enable to move or change HUDs."}, Category.ROCAN_GUI);
	}

	@Override
	public void onEnable() {
		show_gui = true;
	}

	@Override
	public void onDisable() {
		show_gui = true;
	}

	@Override
	public void onUpdate() {
		if (show_gui) {
			mc.displayGuiScreen(Rocan.getClientGUI());

			show_gui = false;
		}
	}
}