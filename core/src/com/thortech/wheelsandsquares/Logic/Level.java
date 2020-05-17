package com.thortech.wheelsandsquares.Logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class Level {
	public static final String TAG = com.thortech.wheelsandsquares.Logic.Level.class.getName();

	private int levelSeed;			//Seed number explaining how the level is build.
	private int numbersOftilesX;
	private int numbersOfTilesY;
	private Random random = new Random();

//	private TileColours[][] tileArray;
	private WheelTypes[][] wheelArray;

	private Array<Array<TileColours>> tileArray = new Array<Array<TileColours>>();

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

	public enum WheelTypes {
		EMPTY (0),
		FULLRED(1), FULLBLUE(2), FULLGREEN(3), FULLBLACK(4), FULLYELLOW(5),
		HALFREDBLUE(6), HALFREDGREEN(7), HALFREDBLACK(8), HALFREDYELLOW(9),
		HALFBLUEGREEN(10), HALFBLUEBLACK(11), HALFBLUEYELLOW(12),
		HALFGREENBLACK(13), HALFGEENYELLOW(14),
		HALFBLACKYELLOW(15),
		THIRDREDBLUEGREEN(16), THIRDREDGREENBLUE(17), THIRDREDGREENBLACK(18), THIRDREDBLACKGREEN(19), THIRDREDBLACKYELLOW(20), THIRDREDYELLOWBLACK (21),
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
		public static WheelTypes getRandomEnum(int maxtTileParts)
		{
			int wheelTypeValue = 0;
			int tileParts = 1;
			if (maxtTileParts > 1) {
				tileParts = random.nextInt(maxtTileParts - 1) + 1;
			}
			switch (tileParts)
			{
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

	public Level()
	{
		int maxSize = 100;
		//tileArray = new TileColours[maxSize][maxSize];

		wheelArray = new WheelTypes[maxSize][maxSize];
		LoadLevel();

	}

	private void LoadLevel()
	{
		try{
			//levelInformation = Gdx.app.getPreferences(levelFile);

		}
		catch (Exception ex){
			Gdx.app.log(TAG, ex.getMessage());
		}
	}

	public void createLevel(int randomSeed, int levelNumber, int numbersOfTilesX, int numbersOfTilesY, int numberOfColouredTiles, int numberOfHelperWheels, int numberOfFakeWheels, int numberOfDraws) {
		try {

			random.setSeed(randomSeed);

			//Need to know what level to create, how many tiles it should have, How many coloured tiles, how many fake wheels, how many draws.
			int ranValueX = 0;
			int ranValueY = 0;
			this.numbersOftilesX = numbersOfTilesX;
			this.numbersOfTilesY = numbersOfTilesY;

			//TODO: Check if the level previously is made.

			//Fill the tileArray with blank tiles

			for (int x = 0; x < numbersOfTilesX; x++) {
				tileArray.add(new Array<TileColours>());
				for (int y = 0; y < numbersOfTilesY; y++) {
					tileArray.get(x).add(TileColours.EMPTY);
					wheelArray[x][y] = WheelTypes.EMPTY;
				}
			}

			//Place the coloured tiles on a random tile
			//And place a full colored wheel, with the same colour on the wheel array on the same coordinate
			int i = 0;
			int falseItterations = 0;
			do {
				ranValueX = random.nextInt(numbersOfTilesX);
				ranValueY = random.nextInt(numbersOfTilesY);

				if (tileArray.get(ranValueX).get(ranValueY) == TileColours.EMPTY){
					int ranColour = random.nextInt(4) + 1;    //There are five colours to choose from
					TileColours tileColour = TileColours.getEnum(ranColour);
					//tileColour.value = ranColour;
					WheelTypes wheelType = WheelTypes.getEnum(ranColour);
					//wheelType.value = ranColour;
					tileArray.get(ranValueX).set(ranValueY, tileColour);
					wheelArray[ranValueX][ranValueY] = wheelType;
					i++;
				} else {
					falseItterations++;
					if (falseItterations > 100) {
						break;
					}
				}
			}
			while (i < numberOfColouredTiles);
			i = 0;

			//Place fake wheels
			do {
				ranValueX = random.nextInt(numbersOfTilesX);
				ranValueY = random.nextInt(numbersOfTilesY);

				if (wheelArray[ranValueX][ranValueY] == WheelTypes.EMPTY) {
					WheelTypes wheelType = WheelTypes.getRandomEnum(random.nextInt(2) + 1);
					wheelArray[ranValueX][ranValueY] = wheelType;
					i++;
				} else {
					falseItterations++;
					if (falseItterations > 100) {
						break;
					}
				}
			} while (i < numberOfFakeWheels);
			if (falseItterations >= 100) {
				Gdx.app.log(TAG, "falseIttterations is more than 100");
			}
		}
		catch (Exception ex)
		{
			Gdx.app.log(TAG, ex.getMessage());
		}
	}

	public TileColours getTileColour(int x, int y)
	{
		if(x >= 0 && x <= numbersOftilesX && y >= 0 && y <= numbersOfTilesY) {
			return tileArray.get(x).get(y);
		}
		return TileColours.EMPTY;
	}

	public WheelTypes getWheeltype(int x, int y)
	{
		if(x >= 0 && x <= numbersOftilesX && y >= 0 && y <= numbersOfTilesY) {
			return wheelArray[x][y];
		}
		return WheelTypes.EMPTY;
	}

	public Array<Array<TileColours>> getTileArray()
	{
		return tileArray;
	}
	public WheelTypes[][] getWheelArray()
	{
		return wheelArray;
	}
}
