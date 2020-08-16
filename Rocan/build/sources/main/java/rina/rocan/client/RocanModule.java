package rina.rocan.client;

// Minecraft.
import net.minecraft.client.Minecraft;

// Java.
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;
import java.util.*;

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
		ROCAN_DEV("DEV", "RocanDev"),
		ROCAN_EXPLOIT("Exploit", "RocanExploit");

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

	protected RocanSetting createSetting(String[] details, boolean value) {
		RocanSetting setting = new RocanSetting(this, details, value);

		setting.setType(RocanSetting.SettingType.SETTING_BOOLEAN);

		Rocan.getSettingManager().addSetting(setting);

		return setting;
	}

	protected RocanSetting createSetting(String[] details, String value) {
		RocanSetting setting = new RocanSetting(this, details, value);

		setting.setType(RocanSetting.SettingType.SETTING_STRING);

		Rocan.getSettingManager().addSetting(setting);

		return setting;
	}

	protected RocanSetting createSetting(String[] details, int value, int min, int max) {
		RocanSetting setting = new RocanSetting(this, details, value, min, max);

		setting.setType(RocanSetting.SettingType.SETTING_INTEGER);

		Rocan.getSettingManager().addSetting(setting);

		return setting;
	}

	protected RocanSetting createSetting(String[] details, double value, double min, double max) {
		RocanSetting setting = new RocanSetting(this, details, value, min, max);

		setting.setType(RocanSetting.SettingType.SETTING_DOUBLE);

		Rocan.getSettingManager().addSetting(setting);

		return setting;
	}

	protected RocanSetting createSetting(String[] details, String value, String[] values) {
		RocanSetting setting = new RocanSetting(this, details, value, values);

		setting.setType(RocanSetting.SettingType.SETTING_LIST);

		Rocan.getSettingManager().addSetting(setting);

		return setting;
	}

	protected ArrayList<String> createStringList(String... list) {
		ArrayList<String> list_requested = new ArrayList<>();

		for (String strings : list) {
			list_requested.add(strings);
		}

		return list_requested;
	} 
}