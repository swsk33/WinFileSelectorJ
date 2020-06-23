package swsk33.WFs;

public class FileSaveDialog extends DialogCore { // 文件保存对话框
	public static final int FILE_ONLY = 1; // 指定保存文件夹并指定其文件名
	public static final int DIR_ONLY = 2; // 仅指定保存文件夹

	public String createSaveDialog(String title, int selectoption) throws Exception { // 创建一个可以选择全部的单选文件保存对话框窗口
		jdt = title;
		selectop = selectoption;
		String sfilepath = "";
		isMultiSelect = false;
		isaSaveDg = true;
		doFliter = false;
		new DialogCore().df();
		sfilepath = selectpath;
		return sfilepath;
	}

	public String createSaveDialog(String title, int selectoption, String[] sfliter) throws Exception { // 创建一个可以选择全部的单选文件保存对话框窗口
		jdt = title;
		selectop = selectoption;
		String sfilepath = "";
		isMultiSelect = false;
		isaSaveDg = true;
		doFliter = true;
		fliter = sfliter;
		new DialogCore().df();
		sfilepath = selectpath;
		return sfilepath;
	}
}
