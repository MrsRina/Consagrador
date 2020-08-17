package rina.rocan.client;

// Minecraft.
import net.minecraft.client.Minecraft;

// Gson.
import com.google.gson.*;

// Java.
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;
import java.util.*;
import java.io.*;

// Setting.
import rina.rocan.client.RocanSetting;

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
public class RocanModule {
	// Get value annotations.
	private final String name        = getAnnotation().name();
	private final String tag         = getAnnotation().tag();
	private final String description = getAnnotation().description();

	private ArrayList<RocanSetting> setting_list = new ArrayList<>();

	private final Category category = getAnnotation().category();

	public final Minecraft mc = Minecraft.getMinecraft();

	private int key_bind;

	private boolean state_module;
	private boolean show_hud_arraylist;

	public RocanModule() {
		this.key_bind = 0;

		this.show_hud_arraylist = true;
		this.state_module       = false;
	}

	public void setState(boolean value) {
		if (value != this.state_module) {
			if (value) {
				setEnable();
			} else {
				setDisable();
			}
		}
	}

	public void setKeyBind(int value) {
		this.key_bind = value;
	}

	public void toggle() {
		setState(!getState());
	}

	public void setEnable() {
		this.state_module = true;

		onEnable();

		Rocan.getPomeloEventManager().addEventListener(this);
	}

	public void setDisable() {
		this.state_module = false;

		onDisable();

		Rocan.getPomeloEventManager().removeEventListener(this);
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

	public Category getCategory() {
		return category;
	}

	public int getKeyBind() {
		return this.key_bind;
	}

	public boolean getState() {
		return this.state_module;
	}

	// Overrides.
	protected void onEnable() {}
	protected void onDisable() {}

	public void onUpdate() {}

	// Annotation details.
	public Define getAnnotation() {
		if (getClass().isAnnotationPresent(Define.class)) {
			return getClass().getAnnotation(Define.class);
		}

		return null;
	}

	// Interface to annotation.
	@Retention(RetentionPolicy.RUNTIME)
	public @interface Define {
		String name();
		String tag();
		String description() default "No description.";

		RocanModule.Category category();
	}

	public enum Category {
		ROCAN_DEV("Rocan Dev", "Dev"),
		ROCAN_EXPLOIT("Rocan Exploit", "Exploit");

		String name;
		String tag;

		Category(String name, String tag) {
			this.name = name;
			this.tag  = tag;
		}

		public String getName() {
			return this.name;
		}

		public String getTag() {
			return this.tag;
		}
	}

	public void loadSettingListFromJsonObject(JsonObject object_cache) {
		for (RocanSetting settings : setting_list) {
			JsonObject SETTING = object_cache.get(settings.getTag()).getAsJsonObject();

			if (SETTING.get("Name") == null || SETTING.get("Tag") == null || SETTING.get("Type") == null) {
				continue;
			}

			if (SETTING.get("Boolean") != null) {
				settings.setBoolean(SETTING.get("Boolean").getAsBoolean());
			} else if (SETTING.get("String") != null) {
				settings.setString(SETTING.get("String").getAsString());
			} else if (SETTING.get("Integer") != null) {
				settings.setInteger(SETTING.get("Integer").getAsInt());
			} else if (SETTING.get("Double") != null) {
				settings.setDouble(SETTING.get("Double").getAsDouble());
			}
		}
	}

	public JsonObject getJsonObjectSettingList() {
		JsonObject MAIN_SETTING_LIST = new JsonObject();

		for (RocanSetting settings : setting_list) {
			JsonObject SETTING = new JsonObject();

			SETTING.add("Name", new JsonPrimitive(settings.getName()));
			SETTING.add("Tag", new JsonPrimitive(settings.getTag()));
			SETTING.add("Type", new JsonPrimitive(settings.getType().name().replace("SETTING_", "")));

			if (settings.getType() == RocanSetting.SettingType.SETTING_BOOLEAN) {
				SETTING.add("Boolean", new JsonPrimitive(settings.getBoolean()));
			} else if (settings.getType() == RocanSetting.SettingType.SETTING_STRING) {
				SETTING.add("String", new JsonPrimitive(settings.getString()));
			} else if (settings.getType() == RocanSetting.SettingType.SETTING_INTEGER) {
				SETTING.add("Integer", new JsonPrimitive(settings.getInteger()));
			} else if (settings.getType() == RocanSetting.SettingType.SETTING_DOUBLE) {
				SETTING.add("Double", new JsonPrimitive(settings.getDouble()));
			} else if (settings.getType() == RocanSetting.SettingType.SETTING_LIST) {
				SETTING.add("String", new JsonPrimitive(settings.getString()));
			}

			MAIN_SETTING_LIST.add(settings.getTag(), SETTING);
		}

		return MAIN_SETTING_LIST;
	}

	protected RocanSetting createSetting(String[] details, boolean value) {
		if (setting_list == null) {
			setting_list = new ArrayList<>();
		}

		Rocan.getSettingManager().addSetting(new RocanSetting(this, details, value));
		Rocan.getSettingManager().getSettingByTag(details[1]).setType(RocanSetting.SettingType.SETTING_BOOLEAN);

		setting_list.add(Rocan.getSettingManager().getSettingByTag(details[1]));

		return Rocan.getSettingManager().getSettingByTag(details[1]);
	}

	protected RocanSetting createSetting(String[] details, String value) {
		if (setting_list == null) {
			setting_list = new ArrayList<>();
		}

		Rocan.getSettingManager().addSetting(new RocanSetting(this, details, value));
		Rocan.getSettingManager().getSettingByTag(details[1]).setType(RocanSetting.SettingType.SETTING_STRING);

		setting_list.add(Rocan.getSettingManager().getSettingByTag(details[1]));

		return Rocan.getSettingManager().getSettingByTag(details[1]);
	}

	protected RocanSetting createSetting(String[] details, int value, int min, int max) {
		if (setting_list == null) {
			setting_list = new ArrayList<>();
		}

		Rocan.getSettingManager().addSetting(new RocanSetting(this, details, value, min, max));
		Rocan.getSettingManager().getSettingByTag(details[1]).setType(RocanSetting.SettingType.SETTING_INTEGER);

		setting_list.add(Rocan.getSettingManager().getSettingByTag(details[1]));

		return Rocan.getSettingManager().getSettingByTag(details[1]);
	}

	protected RocanSetting createSetting(String[] details, double value, double min, double max) {
		if (setting_list == null) {
			setting_list = new ArrayList<>();
		}

		Rocan.getSettingManager().addSetting(new RocanSetting(this, details, value, min, max));
		Rocan.getSettingManager().getSettingByTag(details[1]).setType(RocanSetting.SettingType.SETTING_DOUBLE);

		setting_list.add(Rocan.getSettingManager().getSettingByTag(details[1]));

		return Rocan.getSettingManager().getSettingByTag(details[1]);
	}

	protected RocanSetting createSetting(String[] details, String value, String[] values) {
		if (setting_list == null) {
			setting_list = new ArrayList<>();
		}

		Rocan.getSettingManager().addSetting(new RocanSetting(this, details, value, values));
		Rocan.getSettingManager().getSettingByTag(details[1]).setType(RocanSetting.SettingType.SETTING_LIST);

		setting_list.add(Rocan.getSettingManager().getSettingByTag(details[1]));

		return Rocan.getSettingManager().getSettingByTag(details[1]);
	}

	protected ArrayList<String> createStringList(String... list) {
		ArrayList<String> list_requested = new ArrayList<>();

		for (String strings : list) {
			list_requested.add(strings);
		}

		return list_requested;
	}
}