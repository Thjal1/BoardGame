package com.thortech.wheelsandsquares.Logic;

import com.badlogic.gdx.Gdx;
import com.thortech.wheelsandsquares.Logic.Level;
import com.thortech.wheelsandsquares.Settings;

import java.util.Random;

public class GameLogics {

	public static final String TAG = com.thortech.wheelsandsquares.Logic.GameLogics.class.getName();

	private static int maxLevelsInGame;
	private int drawsMade;
	private int pointsMadeInLevel;

	private int pointsMadeTotal;
	private int maxLevelReached;

	private Level level;
	private int currentNumbersOfTilesX;
	private int currentNumbersOfTilesY;
	private int randomSeed;
	private int randomSeedMax;
	private Random random;

	public GameLogics()
	{
		try {
			//Get the maximum level number the user has reached.
			maxLevelReached = Settings.getLevelReachedByPlayer();

			level = new Level();
			buildLevel(maxLevelReached);
		}
		catch (Exception ex)
		{
			Gdx.app.log(TAG, ex.getMessage());
		}
	}

	public Level.TileColours getTileColour(int x, int y)
	{
		return level.getTileColour(x,y);
	}

	public Level.WheelTypes getWheeltype(int x, int y)
	{
		return level.getWheeltype(x,y);
	}

	public void buildLevel(int levelNumber)
	{
		try {
			randomSeed = Settings.getSeedForRandom();
			randomSeedMax = Settings.getSeedForRandomMax();

			//Extract the random seed needed to build the level.
			for (int i = 0; i < levelNumber; i++) {
				random = new Random(randomSeed);
				randomSeed = random.nextInt(randomSeedMax);
			}

			int numbersOfTilesX = 4;
			int numbersOfTilesY = 4;
			int numberOfClouredTiles = 2;
			int numberOfHelperwheels = 2;
			int numberOfFakeWheels = 1;
			int numberOfDraws = 4;

			//TODO: Add the algorithms calculating the arguments for the creatLevel function
			numbersOfTilesX += (int)levelNumber/10;
			numbersOfTilesY += (int)levelNumber/10;

			level.createLevel(randomSeed, levelNumber, numbersOfTilesX, numbersOfTilesY, numberOfClouredTiles, numberOfHelperwheels, numberOfFakeWheels, numberOfDraws);
			maxLevelsInGame++;
		}
		catch (Exception ex)
		{
			Gdx.app.log(TAG, ex.getMessage());
		}
	}

	public Level.TileColours[][] getBoard()
	{
		return level.getTileArray();
	}

	public Level.WheelTypes[][] getInitialWheelplacements()
	{
		return level.getWheelArray();
	}
}
