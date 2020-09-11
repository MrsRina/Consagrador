package rina.rocan.client.commands;

// Command.
import rina.rocan.client.RocanSetting;
import rina.rocan.client.RocanCommand;
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
 * 15/08/2020.
 *
 **/
public class RocanCommandSet extends RocanCommand {
	public RocanCommandSet() {
		super("set", "To change some module setting.");
	}

	@Override
	public boolean onRequested(String[] args) {
		String value_0 = "null";
		String value_1 = "null";
		String value_2 = "null";

		if (verifyIndex(args, 1)) {
			value_0 = args[1];
		}

		if (verifyIndex(args, 2)) {
			value_1 = args[2];
		}

		if (verifyIndex(args, 3)) {
			value_2 = args[3];
		}

		if (verifyIndex(args, 4)) {
			RocanUtilClient.sendNotifyErrorClient("The correct usage is set <module> <setting> <value>");

			return true;
		}

		if (value_0.equals("null")) {
			RocanUtilClient.sendNotifyErrorClient("The correct usage is set <module> <setting> <value>");

			return true;
		}

		if (value_1.equals("null")) {
			RocanUtilClient.sendNotifyErrorClient("The correct usage is set <module> <setting> <value>");

			return true;
		}

		if (value_2.equals("null")) {
			RocanUtilClient.sendNotifyErrorClient("The correct usage is set <module> <setting> <value>");

			return true;
		}

		RocanModule module_requested = Rocan.getModuleManager().getModuleByTag(value_0); 

		if (module_requested == null) {
			RocanUtilClient.sendNotifyErrorClient("Module does not exist.");

			return true;
		}

		RocanSetting setting_requested = Rocan.getSettingManager().getSettingByTag(module_requested.getTag() + value_1);

		if (setting_requested == null) {
			RocanUtilClient.sendNotifyErrorClient("Setting does not exist.");

			return true;
		}

		if (setting_requested.getType() == RocanSetting.SettingType.SETTING_BOOLEAN) {
			try {
				Boolean.parseBoolean(value_2);
			} catch (Exception exc) {
				RocanUtilClient.sendNotifyErrorClient("Incorrect value.");

				return true;
			}

			setting_requested.setBoolean(Boolean.parseBoolean(value_2));
		
			RocanUtilClient.sendNotifyClient(setting_requested.getName() + " value to " + setting_requested.getBoolean());
		} else if (setting_requested.getType() == RocanSetting.SettingType.SETTING_STRING) {
			setting_requested.setString(value_2);

			RocanUtilClient.sendNotifyClient(setting_requested.getName() + " value to " + setting_requested.getString());
		} else if (setting_requested.getType() == RocanSetting.SettingType.SETTING_INTEGER) {
			try {
				Integer.parseInt(value_2);
			} catch (Exception exc) {
				RocanUtilClient.sendNotifyErrorClient("Incorrect value.");
			}

			setting_requested.setInteger(Integer.parseInt(value_2));
		
			RocanUtilClient.sendNotifyClient(setting_requested.getName() + " value to " + setting_requested.getInteger());
		} else if (setting_requested.getType() == RocanSetting.SettingType.SETTING_DOUBLE) {
			try {
				Double.parseDouble(value_2);
			} catch (Exception exc) {
				RocanUtilClient.sendNotifyErrorClient("Incorrect value.");
			}

			setting_requested.setDouble(Double.parseDouble(value_2));

			RocanUtilClient.sendNotifyClient(setting_requested.getName() + " value to " + setting_requested.getDouble());
		} else if (setting_requested.getType() == RocanSetting.SettingType.SETTING_LIST) {
			if (setting_requested.getList().indexOf(value_2) != -1) {
				setting_requested.setString(value_2);
			
				RocanUtilClient.sendNotifyClient(setting_requested.getName() + " value to " + setting_requested.getString());
			} else {
				RocanUtilClient.sendNotifyErrorClient("This value not exist.");
			}
		}

		return true;
	}
}