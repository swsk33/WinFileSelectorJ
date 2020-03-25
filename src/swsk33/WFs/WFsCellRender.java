package swsk33.WFs;


import java.awt.*;
import javax.swing.*;
import java.io.*;

@SuppressWarnings("serial")

public class WFsCellRender extends DefaultListCellRenderer {
	public Component getListCellRendererComponent(JList<? extends Object> list,Object value,int index,boolean isSelected,boolean cellHasFocus) {
		String filepath=new File(value.toString()).getAbsolutePath();		//文件路径
		String ftyp=new FileRaWUtils().getFileFormat(filepath);		//文件格
		Boolean isfile=new File(filepath).isFile();		//是文件还是文件夹
		setText(new File(value.toString()).getName());		//设置文字
		if(!isfile) {
			ImageIcon ico=new ImageIcon(WFsCellRender.class.getResource("res\\fileico\\common-dir.png"));		//实例化一个ImageIcon对象
			Image img=ico.getImage();		//实例化Image对象获取ico对象的内容 
			img=img.getScaledInstance(35,35,Image.SCALE_DEFAULT);		//把图片全部缩放为35x35
			ico.setImage(img);		//ImageIcon对象重新获取缩放后图标
			setIcon(ico);		//设置图标
		} else {
			ImageIcon ico=new ImageIcon(WFsCellRender.class.getResource("res\\fileico\\common.png"));		//实例化一个ImageIcon对象
			Image img=ico.getImage();		//实例化Image对象获取ico对象的内容 
			img=img.getScaledInstance(35,35,Image.SCALE_DEFAULT);		//把图片全部缩放为35x35
			ico.setImage(img);		//ImageIcon对象重新获取缩放后图标
			setIcon(ico);		//设置图标
		}
		if(isSelected) {		//当某个元素被选中时
			setForeground(Color.BLACK);		//设置前景色（文字颜色）为黑色
			setBackground(new Color(111,217,229));		//设置背景色为蓝色
			System.out.println(index+"被选中");
		} else {		//某个元素未被选中时（取消选中）
			setForeground(Color.BLACK);		//设置前景色（文字颜色）为黑色
			setBackground(Color.WHITE);		//设置背景色为白色
		}
		return this;
	}
}
