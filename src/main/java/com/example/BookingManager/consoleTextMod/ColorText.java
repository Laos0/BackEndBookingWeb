/*
    This class provides color text to the console
    All strings are static and can be used anywhere without
    an instance of this class
 */

package com.example.BookingManager.consoleTextMod;

public class ColorText {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static void testTemplate(String className, String methodName, String variable){
        System.out.println();
    }

    public static void errorTemplate(String s){
        System.out.println();
    }
}
