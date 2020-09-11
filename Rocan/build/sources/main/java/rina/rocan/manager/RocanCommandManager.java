package rina.rocan.manager;

// Folders.
import rina.rocan.client.commands.*;

// Rocan command.
import rina.rocan.client.RocanCommand;

// Java.
import java.util.*;

/**
 *
 * @author Rina!
 *
 * Created by Rina!
 * 15/08/2020.
 *
 **/
public class RocanCommandManager {
	ArrayList<RocanCommand> command_list;

	String prefix;

	public RocanCommandManager(String default_prefix) {
		this.command_list = new ArrayList<>();

		this.prefix = default_prefix;

		addCommand(new RocanCommandHelp());
		addCommand(new RocanCommandPrefix());
		addCommand(new RocanCommandToggle());
		addCommand(new RocanCommandSet());
		addCommand(new RocanCommandClient());
		addCommand(new RocanCommandFriend());
		addCommand(new RocanCommandEnemy());
	}

	public void addCommand(RocanCommand command) {
		this.command_list.add(command);
	}

	public void setPrefix(String value) {
		this.prefix = value;
	}

	public String[] getArgs(String message) {
		String[] args = {};

		if (message.startsWith(getPrefix())) {
			args = message.replaceFirst(getPrefix(), "").split(" ");
		}

		return args;
	}

	public boolean hasPrefix(String message) {
		return message.startsWith(getPrefix());
	}

	public ArrayList<RocanCommand> getCommandList() {
		return this.command_list;
	}

	public RocanCommand getCommandByCommand(String command) {
		for (RocanCommand commands : getCommandList()) {
			if (commands.getCommand().equals(command)) {
				return commands;
			}
		}

		return null;
	}

	public String getPrefix() {
		return this.prefix;
	}
}