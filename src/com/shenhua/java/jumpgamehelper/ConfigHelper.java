package com.shenhua.java.jumpgamehelper;

import java.io.*;

/**
 * Created by shenhua on 2018-01-04-0004.
 *
 * @author shenhua
 *         Email shenhuanet@126.com
 */
public class ConfigHelper {

    private String file;

    public ConfigHelper() {
        this.file = "D:\\jump.ini";
    }

    public ConfigHelper(String file) {
        this.file = file;
    }

    public void write(String txt) {
        try {
            FileWriter fw = new FileWriter(file);
            fw.write(txt);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String read() {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis, "utf-8");
            BufferedReader reader = new BufferedReader(isr);
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
