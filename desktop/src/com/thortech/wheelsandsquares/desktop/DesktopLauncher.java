package com.thortech.wheelsandsquares.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.thortech.wheelsandsquares.PlatformSpecificDesktop;
import com.thortech.wheelsandsquares.WheelsAndSquares;

public class DesktopLauncher {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();

		cfg.title = "WheelsAndSquares";
		cfg.width = 800; //1920;
		cfg.height = 600; //1080;
		PlatformSpecificDesktop platformSpecific = new PlatformSpecificDesktop();
		new LwjglApplication(new WheelsAndSquares(platformSpecific), cfg);
	}
}
