package rina.rocan.client.modules.render;

// Java.
import java.util.*;

// Turok.
import rina.turok.TurokScreenUtil;
import rina.turok.TurokRect;

// Client.
import rina.rocan.client.RocanSetting;
import rina.rocan.client.RocanModule;
import rina.rocan.client.RocanHUD;

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
public class RocanHUDRender extends RocanModule {
	RocanSetting string_color_red   = createSetting(new String[] {"String Red", "StringRed", "Change color red."}, 255, 0, 255);
	RocanSetting string_color_blue  = createSetting(new String[] {"String Green", "StringGreen", "Change color blue."}, 0, 0, 255);
	RocanSetting string_color_green = createSetting(new String[] {"String Blue", "StringBlue", "Change color green."}, 0, 0, 255);

	ArrayList<RocanHUD> hud_list_left_up;
	ArrayList<RocanHUD> hud_list_left_down;
	ArrayList<RocanHUD> hud_list_right_up;
	ArrayList<RocanHUD> hud_list_right_down;

	public static TurokRect RECT_HUD_LEFT_UP    = new TurokRect("LEFTUP", 0, 0, 0, 0);
	public static TurokRect RECT_HUD_LEFT_DOWN  = new TurokRect("LEFTDOWN", 0, 0, 0, 0);
	public static TurokRect RECT_HUD_RIGHT_UP   = new TurokRect("RIGHTUP", 0, 0, 0, 0);
	public static TurokRect RECT_HUD_RIGHT_DOWN = new TurokRect("RIGHTDOWN", 0, 0, 0, 0);

	public RocanHUDRender() {
		super(new String[] {"HUD", "HUD", "Enable for render HUD components."}, Category.ROCAN_RENDER);
	
		this.hud_list_left_up    = new ArrayList<>();
		this.hud_list_left_down  = new ArrayList<>();
		this.hud_list_right_up   = new ArrayList<>();
		this.hud_list_right_down = new ArrayList<>();
	}

	@Override
	public void onUpdate() {
		// HUD seems not a module, but extend module, so I get the list HUD in module manager.
		for (RocanHUD huds : Rocan.getModuleManager().getHUDList()) {
			if (((RocanModule) huds).getState() && huds.getDocking() == RocanHUD.Docking.LEFT_UP && !isOnListLeftUp(huds)) {
				this.hud_list_left_up.add(huds);
			} else if (((RocanModule) huds).getState() && huds.getDocking() != RocanHUD.Docking.LEFT_UP && isOnListLeftUp(huds)) {
				this.hud_list_left_up.remove(huds);
			}
		}

		int left_up_save_y = 1;
		int high_width     = 1;

		for (RocanHUD huds : this.hud_list_left_up) {
			if (!huds.isJoinedToDockRect()) {
				continue;
			}

			huds.setX(1);
			huds.setY(left_up_save_y);

			left_up_save_y = huds.getY() + huds.getHeight() + 1;

			if (high_width >= huds.getWidth()) {
				high_width = huds.getWidth();
			}
		}

		RECT_HUD_LEFT_UP.setX(1);
		RECT_HUD_LEFT_UP.setY(1);
		RECT_HUD_LEFT_UP.setWidth(high_width);
		RECT_HUD_LEFT_UP.setHeight(left_up_save_y);
	}

	public boolean isOnListLeftUp(RocanHUD hud) {
		for (RocanHUD huds : this.hud_list_left_up) {
			if (huds.getTag().equals(hud.getTag())) {
				return true;
			}
		}

		return false;
	}

	public boolean isOnListLeftDown(RocanHUD hud) {
		for (RocanHUD huds : this.hud_list_left_down) {
			if (huds.getTag().equals(hud.getTag())) {
				return true;
			}
		}

		return false;
	}

	public boolean isOnListRightUp(RocanHUD hud) {
		for (RocanHUD huds : this.hud_list_right_up) {
			if (huds.getTag().equals(hud.getTag())) {
				return true;
			}
		}

		return false;
	}

	public boolean isOnListRightDown(RocanHUD hud) {
		for (RocanHUD huds : this.hud_list_right_down) {
			if (huds.getTag().equals(hud.getTag())) {
				return true;
			}
		}

		return false;
	}
}