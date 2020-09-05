package rina.rocan.client.huds;

// Java.
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.*;

// Client.
import rina.rocan.client.RocanSetting;
import rina.rocan.client.RocanModule;
import rina.rocan.client.RocanHUD;

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
public class RocanModuleList extends RocanHUD {
	RocanSetting rgb_effect = addSetting(new String[] {"RGB", "ModuleListRGB", "RGB effect."}, false);
	RocanSetting info_mode  = addSetting(new String[] {"Info", "Info", "Modes for info."}, "[info]", new String[] {"[info]", "info"});

	private List<RocanModule> pretty_module_list;

	public RocanModuleList() {
		super(new String[] {"Module List", "ModuleList", "Draw enabled modules in a list."});
	
		pretty_module_list = new ArrayList<>();
	}

	@Override
	public void onRenderHUD() {
		boolean style = info_mode.getString().equals("[info]") == true ? true : false; 

		Comparator<RocanModule> comparator = (module_1, module_2) -> {
			String module_1_string = module_1.getTag() + (module_1.getInfo().equals("") == true ? "" : (style ? (Rocan.getGrayColor() + " [" + module_1.getInfo() + Rocan.getGrayColor() + "]" + Rocan.setDefaultColor()) : Rocan.getGrayColor() + module_1.getInfo())); 
			String module_2_string = module_2.getTag() + (module_2.getInfo().equals("") == true ? "" : (style ? (Rocan.getGrayColor() + " [" + module_2.getInfo() + Rocan.getGrayColor() + "]" + Rocan.setDefaultColor()) : Rocan.getGrayColor() + module_2.getInfo())); 

			float diff = getStringWidth(module_2_string) - getStringWidth(module_1_string);

			if (this.getDocking() == Docking.LEFT_UP || this.getDocking() == Docking.RIGHT_UP) {
				return diff != 0 ? (int) diff : module_2_string.compareTo(module_1_string);
			} else {
				return (int) diff;
			}
		};

		if (this.getDocking() == Docking.LEFT_UP || this.getDocking() == Docking.RIGHT_UP) {
			pretty_module_list = Rocan.getModuleManager().getModuleList().stream().filter(module -> module.getState()).sorted(comparator).collect(Collectors.toList());
		} else if (this.getDocking() == Docking.LEFT_DOWN || this.getDocking() == Docking.RIGHT_DOWN) {
			pretty_module_list = Rocan.getModuleManager().getModuleList().stream().filter(module -> module.getState()).sorted(Comparator.comparing(module -> getStringWidth(module.getTag() + (module.getInfo().equals("") == true ? "" : (style ? (Rocan.getGrayColor() + " [" + module.getInfo() + Rocan.getGrayColor() + "]" + Rocan.setDefaultColor()) : Rocan.getGrayColor() + module.getInfo()))))).collect(Collectors.toList());
		}

		int position_update_y = 0;

		for (RocanModule modules : pretty_module_list) {
			if (modules.getCategory() == RocanModule.Category.ROCAN_HUD || modules.getCategory() == RocanModule.Category.ROCAN_GUI || !modules.showInHUD()) {
				continue;
			}

			String module_name = modules.getTag() + (modules.getInfo().equals("") == true ? "" : (style ? (Rocan.getGrayColor() + " [" + modules.getInfo() + Rocan.getGrayColor() + "]" + Rocan.setDefaultColor()) : Rocan.getGrayColor() + " " + modules.getInfo()));

			if (rgb_effect.getBoolean()) {
				renderString(module_name, 1, position_update_y, rgb_r, rgb_g, rgb_b);
			} else {
				renderString(module_name, 1, position_update_y);
			};

			position_update_y += getStringHeight(module_name);

			if (getStringWidth(module_name) >= this.rect.getWidth()) {
				this.rect.setWidth(getStringWidth(module_name) + 2);
			}

			this.rect.setHeight(position_update_y);
		}
	}
}