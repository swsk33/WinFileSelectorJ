package swsk33.WFs;

public class FileSaveDialog extends DialogCore { // �ļ�����Ի���
	public static final int FILE_ONLY = 1; // ָ�������ļ��в�ָ�����ļ���
	public static final int DIR_ONLY = 2; // ��ָ�������ļ���

	public String createSaveDialog(String title, int selectoption) throws Exception { // ����һ������ѡ��ȫ���ĵ�ѡ�ļ�����Ի��򴰿�
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

	public String createSaveDialog(String title, int selectoption, String[] sfliter) throws Exception { // ����һ������ѡ��ȫ���ĵ�ѡ�ļ�����Ի��򴰿�
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
