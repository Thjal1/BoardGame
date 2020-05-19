package com.thortech.wheelsandsquares.Actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.thortech.wheelsandsquares.Settings;
import com.thortech.wheelsandsquares.WheelsAndSquares;

import java.util.HashMap;
import java.util.Map;

public class Tile extends AbstractActor {
	private static final String TAG = Tile.class.getName();

	public static final int differentKindsOfColours = 4;

	private float regionHeight;
	private float regionWidth;
	private Vector3 centerV3;
	private Texture tileImage;
	private float elapsedTime = 0;

	private TileColours colour = TileColours.EMPTY;

	public enum TileColours {
		EMPTY(0), RED(1), BLUE(2), GREEN(3), BLACK(4), YELLOW(5);

		private int value;
		private static Map map = new HashMap<>();

		TileColours(int value) {
			this.value = value;
		}

		static {
			for (TileColours tileColours : TileColours.values()) {
				map.put(tileColours.value, tileColours);
			}
		}

		public static TileColours getEnum(int tileColour) {
			return (TileColours) map.get(tileColour);
		}

		public int getValue() {
			return value;
		}
	}

	public Tile(WheelsAndSquares _game) {
		super(_game);
		create();
	}

	private void create() {

		String file = "GameGraphics/TileEmpty128.png";
		regionHeight = Settings.PHYSICALSCALE;
		regionWidth = Settings.PHYSICALSCALE;

		centerV3 = new Vector3(0, 0, 0);

		tileImage = new Texture(Gdx.files.internal(file));

		//switch (type)
	}

	public int getColourValue() {
		return colour.getValue();
	}

	public void setColour(TileColours colour) {
		this.colour = colour;
	}

	@Override
	public void render(float dt) {

		elapsedTime += dt;
		game.batch.draw(tileImage, centerV3.x, centerV3.y);
	}

	public Vector3 getPos() {
		return centerV3;
	}

	public void changePos(Vector3 v3, boolean center) {
		if (center == false) {
			this.centerV3 = v3;
		} else {
			this.centerV3.y = v3.y - regionWidth / 2;
			this.centerV3.x = v3.x - regionHeight / 2;
		}
	}

	public void setState(String _state) {

	}

	public void dispose() {

	}
}