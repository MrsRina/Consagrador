package rina.rocan.gui.component;

// Java.
import java.util.*;

// GUI.
import rina.rocan.gui.widget.RocanWidget;
import rina.rocan.gui.frame.RocanFrame;
import rina.rocan.gui.RocanMainGUI;

// Client.
import rina.rocan.client.RocanSetting;
import rina.rocan.client.RocanModule;

// Constructor.
import rina.rocan.util.constructor.RocanConstructorTimer;

// Turok.
import rina.turok.TurokRenderGL;
import rina.turok.TurokString;
import rina.turok.TurokRect;

// Rocan.
import rina.rocan.Rocan;

/**
  *
  * @author Rina!
  *
  * Created by Rina!
  * 18/08/2020.
  *
  **/
public class RocanComponentModuleButton {
	private RocanFrame master;
	private RocanMainGUI absolute;

	private RocanModule module;

	private ArrayList<RocanWidget> setting_widget_list;

	private TurokRect rect;

	private int save_x;
	private int save_y;

	private int save_width;
	private int save_height;

	private boolean state_button;

	private boolean event_mouse_passing;
	private boolean event_mouse_clicked;

	private boolean event_has_waited;

	private RocanConstructorTimer tickness;

	public RocanComponentModuleButton(RocanFrame master, RocanModule module, int next_y) {
		this.master   = master;
		this.absolute = this.master.getMaster();

		this.setting_widget_list = new ArrayList<>();

		this.module = module;

		this.rect = new TurokRect(this.module.getName(), 0, 0);

		this.rect.setX(this.master.getX());
		this.rect.setY(next_y);

		this.rect.setWidth(this.master.getWidth());
		this.rect.setHeight(3 + TurokString.getStringHeight(this.rect.getTag(), true) + 3);

		this.save_x = 2;
		this.save_y = next_y;

		this.save_width  = 0;
		this.save_height = this.rect.getHeight() + 1;

		loadWidgets();

		resetAllEvent();

		this.state_button = false;

		this.tickness = new RocanConstructorTimer();
	}

	public void loadWidgets() {
		int size  = Rocan.getSettingManager().getSettingListByModule(this.module).size();
		int count = 0;

		for (RocanSetting settings : Rocan.getSettingManager().getSettingListByModule(this.module)) {
			if (settings.getType() == null) {
				continue;
			}

			if (settings.getType() == RocanSetting.SettingType.SETTING_BOOLEAN) {
				RocanComponentWidgetSettingBoolean widgets = new RocanComponentWidgetSettingBoolean(this, settings, this.save_height);

				this.setting_widget_list.add(widgets);

				count++;

				this.save_height += widgets.getHeight() + 1;
			} else if (settings.getType() == RocanSetting.SettingType.SETTING_STRING) {
				RocanComponentWidgetSettingString widgets = new RocanComponentWidgetSettingString(this, settings, this.save_height);

				this.setting_widget_list.add(widgets);

				count++;

				this.save_height += widgets.getHeight() + 1;
			} else if (settings.getType() == RocanSetting.SettingType.SETTING_MACRO && !(settings.getTag().equals(settings.getMaster().getTag() + "Bind"))) {
				RocanComponentWidgetSettingMacro widgets = new RocanComponentWidgetSettingMacro(this, settings, this.save_height);

				this.setting_widget_list.add(widgets);

				count++;

				this.save_height += widgets.getHeight() + 1;
			} else if (settings.getType() == RocanSetting.SettingType.SETTING_INTEGER || settings.getType() == RocanSetting.SettingType.SETTING_DOUBLE) {
				RocanComponentWidgetSettingSlider widgets = new RocanComponentWidgetSettingSlider(this, settings, this.save_height);

				this.setting_widget_list.add(widgets);

				count++;

				this.save_height += widgets.getHeight() + 1;
			} else if (settings.getType() == RocanSetting.SettingType.SETTING_LIST) {
				RocanComponentWidgetSettingList widgets = new RocanComponentWidgetSettingList(this, settings, this.save_height);

				this.setting_widget_list.add(widgets);

				count++;

				this.save_height += widgets.getHeight() + 1;
			}
		}

		RocanComponentWidgetSettingMacro widgets = new RocanComponentWidgetSettingMacro(this, Rocan.getSettingManager().getSettingByTag(this.module.getTag() + "Bind"), this.save_height);

		this.setting_widget_list.add(widgets);

		this.save_height += widgets.getHeight() + 1;
	}

	public void resetAllEvent() {
		this.event_mouse_passing = false;
		this.event_mouse_clicked = false;
	}

	public void resetAllAbsoluteEvent() {
		resetAllEvent();

		this.event_has_waited = false;

		for (RocanWidget widgets : this.setting_widget_list) {
			widgets.resetAllAbsoluteEvent();
		}
	}

	public void setX(int x) {
		this.rect.setX(x);
	}

	public void setY(int y) {
		this.rect.setY(y);
	}

	public void setSaveX(int x) {
		this.save_x = x;
	}

	public void setSaveY(int y) {
		this.save_y = y;
	}

	public void setWidth(int width) {
		this.rect.setWidth(width);
	}

	public void setHeight(int height) {
		this.rect.setHeight(height);
	}

	public void setSaveWidth(int width) {
		this.save_width = width;
	}

	public void setSaveHeight(int height) {
		this.save_height = height;
	}

	public void setMousePassing(boolean state) {
		this.event_mouse_passing = state;
	}

	public void setMouseClick(boolean state) {
		this.event_mouse_clicked = state;
	}

	public void setButtonOpen(boolean state) {
		this.state_button = state;
	}

	public TurokRect getRect() {
		return this.rect;
	}

	public RocanFrame getMaster() {
		return this.master;
	}	

	public String getName() {
		return this.rect.getTag();
	}

	public int getX() {
		return this.rect.getX();
	}

	public int getY() {
		return this.rect.getY();
	}

	public int getSaveX() {
		return this.save_x;
	}

	public int getSaveY() {
		return this.save_y;
	}

	public int getWidth() {
		return this.rect.getWidth();
	}

	public int getHeight() {
		return this.rect.getHeight();
	}

	public int getSaveWidth() {
		return this.save_width;
	}

	public int getSaveHeight() {
		return this.save_height;
	}

	public boolean isMousePassing() {
		return this.event_mouse_passing;
	}

	public boolean isMouseClicked() {
		return this.event_mouse_clicked;
	}

	public boolean isButtonOpen() {
		return this.state_button;
	}

	public void keyboard(char char_, int key) {
		for (RocanWidget widgets : this.setting_widget_list) {
			widgets.keyboard(char_, key);
		}
	}

	public void refreshFocus(int x, int y, int mouse) {
		for (RocanWidget widgets : this.setting_widget_list) {
			widgets.refreshFocus(x, y, mouse);
		}
	}

	public void click(int mouse) {
		if (isButtonOpen()) {
			for (RocanWidget widgets : this.setting_widget_list) {
				widgets.click(mouse);
			}
		}

		if (mouse == 0) {
			if (isMousePassing()) {
				setMouseClick(true);

				this.module.toggle();

				// Frame event.
				this.master.setFrameCancelDrag(true);
			}
		}

		if (mouse == 1) {
			if (isMousePassing()) {
				setButtonOpen(!isButtonOpen());

				this.master.resizeHeight();
			}
		}
	}

	public void release(int mouse) {
		for (RocanWidget widgets : this.setting_widget_list) {
			widgets.release(mouse);
		}

		if (mouse == 0) {
			setMouseClick(false);

			// Frame event.
			this.master.setFrameCancelDrag(false);
		}
	}

	public void render() {
		updateAction(this.absolute.getMouseX(), this.absolute.getMouseY());

		if (this.module.getState()) {
			TurokRenderGL.color(Rocan.getClientGUITheme().button_pressed_r, Rocan.getClientGUITheme().button_pressed_g, Rocan.getClientGUITheme().button_pressed_b, Rocan.getClientGUITheme().button_pressed_a);
			TurokRenderGL.drawSolidRect(this.rect);
		} else {
			TurokRenderGL.color(Rocan.getClientGUITheme().button_r, Rocan.getClientGUITheme().button_g, Rocan.getClientGUITheme().button_b, Rocan.getClientGUITheme().button_a);
			TurokRenderGL.drawSolidRect(this.rect);
		}

		if (isMousePassing()) {
			TurokRenderGL.color(Rocan.getClientGUITheme().button_pass_r, Rocan.getClientGUITheme().button_pass_g, Rocan.getClientGUITheme().button_pass_b, Rocan.getClientGUITheme().button_pass_a);
			TurokRenderGL.drawSolidRect(this.rect);
		}

		TurokString.renderString(this.rect.getTag(), this.rect.getX() + 1, this.rect.getY() + 3, Rocan.getClientGUITheme().button_name_r, Rocan.getClientGUITheme().button_name_g, Rocan.getClientGUITheme().button_name_b, Rocan.getClientGUITheme().shadow_font, Rocan.getClientGUITheme().smooth_font);

		if (isButtonOpen()) {
			TurokString.renderString("-", this.rect.getX() + this.rect.getWidth() - TurokString.getStringWidth("-", Rocan.getClientGUITheme().smooth_font) - 2, this.rect.getY() + 3, Rocan.getClientGUITheme().button_name_r, Rocan.getClientGUITheme().button_name_g, Rocan.getClientGUITheme().button_name_b, Rocan.getClientGUITheme().shadow_font, Rocan.getClientGUITheme().smooth_font);

			for (RocanWidget widgets : this.setting_widget_list) {
				widgets.render();
			}
		} else {
			TurokString.renderString("+", this.rect.getX() + this.rect.getWidth() - TurokString.getStringWidth("+", Rocan.getClientGUITheme().smooth_font) - 2, this.rect.getY() + 3, Rocan.getClientGUITheme().button_name_r, Rocan.getClientGUITheme().button_name_g, Rocan.getClientGUITheme().button_name_b, Rocan.getClientGUITheme().shadow_font, Rocan.getClientGUITheme().smooth_font);
		}
	}

	public void updateEvent(int x, int y) {
		if (this.rect.collide(x, y)) {
			setMousePassing(true);
		} else {
			setMousePassing(false);
		}
	}

	public void updateEventWidget() {
		for (RocanWidget widgets : this.setting_widget_list) {
			widgets.updateEvent(this.absolute.getMouseX(), this.absolute.getMouseY());
		}
	}

	public void resetAllEventWidget() {
		for (RocanWidget widgets : this.setting_widget_list) {
			widgets.resetAllEvent();
		}
	}

	public void updateAction(int x, int y) {
		this.rect.setX(this.master.getX() + this.save_x);
		this.rect.setY(this.master.getY() + this.save_y);

		this.rect.setWidth(this.master.getWidth() - this.save_x * 2);
		this.rect.setHeight(3 + TurokString.getStringHeight(this.rect.getTag(), true) + 3);
	}

	public void updateDescriptionListener() {
		if (isMousePassing()) {
			if (this.tickness.isPassedMS(500) && !this.event_has_waited) {
				this.event_has_waited = true;
			}
		} else {
			this.event_has_waited = false;

			this.tickness.reset();
		}

		if (this.event_has_waited) {
			this.absolute.renderStringMouse(this.module.getDescription());
		}

		// L:i:s:t:e:n.
		for (RocanWidget widgets : this.setting_widget_list) {
			if (isButtonOpen()) {
				widgets.updateDescriptionListener();
			}
		}
	}
}