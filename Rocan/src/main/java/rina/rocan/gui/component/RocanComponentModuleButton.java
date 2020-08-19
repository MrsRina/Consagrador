package rina.rocan.gui.component;

// GUI.
import rina.rocan.gui.frame.RocanFrame;
import rina.rocan.gui.RocanMainGUI;

// Client.
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
  * 18/08/2020.
  *
  **/
public class RocanComponentModuleButton {
	private RocanFrame master;
	private RocanMainGUI absolute;

	private TurokRect rect;

	private int save_x;
	private int save_y;

	private boolean state_button;

	private boolean event_mouse_passing;
	private boolean event_mouse_clicked;

	public RocanComponentModuleButton(RocanFrame master, RocanModule module, int next_y) {
		this.master   = master;
		this.absolute = this.master.getMaster();

		this.rect = new TurokRect(module.getName(), 0, 0);

		this.rect.x = this.master.getX();
		this.rect.y = next_y;

		this.save_x = 2;
		this.save_y = next_y;

		this.rect.width  = this.master.getWidth();
		this.rect.height = 3 + TurokString.getStringHeight(this.rect.getTag(), true) + 3;
	
		resetAllEvent();

		this.state_button = false;
	}

	public void resetAllEvent() {
		this.event_mouse_passing = false;
		this.event_mouse_clicked = false;
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

	public boolean isMousePassing() {
		return this.event_mouse_passing;
	}

	public boolean isMouseClicked() {
		return this.event_mouse_clicked;
	}

	public boolean isButtonOpen() {
		return this.state_button;
	}

	public void click(int mouse) {
		if (mouse == 0) {
			if (isMousePassing()) {
				setMouseClick(true);

				if (!isButtonOpen()) {
					setButtonOpen(true);
				} else {
					setButtonOpen(false);
				}
			}
		}
	}

	public void release(int mouse) {
		if (mouse == 0) {
			setMouseClick(false);
		}
	}

	public void render() {
		updateAction(this.absolute.getMouseX(), this.absolute.getMouseY());

		if (isMousePassing()) {
			TurokRenderGL.color(255, 255, 255, 190);
			TurokRenderGL.drawSolidRect(this.rect);

			TurokString.renderString(this.rect.getTag(), this.rect.getX() + 1, this.rect.getY() + 3, 255, 255, 255, false, true);
		} else {
			TurokRenderGL.color(190, 190, 190, 190);
			TurokRenderGL.drawSolidRect(this.rect);

			TurokString.renderString(this.rect.getTag(), this.rect.getX() + 1, this.rect.getY() + 3, 255, 255, 255, false, true);
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
		this.rect.setX(this.master.getX() + this.save_x);
		this.rect.setY(this.master.getY() + this.save_y);
		this.rect.setWidth(this.master.getWidth() - this.save_x * 2);
	}
}