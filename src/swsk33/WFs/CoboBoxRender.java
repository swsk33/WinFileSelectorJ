package swsk33.WFs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

@SuppressWarnings({ "serial", "rawtypes" })
class CoboBoxRender extends JLabel implements ListCellRenderer {
	public Component getListCellRendererComponent(JList list,Object value,int index,boolean isSelected,boolean cellHasFocus) {
		if(value.toString().equals(DialogCore.desktop)) {
			setText("����");
			setIcon(new ImageIcon(CoboBoxRender.class.getResource("res\\jcbico\\des.png")));
		} else if(value.toString().equals(DialogCore.download)) {
			setText("����");
			setIcon(new ImageIcon(CoboBoxRender.class.getResource("res\\jcbico\\dow.png")));
		} else if(value.toString().equals(DialogCore.document)) {
			setText("�ĵ�");
			setIcon(new ImageIcon(CoboBoxRender.class.getResource("res\\jcbico\\doc.png")));
		} else if(value.toString().equals(DialogCore.picture)) {
			setText("ͼƬ");
			setIcon(new ImageIcon(CoboBoxRender.class.getResource("res\\jcbico\\pic.png")));
		} else if(value.toString().equals(DialogCore.music)) {
			setText("����");
			setIcon(new ImageIcon(CoboBoxRender.class.getResource("res\\jcbico\\mus.png")));
		} else if(value.toString().equals(DialogCore.video)) {
			setText("��Ƶ");
			setIcon(new ImageIcon(CoboBoxRender.class.getResource("res\\jcbico\\vid.png")));
		} else if(value.toString().equals(DialogCore.appdata)) {
			setText("Ӧ������");
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
