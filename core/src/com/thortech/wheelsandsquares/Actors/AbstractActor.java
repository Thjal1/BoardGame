package com.thortech.wheelsandsquares.Actors;

import com.badlogic.gdx.math.Vector3;
import com.thortech.wheelsandsquares.WheelsAndSquares;

/**
 * Created by ToHj on 28-02-2016.
 */
public abstract class AbstractActor {

	protected WheelsAndSquares game;

	public AbstractActor (WheelsAndSquares _game) {
		game = _game;
	}

	public abstract void changePos(Vector3 v3, boolean center);
	public abstract void render(float dt);

	public abstract void dispose();
}