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
		Toolkit kit=Toolkit.getDefaultToolkit();
		Dimension sc=kit.getScreenSize();
		JDialog jdt=new JDialog();
		jdt.setSize(375,180);
		jdt.setUndecorated(true);
		jdt.setModal(true);
		jdt.setLocation(sc.width/2-192,sc.height/2-90);
		URL bg=DialogCore.class.getResource("res/bg-ovt.png");
		JLabel bl=new JLabel(new ImageIcon(bg));          // �������ͼƬ����ӵ�һ����Ϊbl�ı�ǩ��  
		bl.setBounds(0,0,jdt.getWidth(),jdt.getHeight());        //���ñ�ǩ��С
		JPanel imagePanel=(JPanel)jdt.getContentPane();        // �����ݴ���ת��ΪJPanel���������÷���setOpaque()��ʹ���ݴ���͸�� ��ʹ���ݴ���͸���������ʾ����ͼƬ 
		imagePanel.setOpaque(false);          // �ѱ���ͼƬ��ӵ��ֲ㴰�����ײ���Ϊ����  
		jdt.getLayeredPane().add(bl,new Integer(Integer.MIN_VALUE));
		jdt.addMouseListener(new MouseAdapter() {        //���ô��ڿ��϶�����Ӽ�����
		    public void mousePressed(MouseEvent e) {        //��ȡ������ʱ������
		        dx=e.getPoint().x;
		        dy=e.getPoint().y;
		    }
		});      
		jdt.addMouseMotionListener(new MouseMotionAdapter() {        //������ק�󣬴��ڵ�λ��
		    public void mouseDragged(MouseEvent e) {
		        jdt.setLocation(e.getXOnScreen()-dx,e.getYOnScreen()-dy);		
		    }
		});
		JLabel title=new JLabel("����");
		title.setForeground(new Color(255, 69, 0));
		title.setFont(new Font("΢���ź�", Font.BOLD, 18));
		title.setBounds(5, 3, 42, 24);
		JLabel cont=new JLabel("<html>�㶨����ļ����Ѿ����ڣ��Ƿ񸲸ǣ�<html>");
		cont.setForeground(new Color(148, 0, 211));
		cont.setBackground(Color.WHITE);
		cont.setFont(new Font("����", Font.BOLD, 18));
		cont.setBounds(27, 54, 323, 34);
		JButton ok=new JButton("��");
		ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isOvt=true;
				jdt.dispose();
			}
		});
		ok.setForeground(new Color(255, 20, 147));
		ok.setFont(new Font("����", Font.BOLD, 18));
		ok.setBounds(91, 121, 59, 34);
		ok.setContentAreaFilled(false);
		JButton no=new JButton("��");
		no.setForeground(new Color(34, 139, 34));
		no.setFont(new Font("����", Font.BOLD, 18));
		no.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				isOvt=false;
				jdt.dispose();
			}
		});
		no.setBounds(200, 121, 59, 34);
		no.setContentAreaFilled(false);
		JPanel jp=new JPanel();
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
