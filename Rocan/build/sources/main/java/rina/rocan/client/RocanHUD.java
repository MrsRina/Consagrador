package rina.rocan.client;

// Minecraft.
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.Minecraft;

// Turok.
import rina.turok.TurokScreenUtil;
import rina.turok.TurokRenderGL;
import rina.turok.TurokString;
import rina.turok.TurokRect;

// Client.
import rina.rocan.client.RocanModule;

// Util.
import rina.rocan.util.RocanUtilMinecraftHelper;
import rina.rocan.util.RocanUtilClient;

// Rocan.
import rina.rocan.Rocan;

/**
 *
 * @author Rina!
 *
 * Created by Rina!
 * 28/08/2020.
 *
 **/
public class RocanHUD extends RocanModule {
	private RocanSetting setting_smooth;
	private RocanSetting setting_shadow;

	private RocanSetting setting_pos_x;
	private RocanSetting setting_pos_y;

	private RocanSetting setting_dock;

	public TurokRect rect;

	public Docking dock;

	public int hud_r;
	public int hud_g;
	public int hud_b;

	public int rgb_r;
	public int rgb_g;
	public int rgb_b;

	public int move_x;
	public int move_y;

	public int screen_width;
	public int screen_height;

	private boolean event_mouse_click;
	private boolean event_mouse_passing;

	private boolean event_hud_dragging;

	private boolean event_joined_to_dock_rect;

	private boolean event_started;

	public static final Minecraft mc = RocanUtilMinecraftHelper.getMinecraft();

	public RocanHUD(String[] details) {
		super(new String[] {details[0], "HUD" + details[1], details[2]}, Category.ROCAN_HUD);

		this.dock = Docking.LEFT_UP;

		initializeComponentHUD(details);
	}

	public RocanHUD(String[] details, Docking dock) {
		super(new String[] {details[0], "HUD" + details[1], details[2]}, Category.ROCAN_HUD);

		this.dock = dock;

		initializeComponentHUD(details);
	}

	public void initializeComponentHUD(String[] details) {
		this.rect = new TurokRect(this.name, 0, 0);

		this.setting_smooth = createSetting(new String[] {"Smooth", this.tag + "Smooth", "Draw smooth font for HUD."}, true);
		this.setting_shadow = createSetting(new String[] {"Shadow", this.tag + "Shadow", "Enable shadow string."}, true);

		// I dont set the setting type, so, wont render in GUI, but works normal as a setting.
		addSetting(this.setting_pos_x = new RocanSetting((RocanModule) this, new String[] {"Position X", this.tag + "PositionX", "Save X."}, this.rect.getX(), 0, 8096));
		addSetting(this.setting_pos_y = new RocanSetting((RocanModule) this, new String[] {"Position Y", this.tag + "PositionY", "Save Y."}, this.rect.getY(), 0, 8096));
		addSetting(this.setting_dock  = new RocanSetting((RocanModule) this, new String[] {"Dock", this.tag + "Dock", "Dock side."}, this.dock.name()));

		this.screen_width  = 0;
		this.screen_height = 0;

		this.hud_r = 0;
		this.hud_g = 0;
		this.hud_b = 0;

		this.rgb_r = 0;
		this.rgb_g = 0;
		this.rgb_b = 0;

		this.move_x = 0;
		this.move_y = 0;

		this.event_started = true;
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

	public void setHUDDragging(boolean state) {
		this.event_hud_dragging = state;
	}

	public void setJoinedToDockRect(boolean state) {
		this.event_joined_to_dock_rect = state;
	}

	public void setDocking(Docking dock) {
		this.dock = dock;
	}

	public String getName() {
		return this.name;
	}

	public String getTag() {
		return this.tag;
	}

	public String getDescription() {
		return this.description;
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

	public boolean isHUDDragging() {
		return this.event_hud_dragging;
	}

	public boolean isJoinedToDockRect() {
		return this.event_joined_to_dock_rect;
	}

	public Docking getDocking() {
		return this.dock;
	}

	@Override
	public void onRender() {
		ScaledResolution scaled_resolution = new ScaledResolution(mc);

		this.screen_width  = scaled_resolution.getScaledWidth();
		this.screen_height = scaled_resolution.getScaledHeight();

		// I use a system with setting to save x, y of hud.
		if (this.event_started) {
			this.rect.setX(this.setting_pos_x.getInteger());
			this.rect.setY(this.setting_pos_y.getInteger());	

			this.dock = getDockingByName(this.setting_dock.getString());

			this.event_started = false;
		} else {
			this.setting_pos_x.setInteger(this.rect.getX());
			this.setting_pos_y.setInteger(this.rect.getY());

			this.setting_dock.setString(this.dock.name());
		}

		// Update colors.
		this.hud_r = Rocan.getClientHUDRed();
		this.hud_g = Rocan.getClientHUDGreen();
		this.hud_b = Rocan.getClientHUDBlue();

		this.rgb_r = Rocan.getEventManager().getRGBEffect()[0];
		this.rgb_g = Rocan.getEventManager().getRGBEffect()[1];
		this.rgb_b = Rocan.getEventManager().getRGBEffect()[2];

		if (Rocan.getModuleManager().getModuleByTag("HUD").getState()) {
			onRenderHUD();
		}
	}

	public void keyboard(char char_, int key) {}

	public void click(int x, int y, int mouse) {
		if (mouse == 0) {
			if (isMousePassing()) {
				setMouseClick(true);

				setMoveX(x - getX());
				setMoveY(y - getY());
			}
		}
	}

	public void release(int x, int y, int mouse) {
		if (mouse == 0) {
			setMouseClick(false);
		}
	}

	public void render(int x, int y, float partial_ticks) {
		updateEvent(x, y);
		updateActions(x, y);

		onRenderHUD();

		// Prepare to render.
		TurokRenderGL.prepare2D();
		TurokRenderGL.release2D();
	}

	public void updateEvent(int x, int y) {
		if (this.rect.collide(x, y)) {
			setMousePassing(true);
		} else {
			setMousePassing(false);
		}

		if (isMouseClick()) {
			setHUDDragging(true);
		} else {
			setHUDDragging(false);
		}

		if (this.rect.collide(Rocan.getModuleManager().getHUDRectLeftUp()) || this.rect.collide(Rocan.getModuleManager().getHUDRectLeftDown()) || this.rect.collide(Rocan.getModuleManager().getHUDRectRightUp()) || this.rect.collide(Rocan.getModuleManager().getHUDRectRightDown())) {
			setJoinedToDockRect(true);
		} else {
			setJoinedToDockRect(false);
		}
	}

	public void updateActions(int x, int y) {
		if (isHUDDragging()) {
			setX(x - getMoveX());
			setY(y - getMoveY());

			verifyDrag(x, y);
			verifyCollision(x, y);

			// Considering mouse pass.
			drawGUIRect(0, 0, this.rect.getWidth(), this.rect.getHeight(), 255, 255, 255, 100);
		} else {
			if (isMousePassing()) {
				drawGUIRect(0, 0, this.rect.getWidth(), this.rect.getHeight(), 190, 190, 190, 100);
			} else {
				drawGUIRect(0, 0, this.rect.getWidth(), this.rect.getHeight(), 0, 0, 0, 100);
			}
		}
	} 

	protected void onRenderHUD() {}

	protected void drawGUIRect(int x, int y, int width, int height, int r, int g, int b, int a) {
		TurokScreenUtil.drawGUIRect(this.rect.getX() + x, this.rect.getY() + y, this.rect.getX() + x + width, this.rect.getY() + y + height, r, g, b, a);
	}

	protected void renderString(String string, int x, int y) {
		TurokString.renderStringHUD(string, this.rect.getX() + verifyDocking(getStringWidth(string), x), this.rect.getY() + y, this.hud_r, this.hud_g, this.hud_b, this.setting_shadow.getBoolean(), this.setting_smooth.getBoolean());
	}

	protected void renderString(String string, int x, int y, int r, int g, int b) {
		TurokString.renderStringHUD(string, this.rect.getX() + verifyDocking(getStringWidth(string), x), this.rect.getY() + y, r, g, b, this.setting_shadow.getBoolean(), this.setting_smooth.getBoolean());
	}

	protected int getStringWidth(String string) {
		return TurokString.getStringHUDWidth(string, this.setting_smooth.getBoolean());
	}

	protected int getStringHeight(String string) {
		if (this.setting_smooth.getBoolean()) {
			return TurokString.getStringHUDHeight(string, this.setting_smooth.getBoolean()) + 2;
		}

		return TurokString.getStringHUDHeight(string, this.setting_smooth.getBoolean());
	}

	protected int verifyDocking(int width, int x) {
		int final_position = x;

		if (dock == Docking.LEFT_UP) {
			final_position = x;
		}

		if (dock == Docking.LEFT_DOWN) {
			final_position = x;
		}

		if (dock == Docking.RIGHT_UP) {
			final_position = this.rect.getWidth() - width - x;
		}

		if (dock == Docking.RIGHT_DOWN) {
			final_position = this.rect.getWidth() - width - x;
		}

		return final_position;
	}

	protected void verifyDrag(int x, int y) {
		if (this.rect.getX() <= 1) {
			if (getDocking() == Docking.RIGHT_UP) {
				setDocking(Docking.LEFT_UP);
			} else if (getDocking() == Docking.RIGHT_DOWN) {
				setDocking(Docking.LEFT_DOWN);
			}
		}

		if (this.rect.getX() + this.rect.getWidth() >= (this.screen_width - 1)) {
			if (getDocking() == Docking.LEFT_UP) {
				setDocking(Docking.RIGHT_UP);
			} else if (getDocking() == Docking.LEFT_DOWN) {
				setDocking(Docking.RIGHT_DOWN);
			}
		}

		if (this.rect.getY() <= 1) {
			if (getDocking() == Docking.LEFT_DOWN) {
				setDocking(Docking.LEFT_UP);
			} else if (getDocking() == Docking.RIGHT_DOWN) {
				setDocking(Docking.RIGHT_UP);
			}
		}

		if (this.rect.getY() + this.rect.getHeight() >= (this.screen_height - 1)) {
			if (getDocking() == Docking.LEFT_UP) {
				setDocking(Docking.LEFT_DOWN);
			} else if (getDocking() == Docking.RIGHT_UP) {
				setDocking(Docking.RIGHT_DOWN);
			}
		}
	}

	protected void verifyCollision(int x, int y) {
		if (this.rect.getX() <= 0) {
			this.rect.setX(1);
		}

		if (this.rect.getX() + this.rect.getWidth() >= this.screen_width) {
			this.rect.setX(this.screen_width - this.rect.getWidth() - 1);
		}

		if (this.rect.getY() <= 0) {
			this.setY(1);
		}

		if (this.rect.getY() + this.rect.getHeight() >= (this.screen_height - 1)) {
			this.setY(this.screen_height - this.rect.getHeight() - 1);
		}
	}

	public Docking getDockingByName(String name) {
		for (Docking docks : Docking.values()) {
			if (docks.name().equals(name)) {
				return docks;
			}
		}

		return null;
	}

	public enum Docking {
		LEFT_UP,
		LEFT_DOWN,
		RIGHT_UP,
		RIGHT_DOWN;
	}

	protected RocanSetting addSetting(String[] details, boolean value) {
		return createSetting(details, value);
	}

	protected RocanSetting addSetting(String[] details, String value) {
		return createSetting(details, value);
	}

	protected RocanSetting addSetting(String[] details, int value, boolean state) {
		return createSetting(details, value, state);
	}

	protected RocanSetting addSetting(String[] details, int value, int min, int max) {
		return createSetting(details, value, min, max);
	}

	protected RocanSetting addSetting(String[] details, double value, double min, double max) {
		return createSetting(details, value, min, max);
	}

	protected RocanSetting addSetting(String[] details, String value, String[] values) {
		return createSetting(details, value, values);
	}
}