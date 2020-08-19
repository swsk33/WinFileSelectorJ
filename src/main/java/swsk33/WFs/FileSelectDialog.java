package swsk33.WFs;

/**
 * 创建一个文件选择器窗口
 * 
 * @author swsk33
 *
 */
public class FileSelectDialog extends DialogCore {
	/**
	 * 允许选择文件和文件夹
	 */
	public static final int ALL_FILES_ALLOW = 0;
	/**
	 * 只能选择文件
	 */
	public static final int FILE_ONLY = 1;
	/**
	 * 只能选择文件夹
	 */
	public static final int DIR_ONLY = 2;
	/**
	 * 只能选择驱动器
	 */
	public static final int DRIVE_ONLY = 3;

	/**
	 * 创建一个可以选择全部的单选文件选择器窗口
	 * 
	 * @param title        窗口标题
	 * @param selectoption 选择模式
	 * @return String 字符串 即为选择的文件路径（直接取消或者关闭窗口将返回空字符串""）
	 * @throws Exception 文件存在错误或者资源不存在抛出异常
	 */
	public String createSingleSelectionDialog(String title, int selectoption) throws Exception {
		jdt = title;
		selectop = selectoption;
		isMultiSelect = false;
		isaSaveDg = false;
		doFliter = false;
		new DialogCore().df();
		String sfilepath = selectpath;
		return sfilepath;
	}

	/**
	 * 创建一个带过滤的单选文件选择器窗口
	 * 
	 * @param title        窗口标题
	 * @param selectoption 选择模式
	 * @param sfliter      指定显示的文件类型（传入String数组）
	 * @return String 字符串 即为选择的文件路径（直接取消或者关闭窗口将返回空字符串""）
	 * @throws Exception 文件存在错误或者资源不存在抛出异常
	 */
	public String createSingleSelectionDialog(String title, int selectoption, String[] sfliter) throws Exception {
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

	/**
	 * 创建一个可以选择全部的多选文件选择器窗口
	 * 
	 * @param title        窗口标题
	 * @param selectoption 选择模式
	 * @return Object[]
	 *         即为选择的多个文件路径的数组，可以使用toString()方法获取其元素的值（直接取消或者关闭窗口将返回数组{"null"}）
	 * @throws Exception 文件存在错误或者资源不存在抛出异常
	 */
	public Object[] createMultipleSelectionDialog(String title, int selectoption) throws Exception {
		jdt = title;
		selectop = selectoption;
		isMultiSelect = true;
		isaSaveDg = false;
		doFliter = false;
		new DialogCore().df();
		Object[] sfilepath = multiselectpath;
		return sfilepath;
	}

	/**
	 * 创建一个带过滤的多选文件选择器窗口
	 * 
	 * @param title        窗口标题
	 * @param selectoption 选择模式
	 * @param sfliter      指定显示的文件类型（传入String数组）
	 * @return Object[]
	 *         即为选择的多个文件路径的数组，可以使用toString()方法获取其元素的值（直接取消或者关闭窗口将返回数组{"null"}）
	 * @throws Exception 文件存在错误或者资源不存在抛出异常
	 */
	public Object[] createMultipleSelectionDialog(String title, int selectoption, String[] sfliter) throws Exception {
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
