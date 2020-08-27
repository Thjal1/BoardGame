package com.thortech.wheelsandsquares.Actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.thortech.wheelsandsquares.WheelsAndSquares;

import java.util.HashMap;
import java.util.Map;

public class Tile extends AbstractActor {
	private static final String TAG = Tile.class.getName();
	private static int objNumber = 0;

	public static final int differentKindsOfColours = 4;

	private static float physicalHeight = 0.1f;	//static: All tile objects are to allways have the same size
	private static float physicalWidth = 0.1f;	//static: All tile objects are to allways have the same size
	private Vector3 centerV3;

	private Texture tileTexture;
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

	public Tile(WheelsAndSquares _game, Vector3 position) {
		super(_game);
		create();
		changePos(position, false);
	}

	private void create() {

		objNumber++;
		objName = TAG + objNumber;

		String file = "General/GameGraphics/TileEmpty128.png";

		centerV3 = new Vector3(0, 0, 0);

		tileTexture = new Texture(Gdx.files.internal(file));

		//switch (type)
	}

	public int getColourValue() {
		return colour.getValue();
	}

	public void setColour(TileColours colour) {
		this.colour = colour;
		String file = "General/GameGraphics/Tile" + colour.toString() + "128.png";
		tileTexture = new Texture(Gdx.files.internal(file));
	}

	@Override
	public void render(float dt) {
		elapsedTime += dt;
		game.batch.draw(tileTexture, centerV3.x, centerV3.y, physicalWidth, physicalHeight);
	}

	public Vector3 getPos() {
		return centerV3;
	}

	public void changePos(Vector3 v3, boolean center) {
		if (center == false) {
			this.centerV3 = v3;
		} else {
			this.centerV3.y = v3.y - physicalWidth / 2;
			this.centerV3.x = v3.x - physicalHeight / 2;
		}
	}

	public void setState(String _state) {

	}

	public static float getPhysicalWidth() {
		return physicalWidth;
	}

	public static float getPhysicalHeight() {
		return physicalHeight;
	}

	public void dispose() {
		tileTexture.dispose();
	}
}