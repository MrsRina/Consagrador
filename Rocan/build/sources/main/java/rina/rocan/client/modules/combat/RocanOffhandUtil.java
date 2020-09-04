package rina.rocan.client.modules.combat;

// Minecraft.
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemStack;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

// Client.
import rina.rocan.client.RocanSetting;
import rina.rocan.client.RocanModule;

// Util.
import rina.rocan.util.RocanUtilItem;

// Rocan.
import rina.rocan.Rocan;

/**
 *
 * @author Rina!
 *
 * Created by Rina!
 * 26/08/2020.
 *
 **/
public class RocanOffhandUtil extends RocanModule {
	RocanSetting smart_totem        = createSetting(new String[] {"Smart Auto Totem", "OffhandUtilSmartAutoTotem", "Automatically put offhand totem for no die."}, 0.5, 0.0, 10.0);
	RocanSetting enable_auto_totem  = createSetting(new String[] {"Auto Totem Enable", "OffhandUtilAutoTotemEnable", "Enable totem when turn off golden apple or end crystal."}, true);
	RocanSetting bind_totem         = createSetting(new String[] {"Totem", "OffhandUtilTotem", "Set offhand to totem."}, -1, false);
	RocanSetting bind_golden_apple  = createSetting(new String[] {"Golden Apple", "OffhandUtilGoldenApple", "Set offhand to golden apple."}, -1, false);
	RocanSetting bind_end_crystal   = createSetting(new String[] {"End Crystal", "OffhandUtilEndCrystal", "Set offhand to end crystal."}, -1, false);

	boolean state_totem        = false;
	boolean state_golden_apple = false;
	boolean state_end_crystal  = false;

	// hi rina! its me 69hr i learn from your code! its very good!
	// Thank you!
	public RocanOffhandUtil() {
		super(new String[] {"Offhand Util", "OffhandUtil", "Offhand util for totem, golden apple & crystal."}, Category.ROCAN_COMBAT);
	}

	@Override
	public void onUpdate() {
		if (bind_totem.getBoolean()) {
			state_golden_apple = true;
			state_end_crystal  = true;

			if (state_totem) {
				bind_golden_apple.setBoolean(false);
				bind_end_crystal.setBoolean(false);

				sentNotifyClientChat("Totem of Undying has " + Rocan.getGreenColor() + "enabled");

				state_totem = false;
			}

			verifyStuff(Items.TOTEM_OF_UNDYING);

			this.info = "T";
		}

		if (bind_golden_apple.getBoolean()) {
			state_totem        = true;
			state_end_crystal  = true;

			if (state_golden_apple) {
				bind_totem.setBoolean(false);
				bind_end_crystal.setBoolean(false);

				sentNotifyClientChat("Golden Apple has " + Rocan.getGreenColor() + "enabled");

				state_golden_apple = false;
			}

			verifyStuff(Items.GOLDEN_APPLE);

			this.info = "G";
		}

		if (bind_end_crystal.getBoolean()) {
			state_totem        = true;
			state_golden_apple = true;

			if (state_end_crystal) {
				bind_totem.setBoolean(false);
				bind_golden_apple.setBoolean(false);

				sentNotifyClientChat("End Crystal has " + Rocan.getGreenColor() + "enabled");

				state_end_crystal = false;
			}

			verifyStuff(Items.END_CRYSTAL);

			this.info = "E";
		}

		if (enable_auto_totem.getBoolean() && bind_end_crystal.getBoolean() == false && bind_golden_apple.getBoolean() == false && bind_totem.getBoolean() == false) {
			bind_totem.setBoolean(true);
		}
	}

	public void verifyStuff(Item item) {
		if (mc.player == null || mc.world == null) {
			return;
		}

		if (mc.currentScreen instanceof GuiContainer) {
			return;
		}

		if (item != Items.TOTEM_OF_UNDYING && smart_totem.getDouble() != 0.0d && mc.player.getHealth() <= (float) smart_totem.getDouble()) {
			bind_totem.setBoolean(true);
		}

		if (mc.player.getHeldItemOffhand().getItem() == item) {
			return;
		}

		int slot = RocanUtilItem.getItemSlotFromInventory(item);

		if (slot == -1) {
			return;
		}

		RocanUtilItem.setItemSlotToOffhand(slot);
	}
}