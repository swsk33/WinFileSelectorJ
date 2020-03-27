package swsk33.WFs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

@SuppressWarnings({ "serial", "rawtypes" })
class CoboBoxRender extends JLabel implements ListCellRenderer {
	public Component getListCellRendererComponent(JList list,Object value,int index,boolean isSelected,boolean cellHasFocus) {
		if(value.toString().equals(DialogCore.desktop)) {
			setText("桌面");
			setIcon(new ImageIcon(CoboBoxRender.class.getResource("res\\jcbico\\des.png")));
		} else if(value.toString().equals(DialogCore.download)) {
			setText("下载");
			setIcon(new ImageIcon(CoboBoxRender.class.getResource("res\\jcbico\\dow.png")));
		} else if(value.toString().equals(DialogCore.document)) {
			setText("文档");
			setIcon(new ImageIcon(CoboBoxRender.class.getResource("res\\jcbico\\doc.png")));
		} else if(value.toString().equals(DialogCore.picture)) {
			setText("图片");
			setIcon(new ImageIcon(CoboBoxRender.class.getResource("res\\jcbico\\pic.png")));
		} else if(value.toString().equals(DialogCore.music)) {
			setText("音乐");
			setIcon(new ImageIcon(CoboBoxRender.class.getResource("res\\jcbico\\mus.png")));
		} else if(value.toString().equals(DialogCore.video)) {
			setText("视频");
			setIcon(new ImageIcon(CoboBoxRender.class.getResource("res\\jcbico\\vid.png")));
		} else if(value.toString().equals(DialogCore.appdata)) {
			setText("应用数据");
			setIcon(new ImageIcon(CoboBoxRender.class.getResource("res\\jcbico\\apd.png")));
		} else {
			String[] cbbdrina=DialogCore.cbbdriname.toArray(new String[DialogCore.cbbdriname.size()]);
			try {
				setText(cbbdrina[index]);
				setIcon(new ImageIcon(CoboBoxRender.class.getResource("res\\jcbico\\disk.png")));
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
