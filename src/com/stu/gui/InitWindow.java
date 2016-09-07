package com.stu.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JWindow;

import com.stu.database.Configuration;

@SuppressWarnings("serial")
public class InitWindow extends JWindow implements Runnable {
	private static MainFrame mainFrame;
	private Thread splashThread; // 进度条更新线程
	private JProgressBar progress; // 进度条

	public InitWindow() throws IOException {
		Container container = getContentPane(); // 得到容器
		Image image = ImageIO.read(getClass().getResourceAsStream("/pic/stu.jpg")); // 图片的位置
		if (image != null) {
			container.add(new JLabel(new ImageIcon(image)), BorderLayout.CENTER); // 增加图片
		}
		progress = new JProgressBar(1, 100); 				// 实例化进度条
		progress.setStringPainted(true); 					// 描绘文字
//		progress.setString("加载程序中,请稍候......"); 		// 设置显示文字
		
		progress.setBackground(Color.white); 				// 设置背景色
		container.add(progress, BorderLayout.SOUTH); 		// 增加进度条到容器上

		Dimension screen = getToolkit().getScreenSize(); 	// 得到屏幕尺寸
		pack(); // 窗口适应组件尺寸
		setLocation((screen.width - getSize().width) / 2,
				(screen.height - getSize().height) / 2); 	// 设置窗口位置
	}

	public void start() {
		this.toFront(); 									// 窗口前端显示
		splashThread = new Thread(this); 					// 实例化线程
		splashThread.start(); 								// 开始运行线程
	}

	public void run() {
		setVisible(true); 									// 显示窗口
		try {
			Configuration.check();
			for (int i = 0; i < 100; i++) {
				Thread.sleep(10); // 线程休眠
				progress.setValue(progress.getValue() + 1); // 设置进度条值
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		dispose();											// 释放窗口
		showFrame(); 										// 运行主程序
	}

	static void showFrame() {
		mainFrame = new MainFrame();
		mainFrame.setVisible(true);
	}

	public static void main(String[] args) throws IOException {
		InitWindow splash = new InitWindow();
		splash.start(); // 运行启动界面
	}
}

	    			