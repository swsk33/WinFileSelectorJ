package swsk33.WFs;

public class FileSaveDialog extends DialogCore {		//文件保存对话框
	public String createSaveDialog(String title,int selectoption) throws Exception {		//创建一个可以选择全部的单选文件保存对话框窗口
		jdt=title;
		selectop=selectoption;
		String sfilepath="";
		if(selectop==3) {
			try {
				Process tip=Runtime.getRuntime().exec("cmd /c echo msgbox \"窗口是保存对话框时不允许只能选择驱动器！\",64,\"错误\">alert.vbs && start alert.vbs && ping -n 2 127.1>nul && del alert.vbs");
				sfilepath="文件选择器参数错误！";
			} catch(Exception e) {
				e.printStackTrace();
			}
		} else {
			isMultiSelect=false;
			isaSaveDg=true;
			doFliter=false;
			new DialogCore().df();
			sfilepath=selectpath;
		}
		return sfilepath;
	}
	
	public String createSaveDialog(String title,int selectoption,String[] sfliter) throws Exception {		//创建一个可以选择全部的单选文件保存对话框窗口
		jdt=title;
		selectop=selectoption;
		String sfilepath="";
		if(selectop==3) {
			try {
				Process tip=Runtime.getRuntime().exec("cmd /c echo msgbox \"窗口是保存对话框时不允许只能选择驱动器！\",64,\"错误\">alert.vbs && start alert.vbs && ping -n 2 127.1>nul && del alert.vbs");
				sfilepath="文件选择器参数错误！";
			} catch(Exception e) {
				e.printStackTrace();
			}
		} else {
			isMultiSelect=false;
			isaSaveDg=true;
			doFliter=true;
			fliter=sfliter;
			new DialogCore().df();
			sfilepath=selectpath;
		}
		return sfilepath;
	}
}
