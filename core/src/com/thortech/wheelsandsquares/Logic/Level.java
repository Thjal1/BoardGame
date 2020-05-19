package com.thortech.wheelsandsquares.Logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.thortech.wheelsandsquares.Actors.AbstractActor;
import com.thortech.wheelsandsquares.Actors.Board;
import com.thortech.wheelsandsquares.WheelsAndSquares;

import java.util.Random;


public class Level extends AbstractActor{
	public static final String TAG = com.thortech.wheelsandsquares.Logic.Level.class.getName();

	private int levelSeed;            //Seed number explaining how the level is build.
	private int numbersOftilesX;
	private int numbersOfTilesY;
	private Random random = new Random();

	private Board board;


	public Level (WheelsAndSquares _game) {
		super(_game);
		LoadLevel();
	}

	@Override
	public void changePos(Vector3 v3, boolean center) {

	}

	@Override
	public void render(float dt) {

	}

	@Override
	public void dispose() {
		board.dispose();
	}

	private void LoadLevel() {
		try {
			//levelInformation = Gdx.app.getPreferences(levelFile);

		} catch (Exception ex) {
			Gdx.app.log(TAG, ex.getMessage());
		}
	}

	public void createBoard(int randomSeed, int levelNumber, int numbersOfTilesX, int numbersOfTilesY, int numberOfColouredTiles, int numberOfHelperWheels, int numberOfFakeWheels, int numberOfDraws, WheelsAndSquares game) {
		try {

			board = new Board(game);

			random.setSeed(randomSeed);

			//Need to know what level to create, how many tiles it should have, How many coloured tiles, how many fake wheels, how many draws.
			int ranValueX = 0;
			int ranValueY = 0;
			this.numbersOftilesX = numbersOfTilesX;
			this.numbersOfTilesY = numbersOfTilesY;

			//TODO: Check if the level previously is made.

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
					int ranColour = random.nextInt(Board.differentKindsOfColours) + 1;    //There are five colours to choose from
					board.setTileColour(ranValueX, ranValueY, ranColour);

//					wheelArray.get(ranValueX).set(ranValueY, wheelType);
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
/*
			do {
				ranValueX = random.nextInt(numbersOfTilesX);
				ranValueY = random.nextInt(numbersOfTilesY);

				if (wheelArray.get(ranValueX).get(ranValueY) == WheelTypes.EMPTY) {
					WheelTypes wheelType = WheelTypes.getRandomEnum(random.nextInt(2) + 1);
					wheelArray.get(ranValueX).set(ranValueY, wheelType);
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
*/
		} catch (Exception ex) {
			Gdx.app.log(TAG, ex.getMessage());
		}
	}

	public Board getBoard()
	{
		return board;
	}
}
