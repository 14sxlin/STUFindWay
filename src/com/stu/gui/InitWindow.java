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
	private Thread splashThread; // �����������߳�
	private JProgressBar progress; // ������

	public InitWindow() throws IOException {
		Container container = getContentPane(); // �õ�����
		Image image = ImageIO.read(getClass().getResourceAsStream("/pic/stu.jpg")); // ͼƬ��λ��
		if (image != null) {
			container.add(new JLabel(new ImageIcon(image)), BorderLayout.CENTER); // ����ͼƬ
		}
		progress = new JProgressBar(1, 100); 				// ʵ����������
		progress.setStringPainted(true); 					// �������
//		progress.setString("���س�����,���Ժ�......"); 		// ������ʾ����
		
		progress.setBackground(Color.white); 				// ���ñ���ɫ
		container.add(progress, BorderLayout.SOUTH); 		// ���ӽ�������������

		Dimension screen = getToolkit().getScreenSize(); 	// �õ���Ļ�ߴ�
		pack(); // ������Ӧ����ߴ�
		setLocation((screen.width - getSize().width) / 2,
				(screen.height - getSize().height) / 2); 	// ���ô���λ��
	}

	public void start() {
		this.toFront(); 									// ����ǰ����ʾ
		splashThread = new Thread(this); 					// ʵ�����߳�
		splashThread.start(); 								// ��ʼ�����߳�
	}

	public void run() {
		setVisible(true); 									// ��ʾ����
		try {
			Configuration.check();
			for (int i = 0; i < 100; i++) {
				Thread.sleep(10); // �߳�����
				progress.setValue(progress.getValue() + 1); // ���ý�����ֵ
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		dispose();											// �ͷŴ���
		showFrame(); 										// ����������
	}

	static void showFrame() {
		mainFrame = new MainFrame();
		mainFrame.setVisible(true);
	}

	public static void main(String[] args) throws IOException {
		InitWindow splash = new InitWindow();
		splash.start(); // ������������
	}
}

	    			