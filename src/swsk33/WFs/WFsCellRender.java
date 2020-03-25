package swsk33.WFs;


import java.awt.*;
import javax.swing.*;
import java.io.*;

@SuppressWarnings("serial")

public class WFsCellRender extends DefaultListCellRenderer {
	public Component getListCellRendererComponent(JList<? extends Object> list,Object value,int index,boolean isSelected,boolean cellHasFocus) {
		String filepath=new File(value.toString()).getAbsolutePath();		//�ļ�·��
		String ftyp=new FileRaWUtils().getFileFormat(filepath);		//�ļ���
		Boolean isfile=new File(filepath).isFile();		//���ļ������ļ���
		setText(new File(value.toString()).getName());		//��������
		if(!isfile) {
			ImageIcon ico=new ImageIcon(WFsCellRender.class.getResource("res\\fileico\\common-dir.png"));		//ʵ����һ��ImageIcon����
			Image img=ico.getImage();		//ʵ����Image�����ȡico��������� 
			img=img.getScaledInstance(35,35,Image.SCALE_DEFAULT);		//��ͼƬȫ������Ϊ35x35
			ico.setImage(img);		//ImageIcon�������»�ȡ���ź�ͼ��
			setIcon(ico);		//����ͼ��
		} else {
			ImageIcon ico=new ImageIcon(WFsCellRender.class.getResource("res\\fileico\\common.png"));		//ʵ����һ��ImageIcon����
			Image img=ico.getImage();		//ʵ����Image�����ȡico��������� 
			img=img.getScaledInstance(35,35,Image.SCALE_DEFAULT);		//��ͼƬȫ������Ϊ35x35
			ico.setImage(img);		//ImageIcon�������»�ȡ���ź�ͼ��
			setIcon(ico);		//����ͼ��
		}
		if(isSelected) {		//��ĳ��Ԫ�ر�ѡ��ʱ
			setForeground(Color.BLACK);		//����ǰ��ɫ��������ɫ��Ϊ��ɫ
			setBackground(new Color(111,217,229));		//���ñ���ɫΪ��ɫ
			System.out.println(index+"��ѡ��");
		} else {		//ĳ��Ԫ��δ��ѡ��ʱ��ȡ��ѡ�У�
			setForeground(Color.BLACK);		//����ǰ��ɫ��������ɫ��Ϊ��ɫ
			setBackground(Color.WHITE);		//���ñ���ɫΪ��ɫ
		}
		return this;
	}
}
