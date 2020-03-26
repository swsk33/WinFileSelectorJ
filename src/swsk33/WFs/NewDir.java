package swsk33.WFs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class NewDir {
	
	static int x;
	static int y;
	static String name="";
	static JTextField jt=new JTextField(); 
	
	/**
	 * @wbp.parser.entryPoint
	 */
	public void ndfr() {
		Toolkit kit=Toolkit.getDefaultToolkit();
		Dimension sc=kit.getScreenSize();
		JDialog jd=new JDialog();
		jd.setSize(320,145);
		jd.setLocation(sc.width/2-160,sc.height/2-73);
		jd.setUndecorated(true);
		jd.setModal(true);
		JLabel bl=new JLabel(new ImageIcon(DialogCore.class.getResource("res\\bg-newdirdf.png")));          // �������ͼƬ����ӵ�һ����Ϊbl�ı�ǩ��  
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
		JLabel title=new JLabel("�½��ļ���");
		title.setBackground(Color.YELLOW);
		title.setFont(new Font("����", Font.BOLD, 16));
		title.setBounds(5, 5, 91, 18);
		JLabel na=new JLabel("���֣�");
		na.setFont(new Font("����", Font.BOLD, 15));
		na.setBounds(23, 66, 48, 22);
		JButton ok=new JButton("ȷ��");
		ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(jt.getText().equals("")) {
						Process tip=Runtime.getRuntime().exec("cmd /c echo msgbox \"���������ƣ�\",64,\"ERROR\">alert.vbs && start alert.vbs && ping -n 2 127.1>nul && del alert.vbs");
					} else if(jt.getText().contains("\\")||jt.getText().contains("/")||jt.getText().contains(":")||jt.getText().contains("*")||jt.getText().contains("?")||jt.getText().contains("\"")||jt.getText().contains("<")||jt.getText().contains(">")||jt.getText().contains("|")) {
						Process tip=Runtime.getRuntime().exec("cmd /c echo msgbox \"�ļ������Ʋ��ܰ����Ƿ��ַ���\\/:*?\"<>|����\",64,\"ERROR\">alert.vbs && start alert.vbs && ping -n 2 127.1>nul && del alert.vbs");
					} else {
						name=jt.getText().toString();
						jd.dispose();
					}
				} catch(Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		ok.setContentAreaFilled(false);
		ok.setForeground(new Color(0, 191, 255));
		ok.setFont(new Font("����", Font.BOLD, 15));
		ok.setBounds(71, 104, 71, 27);
		JButton cancel=new JButton("ȡ��");
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jd.dispose();
			}
		});
		cancel.setContentAreaFilled(false);
		cancel.setForeground(new Color(255, 165, 0));
		cancel.setFont(new Font("����", Font.BOLD, 15));
		cancel.setBounds(189, 104, 71, 27);
		jt.setBounds(71, 65, 222, 27);
		JPanel jp=new JPanel();
		jp.setOpaque(false);
		jp.setLayout(null);
		jp.add(title);
		jp.add(na);
		jp.add(jt);
		jp.add(ok);
		jp.add(cancel);
		jd.getContentPane().add(jp);
		jd.show();
	}
}
