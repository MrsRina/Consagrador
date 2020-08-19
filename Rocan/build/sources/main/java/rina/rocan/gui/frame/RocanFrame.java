package rina.rocan.gui.frame;

// Java.
import java.util.*;

// GUI.
import rina.rocan.gui.component.RocanComponentModuleButton;
import rina.rocan.gui.RocanMainGUI;

// Client.
import rina.rocan.client.RocanModule;

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
 * 17/08/2020.
 *
 **/
public class RocanFrame {
	private RocanMainGUI master;

	private RocanModule.Category category;

	private TurokRect rect;

	private ArrayList<RocanComponentModuleButton> component_module_button_list;

	private int move_x;
	private int move_y;

	private int save_width;
	private int save_height;

	private boolean event_mouse_click;
	private boolean event_mouse_passing;

	private boolean event_frame_dragging;
	private boolean event_frame_cancel_drag;

	public RocanFrame(RocanMainGUI master, RocanModule.Category category) {
		this.master = master;

		this.category = category;

		this.rect = new TurokRect(this.category.getName(), 0, 0);

		this.component_module_button_list = new ArrayList<>();

		this.rect.width  = 100;
		this.rect.height = 2 + TurokString.getStringHeight(this.rect.getTag(), true) + 2;

		this.move_x = 0;
		this.move_y = 0;

		this.save_width  = 0;
		this.save_height = this.rect.height + 2;

		resetAllEvent();
		loadWidgets();
	}

	public void resetAllEvent() {
		this.event_mouse_click       = false;
		this.event_mouse_passing     = false;
		this.event_frame_dragging    = false;
		this.event_frame_cancel_drag = false;
	}

	public void loadWidgets() {
		int size  = Rocan.getModuleManager().getModuleListByCategory(this.category).size();
		int count = 0;

		for (RocanModule modules : Rocan.getModuleManager().getModuleListByCategory(this.category)) {
			RocanComponentModuleButton module_button = new RocanComponentModuleButton(this, modules, this.save_height);

			module_button.setY(this.save_height + module_button.getHeight());

			count++;

			if (count >= size) {
				this.rect.height = this.save_height + module_button.getHeight() + 2;
				this.save_height = module_button.getY() + module_button.getHeight() + 2;
			} else {
				this.rect.height = this.save_height + module_button.getHeight();
				this.save_height = module_button.getY() + 1;
			}

			this.component_module_button_list.add(module_button);
		}
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

	public TurokRect getRect() {
		return this.rect;
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
		TurokRenderGL.drawRoundedRect(this.rect, 1);

		TurokString.renderString(this.rect.getTag(), this.rect.getX() + (this.rect.getWidth() / 2) - TurokString.getStringWidth(this.rect.getTag(), true) / 2, this.rect.getY() + 3, 255, 255, 255, false, true);

		for (RocanComponentModuleButton module_buttons : this.component_module_button_list) {
			module_buttons.render();
		}
	}

	public void refreshFrame() {
		for (RocanComponentModuleButton module_buttons : this.component_module_button_list) {
			module_buttons.updateEvent(this.master.getMouseX(), this.master.getMouseY());
		}
	}

	public void resetFrame() {
		for (RocanComponentModuleButton module_buttons : this.component_module_button_list) {
			module_buttons.resetAllEvent();
		}
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