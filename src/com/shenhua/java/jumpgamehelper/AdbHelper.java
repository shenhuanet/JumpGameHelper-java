package com.shenhua.java.jumpgamehelper;

import javax.swing.*;
import java.io.*;

/**
 * Created by shenhua on 2018-01-04-0004.
 *
 * @author shenhua
 *         Email shenhuanet@126.com
 */
class AdbHelper {

    private String adbPath;
    private static String SD_FILE = "/sdcard/screenshot.png";
    private static String IMG_FILE = "D:jump.png";

    AdbHelper(String adbPath) {
        this.adbPath = adbPath;
    }

    ImageIcon getPreview() {
        ImageIcon image = new ImageIcon(IMG_FILE);
        try {
            executeShellCommand("shell screencap -p " + SD_FILE);
            executeShellCommand("pull " + SD_FILE + " " + IMG_FILE);
            return image;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }

    String getPix() {
        return executeShellCommand("shell wm size");
    }

    String getDevice() {
        return executeShellCommand("shell getprop ro.product.model");
    }

    void click(int x, int y) {
        executeShellCommand("shell input tap " + x + " " + y);
    }

    void press(int time) {
        executeShellCommand("shell input touchscreen swipe 500 500 500 500 " + time);
    }

    private String executeShellCommand(String command) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            Process process = Runtime.getRuntime().exec(adbPath + " " + command);
            InputStreamReader is = new InputStreamReader(process.getInputStream(), "utf-8");
            BufferedReader reader = new BufferedReader(is);
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            return stringBuilder.toString().trim();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    static boolean isAdbEmpty(String adb) {
        return adb == null || adb.length() < 0 || !adb.contains("adb.exe");
    }

}
