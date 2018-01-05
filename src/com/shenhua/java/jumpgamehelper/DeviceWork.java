package com.shenhua.java.jumpgamehelper;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

/**
 * Created by shenhua on 2018-01-04-0004.
 *
 * @author shenhua
 *         Email shenhuanet@126.com
 */
class DeviceWork {

    private AppWindow appWindow;
    private JLabel mPreview;
    private AdbHelper adbHelper;
    private int phoneWidth = 720;
    private int phoneHeight = 1280;
    private Point startPoint;
    private Point endPoint;

    DeviceWork(AppWindow appWindow) {
        this.appWindow = appWindow;
        mPreview = appWindow.getPreview();
        startPoint = new Point(0, 0);
        endPoint = new Point();
    }

    void start() {
        EventQueue.invokeLater(() -> {
            if (AdbHelper.isAdbEmpty(appWindow.getAdbPathField().getText())) {
                log("ADB路径配置错误,请重新配置");
                return;
            }
            adbHelper = new AdbHelper(appWindow.getAdbPathField().getText());
            String phone = adbHelper.getDevice();
            appWindow.getPhoneLabel().setText("手机: " + phone);
            String pix = adbHelper.getPix();
            appWindow.getPhonePixLabel().setText("像素: " + pix);
            System.out.println("像素: " + parseSize(pix));
            float rate = (phoneHeight / (AppWindow.PREVIEW_HEIGHT * 1f));
            System.out.println("比例: " + rate);
            appWindow.getPhoneRateLabel().setText("比例: " + rate);

            refreshPreview();
            mPreview.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    log("x:" + e.getX() * rate + " y:" + e.getY() * rate);
                    endPoint.setLocation(e.getX() * rate, e.getY() * rate);
                    int distance = JumpHelper.getDistance(startPoint, endPoint);
                    log("distance:" + distance);
                    adbHelper.press((int) (distance * (1468.125f / (phoneWidth * 1f))));
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                    refreshPreview();
                }
            });
            log("初始化完成");
        });
    }

    void refreshPreview() {
        ImageIcon image = adbHelper.getPreview();
        mPreview.setBounds((340 - getImageScaleWidth()) / 2, 10, getImageScaleWidth(), AppWindow.PREVIEW_HEIGHT);
        image.setImage(image.getImage().getScaledInstance(getImageScaleWidth(), AppWindow.PREVIEW_HEIGHT, Image.SCALE_DEFAULT));
        mPreview.setText(null);
        mPreview.setIcon(image);
        try {
            startPoint.setLocation(JumpHelper.findStartPoint(ImageIO.read(new File("D:\\jump.png"))));
            log(startPoint.toString());
        } catch (IOException e1) {
            e1.printStackTrace();
            log("起点失败");
        }
    }

    private int getImageScaleWidth() {
        long a = Math.round(phoneWidth * AppWindow.PREVIEW_HEIGHT / phoneHeight);
        return (int) a;
    }

    private void log(String string) {
        appWindow.getJTextArea().append(string + "\n");
    }

    private Point parseSize(String size) {
        try {
            String[] split = size.split(":");
            size = split[1].trim();
            String[] xes = size.split("x");
            phoneWidth = Integer.parseInt(xes[0]);
            phoneHeight = Integer.parseInt(xes[1]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Point(phoneWidth, phoneHeight);
    }
}
