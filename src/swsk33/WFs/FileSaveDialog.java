package swsk33.WFs;

public class FileSaveDialog extends DialogCore {		//�ļ�����Ի���
	public String createSaveDialog(String title,int selectoption) throws Exception {		//����һ������ѡ��ȫ���ĵ�ѡ�ļ�����Ի��򴰿�
		jdt=title;
		selectop=selectoption;
		String sfilepath="";
		if(selectop==3) {
			try {
				Process tip=Runtime.getRuntime().exec("cmd /c echo msgbox \"�����Ǳ���Ի���ʱ������ֻ��ѡ����������\",64,\"����\">alert.vbs && start alert.vbs && ping -n 2 127.1>nul && del alert.vbs");
				sfilepath="�ļ�ѡ������������";
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
	
	public String createSaveDialog(String title,int selectoption,String[] sfliter) throws Exception {		//����һ������ѡ��ȫ���ĵ�ѡ�ļ�����Ի��򴰿�
		jdt=title;
		selectop=selectoption;
		String sfilepath="";
		if(selectop==3) {
			try {
				Process tip=Runtime.getRuntime().exec("cmd /c echo msgbox \"�����Ǳ���Ի���ʱ������ֻ��ѡ����������\",64,\"����\">alert.vbs && start alert.vbs && ping -n 2 127.1>nul && del alert.vbs");
				sfilepath="�ļ�ѡ������������";
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
