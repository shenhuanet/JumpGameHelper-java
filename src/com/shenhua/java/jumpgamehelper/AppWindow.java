package com.shenhua.java.jumpgamehelper;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

/**
 * Created by shenhua on 2018-01-04-0004.
 *
 * @author shenhua
 *         Email shenhuanet@126.com
 */
public class AppWindow extends JFrame {

    private static final int WIDTH = 590;
    private static final int HEIGHT = 530;
    private static final int PREVIEW_WIDTH = 320;
    static final int PREVIEW_HEIGHT = 480;
    private JLabel mPreview;
    private JTextField mAdbPathField;
    private JTextArea mJTextArea;
    private JLabel mPhoneLabel;
    private JLabel mPhonePixLabel;
    private JLabel mPhoneRateLabel;
    private DeviceWork deviceWork;

    AppWindow() throws HeadlessException {
        super("跳一跳小游戏辅助");
        setResizable(false);
        setBounds((getScreenWidth() - WIDTH) / 2,
                (getScreenHeight() - HEIGHT) / 2, WIDTH, HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getContentPane().add(createContent());
        setVisible(true);

        initData();
    }

    private void initData() {
        deviceWork = new DeviceWork(this);
        deviceWork.start();
    }

    private JPanel createContent() {
        JPanel content = new JPanel();
        content.setLayout(null);

        mPreview = new JLabel("预览", JLabel.CENTER);
        mPreview.setBorder(new LineBorder(Color.GRAY, 1));
        JLabel tagAdb = new JLabel("ADB 路径:");
        mAdbPathField = new JTextField();
        // adbPath.setEditable(false);
        JButton select = new JButton("...");
        mPhoneLabel = new JLabel("手机:");
        mPhonePixLabel = new JLabel("像素:");
        mPhoneRateLabel = new JLabel("比例:");
        JRadioButton jb1 = new JRadioButton("手动模式");
        createButtonGroup(jb1);
        JButton refresh = new JButton("刷新界面");
        JButton refreshDevice = new JButton("刷新设备");
        mJTextArea = new JTextArea();
        mJTextArea.setLineWrap(true);
        mJTextArea.setEditable(false);
        JScrollPane jScrollPane = new JScrollPane(mJTextArea);

        content.add(mPreview);
        content.add(tagAdb);
        content.add(mAdbPathField);
        content.add(select);
        content.add(mPhoneLabel);
        content.add(mPhonePixLabel);
        content.add(mPhoneRateLabel);
        content.add(jb1);
        content.add(refresh);
        content.add(refreshDevice);
        content.add(jScrollPane);

        mPreview.setBounds(10, 10, PREVIEW_WIDTH, PREVIEW_HEIGHT);
        tagAdb.setBounds(340, 10, 90, 24);
        mAdbPathField.setBounds(340, 36, 200, 24);
        select.setBounds(550, 37, 20, 20);
        mPhoneLabel.setBounds(340, 60, 200, 24);
        mPhonePixLabel.setBounds(340, 86, 200, 24);
        mPhoneRateLabel.setBounds(340, 112, 200, 24);
        jb1.setBounds(340, 140, 80, 24);
        refresh.setBounds(340, 450, 100, 40);
        refreshDevice.setBounds(470, 450, 100, 40);
        jScrollPane.setBounds(340, 170, 230, 270);

        addListener(refreshDevice, refresh, select);
        mAdbPathField.setText(new ConfigHelper().read());
        return content;
    }

    private void addListener(JButton refreshDevice, JButton refresh, JButton select) {
        refreshDevice.addActionListener(e -> deviceWork.start());
        refresh.addActionListener(e -> deviceWork.refreshPreview());
        select.addActionListener(e -> {
                    JFileChooser chooser = new JFileChooser();
                    chooser.setCurrentDirectory(new File("D:/"));
                    chooser.setDialogTitle("选择ADB文件");
                    chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                    chooser.setFileFilter(new FileNameExtensionFilter("adb.exe", "exe"));
                    int result = chooser.showOpenDialog(AppWindow.this);
                    if (result == JFileChooser.APPROVE_OPTION) {
                        String name = chooser.getSelectedFile().getPath();
                        getAdbPathField().setText(name);
                        new ConfigHelper().write(name);
                        deviceWork.start();
                    }
                }
        );
    }

    private void createButtonGroup(JRadioButton... buttons) {
        ButtonGroup group = new ButtonGroup();
        if (buttons.length > 0) {
            buttons[0].setSelected(true);
        }
        for (JRadioButton button : buttons) {
            group.add(button);
        }
    }

    private int getScreenWidth() {
        return Toolkit.getDefaultToolkit().getScreenSize().width;
    }

    private int getScreenHeight() {
        return Toolkit.getDefaultToolkit().getScreenSize().height;
    }

    public JTextField getAdbPathField() {
        return mAdbPathField;
    }

    public JLabel getPhoneLabel() {
        return mPhoneLabel;
    }

    public JLabel getPhonePixLabel() {
        return mPhonePixLabel;
    }

    public JTextArea getJTextArea() {
        return mJTextArea;
    }

    public JLabel getPhoneRateLabel() {
        return mPhoneRateLabel;
    }

    public JLabel getPreview() {
        return mPreview;
    }
}
