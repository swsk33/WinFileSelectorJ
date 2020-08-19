package swsk33.WFs;

import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import javax.swing.*;

class OverwriteTip {
	static int dx;
	static int dy;
	static boolean isOvt;

	/**
	 * @wbp.parser.entryPoint
	 */
	void fr() {
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension sc = kit.getScreenSize();
		JDialog jdt = new JDialog();
		jdt.setSize(375, 180);
		jdt.setUndecorated(true);
		jdt.setModal(true);
		jdt.setLocation(sc.width / 2 - 192, sc.height / 2 - 90);
		URL bg = DialogCore.class.getResource("res/bg-ovt.png");
		JLabel bl = new JLabel(new ImageIcon(bg)); // 把上面的图片对象加到一个名为bl的标签里
		bl.setBounds(0, 0, jdt.getWidth(), jdt.getHeight()); // 设置标签大小
		JPanel imagePanel = (JPanel) jdt.getContentPane(); // 把内容窗格转化为JPanel，否则不能用方法setOpaque()来使内容窗格透明
															// ，使内容窗格透明后才能显示背景图片
		imagePanel.setOpaque(false); // 把背景图片添加到分层窗格的最底层作为背景
		jdt.getLayeredPane().add(bl, new Integer(Integer.MIN_VALUE));
		jdt.addMouseListener(new MouseAdapter() { // 设置窗口可拖动，添加监听器
			public void mousePressed(MouseEvent e) { // 获取点击鼠标时的坐标
				dx = e.getPoint().x;
				dy = e.getPoint().y;
			}

			public void mouseReleased(MouseEvent e) {
				jdt.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
		jdt.addMouseMotionListener(new MouseMotionAdapter() { // 设置拖拽后，窗口的位置
			public void mouseDragged(MouseEvent e) {
				jdt.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
				jdt.setLocation(e.getXOnScreen() - dx, e.getYOnScreen() - dy);
			}
		});
		JLabel title = new JLabel("警告");
		title.setForeground(new Color(255, 69, 0));
		title.setFont(new Font("微软雅黑", Font.BOLD, 18));
		title.setBounds(5, 3, 42, 24);
		JLabel cont = new JLabel("<html>你定义的文件名已经存在，是否覆盖？<html>");
		cont.setForeground(new Color(148, 0, 211));
		cont.setBackground(Color.WHITE);
		cont.setFont(new Font("黑体", Font.BOLD, 18));
		cont.setBounds(27, 54, 323, 34);
		JButton ok = new JButton("是");
		ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isOvt = true;
				jdt.dispose();
			}
		});
		ok.setForeground(new Color(255, 20, 147));
		ok.setFont(new Font("黑体", Font.BOLD, 18));
		ok.setBounds(91, 121, 59, 34);
		ok.setContentAreaFilled(false);
		JButton no = new JButton("否");
		no.setForeground(new Color(34, 139, 34));
		no.setFont(new Font("黑体", Font.BOLD, 18));
		no.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				isOvt = false;
				jdt.dispose();
			}
		});
		no.setBounds(200, 121, 59, 34);
		no.setContentAreaFilled(false);
		JPanel jp = new JPanel();
		jp.setOpaque(false);
		jp.setLayout(null);
		jp.add(title);
		jp.add(cont);
		jp.add(ok);
		jp.add(no);
		jdt.getContentPane().add(jp);
		jdt.show();
	}
}
