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
 * 17/08/2020. // 23:49 pm.
 *
 **/
public class RocanGUI extends RocanModule {
	boolean show_gui = true;

	RocanSetting smooth_font = createSetting(new String[] {"Smooth Font", "SmoothFont", "Enable custom font renderer."}, true);
	RocanSetting shadow_font = createSetting(new String[] {"Shadow Font", "ShadowFont", "Shadow in font."}, false);

	RocanSetting frame_name_r = createSetting(new String[] {"Frame Name Red", "FrameNameRed", "Change color red."}, 255, 0, 255);
	RocanSetting frame_name_g = createSetting(new String[] {"Frame Name Green", "FrameNameGreen", "Change color green."}, 255, 0, 255);
	RocanSetting frame_name_b = createSetting(new String[] {"Frame Name Blue", "FrameNameBlue", "Change color blue."}, 255, 0, 255);

	RocanSetting frame_r = createSetting(new String[] {"Red", "FrameRed", "Change color red."}, 0, 0, 255);
	RocanSetting frame_g = createSetting(new String[] {"Green", "FrameGreen", "Change color green."}, 0, 0, 255);
	RocanSetting frame_b = createSetting(new String[] {"Blue", "FrameBlue", "Change color blue."}, 0, 0, 255);
	RocanSetting frame_a = createSetting(new String[] {"Alpha", "FrameAlpha", "Change color alpha."}, 190, 0, 255);

	RocanSetting button_name_r = createSetting(new String[] {"Button Name Red", "ButtonNameRed", "Change color red."}, 255, 0, 255);
	RocanSetting button_name_g = createSetting(new String[] {"Button Name Green", "ButtonNameGreen", "Change color green."}, 255, 0, 255);
	RocanSetting button_name_b = createSetting(new String[] {"Button Name Blue", "ButtonNameBlue", "Change color blue."}, 255, 0, 255);

	RocanSetting button_r = createSetting(new String[] {"Red", "ButtonRed", "Change color red."}, 190, 0, 255);
	RocanSetting button_g = createSetting(new String[] {"Green", "ButtonGreen", "Change color green."}, 190, 0, 255);
	RocanSetting button_b = createSetting(new String[] {"Blue", "ButtonBlue", "Change color blue."}, 190, 0, 255);
	RocanSetting button_a = createSetting(new String[] {"Alpha", "ButtonAlpha", "Change color alpha."}, 190, 0, 255);

	RocanSetting button_pressed_r = createSetting(new String[] {"Pressed Red", "ButtonPressedRed", "Change color red."}, 190, 0, 255);
	RocanSetting button_pressed_g = createSetting(new String[] {"Pressed Green", "ButtonPressedGreen", "Change color green."}, 0, 0, 255);
	RocanSetting button_pressed_b = createSetting(new String[] {"Pressed Blue", "ButtonPressedBlue", "Change color blue."}, 0, 0, 255);
	RocanSetting button_pressed_a = createSetting(new String[] {"Pressed Alpha", "ButtonPressedAlpha", "Change color alpha."}, 190, 0, 255);

	public RocanGUI() {
		super(new String[] {"GUI", "GUI", "Draw GUI."}, Category.ROCAN_GUI);
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