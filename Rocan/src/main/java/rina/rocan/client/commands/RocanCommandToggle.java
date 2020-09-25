package rina.rocan.client.commands;

// Command.
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
public class RocanCommandToggle extends RocanCommand {
	public RocanCommandToggle() {
		super("t", "Turn on some module or off.");
	}

	@Override
	public boolean onRequested(String[] args) {
		String value_0 = "null";

		if (verifyIndex(args, 1)) {
			value_0 = args[1];
		}

		if (verifyIndex(args, 2)) {
			RocanUtilClient.sendNotifyErrorClient("The usage correct is t <module>");

			return true;
		}

		if (value_0.equals("null")) {
			RocanUtilClient.sendNotifyErrorClient("The usage correct is t <module>");

			return true;
		}

		RocanModule module_requested = Rocan.getModuleManager().getModuleByTag(value_0); 

		if (module_requested.getCategory() == RocanModule.Category.ROCAN_SYSTEM) {
			RocanUtilClient.sendNotifyErrorClient("Sorry you can't acess this system module.");

			return true;
		}

		if (module_requested == null) {
			RocanUtilClient.sendNotifyErrorClient("Module does not exist.");

			return true;
		}

		module_requested.toggle();

		RocanUtilClient.sendNotifyClient(module_requested.getName() + " has been toggled.");

		return true;
	}
}