package swsk33.WFs;

public class FileSelectorDialog extends DialogCore {		//����һ���ļ�ѡ��������
	public String createSingleElectionDialog(String title,int selectoption) throws Exception {
		jdt=title;
		selectop=selectoption;
		isMultiSelect=false;
		isaSaveDg=false;
		doFliter=false;
		new DialogCore().df();
		String sfilepath=selectpath;
		return sfilepath;
	}
}
