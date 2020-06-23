package swsk33.WFs;

public class FileSelectDialog extends DialogCore { // 创建一个文件选择器窗口
	public static final int ALL_FILES_ALLOW = 0; // 允许选择文件和文件夹
	public static final int FILE_ONLY = 1; // 只能选择文件
	public static final int DIR_ONLY = 2; // 只能选择文件夹
	public static final int DRIVE_ONLY = 3; // 只能选择驱动器

	public String createSingleSelectionDialog(String title, int selectoption) throws Exception { // 创建一个可以选择全部的单选文件选择器窗口
		jdt = title;
		selectop = selectoption;
		isMultiSelect = false;
		isaSaveDg = false;
		doFliter = false;
		new DialogCore().df();
		String sfilepath = selectpath;
		return sfilepath;
	}

	public String createSingleSelectionDialog(String title, int selectoption, String[] sfliter) throws Exception { // 创建一个带过滤的单选文件选择器窗口
		jdt = title;
		selectop = selectoption;
		isMultiSelect = false;
		isaSaveDg = false;
		doFliter = true;
		fliter = sfliter;
		new DialogCore().df();
		String sfilepath = selectpath;
		return sfilepath;
	}

	public Object[] createMultipleSelectionDialog(String title, int selectoption) throws Exception { // 创建一个可以选择全部的多选文件选择器窗口
		jdt = title;
		selectop = selectoption;
		isMultiSelect = true;
		isaSaveDg = false;
		doFliter = false;
		new DialogCore().df();
		Object[] sfilepath = multiselectpath;
		return sfilepath;
	}

	public Object[] createMultipleSelectionDialog(String title, int selectoption, String[] sfliter) throws Exception { // 创建一个带过滤的多选文件选择器窗口
		jdt = title;
		selectop = selectoption;
		isMultiSelect = true;
		isaSaveDg = false;
		doFliter = true;
		fliter = sfliter;
		new DialogCore().df();
		Object[] sfilepath = multiselectpath;
		return sfilepath;
	}
}
