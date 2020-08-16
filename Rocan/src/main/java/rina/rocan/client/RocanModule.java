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
public class RocanModule {
	// Get value annotations.
	private final String name        = getAnnotation().name();
	private final String tag         = getAnnotation().tag();
	private final String description = getAnnotation().description();

	private final Category category = getAnnotation().category();

	private final Minecraft mc = Minecraft.getMinecraft();

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
		ROCAN_DEV("DEV", "RocanDev");

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
}