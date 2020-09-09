package rina.rocan.client.huds;

// Miencraft.
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;

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
 * 30/08/2020.
 *
 **/
public class RocanInventory extends RocanHUD {
	RocanSetting background_alpha = addSetting(new String[] {"Background Alpha", "InventoryBackgroundAlpha", "Inventory background alpha."}, 70, 0, 255);

	public RocanInventory() {
		super(new String[] {"Inventory", "Inventory", "Draw inventory."});
	}

	@Override
	public void onRenderHUD() {
		if (mc.player == null || mc.world == null) {
			return;
		}
		GlStateManager.pushMatrix();
		RenderHelper.enableGUIStandardItemLighting();

		drawGUIRect(0, 0, this.rect.getWidth(), this.rect.getHeight(), 0, 0, 0, background_alpha.getInteger());

		for (int i = 0; i < 27; i++) {
			ItemStack item_stack = mc.player.inventory.mainInventory.get(i + 9);

			int item_position_x = (int) this.rect.getX() + (i % 9) * 16;
			int item_position_y = (int) this.rect.getY() + (i / 9) * 16;

			mc.getRenderItem().renderItemAndEffectIntoGUI(item_stack, item_position_x, item_position_y);
			mc.getRenderItem().renderItemOverlayIntoGUI(mc.fontRenderer, item_stack, item_position_x, item_position_y, null);
		}

		mc.getRenderItem().zLevel = - 5.0f;

		RenderHelper.disableStandardItemLighting();			
	
		GlStateManager.popMatrix();

		this.rect.setWidth(16 * 9);
		this.rect.setHeight(16 * 3);
	}
}