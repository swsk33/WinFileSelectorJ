package com.gitee.swsk33.winfileselector;

/**
 * 创建一个文件保存对话框
 * 
 * @author swsk33
 *
 */
public class FileSaveDialog extends DialogCore {

	/**
	 * 指定保存文件夹并指定其文件名
	 */
	public static final int FILE_ONLY = 1;
	/**
	 * 仅指定保存文件夹
	 */
	public static final int DIR_ONLY = 2;

	/**
	 * 创建一个可以选择全部的单选文件保存对话框窗口
	 * 
	 * @param title        窗口标题
	 * @param selectoption 选择模式参数（FileSaveDialog.FILE_ONLY
	 *                     指定保存文件的文件夹及文件名，FileSaveDialog.DIR_ONLY 仅选择保存文件夹路径）
	 * @return String 字符串 选择保存的文件/文件夹路径（直接取消或者关闭窗口将返回空字符串""）
	 * @throws Exception 文件存在错误或者资源不存在抛出异常
	 */
	public static String createSaveDialog(String title, int selectoption) throws Exception {
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

	/**
	 * 创建一个带有文件类型过滤显示的单选文件保存对话框窗口
	 * 
	 * @param title        窗口标题
	 * @param selectoption 选择模式参数（FileSaveDialog.FILE_ONLY
	 *                     指定保存文件的文件夹及文件名，FileSaveDialog.DIR_ONLY 仅选择保存文件夹路径）
	 * @param sfliter      指定显示的文件类型（传入String数组）
	 * @return String 字符串 选择保存的文件/文件夹路径（直接取消或者关闭窗口将返回空字符串""）
	 * @throws Exception 文件存在错误或者资源不存在抛出异常
	 */
	public static String createSaveDialog(String title, int selectoption, String[] sfliter) throws Exception {
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