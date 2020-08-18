package rina.rocan.manager;

// Java.
import java.util.*;

// Client.
import rina.rocan.client.RocanSetting;
import rina.rocan.client.RocanModule;

/**
  *
  * @author Rina!
  *
  * Created by Rina!
  * 15/08/2020.
  *
  **/
public class RocanSettingManager {
	ArrayList<RocanSetting> setting_list;

	public RocanSettingManager() {
		this.setting_list = new ArrayList<>();
	}

	public void addSetting(RocanSetting setting) {
		this.setting_list.add(setting);
	}

	public ArrayList<RocanSetting> getSettingList() {
		return this.setting_list;
	}

	public RocanSetting getSettingByTag(String tag) {
		for (RocanSetting settings : getSettingList()) {
			if (settings.getTag().equalsIgnoreCase(tag)) {
				return settings;
			}
		}

		return null;
	}

	public ArrayList<RocanSetting> getSettingListByModule(RocanModule module) {
		ArrayList<RocanSetting> settings_requested = null;

		for (RocanSetting settings : getSettingList()) {
			if (settings.getMaster().equals(module)) {
				settings_requested.add(settings);
			}
		}

		return settings_requested;
	}
}