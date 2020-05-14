package com.thortech.wheelsandsquares;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.thortech.wheelsandsquares.WheelsAndSquares;

import static com.thortech.wheelsandsquares.WheelsAndSquares.platformSpecific;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

		PlatformSpecificAndroid platformSpecific = new PlatformSpecificAndroid();

		initialize(new WheelsAndSquares(platformSpecific), config);
	}
}
