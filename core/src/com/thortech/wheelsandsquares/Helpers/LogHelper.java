package com.thortech.wheelsandsquares.Helpers;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationLogger;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.thortech.wheelsandsquares.Settings;
import com.thortech.wheelsandsquares.WheelsAndSquares;

import java.time.LocalDateTime;

public class LogHelper {

    public static final String TAG = com.thortech.wheelsandsquares.Helpers.LogHelper.class.getName();
    private static LogHelper instance = new LogHelper();

    private static final String logFileName = "thortech_wheelsandsquares_logFile";
    private static final String logFileSuffix = ".txt";
    private static String logFileDateTime = "";
    private static FileHandle logFile;

    private LogHelper()
    {
        if (WheelsAndSquares.platformSpecific.isDebug()) {
           Gdx.app.setLogLevel(Application.LOG_DEBUG);    //Set log level to debug if the application is running in debug mode (very verbose)
           LocalDateTime localDate = LocalDateTime.now(); // Create a date object
            logFileDateTime = localDate.toString().replaceAll(":","-");

            logFile = Gdx.files.local(logFileName+logFileDateTime+logFileSuffix);
            Gdx.app.log(TAG, "logFile name: " + logFile.name());
        }
        else
            Gdx.app.setLogLevel(Application.LOG_INFO);	//Make sure that intelliJ will be verbose in the run window, when calling the Gdx.app.log()
    }

    public static void Log(String tag, String message, int logLevel)
    {
        //Didn't find a better way to do this...
        String logType;
        switch (logLevel) {
            case Application.LOG_NONE:
                logType = "None";
                break;
            case Application.LOG_ERROR:
                logType = "Error";
                break;
            case Application.LOG_INFO:
                logType = "Info";
                break;
            case Application.LOG_DEBUG:
                logType = "Debug";
                break;
            default:
                logType = "";
        }
        Gdx.app.log(tag, message);
        if(WheelsAndSquares.platformSpecific.isDebug())
            logFile.writeString(logType +" - "+ tag + ": "+message + '\n', true );
    }
}
