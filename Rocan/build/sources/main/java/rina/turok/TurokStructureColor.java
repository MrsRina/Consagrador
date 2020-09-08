package rina.turok;

// Rocan.
import rina.rocan.Rocan;

/**
  *
  * @author Rina!
  *
  * Created by Rina!
  * 26/08/2020.
  *
  **/
public class TurokStructureColor {
	public int frame_name_r;
	public int frame_name_g;
	public int frame_name_b;

	public int frame_r;
	public int frame_g;
	public int frame_b;
	public int frame_a;

	public int button_name_r;
	public int button_name_g;
	public int button_name_b;

	public int button_r;
	public int button_g;
	public int button_b;
	public int button_a;

	public int button_pressed_r;
	public int button_pressed_g;
	public int button_pressed_b;
	public int button_pressed_a;

	public int button_pass_r;
	public int button_pass_g;
	public int button_pass_b;
	public int button_pass_a;

	public boolean smooth_font;
	public boolean shadow_font;

	public TurokStructureColor() {
		this.frame_name_r = 255;
		this.frame_name_g = 255;
		this.frame_name_b = 255;

		this.frame_r = 255;
		this.frame_g = 255;
		this.frame_b = 255;
		this.frame_a = 255;

		this.button_name_r = 255;
		this.button_name_g = 255;
		this.button_name_b = 255;

		this.button_r = 255;
		this.button_g = 255;
		this.button_b = 255;
		this.button_a = 255;

		this.button_pressed_r = 255;
		this.button_pressed_g = 255;
		this.button_pressed_b = 255;
		this.button_pressed_a = 255;

		this.button_pass_r = 255;
		this.button_pass_g = 255;
		this.button_pass_b = 255;
		this.button_pass_a = 255;

		this.smooth_font = true;
		this.shadow_font = false;
	}

	public void refresh() {
		this.frame_name_r = Rocan.getSettingManager().getSettingByModuleAndTag("GUI", "FrameNameRed").getInteger();
		this.frame_name_g = Rocan.getSettingManager().getSettingByModuleAndTag("GUI", "FrameNameGreen").getInteger();
		this.frame_name_b = Rocan.getSettingManager().getSettingByModuleAndTag("GUI", "FrameNameBlue").getInteger();

		this.frame_r = Rocan.getSettingManager().getSettingByModuleAndTag("GUI", "FrameRed").getInteger();
		this.frame_g = Rocan.getSettingManager().getSettingByModuleAndTag("GUI", "FrameGreen").getInteger();
		this.frame_b = Rocan.getSettingManager().getSettingByModuleAndTag("GUI", "FrameBlue").getInteger();
		this.frame_a = Rocan.getSettingManager().getSettingByModuleAndTag("GUI", "FrameAlpha").getInteger();

		this.button_name_r = Rocan.getSettingManager().getSettingByModuleAndTag("GUI", "ButtonNameRed").getInteger();
		this.button_name_g = Rocan.getSettingManager().getSettingByModuleAndTag("GUI", "ButtonNameGreen").getInteger();
		this.button_name_b = Rocan.getSettingManager().getSettingByModuleAndTag("GUI", "ButtonNameBlue").getInteger();

		this.button_r = Rocan.getSettingManager().getSettingByModuleAndTag("GUI", "ButtonRed").getInteger();
		this.button_g = Rocan.getSettingManager().getSettingByModuleAndTag("GUI", "ButtonGreen").getInteger();
		this.button_b = Rocan.getSettingManager().getSettingByModuleAndTag("GUI", "ButtonBlue").getInteger();
		this.button_a = Rocan.getSettingManager().getSettingByModuleAndTag("GUI", "ButtonAlpha").getInteger();

		this.button_pressed_r = Rocan.getSettingManager().getSettingByModuleAndTag("GUI", "ButtonPressedRed").getInteger();
		this.button_pressed_g = Rocan.getSettingManager().getSettingByModuleAndTag("GUI", "ButtonPressedGreen").getInteger();
		this.button_pressed_b = Rocan.getSettingManager().getSettingByModuleAndTag("GUI", "ButtonPressedBlue").getInteger();
		this.button_pressed_a = Rocan.getSettingManager().getSettingByModuleAndTag("GUI", "ButtonPressedAlpha").getInteger();

		this.button_pass_r = Rocan.getSettingManager().getSettingByModuleAndTag("GUI", "ButtonPressedRed").getInteger();
		this.button_pass_g = Rocan.getSettingManager().getSettingByModuleAndTag("GUI", "ButtonPressedGreen").getInteger();
		this.button_pass_b = Rocan.getSettingManager().getSettingByModuleAndTag("GUI", "ButtonPressedBlue").getInteger();
		this.button_pass_a = 50;

		this.smooth_font = Rocan.getSettingManager().getSettingByModuleAndTag("GUI", "SmoothFont").getBoolean();
		this.shadow_font = Rocan.getSettingManager().getSettingByModuleAndTag("GUI", "ShadowFont").getBoolean();
	}
}