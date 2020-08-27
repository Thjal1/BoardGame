package com.thortech.wheelsandsquares.Actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.thortech.wheelsandsquares.WheelsAndSquares;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Wheel extends AbstractActor {
	private static final String TAG = Wheel.class.getName();
	private static int objNumber = 0;

	private float physicalHeight = 0.1f;
	private float physicalWidth = 0.1f;
	private Vector3 centerV3;
	private Texture wheelTexture;
	private Sprite wheelSprite;
	private float elapsedTime = 0;
	private String state = "";
	private Vector2 fromCoordinates, toCoordinates;

	private WheelTypes type = WheelTypes.EMPTY;

	public enum WheelTypes {
		EMPTY(0),
		FULLRED(1), FULLBLUE(2), FULLGREEN(3), FULLBLACK(4), FULLYELLOW(5),
		HALFREDBLUE(6), HALFREDGREEN(7), HALFREDBLACK(8), HALFREDYELLOW(9),
		HALFBLUEGREEN(10), HALFBLUEBLACK(11), HALFBLUEYELLOW(12),
		HALFGREENBLACK(13), HALFGEENYELLOW(14),
		HALFBLACKYELLOW(15),
		THIRDREDBLUEGREEN(16), THIRDREDGREENBLUE(17), THIRDREDGREENBLACK(18), THIRDREDBLACKGREEN(19), THIRDREDBLACKYELLOW(20), THIRDREDYELLOWBLACK(21),
		THIRDBLUEGREENBLACK(22), THIRDBLUEBLAKGREEN(23), THIRDBLUEBLACKYELLOW(24), THIRDBLUEYELLOWBLACK(25),
		THIRDGREENBLACKYELLOW(26), THIRDGREENYELLOWBLACK(27);

		private int value;
		private static Map map = new HashMap<>();
		private static Random random = new Random();

		WheelTypes(int value) {
			this.value = value;
		}

		static {
			for (WheelTypes wheelTypes : WheelTypes.values()) {
				map.put(wheelTypes.value, wheelTypes);
			}
		}

		public static WheelTypes getEnum(int wheelType) {
			return (WheelTypes) map.get(wheelType);
		}

		public int getValue() {
			return value;
		}

		//maxTileParts 1: Fullcircle, 2: Halfcircle, 3: 1/3 circles, any other number: Empty.
		public static WheelTypes getRandomEnum(int maxtTileParts) {
			int wheelTypeValue = 0;
			int tileParts = 1;
			if (maxtTileParts > 1) {
				tileParts = random.nextInt(maxtTileParts - 1) + 1;
			}
			switch (tileParts) {
				case 1:
					wheelTypeValue = random.nextInt(4) + 1;
					break;
				case 2:
					wheelTypeValue = random.nextInt(9) + 6;
					break;
				case 3:
					wheelTypeValue = random.nextInt(11) + 16;
					break;
				default:
					wheelTypeValue = 0;
					break;

			}
			return WheelTypes.getEnum(wheelTypeValue);
		}

		public static int getRandomValue(int maxTileParts)
		{
			return getRandomEnum(maxTileParts).value;
		}
	}

	public Wheel(WheelsAndSquares _game) {
		super(_game);
		create();
	}

	public Wheel(WheelsAndSquares game, Vector3 physicalCoordinates, Wheel.WheelTypes type)
	{
		super(game);
		create();
		changePos(physicalCoordinates, false);
		setType(type);
	}

	private void create() {

		objNumber++;
		objName = TAG + objNumber;

		centerV3 = new Vector3(0, 0, 0);
		fromCoordinates = new Vector2(0,0);
		toCoordinates = new Vector2(0, 0);
		state = "StandStill";
	}

	public int getTypeValue() {
		return type.getValue();
	}

	public void setType(WheelTypes type)
	{
		this.type = type;
		String file = "General/GameGraphics/Wheel" + type.toString() + "128.png";
		wheelTexture = new Texture(Gdx.files.internal(file));

		wheelSprite = new Sprite(wheelTexture);

		wheelSprite.setPosition(centerV3.x, centerV3.y);
		wheelSprite.setSize(physicalWidth,physicalHeight);
	}

	@Override
	public void render(float dt) {
		elapsedTime += dt;
		if(wheelSprite != null && type != WheelTypes.EMPTY)
			game.batch.draw(wheelSprite, centerV3.x, centerV3.y, physicalWidth, physicalHeight);
	}

	public Vector3 getPhysicalPosistion() {
		return centerV3;
	}
	public Vector2 getFromCoordinates() { return fromCoordinates; }
	public Vector2 toCoordinates() { return toCoordinates; }

	public void changePos(Vector3 v3, boolean center) {
		if (center == false) {
			this.centerV3 = v3;
		} else {
			this.centerV3.y = v3.y - physicalWidth / 2;
			this.centerV3.x = v3.x - physicalHeight / 2;
		}
	}

	public void moveToCoordinate(Vector2 moveTo)
	{
		toCoordinates = moveTo;
		setState("Moving");
	}

	public void setState(String state) {
		this.state = state;
	}

	public void dispose() {
		wheelSprite.getTexture().dispose();
	}
}