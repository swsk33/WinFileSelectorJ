import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import swsk33.WFs.*;
public class MainTest {
	public static void main(String[] args) throws Exception {
		String[] fl={"png","jpg","exe"};
		FileSaveDialog fsd=new FileSaveDialog();
		String s=fsd.createSaveDialog("ѡ�񱣴�·����",fsd.ALL_FILES_ALLOW,fl);
		System.out.println(s);
	}
}
