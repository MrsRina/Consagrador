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
  * 25/08/2020.
  *
  **/                                                  // No-no-no, why this isn't a component no extend...
public class RocanComponentWidgetSettingList extends RocanWidget {
	private RocanComponentModuleButton master;
	private RocanMainGUI absolute;

	private RocanSetting setting;

	private TurokRect rect;

	private int index;

	private int save_x;
	private int save_y;

	private int save_width;
	private int save_height;

	private boolean event_mouse_passing;
	private boolean event_mouse_clicked;

	private boolean event_started;

	private boolean event_has_waited;
	
	private RocanConstructorTimer tickness;

	public RocanComponentWidgetSettingList(RocanComponentModuleButton master, RocanSetting setting, int next_y) {
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

		this.index = 0;

		resetAllEvent();

		this.event_started = true;

		this.tickness = new RocanConstructorTimer();
	}

	@Override
	public void resetAllEvent() {
		this.event_mouse_passing = false;
		this.event_mouse_clicked = false;
	}

	@Override
	public void resetAllAbsoluteEvent() {
		resetAllEvent();

		this.event_has_waited = false;
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

	public void setStarted(boolean state) {
		this.event_started = state;
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

	public boolean isStarted() {
		return this.event_started;
	}

	public void refreshFocus(int x, int y, int mouse) {}

	public void click(int mouse) {
		if (mouse == 0) {
			if (isMousePassing()) {
				this.master.getMaster().setFrameCancelDrag(true);

				setMouseClick(true);

				if (this.index >= (this.setting.getList().size() - 1)) {
					this.index = 0;
				} else {
					this.index++;
				}
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

		TurokRenderGL.color(Rocan.getClientGUITheme().button_r, Rocan.getClientGUITheme().button_g, Rocan.getClientGUITheme().button_b, Rocan.getClientGUITheme().button_a);
		TurokRenderGL.drawSolidRect(this.rect);

		if (isMousePassing()) {
			TurokRenderGL.color(Rocan.getClientGUITheme().button_pass_r, Rocan.getClientGUITheme().button_pass_g, Rocan.getClientGUITheme().button_pass_b, Rocan.getClientGUITheme().button_pass_a);
			TurokRenderGL.drawSolidRect(this.rect);
		}

		TurokString.renderString(this.rect.getTag() + " " + this.setting.getString(), this.rect.getX() + 1, this.rect.getY() + 3, Rocan.getClientGUITheme().button_name_r, Rocan.getClientGUITheme().button_name_g, Rocan.getClientGUITheme().button_name_b, Rocan.getClientGUITheme().shadow_font, Rocan.getClientGUITheme().smooth_font);
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
	
		if (isStarted()) {
			this.index = this.setting.getList().indexOf(this.setting.getString()) == -1 ? 0 : this.setting.getList().indexOf(this.setting.getString());

			// miiss.
			setStarted(false);
		} else {
			this.setting.setString(((ArrayList<String>) this.setting.getList()).get(this.index));
		}
	}

	@Override
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
			this.absolute.renderStringMouse(this.setting.getDescription());
		}
	}
}