package rina.rocan.client.modules.misc;

// Minecraft.
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.item.ItemEndCrystal;
import net.minecraft.item.ItemExpBottle;
import net.minecraft.util.math.BlockPos;
import net.minecraft.network.Packet;
import net.minecraft.item.ItemBow;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

// Module.
import rina.rocan.client.RocanModule.Category;
import rina.rocan.client.RocanModule.Define;
import rina.rocan.client.RocanSetting;
import rina.rocan.client.RocanModule;

/**
 *
 * @author Rina!
 *
 * Created by Rina!
 * 26/08/2020.
 *
 **/
@Define(name = "Fast Util", tag = "Fast Util", description = "Fast utils like crystal, bottle...", category = Category.ROCAN_MISC)
public class RocanFastUtil extends RocanModule {
	RocanSetting bottle_xp = createSetting(new String[] {"Bottle XP", "BottleXP", "Splash fast bottle XP."}, true);
	RocanSetting crystal   = createSetting(new String[] {"Crystal", "Crystal", "Place fast crystal."}, true);
	RocanSetting place     = createSetting(new String[] {"Place", "Place", "Place fast anything."}, true);
	RocanSetting break_    = createSetting(new String[] {"Break", "Break", "Fast break."}, true);
	RocanSetting bow       = createSetting(new String[] {"Bow", "Bow", "Fire fast arrows."}, true);

	@Override
	public void onUpdate() {
		boolean main_exp = mc.player.getHeldItemMainhand().getItem() instanceof ItemExpBottle;
		boolean off_exp  = mc.player.getHeldItemOffhand().getItem() instanceof ItemExpBottle;
		boolean main_cry = mc.player.getHeldItemMainhand().getItem() instanceof ItemEndCrystal;
		boolean off_cry  = mc.player.getHeldItemOffhand().getItem() instanceof ItemEndCrystal;

		if (main_exp | off_exp && bottle_xp.getBoolean()) {
			mc.rightClickDelayTimer = 0;
		}

		if (main_cry | off_cry && crystal.getBoolean()) {
			mc.rightClickDelayTimer = 0;
		}

		if (!(main_exp | off_exp | main_cry | off_cry) && place.getBoolean()) {
			mc.rightClickDelayTimer = 0;
		}

		if (break_.getBoolean()) {
			mc.playerController.blockHitDelay = 0;
		}

		if (mc.player.inventory.getCurrentItem().getItem() instanceof ItemBow && bow.getBoolean()) {
			if (mc.player.isHandActive() && mc.player.getItemInUseMaxCount() >= 3) {
				mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, mc.player.getHorizontalFacing()));
				mc.player.connection.sendPacket(new CPacketPlayerTryUseItem(mc.player.getActiveHand()));
				mc.player.stopActiveHand();
			}
		}
	}
}