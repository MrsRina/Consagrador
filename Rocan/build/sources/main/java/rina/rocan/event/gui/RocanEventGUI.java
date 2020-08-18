package rina.rocan.event.gui;

// Minecraft.
import net.minecraft.client.gui.GuiScreen;

// Event.
import rina.rocan.event.RocanEventCancellable;

/**
  *
  * @author Rina!
  *
  * Created by Rina!
  * 17/08/2020.
  *
  **/
public class RocanEventGUI extends RocanEventCancellable {
	GuiScreen guiscree;

	public RocanEventGUI(GuiScreen guiscree) {
		super();

		this.guiscree = guiscree;
	}

	public void setGuiScreen(GuiScreen guiscree) {
		this.guiscree = guiscree;
	}

	public GuiScreen getGuiScreen() {
		return this.guiscree;
	}
}
