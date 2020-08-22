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
@Define(name = "Dev Module 3", tag = "DevModule3", description = "To dev", category = Category.ROCAN_DEV)
public class RocanTestModule3 extends RocanModule {
	RocanSetting override_stand = createSetting(new String[] {"Rect", "DevModuleRect", "REtard"}, 0, false);

	@Override
	public void onUpdate() {
		if (override_stand.getBoolean()) {
			RocanUtilClient.sendNotifyClient("u are chad!");
		}
	}
}