import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import swsk33.WFs.*;
public class MainTest {
	public static void main(String[] args) throws Exception {
		String s=new FileSelectorDialog().createSingleElectionDialog("Ñ¡ÔñÎÄ¼þ°¡",FileSelectorDialog.FILE_ONLY);
		System.out.println(s);
	}
}
