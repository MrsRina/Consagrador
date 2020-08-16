package rina.rocan.client.modules.dev;

// Rocan module.
import rina.rocan.client.RocanModule.Category;
import rina.rocan.client.RocanModule.Define;
import rina.rocan.client.RocanModule;

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
	public RocanTestModule() {
		setKeyBind(19);
	}

	@Override
	public void onUpdate() {
		RocanUtilClient.sendNotifyClient("Testing");
	}
}