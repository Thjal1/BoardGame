package com.thortech.wheelsandsquares.Actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.thortech.wheelsandsquares.Settings;
import com.thortech.wheelsandsquares.WheelsAndSquares;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Wheel extends AbstractActor {
	private static final String TAG = Wheel.class.getName();

	private float regionHeight;
	private float regionWidth;
	private Vector3 centerV3;
	private Texture wheelImage;
	private float elapsedTime = 0;

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
	}

	public Wheel(WheelsAndSquares _game) {
		super(_game);
		create();
	}

	private void create() {

		regionHeight = Settings.PHYSICALSCALE;
		regionWidth = Settings.PHYSICALSCALE;

		centerV3 = new Vector3(0, 0, 0);
	}

	public int getTypeValue() {
		return type.getValue();
	}

	public void setType(WheelTypes type)
	{
		this.type = type;
	}

	@Override
	public void render(float dt) {

		elapsedTime += dt;
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