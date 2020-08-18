package rina.rocan.event.network;

// Minecraft.
import net.minecraft.network.Packet;

// Event.
import rina.rocan.event.RocanEventCancellable;

/**
  *
  * @author Rina!
  *
  * Created by Rina!
  * 16/08/2020.
  *
  **/
public class RocanEventPacketReceive extends RocanEventCancellable {
	private Packet packet;

	public RocanEventPacketReceive(EventStage stage, Packet packet) {
		super(stage);

		this.packet = packet;
	}

	public void setPacket(Packet packet) {
		this.packet = packet;
	}

	public Packet getPacket() {
		return this.packet;
	}
}