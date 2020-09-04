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

// Client.
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
public class RocanFastUtil extends RocanModule {
	RocanSetting bottle_xp = createSetting(new String[] {"Bottle XP", "FastUtilBottleXP", "Splash fast bottle XP."}, true);
	RocanSetting crystal   = createSetting(new String[] {"Crystal", "FastUtilCrystal", "Place fast crystal."}, true);
	RocanSetting place     = createSetting(new String[] {"Place", "FastUtilPlace", "Place fast anything."}, true);
	RocanSetting break_    = createSetting(new String[] {"Break", "FastUtilBreak", "Fast break."}, true);
	RocanSetting bow       = createSetting(new String[] {"Bow", "FastUtilBow", "Fire fast arrows."}, true);

	public RocanFastUtil() {
		super(new String[] {"Fast Util", "FastUtil", "Fast util for client."}, Category.ROCAN_MISC);
	}

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