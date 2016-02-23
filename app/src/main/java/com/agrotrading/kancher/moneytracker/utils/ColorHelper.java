package com.agrotrading.kancher.moneytracker.utils;

import android.graphics.Color;

import java.util.Random;

public class ColorHelper {

    public static int getRandomColor() {
        Random random = new Random();
        return Color.argb(128, random.nextInt(256), random.nextInt(256), random.nextInt(256));
    }

    public static int RemoveAlpha(int foreground, int background)
    {
        float alpha;
        float diff;

        if (Color.alpha(foreground) == 255) return foreground;

        alpha = (float) (Color.alpha(foreground) / 255.0);
        diff = (float) (1.0 - alpha);

        return Color.rgb(
                Math.round((float) Color.red(foreground) * alpha + Color.red(background) * diff),
                Math.round((float) Color.green(foreground) * alpha + Color.green(background) * diff),
                Math.round((float) Color.blue(foreground) * alpha + Color.blue(background) * diff)
        );
    }
}
