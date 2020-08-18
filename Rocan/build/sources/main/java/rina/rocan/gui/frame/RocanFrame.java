package rina.rocan.gui.frame;

// GUI.
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
 * 17/08/2020.
 *
 **/
public class RocanFrame {
	private RocanMainGUI master;

	private TurokRect rect;

	private int move_x;
	private int move_y;

	private boolean event_mouse_click;
	private boolean event_mouse_passing;

	private boolean event_frame_dragging;
	private boolean event_frame_cancel_drag;

	public RocanFrame(RocanMainGUI master, RocanModule.Category category) {
		this.master = master;

		this.rect = new TurokRect(category.getName(), 0, 0);

		this.rect.width  = 100;
		this.rect.height = 3 + TurokString.getStringWidth(this.rect.getTag(), true) + 3;

		this.move_x = 0;
		this.move_y = 0;

		resetAllEvent();
	}

	public void resetAllEvent() {
		this.event_mouse_click       = false;
		this.event_mouse_passing     = false;
		this.event_frame_dragging    = false;
		this.event_frame_cancel_drag = false;
	}

	public void setX(int x) {
		this.rect.setX(x);
	}

	public void setY(int y) {
		this.rect.setY(y);
	}

	public void setMoveX(int x) {
		this.move_x = x;
	}

	public void setMoveY(int y) {
		this.move_y = y;
	}

	public void setWidth(int width) {
		this.rect.setWidth(width);
	}

	public void setHeight(int height) {
		this.rect.setHeight(height);
	}

	public void setMouseClick(boolean state) {
		this.event_mouse_click = state;
	}

	public void setMousePassing(boolean state) {
		this.event_mouse_passing = state;
	}

	public void setFrameDragging(boolean state) {
		this.event_frame_dragging = state;
	}

	public void setFrameCancelDrag(boolean state) {
		this.event_frame_cancel_drag = state;
	}

	public RocanMainGUI getMaster() {
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

	public int getMoveX() {
		return this.move_x;
	}

	public int getMoveY() {
		return this.move_y;
	}

	public int getWidth() {
		return this.rect.getWidth();
	}

	public int getHeight() {
		return this.rect.getHeight();
	}

	public boolean isMouseClick() {
		return this.event_mouse_click;
	}

	public boolean isMousePassing() {
		return this.event_mouse_passing;
	}

	public boolean isFrameDragging() {
		return this.event_frame_dragging;
	}

	public boolean isFrameCancelingClick() {
		return this.event_frame_cancel_drag;
	}

	public boolean verifyFrame(int x, int y) {
		boolean can = false;

		if (isMousePassing()) {
			can = true;
		}

		return can;
	}

	public void click(int mouse) {}

	public void release(int mouse) {}

	public void render() {
		updateEvent(this.master.getMouseX(), this.master.getMouseY());
		updateActions(this.master.getMouseX(), this.master.getMouseY());

		TurokRenderGL.color(0, 0, 0, 100);
		TurokRenderGL.drawRoundedRect(this.rect, 2);
	}

	public void updateEvent(int x, int y) {
		if (this.rect.collide(x, y)) {
			setMousePassing(true);
		} else {
			setMousePassing(false);
		}

		if (isMouseClick()) {
			setFrameDragging(true);
		} else {
			setFrameDragging(false);
		}
	}

	public void updateActions(int x, int y) {
		if (isFrameDragging()) {
			this.rect.setX(x - getMoveX());
			this.rect.setY(y - getMoveY());
		}
	}
}