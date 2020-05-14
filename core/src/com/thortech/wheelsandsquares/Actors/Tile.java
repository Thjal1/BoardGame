package com.thortech.wheelsandsquares.Actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.thortech.wheelsandsquares.Actors.AbstractActor;
import com.thortech.wheelsandsquares.Actors.Board;
import com.thortech.wheelsandsquares.Settings;
import com.thortech.wheelsandsquares.WheelsAndSquares;

public class Tile extends AbstractActor {
	private static final String TAG = Board.class.getName();

	private float regionHeight;
	private float regionWidth;
	private Vector3 centerV3;
	private Texture tileImage;
	private float elapsedTime = 0;

	public Tile(WheelsAndSquares _game)
	{
		super(_game);
		create();
	}


	private void create(){

		regionHeight = Settings.PHYSICALSCALE;
		regionWidth = Settings.PHYSICALSCALE;


		centerV3 = new Vector3(0,0,0);

	}

	@Override
	public void render (float dt) {

		elapsedTime += dt;
	}

	public Vector3 getPos(){
		return centerV3;
	}
	public void changePos(Vector3 v3, boolean center)
	{
		if (center == false){
			this.centerV3 = v3;
		}
		else{
			this.centerV3.y = v3.y - regionWidth / 2;
			this.centerV3.x = v3.x - regionHeight / 2;
		}
	}
	public void setState(String _state){

	}

	public void dispose() {

	}
}