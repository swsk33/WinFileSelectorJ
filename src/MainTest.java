import swsk33.WFs.*;
public class MainTest {
	public static void main(String[] args) throws Exception {
		String[] ss={"png","jpg","exe"};
		String s=new FileSaveDialog().createSaveDialog("�����ļ�",FileSaveDialog.FILE_ONLY,ss);
		System.out.println(s);
	}
}
