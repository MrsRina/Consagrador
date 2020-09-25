package rina.rocan.gui.component;

// Java.
import java.math.*;
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
  * 19/08/2020.
  *
  **/                                                  // No-no-no, why this isn't a component no extend...
public class RocanComponentWidgetSettingSlider extends RocanWidget {
	private RocanComponentModuleButton master;
	private RocanMainGUI absolute;

	private RocanSetting setting;

	private TurokRect rect;

	private int save_x;
	private int save_y;

	// Just save_width can be a true float.
	private float save_width;
	private int   save_height;

	private boolean event_mouse_passing;
	private boolean event_mouse_clicked;

	private boolean event_has_waited;

	private RocanConstructorTimer tickness;

	public RocanComponentWidgetSettingSlider(RocanComponentModuleButton master, RocanSetting setting, int next_y) {
		this.master   = master;
		this.absolute = this.master.getMaster().getMaster();

		this.setting = setting;

		this.rect = new TurokRect(this.setting.getName(), 0, 0);

		this.rect.setX(this.master.getX());
		this.rect.setY(next_y);

		this.save_x = 0;
		this.save_y = next_y;

		this.rect.setWidth(this.master.getWidth());
		this.rect.setHeight(3 + TurokString.getStringHeight(this.rect.getTag(), true) + 3);
	
		resetAllEvent();

		this.tickness = new RocanConstructorTimer();
	}

	@Override
	public void resetAllEvent() {
		this.event_mouse_passing = false;
	}

	@Override
	public void resetAllAbsoluteEvent() {
		resetAllEvent();

		this.event_mouse_clicked = false;
		this.event_has_waited    = false;
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

	public void setMousePassing(boolean state) {
		this.event_mouse_passing = state;
	}

	public void setMouseClick(boolean state) {
		this.event_mouse_clicked = state;
	}

	public TurokRect getRect() {
		return this.rect;
	}

	public RocanComponentModuleButton getMaster() {
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

	public boolean isMousePassing() {
		return this.event_mouse_passing;
	}

	public boolean isMouseClicked() {
		return this.event_mouse_clicked;
	}

	public void refreshFocus(int x, int y, int mouse) {}

	public void click(int mouse) {
		if (mouse == 0) {
			if (isMousePassing()) {
				this.master.getMaster().setFrameCancelDrag(true);

				setMouseClick(true);
			}
		}
	}

	public void release(int mouse) {
		if (mouse == 0) {
			this.master.getMaster().setFrameCancelDrag(false);

			setMouseClick(false);
		}
	}

	public void render() {
		updateAction(this.absolute.getMouseX(), this.absolute.getMouseY());

		String value = this.setting.getType() == RocanSetting.SettingType.SETTING_INTEGER ? Integer.toString(this.setting.getInteger()) : Double.toString(this.setting.getDouble());

		TurokRenderGL.color(Rocan.getClientGUITheme().button_pressed_r, Rocan.getClientGUITheme().button_pressed_g, Rocan.getClientGUITheme().button_pressed_b, Rocan.getClientGUITheme().button_pressed_a);
		TurokRenderGL.drawSolidRect(this.rect.getX(), this.rect.getY(), this.rect.getX() + this.save_width, this.rect.getY() + this.rect.getHeight());

		if (isMousePassing()) {
			TurokRenderGL.color(Rocan.getClientGUITheme().button_pass_r, Rocan.getClientGUITheme().button_pass_g, Rocan.getClientGUITheme().button_pass_b, Rocan.getClientGUITheme().button_pass_a);
			TurokRenderGL.drawSolidRect(this.rect);
		}

		TurokString.renderString(this.rect.getTag(), this.rect.getX() + 1, this.rect.getY() + 3, Rocan.getClientGUITheme().button_name_r, Rocan.getClientGUITheme().button_name_g, Rocan.getClientGUITheme().button_name_b, Rocan.getClientGUITheme().shadow_font, Rocan.getClientGUITheme().smooth_font);
		TurokString.renderString(value, this.rect.getX() + this.rect.getWidth() - TurokString.getStringWidth(value, Rocan.getClientGUITheme().smooth_font) - 2, this.rect.getY() + 3, Rocan.getClientGUITheme().button_name_r, Rocan.getClientGUITheme().button_name_g, Rocan.getClientGUITheme().button_name_b, Rocan.getClientGUITheme().shadow_font, Rocan.getClientGUITheme().smooth_font);
	}

	public void updateEvent(int x, int y) {
		if (this.rect.collide(x, y)) {
			setMousePassing(true);
		} else {
			setMousePassing(false);
		}
	}

	public void updateAction(int x, int y) {
		this.rect.setX(this.master.getX());
		this.rect.setY(this.master.getY() + this.save_y);

		this.rect.setWidth(this.master.getWidth());
		this.rect.setHeight(3 + TurokString.getStringHeight(this.rect.getTag(), true) + 3);

		double mouse = Math.min(this.rect.getWidth(), Math.max(0, x - this.rect.getX()));

		Number min = this.setting.getType() == RocanSetting.SettingType.SETTING_INTEGER ? (int) this.setting.getMin() : this.setting.getMin();
		Number max = this.setting.getType() == RocanSetting.SettingType.SETTING_INTEGER ? (int) this.setting.getMax() : this.setting.getMax();

		Number value = this.setting.getType() == RocanSetting.SettingType.SETTING_INTEGER ? this.setting.getInteger() : this.setting.getDouble();

		this.save_width = (float) ((this.rect.getWidth()) * ((double) value - (double) min) / ((double) max - (double) min));

		if (isMouseClicked()) {
			if (mouse == 0) {
				if (this.setting.getType() == RocanSetting.SettingType.SETTING_INTEGER) {
					this.setting.setInteger((int) this.setting.getMin());
				} else if (this.setting.getType() == RocanSetting.SettingType.SETTING_DOUBLE) {
					this.setting.setDouble(this.setting.getMin());
				}
			} else {
				if (this.setting.getType() == RocanSetting.SettingType.SETTING_INTEGER) {
					int round_value = (int) round(((mouse / this.rect.getWidth()) * ((double) max - (double) min) + (double) min));

					this.setting.setInteger(round_value);
				} else if (this.setting.getType() == RocanSetting.SettingType.SETTING_DOUBLE) {
					double round_value = round(((mouse / this.rect.getWidth()) * ((double) max - (double) min) + (double) min));

					this.setting.setDouble(round_value);
				}
			}
		}
	}

	public double round(double var_double) {
		BigDecimal decimal = new BigDecimal(var_double);

		decimal = decimal.setScale(2, RoundingMode.HALF_UP);

		return decimal.doubleValue();
	}

	@Override
	public void updateDescriptionListener() {
		if (isMousePassing() && !isMouseClicked()) {
			if (this.tickness.isPassedMS(500) && !this.event_has_waited) {
				this.event_has_waited = true;

				this.tickness.reset();
			}
		} else {
			this.event_has_waited = false;

			this.tickness.reset();
		}

		if (this.event_has_waited) {
			this.absolute.renderStringMouse(this.setting.getDescription());
		}
	}
}