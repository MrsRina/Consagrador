package rina.rocan.gui.component;

// Minecraft.
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.client.gui.GuiTextField;

// OpenGL.
import org.lwjgl.input.Keyboard;

// GUI.
import rina.rocan.gui.widget.RocanWidget;
import rina.rocan.gui.frame.RocanFrame;
import rina.rocan.gui.RocanMainGUI;

// Client.
import rina.rocan.client.RocanSetting;
import rina.rocan.client.RocanModule;

// Util.
import rina.rocan.util.RocanUtilMinecraftHelper;

// Turok.
import rina.turok.TurokRenderGL;
import rina.turok.TurokString;
import rina.turok.TurokCache;
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
public class RocanComponentWidgetSettingString extends RocanWidget {
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

	private boolean event_widget_able_to_type;

	private boolean maximum_length;

	private GuiTextField entry;

	private String _string_entry;

	private int tick_to_entry;

	public RocanComponentWidgetSettingString(RocanComponentModuleButton master, RocanSetting setting, int next_y) {
		this.master   = master;
		this.absolute = this.master.getMaster().getMaster();

		this.setting = setting;

		this.rect = new TurokRect(this.setting.getName(), 0, 0);

		this.rect.setX(this.master.getX());
		this.rect.setY(next_y);

		this.save_x = 0;
		this.save_y = next_y;

		this.event_widget_able_to_type = false;

		this.maximum_length = false;

		this.rect.setWidth(this.master.getWidth());
		this.rect.setHeight(3 + TurokString.getStringHeight(this.rect.getTag(), true) + 3);
	
		resetAllEvent();

		this.entry = new GuiTextField(0, RocanUtilMinecraftHelper.getMinecraft().fontRenderer, this.rect.getX(), this.rect.getY(), this.rect.getWidth(), this.rect.getHeight());
		this.entry.setText(this.setting.getString());

		this._string_entry = "";
		this.tick_to_entry = 0;
	}

	@Override
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

	public void setAbleToType(boolean state) {
		this.event_widget_able_to_type = state;
	}

	public void setInMaximumLength(boolean state) {
		this.maximum_length = state;
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

	public boolean isAbleToType() {
		return this.event_widget_able_to_type;
	}

	public boolean isMaximumLength() {
		return this.maximum_length;
	}

	public void keyboard(char char_, int key) {
		if (isAbleToType()) {
			this.entry.textboxKeyTyped(char_, key);
		}
	}

	public void refreshFocus(int x, int y, int mouse) {
		if (mouse == 0) {
			if (isAbleToType()) {
				setAbleToType(false);
			}
		}
	}

	public void click(int mouse) {
		this.entry.mouseClicked(this.absolute.getMouseX(), this.absolute.getMouseY(), mouse);

		if (mouse == 0) {
			setAbleToType(false);

			if (isMousePassing()) {
				setAbleToType(true);

				this.master.getMaster().setFrameCancelDrag(true);

				setMouseClick(true);
				setAbleToType(true);
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

		if (isAbleToType()) {
			if (isMousePassing()) {
				TurokRenderGL.color(Rocan.getClientGUITheme().button_pressed_pass_r, Rocan.getClientGUITheme().button_pressed_pass_g, Rocan.getClientGUITheme().button_pressed_pass_b, Rocan.getClientGUITheme().button_pressed_pass_a);
				TurokRenderGL.drawSolidRect(this.rect);
			} else {
				TurokRenderGL.color(Rocan.getClientGUITheme().button_pressed_r, Rocan.getClientGUITheme().button_pressed_g, Rocan.getClientGUITheme().button_pressed_b, Rocan.getClientGUITheme().button_pressed_a);
				TurokRenderGL.drawSolidRect(this.rect);
			}

			TurokString.renderString(this.entry.getText() + this._string_entry, this.rect.getX() + 1, this.rect.getY() + 3, Rocan.getClientGUITheme().button_name_r, Rocan.getClientGUITheme().button_name_g, Rocan.getClientGUITheme().button_name_b, Rocan.getClientGUITheme().shadow_font, Rocan.getClientGUITheme().smooth_font);
		} else {
			if (isMousePassing()) {
				TurokRenderGL.color(Rocan.getClientGUITheme().button_pass_r, Rocan.getClientGUITheme().button_pass_g, Rocan.getClientGUITheme().button_pass_b, Rocan.getClientGUITheme().button_pass_a);
				TurokRenderGL.drawSolidRect(this.rect);

				TurokString.renderString(this.entry.getText(), this.rect.getX() + 1, this.rect.getY() + 3, Rocan.getClientGUITheme().button_name_r, Rocan.getClientGUITheme().button_name_g, Rocan.getClientGUITheme().button_name_b, Rocan.getClientGUITheme().shadow_font, Rocan.getClientGUITheme().smooth_font);
			} else {
				TurokRenderGL.color(Rocan.getClientGUITheme().button_r, Rocan.getClientGUITheme().button_g, Rocan.getClientGUITheme().button_b, Rocan.getClientGUITheme().button_a);
				TurokRenderGL.drawSolidRect(this.rect);

				TurokString.renderString(this.rect.getTag(), this.rect.getX() + 1, this.rect.getY() + 3, Rocan.getClientGUITheme().button_name_r, Rocan.getClientGUITheme().button_name_g, Rocan.getClientGUITheme().button_name_b, Rocan.getClientGUITheme().shadow_font, Rocan.getClientGUITheme().smooth_font);
			}
		}

		if (isAbleToType()) {
			this.tick_to_entry++;

			if (this.tick_to_entry >= 30) {
				this._string_entry = "_";
			}

			if (this.tick_to_entry >= 60) {
				this._string_entry = "";

				this.tick_to_entry = 0;
			}
		}

		this.setting.setString(this.entry.getText());
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

		this.entry.x      = this.rect.getX() + 1;
		this.entry.y      = this.rect.getY() + 1;
		this.entry.width  = this.rect.getWidth() - 2;
		this.entry.height = this.rect.getHeight() - 2;
	}
}