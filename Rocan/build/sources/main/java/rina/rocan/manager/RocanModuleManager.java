package rina.rocan.manager;

// Folders.
// import rina.rocan.client.modules.combat.*;
// import rina.rocan.client.modules.render.*;
// import rina.rocan.client.modules.misc.*;
// import rina.rocan.client.modules.exploit.*;
// import rina.rocan.client.modules.movement.*;
import rina.rocan.client.modules.dev.*;

// Rocan module.
import rina.rocan.client.RocanModule.Category;
import rina.rocan.client.RocanModule;

// Java.
import java.util.*;

/**
 *
 * @author Rina!
 *
 * Created by Rina!
 * 15/08/2020.
 *
 **/
public class RocanModuleManager {
	ArrayList<RocanModule> module_list;

	public RocanModuleManager() {
		this.module_list = new ArrayList<>();

		addModule(new RocanTestModule());
		addModule(new RocanXCarry());
	}

	public void addModule(RocanModule module) {
		this.module_list.add(module);
	}

	public void onUpdateModuleList() {
		for (RocanModule modules : getModuleList()) {
			if (modules.getState()) {
				modules.onUpdate();
			}
		}
	}

	public void onPressedKeyBind(int key) {
		for (RocanModule modules : getModuleList()) {
			if (modules.getKeyBind() == key) {
				modules.toggle();
			}
		}
	}

	public ArrayList<RocanModule> getModuleList() {
		return this.module_list;
	}

	public ArrayList<RocanModule> getModuleListByCategory(Category category) {
		ArrayList<RocanModule> category_module_list = new ArrayList<>();

		for (RocanModule modules : getModuleList()) {
			if (modules.getCategory() == category) {
				category_module_list.add(modules);
			}
		}

		return category_module_list;
	}

	public RocanModule getModuleByTag(String tag) {
		for (RocanModule modules : getModuleList()) {
			if (modules.getTag().equalsIgnoreCase(tag)) {
				return modules;
			}
		}

		return null;
	}
}