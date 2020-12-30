package com.sayurklik.app.helpers;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;

import java.lang.reflect.Field;

public class FontsHelper extends Application {
    String URL_FONT = "font/Lato-Light.ttf";

    @Override
    public void onCreate() {
        super.onCreate();
        TypeFace.setDefaultFont(this, "DEFAULT", "Roboto/Roboto-Regular.ttf");
        TypeFace.setDefaultFont(this, "MONOSPACE", "Roboto/Roboto-Italic.ttf");
        TypeFace.setDefaultFont(this, "SERIF", "Lato/Lato-Regular.ttf");
        TypeFace.setDefaultFont(this, "SANS_SERIF", "Roboto/Roboto-Light.ttf");
    }

    static class TypeFace {
        private static void setDefaultFont(Context context,
                                           String staticTypefaceFieldName, String fontAssetName) {
            final Typeface customFontTypeFace = Typeface.createFromAsset(context.getAssets(),
                    fontAssetName);
            replaceFont(staticTypefaceFieldName, customFontTypeFace);
        }

        protected static void replaceFont(String staticTypefaceFieldName,
                                          final Typeface newTypeface) {
            try {
                final Field staticField = Typeface.class
                        .getDeclaredField(staticTypefaceFieldName);
                staticField.setAccessible(true);
                staticField.set(null, newTypeface);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
