package com.shatteredpixel.shatteredpixeldungeon.utils;

import java.util.Locale;

public class Utils {

    public static String capitalize( String str ) {
        return Character.toUpperCase( str.charAt( 0 ) ) + str.substring( 1 );
    }

    public static String format( String format, Object...args ) {
        return String.format( Locale.ENGLISH, format, args );
    }

    public static String VOWELS	= "aoeiu";

    public static String indefinite( String noun ) {
        if (noun.length() == 0) {
            return "a";
        } else {
            return (VOWELS.indexOf( Character.toLowerCase( noun.charAt( 0 ) ) ) != -1 ? "an " : "a ") + noun;
        }
    }
}
