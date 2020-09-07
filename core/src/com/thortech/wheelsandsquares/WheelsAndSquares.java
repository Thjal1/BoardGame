package com.thortech.wheelsandsquares;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.thortech.wheelsandsquares.Helpers.LogHelper;
import com.thortech.wheelsandsquares.Screens.ScreenCredits;
import com.thortech.wheelsandsquares.Screens.ScreenLoad;
import com.thortech.wheelsandsquares.Screens.ScreenGame;
import com.thortech.wheelsandsquares.Screens.ScreenMenu;
import com.thortech.wheelsandsquares.Screens.ScreenPreferences;


public class WheelsAndSquares extends Game {

	//	public static GameShell game = new GameShell();
	private static final String TAG = WheelsAndSquares.class.getName();
	public static PlatformSpecific platformSpecific;

	//Define the screens to be used in the game
	public static Screen screenLoad;
	public static Screen screenMenu;
	public static Screen screenPreference;
	public static Screen screenCredits;
	public static Screen screenGame;

	public static Settings settings;

	public SpriteBatch batch;

	public WheelsAndSquares(PlatformSpecific _platformSpecific){
		platformSpecific = _platformSpecific;

	}

	@Override
	public void create () {
		try {
			batch = new SpriteBatch();
			Settings.instance.init();

			screenLoad = new ScreenLoad(this);
			screenMenu = new ScreenMenu(this);
			screenGame = new ScreenGame(this);
			screenPreference = new ScreenPreferences(this);
			screenCredits = new ScreenCredits(this);

			setScreen(screenLoad);				//Testing
/*
			Gdx.app.setLogLevel(Application.LOG_INFO);	//Make sure that intelliJ will be verbose in the run window, when calling the LogHelper.Log()
			LogHelper.Log(TAG, "Created OK");
*/
			LogHelper.Log(TAG, "Created OK", Application.LOG_INFO);

		}
		catch (final Exception ex) {
			LogHelper.Log(TAG, "create() failed" + ex.getMessage(), Application.LOG_ERROR);
			//TODO: Code for handling exceptions and/or exit the game OK.
		}
	}

	@Override
	public void resize(int width, int height) {
		getScreen().resize(width, height);
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose(){
		Gdx.app.exit();
	}
}