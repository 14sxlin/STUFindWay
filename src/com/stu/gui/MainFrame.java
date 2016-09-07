package com.stu.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.stu.database.Configuration;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {

	private JButton visit,admin,free;
	private JFrame visitF,adminF,freeF;
	public MainFrame() {
		this.setTitle("选择模式");
		
		this.setLayout(new GridBagLayout());

		visit = new JButton("参观者模式");
		free = new JButton("自由模式");
		admin = new JButton("管理员模式");
		
		
		visit.addActionListener(e->{
			visitF = new VisitModeFrame();
			visitF.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			visitF.setVisible(true);
		});
		free.addActionListener(e->{
			freeF = new FreeModeFrame();
			freeF.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			freeF.setVisible(true);
		});
		admin.addActionListener(e->{
			WayPointManagePanel panel = new WayPointManagePanel(
					Configuration.WAYPONINTMODELPATH,
					Configuration.PLACEMODELPATH,
					true);
			adminF = new JFrame();
			adminF.add(panel);
			adminF.setSize(1500,(int)Toolkit.getDefaultToolkit().getScreenSize().getHeight()-50);
			adminF.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			adminF.setVisible(true);
		});
		
		GridBagConstraints con= new GridBagConstraints();
		
		JLabel picLabel = new JLabel();
		try {
			picLabel.setIcon(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/pic/stu.jpg"))));
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(this, "图片载入错误");
		}
		
		con.fill =GridBagConstraints.BOTH;
		con.gridwidth = GridBagConstraints.REMAINDER;
		this.add(picLabel, con);
		
//		con.fill = GridBagConstraints.BOth;
		con.gridwidth = 1;
		con.weightx = 1;
		con.weighty =0.1;
//		con.insets.left = 10;
//		con.insets.right = 10;
		
		this.add(visit, con);
		this.add(free, con);
		this.add(admin, con);
		
		this.setTitle("汕大寻路");
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize((int)dimension.getWidth()/2,(int)dimension.getHeight()*3/4-50);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		new MainFrame().setVisible(true);

	}

}
