package rina.rocan.client;

// Minecraft.
import net.minecraft.client.Minecraft;

// Java.
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;
import java.awt.*;

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
public class RocanCommand {
	private String command;
	private String description;

	public static final Minecraft mc = Minecraft.getMinecraft();

	public RocanCommand(String command, String description) {
		this.command     = command;
		this.description = description;
	}

	public String getCommand() {
		return this.command;
	}

	public String getDescription() {
		return this.description;
	}

	public boolean onRequested(String[] args) {
		return false;
	}

	public boolean verifyIndex(String[] args, int index) {
		if (args.length > index) {
			return true;
		}

		return false;
	}
}