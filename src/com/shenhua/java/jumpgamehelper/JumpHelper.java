package com.shenhua.java.jumpgamehelper;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by shenhua on 2018-01-05-0005.
 *
 * @author shenhua
 *         Email shenhuanet@126.com
 */
public class JumpHelper {

    public static Point findStartPoint(BufferedImage bufferedImage) {
        Point point = new Point(0, 0);
        if (bufferedImage == null) {
            System.out.println("null");
            return point;
        }
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        int color;
        int[] rgb = new int[3];

        for (int h = 200; h < height - 200; h++) {
            for (int w = 70; w < width - 70; w++) {
                color = bufferedImage.getRGB(w, h);
                rgb[0] = (color & 0xff0000) >> 16;
                rgb[1] = (color & 0xff00) >> 8;
                rgb[2] = (color & 0xff);
                if (rgb[1] == 55 && rgb[0] + rgb[2] > 145 && rgb[0] + rgb[2] < 148) {
                    point.setLocation(w, h);
                    return point;
                }
            }
        }
        return point;
    }

    public static int getDistance(Point start, Point end) {
        return (int) Math.sqrt((start.getX() - end.getX()) * (start.getX() - end.getX())
                + (start.getY() - end.getY()) * (start.getY() - end.getY()));
    }

}
