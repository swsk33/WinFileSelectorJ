package swsk33.WFs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
@SuppressWarnings({ "serial", "rawtypes" })
class CoboBoxRender extends JLabel implements ListCellRenderer {
	public Component getListCellRendererComponent(JList list,Object value,int index,boolean isSelected,boolean cellHasFocus) {
		if(value.toString().equals(DialogCore.desktop)) {
			URL icp=CoboBoxRender.class.getResource("res/jcbico/des.png");
			setText("桌面");
			setIcon(new ImageIcon(icp));
		} else if(value.toString().equals(DialogCore.download)) {
			URL icp=CoboBoxRender.class.getResource("res/jcbico/dow.png");
			setText("下载");
			setIcon(new ImageIcon(icp));
		} else if(value.toString().equals(DialogCore.document)) {
			URL icp=CoboBoxRender.class.getResource("res/jcbico/doc.png");
			setText("文档");
			setIcon(new ImageIcon(icp));
		} else if(value.toString().equals(DialogCore.picture)) {
			URL icp=CoboBoxRender.class.getResource("res/jcbico/pic.png");
			setText("图片");
			setIcon(new ImageIcon(icp));
		} else if(value.toString().equals(DialogCore.music)) {
			URL icp=CoboBoxRender.class.getResource("res/jcbico/mus.png");
			setText("音乐");
			setIcon(new ImageIcon(icp));
		} else if(value.toString().equals(DialogCore.video)) {
			URL icp=CoboBoxRender.class.getResource("res/jcbico/vid.png");
			setText("视频");
			setIcon(new ImageIcon(icp));
		} else if(value.toString().equals(DialogCore.appdata)) {
			URL icp=CoboBoxRender.class.getResource("res/jcbico/apd.png");
			setText("应用数据");
			setIcon(new ImageIcon(icp));
		} else {
			String[] cbbdrina=DialogCore.cbbdriname.toArray(new String[DialogCore.cbbdriname.size()]);
			try {
				URL icp=CoboBoxRender.class.getResource("res/jcbico/disk.png");
				setText(cbbdrina[index]);
				setIcon(new ImageIcon(icp));
			} catch(Exception e) {
				//do nothing
			}
		}
		list.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
            	list.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
            public void mouseExited(MouseEvent e) {
                list.setCursor(Cursor.getDefaultCursor());
            }
        });
		return this;
	}
}
