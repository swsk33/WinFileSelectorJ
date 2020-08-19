package swsk33.WFs;

import java.awt.*;
import javax.swing.*;
import java.net.*;

class PreFrame {

	static int x;
	static int y;
	static JFrame jf = new JFrame();
	static JLabel imgp = new JLabel();

	/**
	 * @wbp.parser.entryPoint
	 */
	public void prefr() {
		jf.setUndecorated(true);
		jf.setSize(260, 195);
		jf.setAlwaysOnTop(true);
		jf.setType(JFrame.Type.UTILITY); // 隐藏任务栏图标
		URL bg = PreFrame.class.getResource("res/thbfr.png");
		JLabel bl = new JLabel(new ImageIcon(bg)); // 把上面的图片对象加到一个名为bl的标签里
		bl.setBounds(0, 0, jf.getWidth(), jf.getHeight()); // 设置标签大小
		JPanel imagePanel = (JPanel) jf.getContentPane(); // 把内容窗格转化为JPanel，否则不能用方法setOpaque()来使内容窗格透明 ，使内容窗格透明后才能显示背景图片
		imagePanel.setOpaque(false); // 把背景图片添加到分层窗格的最底层作为背景
		jf.getLayeredPane().add(bl, new Integer(Integer.MIN_VALUE));
		jf.setBackground(new Color(0, 0, 0, 0));
		imgp.setBounds(5, 5, 250, 156);
		JPanel jp = new JPanel();
		jp.setOpaque(false);
		jp.setLayout(null);
		jp.add(imgp);
		jf.getContentPane().add(jp);
		jf.show();
	}
}
