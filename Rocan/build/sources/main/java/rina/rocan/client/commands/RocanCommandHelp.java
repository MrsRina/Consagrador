package rina.rocan.client.commands;

// Command.
import rina.rocan.client.RocanCommand;

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
public class RocanCommandHelp extends RocanCommand {
	public RocanCommandHelp() {
		super("help", "Get util command client.");
	}

	@Override
	public boolean onRequested(String[] args) {
		String value_0 = null;

		if (verifyErrorArg(args, 0)) {
			RocanUtilClient.sendNotifyErrorClient("arguments are incorrects.");

			return true;
		}

		value_0 = args[0];

		RocanCommand command_requested = Rocan.getCommandManager().getCommandByCommand(value_0); 

		if (command_requested == null) {
			RocanUtilClient.sendNotifyErrorClient("Command does not exist.");

			return true;
		}

		RocanUtilClient.sendNotifyClient(command_requested.getDescription());

		return false;
	}
}