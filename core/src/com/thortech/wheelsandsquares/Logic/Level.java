package com.thortech.wheelsandsquares.Logic;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.math.Vector2;
import com.thortech.wheelsandsquares.Actors.Wheel;
import com.thortech.wheelsandsquares.Helpers.LogHelper;
import com.thortech.wheelsandsquares.Scenes.BoardStage;
import com.thortech.wheelsandsquares.WheelsAndSquares;

import java.util.Random;


public class Level{
	public static final String TAG = com.thortech.wheelsandsquares.Logic.Level.class.getName();

	private int levelSeed;            //Seed number explaining how the level is build.
	private int numbersOftilesX;
	private int numbersOfTilesY;
	private Random random = new Random();

	static BoardStage board;

	public Level () {
		LoadLevel();
	}

	private void LoadLevel() {
		try {
			//levelInformation = Gdx.app.getPreferences(levelFile);

		} catch (Exception ex) {
			LogHelper.Log(TAG, ex.getMessage(), Application.LOG_ERROR);
		}
	}

	public void createBoard(int randomSeed, int levelNumber, int numbersOfTilesX, int numbersOfTilesY, int numberOfColouredTiles, int numberOfHelperWheels, int numberOfFakeWheels, int numberOfDraws, WheelsAndSquares game) {
		try {
			board = new BoardStage(game);

			random.setSeed(randomSeed);

			//Need to know what level to create, how many tiles it should have, How many coloured tiles, how many fake wheels, how many draws.
			int ranValueX = 0;
			int ranValueY = 0;
			this.numbersOftilesX = numbersOfTilesX;
			this.numbersOfTilesY = numbersOfTilesY;

			//TODO: Check if the level previously is made. Maybe not?

			//Fill the tileArray with blank tiles
			board.setEmptyBoard(numbersOfTilesX, numbersOfTilesY);

			//Place the coloured tiles on a random tile
			//And place a full colored wheel, with the same colour on the wheel array on the same coordinate
			int i = 0;
			int falseItterations = 0;
			do {
				ranValueX = random.nextInt(numbersOfTilesX);
				ranValueY = random.nextInt(numbersOfTilesY);

				if (board.getTileColourValue(ranValueX, ranValueY) == 0)
				{
					int ranColour = random.nextInt(BoardStage.differentKindsOfColours) + 1;    //There are five colours to choose from
					board.setTileColour(ranValueX, ranValueY, ranColour);
					board.placeNewWheel(new Vector2(ranValueX, ranValueY), ranColour);
					i++;
				} else {
					falseItterations++;
					if (falseItterations > 100) {
						break;
					}
				}
			}
			while (i < numberOfColouredTiles);

			//Place fake wheels
			i = 0;
			do {
				ranValueX = random.nextInt(numbersOfTilesX);
				ranValueY = random.nextInt(numbersOfTilesY);

				boolean wheelPlacedOk = board.placeNewWheel(new Vector2(ranValueX, ranValueY), Wheel.WheelTypes.getRandomValue(random.nextInt(2) + 1));
				if (wheelPlacedOk)
					i++;
				else
					falseItterations++;

				if (falseItterations > 100)
					break;
			}
			while (i < numberOfFakeWheels);

			if (falseItterations >= 100) {
				LogHelper.Log(TAG, "Fake wheels: falseIttterations is more than 100, (X,Y): (" + ranValueX +","+ ranValueY+")", Application.LOG_INFO);
			}

			//Place Helper wheels
			i = 0;
			do {
				ranValueX = random.nextInt(numbersOfTilesX);
				ranValueY = random.nextInt(numbersOfTilesY);

				boolean wheelPlacedOk = board.placeNewWheel(new Vector2(ranValueX, ranValueY), Wheel.WheelTypes.getRandomValue(random.nextInt(2) + 1));
				if (wheelPlacedOk)
					i++;
				else
					falseItterations++;

				if (falseItterations > 100)
					break;
			}
			while (i < numberOfFakeWheels);

			if (falseItterations >= 100) {
				LogHelper.Log(TAG, "Helper wheels: falseIttterations is more than 100, (X,Y): (" + ranValueX +","+ ranValueY+")", Application.LOG_INFO);
			}
			LogHelper.Log(TAG, "Final random number: " + Integer.toString(random.nextInt()), Application.LOG_INFO);
		}

		catch (Exception ex) {
			LogHelper.Log(TAG, ex.getMessage(), Application.LOG_ERROR);
		}
	}

}
