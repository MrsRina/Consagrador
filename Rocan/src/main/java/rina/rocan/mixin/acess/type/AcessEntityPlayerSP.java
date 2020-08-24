package rina.rocan.mixin.acess.type;

/**
 * @author Brennan
 **/
public interface AcessEntityPlayerSP {
	boolean isInLiquid();
	boolean isOnLiquid();
	boolean isMoving();

	void setInPortal(boolean portal);
}