package rina.rocan.client.modules.movement;

// Minecraft.
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.block.BlockAir;
import net.minecraft.block.Block;

// Pomelo.
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

// Event.
import rina.rocan.event.player.RocanEventPlayerUpdateWalking;

// Client.
import rina.rocan.client.RocanSetting;
import rina.rocan.client.RocanModule;

/**
 *
 * @author Rina!
 *
 * Created by Rina!
 * 25/08/2020. // 23:05 pm
 *
 **/
public class RocanStep extends RocanModule {
	RocanSetting size_block = createSetting(new String[] {"Block", "StepBlock", "Size of block to step."}, "Two", new String[] {"One", "Two"});

	public RocanStep() {
		super(new String[] {"Step", "Step", "Make you step 1 or 2 blocks."}, Category.ROCAN_MOVEMENT);
	}

	private final double[] one_block_height = {
		0.42d, 0.75d
	};

	private final double[] two_block_height = {
		0.4d, 0.75d, 0.5d, 0.41d, 0.83d, 1.16d, 1.41d, 1.57d, 1.58d, 1.42d
	};

	private double[] selected = new double[0];

	// Packets.
	private int packets;

	@Override
	public void onUpdate() {
		if (size_block.getString().equals("One")) {
			selected = one_block_height;
		} else if (size_block.getString().equals("Two")) {
			selected = two_block_height;
		}

		if (mc.player.collidedHorizontally && mc.player.onGround) {
			packets++;
		}

		final AxisAlignedBB bb = mc.player.getEntityBoundingBox();

		for (int x = MathHelper.floor(bb.minX); x < MathHelper.floor(bb.maxX + 1.0d); x++) {
			for (int z = MathHelper.floor(bb.minZ); z < MathHelper.floor(bb.maxZ + 1.0d); z++) {
				final Block block = mc.world.getBlockState(new BlockPos(x, bb.maxY + 1, z)).getBlock();

				if (!(block instanceof BlockAir)) {
					return;
				}
			}
		}

		if (mc.player.onGround && !mc.player.isInsideOfMaterial(Material.WATER) && !mc.player.isInsideOfMaterial(Material.LAVA) && !mc.player.isInWeb && mc.player.collidedVertically && mc.player.fallDistance == 0 && !mc.gameSettings.keyBindJump.pressed && mc.player.collidedHorizontally && !mc.player.isOnLadder() && packets > selected.length - 2) {
			for (double position : selected) {
				mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + position, mc.player.posZ, true));
			}

			mc.player.setPosition(mc.player.posX, mc.player.posY + selected[selected.length - 1], mc.player.posZ);
		
			packets = 0;
		}
	}
}