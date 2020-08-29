package rina.rocan.client;

// Minecraft.
import net.minecraft.client.Minecraft;

// Turok.
import rina.turok.TurokScreenUtil;
import rina.turok.TurokString;
import rina.turok.TurokRect;

// Client.
import rina.rocan.client.RocanModule;

// Util.
import rina.rocan.util.RocanUtilMinecraftHelper;

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
	private String name;
	private String tag;
	private String description;

	private RocanSetting setting_smooth;
	private RocanSetting setting_shadow;

	public TurokRect rect;

	public Docking dock;

	public int hud_r;
	public int hud_g;
	public int hud_b;

	public int rgb_r;
	public int rgb_g;
	public int rgb_b;

	public static final Minecraft mc = RocanUtilMinecraftHelper.getMinecraft();

	public RocanHUD(String[] details) {
		super(new String[] {details[0], "HUD" + details[1], details[2]}, Category.ROCAN_GUI);

		this.dock = Docking.LEFT_UP;

		initializeComponentHUD(details);
	}

	public RocanHUD(String[] details, Docking dock) {
		super(new String[] {details[0], "HUD" + details[1], details[2]}, Category.ROCAN_GUI);

		this.dock = dock;

		initializeComponentHUD(details);
	}

	public void initializeComponentHUD(String[] details) {
		this.name        = details[0];
		this.tag         = details[1];
		this.description = details[2];

		this.rect = new TurokRect(this.name, 0, 0);

		this.setting_smooth = createSetting(new String[] {"Smooth", "Smooth", "Draw smooth font for HUD"}, true);
		this.setting_shadow = createSetting(new String[] {"Shadow", "Shadow", "Enable shadow string."}, true);

		this.hud_r = 0;
		this.hud_g = 0;
		this.hud_b = 0;

		this.rgb_r = 0;
		this.rgb_g = 0;
		this.rgb_b = 0;
	}

	public void setX(int x) {
		this.rect.setX(x);
	}

	public void setY(int y) {
		this.rect.setY(y);
	}

	public void setWidth(int width) {
		this.rect.setWidth(width);
	}

	public void setHeight(int height) {
		this.rect.setHeight(height);
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

	public int getWidth() {
		return this.rect.getWidth();
	}

	public int getHeight() {
		return this.rect.getHeight();
	}

	@Override
	public void onRender() {
		// Update colors.
		this.hud_r = Rocan.getSettingManager().getSettingByModuleAndTag("HUD", "StringRed").getInteger();
		this.hud_g = Rocan.getSettingManager().getSettingByModuleAndTag("HUD", "StringGreen").getInteger();
		this.hud_b = Rocan.getSettingManager().getSettingByModuleAndTag("HUD", "StringBlue").getInteger();

		this.rgb_r = Rocan.getEventManager().getRGBEffect()[0];
		this.rgb_g = Rocan.getEventManager().getRGBEffect()[1];
		this.rgb_b = Rocan.getEventManager().getRGBEffect()[2];

		if (Rocan.getModuleManager().getModuleByTag("HUD").getState()) {
			onRenderHUD();
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