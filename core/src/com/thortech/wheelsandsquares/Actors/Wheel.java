package com.thortech.wheelsandsquares.Actors;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.RotateByAction;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.thortech.wheelsandsquares.Helpers.LogHelper;
import com.thortech.wheelsandsquares.Settings;
import com.thortech.wheelsandsquares.WheelsAndSquares;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Wheel extends AbstractActor {
	private static final String TAG = Wheel.class.getName();
	private static int objNumber = 0;

	private float physicalHeight = 0.1f; //0.05f * Gdx.graphics.getHeight();
	private float physicalWidth = 0.1f; //0.05f * Gdx.graphics.getWidth();
	private Vector3 centerV3;
	private TextureRegion wheelTexture;
	private Sprite wheelSprite;
	private float elapsedTime = 0;
	private String state = "";
	private Vector2 fromCoordinates, toCoordinates;
	private static Random random = new Random();
	private float timeUsedToMoveTile = 1.0f;

	private WheelTypes type = WheelTypes.EMPTY;

	private String objName;

	private boolean selected = false;

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

		public static int getRandomValue(int maxTileParts)
		{
			return getRandomEnum(maxTileParts).value;
		}
	}

	public Wheel(WheelsAndSquares game) {
		super(game);
		create();
	}

	public Wheel(WheelsAndSquares game, Vector3 physicalCoordinates, Wheel.WheelTypes type)
	{
		super(game);
		create();
		changePos(physicalCoordinates, false);
		setType(type);
		prepareActor();
	}

	private void create() {

		objNumber++;
		objName = TAG + objNumber;

		centerV3 = new Vector3(0, 0, 0);
		fromCoordinates = new Vector2(0,0);
		toCoordinates = new Vector2(0, 0);
		state = "StandStill";

		random.setSeed(Settings.getSeedForRandom());
	}

	public int getTypeValue() {
		return type.getValue();
	}

	public void setType(WheelTypes type)
	{
		this.type = type;
		String file = "General/GameGraphics/Wheel" + type.toString() + "128.png";
		wheelTexture = new TextureRegion(new Texture(file));

		wheelSprite = new Sprite(wheelTexture);

		wheelSprite.setSize(physicalWidth* Gdx.graphics.getWidth(),physicalHeight * Gdx.graphics.getHeight());
	}

	//Prepare the actor for being an actor on the stage
	private void prepareActor(){
		setBounds(centerV3.x, centerV3.y, wheelSprite.getWidth(), wheelSprite.getHeight());
		setPosition(centerV3.x, centerV3.y);
		setOrigin(this.getWidth()/2, this.getHeight()/2);
		setTouchable(Touchable.enabled);

		final MoveByAction moveByAction = new MoveByAction();
		final RotateByAction rotateByAction = new RotateByAction();

		addListener(new ActorGestureListener(){

			@Override
			public void pan(InputEvent event, float x, float y, float deltaX, float deltaY) {
				super.pan(event, x, y, deltaX, deltaY);
				/*
				if(Wheel.this.getActions().notEmpty()) {
					if (moveByAction.isComplete()) {
						return;
					}
				}

				 */
				LogHelper.Log(objName, event.toString(), Application.LOG_INFO);
				moveByAction.setAmount(-getWidth(), 0f);
				moveByAction.setDuration(timeUsedToMoveTile);
				rotateByAction.setAmount(90);
				rotateByAction.setDuration(timeUsedToMoveTile);

				Wheel.this.addAction(moveByAction);
				Wheel.this.addAction(rotateByAction);
			}
		});

		addListener(new InputListener(){

			@Override
			public boolean keyDown(InputEvent event, int keycode){
				LogHelper.Log(objName, event.toString(), Application.LOG_INFO);
				if(!selected)
					return false;
				if(keycode == Input.Keys.RIGHT){
					LogHelper.Log(objName, event.toString(), Application.LOG_INFO);
					moveByAction.setAmount(getWidth(), 0f);
					moveByAction.setDuration(timeUsedToMoveTile);
					rotateByAction.setAmount(-90);
					rotateByAction.setDuration(timeUsedToMoveTile);

					Wheel.this.addAction(moveByAction);
					Wheel.this.addAction(rotateByAction);
				}
				return true;
			}

			@Override
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button)
			{
				LogHelper.Log(objName, event.toString(), Application.LOG_INFO);

				MoveByAction moveByAction = new MoveByAction();
				RotateByAction rotateByAction = new RotateByAction();
				moveByAction.setAmount(getWidth(), 0f);
				moveByAction.setDuration(timeUsedToMoveTile);
				rotateByAction.setAmount(-90);
				rotateByAction.setDuration(timeUsedToMoveTile);

				Wheel.this.addAction(moveByAction);
				Wheel.this.addAction(rotateByAction);

				//((Wheel)event.getTarget()).selected = true;

				selected = true;
				return true;
			}
		});

	}

	@Override
	public void render(float dt) {
		//Never called
		game.batch.draw(wheelTexture, this.getX(), this.getY(), this.getOriginX(), this.getOriginY(),this.getWidth(), this.getHeight(), this.getScaleX(), this.getScaleY(), this.getRotation());
	}
	@Override
	public void draw(Batch batch, float parentAlpha) {
		//batch.draw(wheelTexture, this.getX(), this.getY(), this.getOriginX(), this.getOriginY(),this.getWidth(), this.getHeight(), this.getScaleX(), this.getScaleY(), this.getRotation());
		game.batch.draw(wheelTexture, this.getX(), this.getY(), this.getOriginX(), this.getOriginY(),this.getWidth(), this.getHeight(), this.getScaleX(), this.getScaleY(), this.getRotation());
	}

	@Override
	public void act(float delta)
	{
		super.act(delta);
	}

	/*
	@Override
	protected void positionChanged(){
		wheelSprite.setPosition(getX(), getY());
		super.positionChanged();
	}
*/
	public Vector3 getPhysicalPosistion() {
		return centerV3;
	}
	public Vector2 getFromCoordinates() { return fromCoordinates; }
	public Vector2 toCoordinates() { return toCoordinates; }

	public void changePos(Vector3 v3, boolean center) {
		v3.x = v3.x * Gdx.graphics.getWidth();
		v3.y = v3.y * Gdx.graphics.getHeight();
		if (center == false) {
			this.centerV3 = v3;

		} else {
			this.centerV3.y = v3.y - physicalWidth / 2;
			this.centerV3.x = v3.x - physicalHeight / 2;
		}
		/*
		this.setX(centerV3.x);
		this.setY(centerV3.y);
		positionChanged();
		 */
	}

	public void moveToCoordinate(Vector2 moveTo)
	{
		toCoordinates = moveTo;
		setState("Moving");
	}

	@Override
	public String getName()
	{
		return objName;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void dispose() {
		wheelSprite.getTexture().dispose();
	}
}