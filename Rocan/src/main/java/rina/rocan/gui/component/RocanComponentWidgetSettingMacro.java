package rina.rocan.gui.component;

// OpenGL.
import org.lwjgl.input.Keyboard;

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
  * 21/08/2020.
  *
  **/                                                  // No-no-no, why this isn't a component no extend...
public class RocanComponentWidgetSettingMacro extends RocanWidget {
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

	private boolean event_waiting;

	private String waiting_animation;

	private int waiting_tick_animation;

	private boolean event_has_waited;

	private RocanConstructorTimer tickness;

	public RocanComponentWidgetSettingMacro(RocanComponentModuleButton master, RocanSetting setting, int next_y) {
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

		this.event_waiting = false;

		this.waiting_animation      = ".";
		this.waiting_tick_animation = 0;

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

		this.event_waiting    = false;
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

	public void setWaiting(boolean state) {
		this.event_waiting = state;
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

	public boolean isWaiting() {
		return this.event_waiting;
	}

	public void keyboard(char char_, int key) {
		if (isWaiting()) {
			switch (key) {
				case Keyboard.KEY_ESCAPE : {
					setWaiting(false);

					break;
				}

				case Keyboard.KEY_DELETE : {
					this.setting.setInteger(-1);

					setWaiting(false);

					this.absolute.setCancelToCloseGUI(false);

					break;
				}

				default : {
					this.setting.setInteger(key);

					setWaiting(false);

					this.absolute.setCancelToCloseGUI(false);

					break;
				}
			}
		}
	}

	public void refreshFocus(int x, int y, int mouse) {
		if (mouse == 0) {
			this.absolute.setCancelToCloseGUI(false);

			if (isWaiting()) {
				setWaiting(false);
			}
		}

		if (mouse == 1) {
			this.absolute.setCancelToCloseGUI(false);

			if (isWaiting()) {
				setWaiting(false);
			}
		}
	}

	public void click(int mouse) {
		if (mouse == 0) {
			if (isMousePassing()) {
				this.master.getMaster().setFrameCancelDrag(true);

				setWaiting(true);
				setMouseClick(true);

				this.absolute.setCancelToCloseGUI(true);
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

		if (this.setting.getBoolean()) {
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

		String key = this.setting.getInteger() == -1 ? "none" : Keyboard.getKeyName(this.setting.getInteger()).toLowerCase();

		if (isWaiting()) {
			this.waiting_tick_animation++;

			if (this.waiting_tick_animation >= 30) {
				this.waiting_animation = ".";
			}

			if (this.waiting_tick_animation >= 60) {
				this.waiting_animation = "..";
			}

			if (this.waiting_tick_animation >= 90) {
				this.waiting_animation = "...";

				this.waiting_tick_animation = 0;
			}

			TurokString.renderString("<" + this.waiting_animation + ">", this.rect.getX() + this.rect.getWidth() - TurokString.getStringWidth("<" + this.waiting_animation + ">", true) - 2, this.rect.getY() + 3, Rocan.getClientGUITheme().button_name_r, Rocan.getClientGUITheme().button_name_g, Rocan.getClientGUITheme().button_name_b, Rocan.getClientGUITheme().shadow_font, Rocan.getClientGUITheme().smooth_font);
		} else {
			TurokString.renderString("<" + key + ">", this.rect.getX() + this.rect.getWidth() - TurokString.getStringWidth("<" + key + ">", Rocan.getClientGUITheme().smooth_font) - 2, this.rect.getY() + 3, Rocan.getClientGUITheme().button_name_r, Rocan.getClientGUITheme().button_name_g, Rocan.getClientGUITheme().button_name_b, Rocan.getClientGUITheme().shadow_font, Rocan.getClientGUITheme().smooth_font);
		}
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
	}

	@Override
	public void updateDescriptionListener() {
		if (isMousePassing() && !isWaiting()) {
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