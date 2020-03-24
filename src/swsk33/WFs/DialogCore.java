package swsk33.WFs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class DialogCore {
	
	private static int x;
	private static int y;
	static String oks="确定";
	static String jdt="选择文件";
	static JComboBox jcbidx=new JComboBox();
	static JComboBox jcbtyp=new JComboBox();
	static DefaultListModel dfl=new DefaultListModel();
	static JTextField jtn=new JTextField();
	
	void idxfileexa() throws Exception {		//路径记录文件自检
		File appdir=new File(System.getProperty("user.home")+"\\AppData\\Local\\WinFileSelectorJ");
		File reidx=new File(System.getProperty("user.home")+"\\AppData\\Local\\WinFileSelectorJ\\repath.wfs");
		if(!reidx.exists()) {
			appdir.mkdir();
			reidx.createNewFile();
			new FileRaWUtils().writeText(System.getProperty("user.home")+"\\AppData\\Local\\WinFileSelectorJ\\repath.wfs",System.getProperty("user.home"));
		}
	}
	
	/**
	 * @throws Exception 
	 * @wbp.parser.entryPoint
	 */
	public void df() throws Exception {
		this.idxfileexa();
		Toolkit kit=Toolkit.getDefaultToolkit();
		Dimension sc=kit.getScreenSize();
		JDialog jd=new JDialog();
		jd.setSize(750,500);
		jd.setLocation(sc.width/2-375,sc.height/2-250);
		jd.setUndecorated(true);
		jd.setModal(true);
		JLabel bl=new JLabel(new ImageIcon(DialogCore.class.getResource("res\\bg.png")));          // 把上面的图片对象加到一个名为bl的标签里  
		bl.setBounds(0,0,jd.getWidth(),jd.getHeight());        //设置标签大小
		JPanel imagePanel=(JPanel)jd.getContentPane();        // 把内容窗格转化为JPanel，否则不能用方法setOpaque()来使内容窗格透明 ，使内容窗格透明后才能显示背景图片 
		imagePanel.setOpaque(false);          // 把背景图片添加到分层窗格的最底层作为背景  
		jd.getLayeredPane().add(bl,new Integer(Integer.MIN_VALUE));
		jd.addMouseListener(new MouseAdapter() {        //设置窗口可拖动，添加监听器
		    public void mousePressed(MouseEvent e) {        //获取点击鼠标时的坐标
		        x=e.getPoint().x;
		        y=e.getPoint().y;
		    }
		});      
		jd.addMouseMotionListener(new MouseMotionAdapter() {        //设置拖拽后，窗口的位置
		    public void mouseDragged(MouseEvent e) {
		        jd.setLocation(e.getXOnScreen()-x,e.getYOnScreen()-y);		
		    }
		});
		JLabel title=new JLabel(jdt);
		title.setFont(new Font("黑体", Font.BOLD, 18));
		title.setBounds(6, 4, 255, 27);
		title.setToolTipText(jdt);
		JLabel idx=new JLabel("快速索引：");
		idx.setFont(new Font("等线", Font.BOLD, 18));
		idx.setBounds(20, 51, 90, 27);
		JLabel name=new JLabel("文件名：");
		name.setFont(new Font("等线", Font.BOLD, 20));
		name.setBounds(53, 406, 80, 18);
		JLabel type=new JLabel("文件类型：");
		type.setFont(new Font("等线", Font.BOLD, 20));
		type.setBounds(33, 456, 100, 18);
		JButton close=new JButton(new ImageIcon(DialogCore.class.getResource("res\\bt-close.png")));
		close.addActionListener(new ActionListener() {		//关闭窗口
			public void actionPerformed(ActionEvent arg0) {
				jd.dispose();
			}
		});
		close.setBounds(715, 2, 32, 32);
		close.setBorderPainted(false);
		close.setContentAreaFilled(false);
		close.setToolTipText("关闭窗口");
		JButton computer=new JButton(new ImageIcon(DialogCore.class.getResource("res\\bt-computer.png")));
		computer.setBounds(511, 47, 38, 38);
		computer.setBorderPainted(false);
		computer.setContentAreaFilled(false);
		computer.setToolTipText("进入\"我的电脑\"");
		JButton home=new JButton(new ImageIcon(DialogCore.class.getResource("res\\bt-home.png")));
		home.setBounds(561, 47, 38, 38);
		home.setBorderPainted(false);
		home.setContentAreaFilled(false);
		home.setToolTipText("进入用户目录");
		JButton delete=new JButton(new ImageIcon(DialogCore.class.getResource("res\\bt-delete.png")));
		delete.setBounds(611, 47, 38, 38);
		delete.setBorderPainted(false);
		delete.setContentAreaFilled(false);
		delete.setToolTipText("删除选中文件/文件夹");
		JButton front=new JButton(new ImageIcon(DialogCore.class.getResource("res\\bt-front.png")));
		front.setBounds(411, 47, 38, 38);
		front.setContentAreaFilled(false);
		front.setBorderPainted(false);
		front.setToolTipText("进入上一级目录");
		JButton refresh=new JButton(new ImageIcon(DialogCore.class.getResource("res\\bt-refresh.png")));
		refresh.setBounds(461, 47, 38, 38);
		refresh.setContentAreaFilled(false);
		refresh.setBorderPainted(false);
		refresh.setToolTipText("刷新列表");
		JButton view=new JButton(new ImageIcon(DialogCore.class.getResource("res\\bt-view.png")));
		view.setBounds(661, 47, 38, 38);
		view.setContentAreaFilled(false);
		view.setBorderPainted(false);
		view.setToolTipText("更改视图");
		JButton ok=new JButton(oks);
		ok.setForeground(new Color(255, 0, 153));
		ok.setFont(new Font("黑体", Font.BOLD, 20));
		ok.setBounds(605, 398, 88, 35);
		ok.setContentAreaFilled(false);
		JButton cancel=new JButton("取消");
		cancel.setForeground(new Color(255, 102, 0));
		cancel.setFont(new Font("黑体", Font.BOLD, 20));
		cancel.setBounds(605, 448, 88, 35);
		cancel.setContentAreaFilled(false);
		JList fls=new JList(dfl);
		JScrollPane jsp=new JScrollPane();
		jsp.setBounds(43, 108, 656, 278);
		jsp.setViewportView(fls);
		jcbidx.setBounds(113, 47, 289, 38);
		jcbtyp.setBounds(141, 448, 447, 35);
		jtn.setBounds(141, 399, 447, 35);
		JPanel jp=new JPanel();
		jp.setOpaque(false);
		jp.setLayout(null);
		jp.add(title);
		jp.add(idx);
		jp.add(name);
		jp.add(type);
		jp.add(close);
		jp.add(computer);
		jp.add(home);
		jp.add(delete);
		jp.add(front);
		jp.add(refresh);
		jp.add(view);
		jp.add(ok);
		jp.add(cancel);
		jp.add(jsp);
		jp.add(jcbidx);
		jp.add(jcbtyp);
		jp.add(jtn);
		jd.getContentPane().add(jp);
		jd.show();
	}
}
