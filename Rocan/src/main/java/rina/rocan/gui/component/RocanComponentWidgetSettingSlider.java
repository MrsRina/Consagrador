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

// Turok.
import rina.turok.TurokRenderGL;
import rina.turok.TurokString;
import rina.turok.TurokRect;

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

	private int save_width;
	private int save_height;

	private boolean event_mouse_passing;
	private boolean event_mouse_clicked;

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
	}

	@Override
	public void resetAllEvent() {
		this.event_mouse_passing = false;
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

		int width_calculed = 0;

		String value = "";

		if (this.setting.getType() == RocanSetting.SettingType.SETTING_INTEGER) {
			width_calculed = ((int) ((this.rect.getWidth()) * (this.setting.getInteger() - this.setting.getMin()) / (this.setting.getMax() - this.setting.getMin())));

			value = Integer.toString(this.setting.getInteger());
		} else if (this.setting.getType() == RocanSetting.SettingType.SETTING_DOUBLE) {
			width_calculed = ((int) ((this.rect.getWidth()) * (this.setting.getDouble() - this.setting.getMin()) / (this.setting.getMax() - this.setting.getMin())));

			value = Double.toString(this.setting.getDouble());
		}

		if (isMousePassing()) {
			TurokRenderGL.color(255, 255, 255, 190);

			TurokRenderGL.color(255, 0, 0, 190);
			TurokRenderGL.drawSolidRect(this.rect.getX(), this.rect.getY(), this.rect.getX() + width_calculed, this.rect.getY() + this.rect.getHeight());
		} else {
			TurokRenderGL.color(255, 0, 0, 190);
			TurokRenderGL.drawSolidRect(this.rect.getX(), this.rect.getY(), this.rect.getX() + width_calculed, this.rect.getY() + this.rect.getHeight());
		}

		TurokString.renderString(this.rect.getTag(), this.rect.getX() + 1, this.rect.getY() + 3, 255, 255, 255, false, true);
		TurokString.renderString(value, this.rect.getX() + this.rect.getWidth() - TurokString.getStringWidth(value, true) - 2, this.rect.getY() + 3, 255, 255, 255, false, true);
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

		if (isMouseClicked()) {
			double mouse = Math.min(this.rect.getWidth(), Math.max(0, x - this.rect.getX()));

			if (mouse != 0) {
				if (this.setting.getType() == RocanSetting.SettingType.SETTING_INTEGER) {
					this.setting.setInteger((int) round((mouse / this.rect.getWidth()) * (this.setting.getMax() - this.setting.getMin() + this.setting.getMin())));
				} else if (this.setting.getType() == RocanSetting.SettingType.SETTING_DOUBLE) {
					this.setting.setDouble(round((mouse / this.rect.getWidth()) * (this.setting.getMax() - this.setting.getMin() + this.setting.getMin())));
				}
			} else {
				if (this.setting.getType() == RocanSetting.SettingType.SETTING_INTEGER) {
					this.setting.setInteger((int) this.setting.getMin());
				} else if (this.setting.getType() == RocanSetting.SettingType.SETTING_DOUBLE) {
					this.setting.setDouble(this.setting.getMin());
				}
			}
		}
	}

	public double round(double var_double) {
		BigDecimal decimal = new BigDecimal(var_double);

		decimal = decimal.setScale(2, RoundingMode.HALF_UP);

		return decimal.doubleValue();
	}
}