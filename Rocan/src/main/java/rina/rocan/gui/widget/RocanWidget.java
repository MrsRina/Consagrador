package rina.rocan.gui.widget;

/**
  *
  * @author Rina!
  *
  * Created by Rina!
  * 18/08/2020.
  *
  **/
public abstract class RocanWidget {
	public void setX(int x) {}
	public void setY(int y) {}

	public void setWidth(int width) {}
	public void setHeight(int height) {}

	public int getX() {
		return 0;
	}

	public int getY() {
		return 0;
	}

	public int getWidth() {
		return 0;
	}

	public int getHeight() {
		return 0;
	}

	public void click(int mouse) {}
	public void release(int mouse) {}
	public void render() {}
}