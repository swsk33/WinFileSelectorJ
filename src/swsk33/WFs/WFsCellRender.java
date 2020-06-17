package swsk33.WFs;

import java.awt.*;
import javax.swing.*;
import java.net.*;
import java.io.*;
@SuppressWarnings("serial")
class WFsCellRender extends DefaultListCellRenderer {
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
			ftyp=new FileRaWUtils().getFileFormat(filepath).toLowerCase();
			Boolean isfile=new File(filepath).isFile();		//���ļ������ļ���
			setText(new File(value.toString()).getName());		//��������Ϊ�ļ���
			//�ļ�ͼ����趨
			if(!isfile) {
				URL icp=WFsCellRender.class.getResource("res/fileico/common-dir.png");
				ImageIcon ico=new ImageIcon(icp);		//ʵ����һ��ImageIcon����
				Image img=ico.getImage();		//ʵ����Image�����ȡico��������� 
				img=img.getScaledInstance(icox,icoy,Image.SCALE_DEFAULT);		//������ʾͼƬ
				ico.setImage(img);		//ImageIcon�������»�ȡ���ź�ͼ��
				setIcon(ico);		//����ͼ��
			} else {
				if(this.isPic()) {
					if(DialogCore.viewop==2&&(ftyp.equalsIgnoreCase("jpg")||ftyp.equalsIgnoreCase("jpeg")||ftyp.equalsIgnoreCase("png")||ftyp.equalsIgnoreCase("bmp")||ftyp.equalsIgnoreCase("gif"))) {
						int iw;
						int ih;
						ImageIcon ico=new ImageIcon(filepath);
						if(ico.getIconWidth()>ico.getIconHeight()) {
							iw=120;
							ih=(int)(((float)120/ico.getIconWidth())*ico.getIconHeight());
						} else {
							ih=120;
							iw=(int)(((float)120/ico.getIconHeight())*ico.getIconWidth());
						}
						Image img=ico.getImage();
						img=img.getScaledInstance(iw,ih,Image.SCALE_AREA_AVERAGING);
						ico.setImage(img);
						setIcon(ico);
					} else {
						URL icp=WFsCellRender.class.getResource("res/fileico/pic/"+ftyp+".png");
						ImageIcon ico=new ImageIcon(icp);
						Image img=ico.getImage();
						img=img.getScaledInstance(icox,icoy,Image.SCALE_DEFAULT);
						ico.setImage(img);
						setIcon(ico);
					}
				} else if(this.isAu()) {
					URL icp=WFsCellRender.class.getResource("res/fileico/aud/"+ftyp+".png");
					ImageIcon ico=new ImageIcon(icp);
					Image img=ico.getImage();
					img=img.getScaledInstance(icox,icoy,Image.SCALE_DEFAULT);
					ico.setImage(img);
					setIcon(ico);
				} else if(this.isVe()) {
					URL icp=WFsCellRender.class.getResource("res/fileico/ved/"+ftyp+".png");
					ImageIcon ico=new ImageIcon(icp);
					Image img=ico.getImage();
					img=img.getScaledInstance(icox,icoy,Image.SCALE_DEFAULT);
					ico.setImage(img);
					setIcon(ico);
				} else if(this.isDoc()) {
					URL icp=WFsCellRender.class.getResource("res/fileico/docf/"+ftyp+".png");
					ImageIcon ico=new ImageIcon(icp);
					Image img=ico.getImage();
					img=img.getScaledInstance(icox,icoy,Image.SCALE_DEFAULT);
					ico.setImage(img);
					setIcon(ico);
				} else if(this.isZip()) {
					URL icp=WFsCellRender.class.getResource("res/fileico/zips/"+ftyp+".png");
					ImageIcon ico=new ImageIcon(icp);
					Image img=ico.getImage();
					img=img.getScaledInstance(icox,icoy,Image.SCALE_DEFAULT);
					ico.setImage(img);
					setIcon(ico);
				} else if(this.isProg()) {
					URL icp=WFsCellRender.class.getResource("res/fileico/cod/"+ftyp+".png");
					ImageIcon ico=new ImageIcon(icp);
					Image img=ico.getImage();
					img=img.getScaledInstance(icox,icoy,Image.SCALE_DEFAULT);
					ico.setImage(img);
					setIcon(ico);
				} else {
					URL icp=WFsCellRender.class.getResource("res/fileico/common.png");
					ImageIcon ico=new ImageIcon(icp);
					Image img=ico.getImage();
					img=img.getScaledInstance(icox,icoy,Image.SCALE_DEFAULT);
					ico.setImage(img);
					setIcon(ico);
				}
			}
		} else {		//���ҵĵ��������ʱ��
			String[] drina=DialogCore.driname.toArray(new String[DialogCore.driname.size()]);
			String[] drity=DialogCore.dritype.toArray(new String[DialogCore.dritype.size()]);
			try {
				setText(drina[index]);
				String disktype=drity[index];
				if(disktype.startsWith("��")||disktype.startsWith("Local")) {		// ������Ϊ���ش���ʱ
					URL icp=WFsCellRender.class.getResource("res/diskico/disk.png");
					ImageIcon finaico=new ImageIcon(icp);
					Image img=finaico.getImage().getScaledInstance(icox,icoy,Image.SCALE_DEFAULT);
					finaico.setImage(img);
					setIcon(finaico);
				} else if(disktype.startsWith("U")||disktype.startsWith("Removable")) {		//Ϊ���ƶ�����ʱ
					URL icp=WFsCellRender.class.getResource("res/diskico/udisk.png");
					ImageIcon finaico=new ImageIcon(icp);
					Image img=finaico.getImage().getScaledInstance(icox,icoy,Image.SCALE_DEFAULT);
					finaico.setImage(img);
					setIcon(finaico);
				} else if(disktype.startsWith("CD")) {		//ΪCD������ʱ
					URL icp=WFsCellRender.class.getResource("res/diskico/cd.png");
					ImageIcon finaico=new ImageIcon(icp);
					Image img=finaico.getImage().getScaledInstance(icox,icoy,Image.SCALE_DEFAULT);
					finaico.setImage(img);
					setIcon(finaico);
				}
			} catch(Exception e) {
				//do nothing
			}
		} 
		if(DialogCore.viewop==2) {
			setHorizontalTextPosition(SwingConstants.CENTER);
			setVerticalTextPosition(SwingConstants.BOTTOM);
		} else {
			setHorizontalTextPosition(SwingConstants.RIGHT);
			setVerticalTextPosition(SwingConstants.CENTER);
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
