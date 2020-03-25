package swsk33.WFs;

import java.awt.*;
import javax.swing.*;
import javax.swing.filechooser.*;
import java.io.*;

@SuppressWarnings("serial")

public class WFsCellRender extends DefaultListCellRenderer {
	
	static String ftyp;		//�ļ�����
	static int icox;
	static int icoy;
	
	boolean isPic() {		//�ж��ǲ���ͼƬ�ļ�
		boolean res=false;
		for(String ftt:DialogCore.compicfo) {
			if(ftt.equalsIgnoreCase(ftyp)) {
				res=true;
				break;
			}
		}
		for(String ftt:DialogCore.othpicfo) {
			if(ftt.equalsIgnoreCase(ftyp)) {
				res=true;
				break;
			}
		}
		return res;
	}
	
	boolean isAu() {		//�ж��ǲ�����Ƶ�ļ�
		boolean res=false;
		for(String ftt:DialogCore.audfo) {
			if(ftt.equalsIgnoreCase(ftyp)) {
				res=true;
				break;
			}
		}
		return res;
	}
	
	boolean isVe() {		//�ж��ǲ�����Ƶ�ļ�
		boolean res=false;
		for(String ftt:DialogCore.vedfo) {
			if(ftt.equalsIgnoreCase(ftyp)) {
				res=true;
				break;
			}
		}
		return res;
	}
	
	boolean isDoc() {		//�ж��ǲ����ĵ��ļ�
		boolean res=false;
		for(String ftt:DialogCore.docfo) {
			if(ftt.equalsIgnoreCase(ftyp)) {
				res=true;
				break;
			}
		}
		return res;
	}
	
	boolean isZip() {		//�ж��ǲ���ѹ���ļ�
		boolean res=false;
		for(String ftt:DialogCore.zipfo) {
			if(ftt.equalsIgnoreCase(ftyp)) {
				res=true;
				break;
			}
		}
		return res;
	}
	
	boolean isProg() {		//�ж��ǲ��Ǵ��뼰�������ļ�
		boolean res=false;
		for(String ftt:DialogCore.progfo) {
			if(ftt.equalsIgnoreCase(ftyp)) {
				res=true;
				break;
			}
		}
		return res;
	}
	
	public Component getListCellRendererComponent(JList<? extends Object> list,Object value,int index,boolean isSelected,boolean cellHasFocus) {
		if(DialogCore.viewop==2) {		//�趨ͼ���С
			icox=120;
			icoy=120;
		} else {
			icox=35;
			icoy=35;
		}
		if(DialogCore.isInaDisk) {		//��һ����������ʱ
			String filepath=new File(value.toString()).getAbsolutePath();
			ftyp=new FileRaWUtils().getFileFormat(filepath);
			Boolean isfile=new File(filepath).isFile();		//���ļ������ļ���
			setText(new File(value.toString()).getName());		//��������Ϊ�ļ���
			//�ļ�ͼ����趨
			if(!isfile) {
				ImageIcon ico=new ImageIcon(WFsCellRender.class.getResource("res\\fileico\\common-dir.png"));		//ʵ����һ��ImageIcon����
				Image img=ico.getImage();		//ʵ����Image�����ȡico��������� 
				img=img.getScaledInstance(icox,icoy,Image.SCALE_DEFAULT);		//������ʾͼƬ
				ico.setImage(img);		//ImageIcon�������»�ȡ���ź�ͼ��
				setIcon(ico);		//����ͼ��
			} else {
				if(this.isPic()) {
					ImageIcon ico=new ImageIcon(WFsCellRender.class.getResource("res\\fileico\\pic\\"+ftyp+".png"));
					Image img=ico.getImage();
					img=img.getScaledInstance(icox,icoy,Image.SCALE_DEFAULT);
					ico.setImage(img);
					setIcon(ico);
				} else if(this.isAu()) {
					ImageIcon ico=new ImageIcon(WFsCellRender.class.getResource("res\\fileico\\aud\\"+ftyp+".png"));
					Image img=ico.getImage();
					img=img.getScaledInstance(icox,icoy,Image.SCALE_DEFAULT);
					ico.setImage(img);
					setIcon(ico);
				} else if(this.isVe()) {
					ImageIcon ico=new ImageIcon(WFsCellRender.class.getResource("res\\fileico\\ved\\"+ftyp+".png"));
					Image img=ico.getImage();
					img=img.getScaledInstance(icox,icoy,Image.SCALE_DEFAULT);
					ico.setImage(img);
					setIcon(ico);
				} else if(this.isDoc()) {
					ImageIcon ico=new ImageIcon(WFsCellRender.class.getResource("res\\fileico\\docf\\"+ftyp+".png"));
					Image img=ico.getImage();
					img=img.getScaledInstance(icox,icoy,Image.SCALE_DEFAULT);
					ico.setImage(img);
					setIcon(ico);
				} else if(this.isZip()) {
					ImageIcon ico=new ImageIcon(WFsCellRender.class.getResource("res\\fileico\\zips\\"+ftyp+".png"));
					Image img=ico.getImage();
					img=img.getScaledInstance(icox,icoy,Image.SCALE_DEFAULT);
					ico.setImage(img);
					setIcon(ico);
				} else if(this.isProg()) {
					ImageIcon ico=new ImageIcon(WFsCellRender.class.getResource("res\\fileico\\cod\\"+ftyp+".png"));
					Image img=ico.getImage();
					img=img.getScaledInstance(icox,icoy,Image.SCALE_DEFAULT);
					ico.setImage(img);
					setIcon(ico);
				} else {
					ImageIcon ico=new ImageIcon(WFsCellRender.class.getResource("res\\fileico\\common.png"));
					Image img=ico.getImage();
					img=img.getScaledInstance(icox,icoy,Image.SCALE_DEFAULT);
					ico.setImage(img);
					setIcon(ico);
				}
			}
		} else {		//���ҵĵ��������ʱ��
			File disk=new File(value.toString());
			FileSystemView fsv=FileSystemView.getFileSystemView();
			setText(fsv.getSystemDisplayName(disk));
			String disktype=fsv.getSystemTypeDescription(disk);
			if(disktype.startsWith("��")||disktype.startsWith("Local")) {		// ������Ϊ���ش���ʱ
				ImageIcon finaico=new ImageIcon(WFsCellRender.class.getResource("res\\diskico\\disk.png"));
				Image img=finaico.getImage().getScaledInstance(icox,icoy,Image.SCALE_DEFAULT);
				finaico.setImage(img);
				setIcon(finaico);
			} else if(disktype.startsWith("U")||disktype.startsWith("Removable")) {		//Ϊ���ƶ�����ʱ
				ImageIcon finaico=new ImageIcon(WFsCellRender.class.getResource("res\\diskico\\udisk.png"));
				Image img=finaico.getImage().getScaledInstance(icox,icoy,Image.SCALE_DEFAULT);
				finaico.setImage(img);
				setIcon(finaico);
			} else if(disktype.startsWith("CD")) {		//ΪCD������ʱ
				ImageIcon finaico=new ImageIcon(WFsCellRender.class.getResource("res\\diskico\\cd.png"));
				Image img=finaico.getImage().getScaledInstance(icox,icoy,Image.SCALE_DEFAULT);
				finaico.setImage(img);
				setIcon(finaico);
			}	
		}
		if(isSelected) {		//��ĳ��Ԫ�ر�ѡ��ʱ
			setForeground(Color.BLACK);		//����ǰ��ɫ��������ɫ��Ϊ��ɫ
			setBackground(new Color(111,217,229));		//���ñ���ɫΪ��ɫ
		} else {		//ĳ��Ԫ��δ��ѡ��ʱ��ȡ��ѡ�У�
			setForeground(Color.BLACK);		//����ǰ��ɫ��������ɫ��Ϊ��ɫ
			setBackground(Color.WHITE);		//���ñ���ɫΪ��ɫ
		}
		return this;
	}
}
