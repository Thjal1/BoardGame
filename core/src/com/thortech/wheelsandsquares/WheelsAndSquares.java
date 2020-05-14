package com.thortech.wheelsandsquares;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

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

			if(platformSpecific.isDebug())
				Gdx.app.log(TAG, "Created OK");

		}
		catch (final Exception ex) {
			Gdx.app.log(TAG, "create() failed", ex);
			//TODO: Code for handling exceptions and/or exit the game OK.
		}
	}

	@Override
	public void resize(int width, int height) {

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

	public void dispose(){

	}
}