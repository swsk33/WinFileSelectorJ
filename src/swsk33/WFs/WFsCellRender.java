package swsk33.WFs;

import java.awt.*;
import javax.swing.*;
import javax.swing.filechooser.*;
import java.io.*;

@SuppressWarnings("serial")

public class WFsCellRender extends DefaultListCellRenderer {
	
	static String ftyp;		//文件类型
	static int icox;
	static int icoy;
	
	boolean isPic() {		//判断是不是图片文件
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
	
	boolean isAu() {		//判断是不是音频文件
		boolean res=false;
		for(String ftt:DialogCore.audfo) {
			if(ftt.equalsIgnoreCase(ftyp)) {
				res=true;
				break;
			}
		}
		return res;
	}
	
	boolean isVe() {		//判断是不是视频文件
		boolean res=false;
		for(String ftt:DialogCore.vedfo) {
			if(ftt.equalsIgnoreCase(ftyp)) {
				res=true;
				break;
			}
		}
		return res;
	}
	
	boolean isDoc() {		//判断是不是文档文件
		boolean res=false;
		for(String ftt:DialogCore.docfo) {
			if(ftt.equalsIgnoreCase(ftyp)) {
				res=true;
				break;
			}
		}
		return res;
	}
	
	boolean isZip() {		//判断是不是压缩文件
		boolean res=false;
		for(String ftt:DialogCore.zipfo) {
			if(ftt.equalsIgnoreCase(ftyp)) {
				res=true;
				break;
			}
		}
		return res;
	}
	
	boolean isProg() {		//判断是不是代码及二进制文件
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
		if(DialogCore.viewop==2) {		//设定图标大小
			icox=120;
			icoy=120;
		} else {
			icox=35;
			icoy=35;
		}
		if(DialogCore.isInaDisk) {		//在一个磁盘里面时
			String filepath=new File(value.toString()).getAbsolutePath();
			ftyp=new FileRaWUtils().getFileFormat(filepath);
			Boolean isfile=new File(filepath).isFile();		//是文件还是文件夹
			setText(new File(value.toString()).getName());		//设置文字为文件名
			//文件图标的设定
			if(!isfile) {
				ImageIcon ico=new ImageIcon(WFsCellRender.class.getResource("res\\fileico\\common-dir.png"));		//实例化一个ImageIcon对象
				Image img=ico.getImage();		//实例化Image对象获取ico对象的内容 
				img=img.getScaledInstance(icox,icoy,Image.SCALE_DEFAULT);		//缩放显示图片
				ico.setImage(img);		//ImageIcon对象重新获取缩放后图标
				setIcon(ico);		//设置图标
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
		} else {		//在我的电脑里面的时候
			File disk=new File(value.toString());
			FileSystemView fsv=FileSystemView.getFileSystemView();
			setText(fsv.getSystemDisplayName(disk));
			String disktype=fsv.getSystemTypeDescription(disk);
			if(disktype.startsWith("本")||disktype.startsWith("Local")) {		// 当磁盘为本地磁盘时
				ImageIcon finaico=new ImageIcon(WFsCellRender.class.getResource("res\\diskico\\disk.png"));
				Image img=finaico.getImage().getScaledInstance(icox,icoy,Image.SCALE_DEFAULT);
				finaico.setImage(img);
				setIcon(finaico);
			} else if(disktype.startsWith("U")||disktype.startsWith("Removable")) {		//为可移动磁盘时
				ImageIcon finaico=new ImageIcon(WFsCellRender.class.getResource("res\\diskico\\udisk.png"));
				Image img=finaico.getImage().getScaledInstance(icox,icoy,Image.SCALE_DEFAULT);
				finaico.setImage(img);
				setIcon(finaico);
			} else if(disktype.startsWith("CD")) {		//为CD驱动器时
				ImageIcon finaico=new ImageIcon(WFsCellRender.class.getResource("res\\diskico\\cd.png"));
				Image img=finaico.getImage().getScaledInstance(icox,icoy,Image.SCALE_DEFAULT);
				finaico.setImage(img);
				setIcon(finaico);
			}	
		}
		if(isSelected) {		//当某个元素被选中时
			setForeground(Color.BLACK);		//设置前景色（文字颜色）为黑色
			setBackground(new Color(111,217,229));		//设置背景色为蓝色
		} else {		//某个元素未被选中时（取消选中）
			setForeground(Color.BLACK);		//设置前景色（文字颜色）为黑色
			setBackground(Color.WHITE);		//设置背景色为白色
		}
		return this;
	}
}
