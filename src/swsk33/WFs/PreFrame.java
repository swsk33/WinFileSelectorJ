package swsk33.WFs;

import java.awt.*;
import javax.swing.*;

class PreFrame {
	
	static int x;
	static int y;
	static JFrame jf=new JFrame();
	static JLabel imgp=new JLabel();
	
	/**
	 * @wbp.parser.entryPoint
	 */
	public void prefr() {
		jf.setUndecorated(true);
		jf.setSize(260,195);
		jf.setAlwaysOnTop(true);
		jf.setType(JFrame.Type.UTILITY);		//����������ͼ��
		JLabel bl=new JLabel(new ImageIcon(PreFrame.class.getResource("res\\thbfr.png")));          // �������ͼƬ����ӵ�һ����Ϊbl�ı�ǩ��  
		bl.setBounds(0,0,jf.getWidth(),jf.getHeight());        //���ñ�ǩ��С
		JPanel imagePanel=(JPanel)jf.getContentPane();        // �����ݴ���ת��ΪJPanel���������÷���setOpaque()��ʹ���ݴ���͸�� ��ʹ���ݴ���͸���������ʾ����ͼƬ 
		imagePanel.setOpaque(false);          // �ѱ���ͼƬ��ӵ��ֲ㴰�����ײ���Ϊ����  
		jf.getLayeredPane().add(bl,new Integer(Integer.MIN_VALUE));
		jf.setBackground(new Color(0,0,0,0));
		imgp.setBounds(5, 5, 250, 156);
		JPanel jp=new JPanel();
		jp.setOpaque(false);
		jp.setLayout(null);
		jp.add(imgp);
		jf.getContentPane().add(jp);
		jf.show();
	}
}
