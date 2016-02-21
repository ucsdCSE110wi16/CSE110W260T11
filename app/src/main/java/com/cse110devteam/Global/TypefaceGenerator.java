package com.cse110devteam.Global;

import android.content.res.AssetManager;
import android.graphics.Typeface;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by anthonyaltieri on 2/21/16.
 */
public class TypefaceGenerator {

    public static Typeface get(String font, AssetManager am){
        switch (font) {
            case "roboto": return Typeface.createFromAsset(am, "fonts/Roboto-Regular.ttf");
            case "robotoBlack": return Typeface.createFromAsset(am, "fonts/Roboto-Black.ttf");
            case "robotoLight": return Typeface.createFromAsset(am, "fonts/Roboto-Light.ttf");
            case "robotoBold": return Typeface.createFromAsset(am, "fonts/Roboto-Bold.ttf");
            case "robotoMedium": return Typeface.createFromAsset(am, "fonts/Roboto-Medium.ttf");
        }
        return null;
    }

}
