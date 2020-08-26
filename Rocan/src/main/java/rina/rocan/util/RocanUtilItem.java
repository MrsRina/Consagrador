package rina.rocan.util;

// Minecraft.
import net.minecraft.inventory.ClickType;
import net.minecraft.item.Item;

/**
  *
  * @author Rina!
  *
  * Created by Rina!
  * 26/08/2020.
  *
  **/
public class RocanUtilItem {
	public static int getItemSlotFromInventory(Item item) {
		for (int i = 0; i < 36; i++) {
			Item items = RocanUtilMinecraftHelper.getMinecraft().player.inventory.getStackInSlot(i).getItem();

			if (items == item) {
				if (i < 9) {
					i += 36;
				}

				return i;
			}
		}

		return -1;
	}

	public static int getItemSlotFromHotBar(Item item) {
		for (int i = 0; i < 9; i++) {
			Item items = RocanUtilMinecraftHelper.getMinecraft().player.inventory.getStackInSlot(i).getItem();

			if (items == item) {
				return i;
			}
		}

		return -1;
	}

	public static void setItemSlotToOffhand(int slot) {
		RocanUtilMinecraftHelper.getMinecraft().playerController.windowClick(RocanUtilMinecraftHelper.getMinecraft().player.inventoryContainer.windowId, slot, 0, ClickType.PICKUP, RocanUtilMinecraftHelper.getMinecraft().player);
		RocanUtilMinecraftHelper.getMinecraft().playerController.windowClick(RocanUtilMinecraftHelper.getMinecraft().player.inventoryContainer.windowId, 45, 0, ClickType.PICKUP, RocanUtilMinecraftHelper.getMinecraft().player);
		RocanUtilMinecraftHelper.getMinecraft().playerController.windowClick(RocanUtilMinecraftHelper.getMinecraft().player.inventoryContainer.windowId, slot, 0, ClickType.PICKUP, RocanUtilMinecraftHelper.getMinecraft().player);
		RocanUtilMinecraftHelper.getMinecraft().playerController.updateController();
	}
}