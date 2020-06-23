import swsk33.WFs.*;

public class Test {
	public static void main(String[] args) throws Exception {
		FileSelectDialog fd = new FileSelectDialog();
		FileSaveDialog fs = new FileSaveDialog();
		String[] dd = { "png", "caj", "exe" };
		String s = fs.createSaveDialog("±£´æÎÄ¼þ", fs.FILE_ONLY,dd);
		System.out.println(s);

	}
}
