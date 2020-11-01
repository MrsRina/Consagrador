package rina.rocan.client;

// Minecraft.
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraftforge.client.event.*;
import net.minecraft.client.Minecraft;

// Gson.
import com.google.gson.*;

// Java.
import java.util.*;
import java.io.*;

// OpenGL.
import org.lwjgl.input.Keyboard;

// Event.
import rina.rocan.event.render.RocanEventRender;

// Setting.
import rina.rocan.client.RocanSetting;

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
public class RocanModule {
	public String name;
	public String tag;
	public String description;

	public String info;

	private ArrayList<RocanSetting> setting_list = new ArrayList<>();

	private final Category category;

	public static final Minecraft mc = Minecraft.getMinecraft();

	private RocanSetting setting_module;

	private boolean show_hud_arraylist;

	private ICamera camera = new Frustum();

	public RocanModule(String[] details, Category category, boolean state) {
		this.name        = details[0];
		this.tag         = details[1];
		this.description = details[2];

		this.setting_module     = createSetting(new String[] {"Bind", this.tag + "Bind", "Key bind to module."}, -1, state);
		this.category           = category;
		this.show_hud_arraylist = true;

		// Empty info.
		this.info = "";
	}

	public RocanModule(String[] details, Category category) {
		this.name = details[0];
		this.tag = details[1];
		this.description = details[2];

		this.setting_module = createSetting(new String[] {"Bind", this.tag + "Bind", "Key bind to module."}, -1, false);
		this.category = category;
		this.show_hud_arraylist = true;

		// Empty info.
		this.info = "";
	}

	public void setState(boolean value) {
		if (this.setting_module.getBoolean() != value) {
			if (value) {
				setEnable();
			} else {
				setDisable();
			}
		}
	}

	public void toggle() {
		setState(!getState());
	}

	public void setEnable() {
		this.setting_module.setBoolean(true);

		onEnable();

		Rocan.getPomeloEventManager().addEventListener(this);
	}

	public void setDisable() {
		this.setting_module.setBoolean(false);

		onDisable();

		Rocan.getPomeloEventManager().removeEventListener(this);
	}

	public RocanSetting getSettingModule() {
		return this.setting_module;
	}

	public String getName() {
		return this.name;
	}

	public String getTag() {
		return this.tag;
	}

	public String getDescription() {
		return this.description;
	}

	public String getInfo() {
		return this.info;
	}

	public Category getCategory() {
		return this.category;
	}

	public ICamera getICamera() {
		return camera;
	}

	public String getKeyBindName() {
		if (this.setting_module.getInteger() == -1) {
			return "none";
		}

		return Keyboard.getKeyName(this.setting_module.getInteger()).toLowerCase();
	}

	public int getKeyBind() {
		return this.setting_module.getInteger();
	}

	public boolean getState() {
		return this.setting_module.getBoolean();
	}

	public boolean showInHUD() {
		return this.show_hud_arraylist;
	}

	public ArrayList<RocanSetting> getSettingList() {
		if (setting_list == null) {
			setting_list = new ArrayList<>();
		}

		return setting_list;
	}

	// Overrides.
	public void onEnable() {}
	public void onDisable() {}

	public void onUpdate() {}
	public void onRender() {}
	public void onRender(RocanEventRender event) {}

	public enum Category {
		ROCAN_COMBAT("Rocan Combat", "Combat"),
		ROCAN_MOVEMENT("Rocan Movement", "Movement"),
		ROCAN_EXPLOIT("Rocan Exploit", "Exploit"),
		ROCAN_RENDER("Rocan Render", "Render"),
		ROCAN_MISC("Rocan Misc", "Misc"),
		ROCAN_HUD("Rocan HUD", "HUD"),
		ROCAN_GUI("Rocan GUI", "GUI"),
		ROCAN_SYSTEM("Rocan System", "System");

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
			if (object_cache.get(settings.getTag()) == null) {
				continue;
			}

			JsonObject SETTING = object_cache.get(settings.getTag()).getAsJsonObject();

			if (SETTING.get("name") == null || SETTING.get("tag") == null) {
				continue;
			}

			if (SETTING.get("boolean") != null) {
				settings.setBoolean(SETTING.get("boolean").getAsBoolean());
			}

			if (SETTING.get("string") != null) {
				settings.setString(SETTING.get("string").getAsString());
			}

			if (SETTING.get("integer") != null) {
				settings.setInteger(SETTING.get("integer").getAsInt());
			}

			if (SETTING.get("double") != null) {
				settings.setDouble(SETTING.get("double").getAsDouble());
			}
		}
	}

	public void reloadModule() {
		if (this.setting_module.getBoolean()) {
			setEnable();
		} else {
			setDisable();
		}
	}

	public JsonObject getJsonObjectSettingList() {
		JsonObject MAIN_SETTING_LIST = new JsonObject();

		for (RocanSetting settings : setting_list) {
			JsonObject SETTING = new JsonObject();

			SETTING.add("name", new JsonPrimitive(settings.getName()));
			SETTING.add("tag", new JsonPrimitive(settings.getTag()));

			if ((Boolean) settings.getBoolean() != null) {
				SETTING.add("boolean", new JsonPrimitive(settings.getBoolean()));
			}

			if ((String) settings.getString() != null) {
				SETTING.add("string", new JsonPrimitive(settings.getString()));
			}

			if ((Integer) settings.getInteger() != null) {
				SETTING.add("integer", new JsonPrimitive(settings.getInteger()));
			}

			if ((Double) settings.getDouble() != null) {
				SETTING.add("double", new JsonPrimitive(settings.getDouble()));
			}

			MAIN_SETTING_LIST.add(settings.getTag(), SETTING);
		}

		return MAIN_SETTING_LIST;
	}

	protected void sentNotifyClientChat(String message) {
		RocanUtilClient.sendNotify(Rocan.getGrayColor() + tag + " " + Rocan.setDefaultColor() + message);
	}

	protected void addSetting(RocanSetting setting) {
		if (setting_list == null) {
			setting_list = new ArrayList<>();
		}

		setting_list.add(setting);

		Rocan.getSettingManager().addSetting(setting);
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

	protected RocanSetting createSetting(String[] details, int value, boolean state) {
		if (setting_list == null) {
			setting_list = new ArrayList<>();
		}

		Rocan.getSettingManager().addSetting(new RocanSetting(this, details, value, state));
		Rocan.getSettingManager().getSettingByTag(details[1]).setType(RocanSetting.SettingType.SETTING_MACRO);

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