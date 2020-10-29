package com.thortech.wheelsandsquares.Actors;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.thortech.wheelsandsquares.WheelsAndSquares;

/**
 * Created by ToHj on 28-02-2016.
 */
public abstract class AbstractActor extends Actor {

	protected WheelsAndSquares game;
	protected static String objName;
	protected static int objNumber = 0;

	public AbstractActor (WheelsAndSquares game) {
		this.game = game;
	}

	public abstract void changePos(Vector3 v3, boolean center);
	public abstract void render(float dt);

	public abstract void dispose();
}