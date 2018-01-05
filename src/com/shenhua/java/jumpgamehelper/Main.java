package com.shenhua.java.jumpgamehelper;

import javax.swing.*;
import java.awt.*;

/**
 * Created by shenhua on 2018-01-04-0004.
 *
 * @author shenhua
 *         Email shenhuanet@126.com
 */
public class Main {

    private static JFrame content;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> content = new AppWindow());
    }
}
