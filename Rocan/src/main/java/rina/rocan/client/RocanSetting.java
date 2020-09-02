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
  * 16/08/2020.
  *
  **/
public class RocanSetting<T> {
	private RocanModule master;

	private String name;
	private String tag;
	private String description;

	private SettingType type;

	// All values.
	private boolean value_boolean;

	private String value_string;

	private int value_integer;

	private double value_double;

	private String[] values;

	private double min, max;

	public RocanSetting(RocanModule master, String[] details, boolean value) {
		this.master = master;

		this.name        = details[0];
		this.tag         = details[1];
		this.description = details[2];

		this.value_boolean = value;
	}

	public RocanSetting(RocanModule master, String[] details, String value) {
		this.master = master;

		this.name        = details[0];
		this.tag         = details[1];
		this.description = details[2];

		this.value_string = value;
	}

	public RocanSetting(RocanModule master, String[] details, int value, boolean state) {
		this.master = master;

		this.name        = details[0];
		this.tag         = details[1];
		this.description = details[2];

		this.value_integer = value;
		this.value_boolean = state;
	}

	public RocanSetting(RocanModule master, String[] details, int value, int min, int max) {
		this.master = master;

		this.name        = details[0];
		this.tag         = details[1];
		this.description = details[2];

		this.value_integer = value;

		this.min = (double) min;
		this.max = (double) max;
	}

	public RocanSetting(RocanModule master, String[] details, double value, double min, double max) {
		this.master = master;

		this.name        = details[0];
		this.tag         = details[1];
		this.description = details[2];

		this.value_double = value;

		this.min = min;
		this.max = max;
	}

	public RocanSetting(RocanModule master, String[] details, String value, String[] values) {
		this.master = master;

		this.name        = details[0];
		this.tag         = details[1];
		this.description = details[2];

		this.value_string = value;
		this.values       = values;
	}

	public void setBoolean(boolean value) {
		this.value_boolean = value;
	}

	public void setString(String value) {
		this.value_string = value;
	}

	public void setInteger(int value) {
		this.value_integer = value;
	}

	public void setDouble(double value) {
		this.value_double = value;
	}

	public void setValues(String[] values) {
		this.values = values;
	}

	public void setType(SettingType type) {
		this.type = type;
	}

	public RocanModule getMaster() {
		return master;
	}

	public String getName() {
		return name;
	}

	public String getTag() {
		return tag;
	}

	public String getDescription() {
		return description;
	}

	public SettingType getType() {
		return type;
	}

	public boolean getBoolean() {
		return value_boolean;
	}

	public String getString() {
		return value_string;
	}

	public int getInteger() {
		return value_integer;
	}

	public double getDouble() {
		return value_double;
	}

	public String[] getList() {
		return values;
	}

	public double getMin() {
		return min;
	}

	public double getMax() {
		return max;
	}

	public enum SettingType {
		SETTING_BOOLEAN,
		SETTING_STRING,
		SETTING_MACRO,
		SETTING_INTEGER,
		SETTING_DOUBLE,
		SETTING_LIST,
		SETTING_GHOST;
	}
}