package swsk33.WFs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class DialogCore {
	
	private static int x;
	private static int y;
	static String oks="ȷ��";
	static String jdt="ѡ���ļ�";
	static JComboBox jcbidx=new JComboBox();
	static JComboBox jcbtyp=new JComboBox();
	static DefaultListModel dfl=new DefaultListModel();
	static JTextField jtn=new JTextField();
	
	void idxfileexa() throws Exception {		//·����¼�ļ��Լ�
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
		JLabel bl=new JLabel(new ImageIcon(DialogCore.class.getResource("res\\bg.png")));          // �������ͼƬ����ӵ�һ����Ϊbl�ı�ǩ��  
		bl.setBounds(0,0,jd.getWidth(),jd.getHeight());        //���ñ�ǩ��С
		JPanel imagePanel=(JPanel)jd.getContentPane();        // �����ݴ���ת��ΪJPanel���������÷���setOpaque()��ʹ���ݴ���͸�� ��ʹ���ݴ���͸���������ʾ����ͼƬ 
		imagePanel.setOpaque(false);          // �ѱ���ͼƬ��ӵ��ֲ㴰�����ײ���Ϊ����  
		jd.getLayeredPane().add(bl,new Integer(Integer.MIN_VALUE));
		jd.addMouseListener(new MouseAdapter() {        //���ô��ڿ��϶�����Ӽ�����
		    public void mousePressed(MouseEvent e) {        //��ȡ������ʱ������
		        x=e.getPoint().x;
		        y=e.getPoint().y;
		    }
		});      
		jd.addMouseMotionListener(new MouseMotionAdapter() {        //������ק�󣬴��ڵ�λ��
		    public void mouseDragged(MouseEvent e) {
		        jd.setLocation(e.getXOnScreen()-x,e.getYOnScreen()-y);		
		    }
		});
		JLabel title=new JLabel(jdt);
		title.setFont(new Font("����", Font.BOLD, 18));
		title.setBounds(6, 4, 255, 27);
		title.setToolTipText(jdt);
		JLabel idx=new JLabel("����������");
		idx.setFont(new Font("����", Font.BOLD, 18));
		idx.setBounds(20, 51, 90, 27);
		JLabel name=new JLabel("�ļ�����");
		name.setFont(new Font("����", Font.BOLD, 20));
		name.setBounds(53, 406, 80, 18);
		JLabel type=new JLabel("�ļ����ͣ�");
		type.setFont(new Font("����", Font.BOLD, 20));
		type.setBounds(33, 456, 100, 18);
		JButton close=new JButton(new ImageIcon(DialogCore.class.getResource("res\\bt-close.png")));
		close.addActionListener(new ActionListener() {		//�رմ���
			public void actionPerformed(ActionEvent arg0) {
				jd.dispose();
			}
		});
		close.setBounds(715, 2, 32, 32);
		close.setBorderPainted(false);
		close.setContentAreaFilled(false);
		close.setToolTipText("�رմ���");
		JButton computer=new JButton(new ImageIcon(DialogCore.class.getResource("res\\bt-computer.png")));
		computer.setBounds(511, 47, 38, 38);
		computer.setBorderPainted(false);
		computer.setContentAreaFilled(false);
		computer.setToolTipText("����\"�ҵĵ���\"");
		JButton home=new JButton(new ImageIcon(DialogCore.class.getResource("res\\bt-home.png")));
		home.setBounds(561, 47, 38, 38);
		home.setBorderPainted(false);
		home.setContentAreaFilled(false);
		home.setToolTipText("�����û�Ŀ¼");
		JButton delete=new JButton(new ImageIcon(DialogCore.class.getResource("res\\bt-delete.png")));
		delete.setBounds(611, 47, 38, 38);
		delete.setBorderPainted(false);
		delete.setContentAreaFilled(false);
		delete.setToolTipText("ɾ��ѡ���ļ�/�ļ���");
		JButton front=new JButton(new ImageIcon(DialogCore.class.getResource("res\\bt-front.png")));
		front.setBounds(411, 47, 38, 38);
		front.setContentAreaFilled(false);
		front.setBorderPainted(false);
		front.setToolTipText("������һ��Ŀ¼");
		JButton refresh=new JButton(new ImageIcon(DialogCore.class.getResource("res\\bt-refresh.png")));
		refresh.setBounds(461, 47, 38, 38);
		refresh.setContentAreaFilled(false);
		refresh.setBorderPainted(false);
		refresh.setToolTipText("ˢ���б�");
		JButton view=new JButton(new ImageIcon(DialogCore.class.getResource("res\\bt-view.png")));
		view.setBounds(661, 47, 38, 38);
		view.setContentAreaFilled(false);
		view.setBorderPainted(false);
		view.setToolTipText("������ͼ");
		JButton ok=new JButton(oks);
		ok.setForeground(new Color(255, 0, 153));
		ok.setFont(new Font("����", Font.BOLD, 20));
		ok.setBounds(605, 398, 88, 35);
		ok.setContentAreaFilled(false);
		JButton cancel=new JButton("ȡ��");
		cancel.setForeground(new Color(255, 102, 0));
		cancel.setFont(new Font("����", Font.BOLD, 20));
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
