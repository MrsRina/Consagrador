package rina.rocan.client.modules.dev;

// Module.
import rina.rocan.client.RocanModule.Category;
import rina.rocan.client.RocanModule.Define;
import rina.rocan.client.RocanModule;
import rina.rocan.client.RocanSetting;

// Utils.
import rina.rocan.util.RocanUtilClient;

/**
 *
 * @author Rina!
 *
 * Created by Rina!
 * 15/08/2020.
 *
 **/
@Define(name = "Dev Module", tag = "DevModule", description = "To dev", category = Category.ROCAN_DEV)
public class RocanTestModule extends RocanModule {
	RocanSetting string_name    = createSetting(new String[] {"Name", "DevModuleName", "To test"}, "Level");
	RocanSetting can_jump       = createSetting(new String[] {"Jump", "DevModuleJump", "Able jump"}, false);
	RocanSetting intenger_value = createSetting(new String[] {"NumberInt", "DevModuleNumberInt", "To test"}, 1, 0, 10);
	RocanSetting double_value   = createSetting(new String[] {"NumberDouble", "DevModuleNumberDouble", "To test"}, 2.0, 0.0, 10.0);

	@Override
	public void onUpdate() {
		RocanUtilClient.sendNotifyClient(string_name.getName() + " " + string_name.getString() + ", " + intenger_value.getName() + " " + intenger_value.getInteger() + ", " + double_value.getName() + " " + double_value.getDouble());

		if (can_jump.getBoolean()) {
			mc.player.jump();
		}
	}
}