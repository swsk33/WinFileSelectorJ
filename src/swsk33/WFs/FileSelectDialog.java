package swsk33.WFs;

public class FileSelectDialog extends DialogCore { // ����һ���ļ�ѡ��������
	public static final int ALL_FILES_ALLOW = 0; // ����ѡ���ļ����ļ���
	public static final int FILE_ONLY = 1; // ֻ��ѡ���ļ�
	public static final int DIR_ONLY = 2; // ֻ��ѡ���ļ���
	public static final int DRIVE_ONLY = 3; // ֻ��ѡ��������

	public String createSingleSelectionDialog(String title, int selectoption) throws Exception { // ����һ������ѡ��ȫ���ĵ�ѡ�ļ�ѡ��������
		jdt = title;
		selectop = selectoption;
		isMultiSelect = false;
		isaSaveDg = false;
		doFliter = false;
		new DialogCore().df();
		String sfilepath = selectpath;
		return sfilepath;
	}

	public String createSingleSelectionDialog(String title, int selectoption, String[] sfliter) throws Exception { // ����һ�������˵ĵ�ѡ�ļ�ѡ��������
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

	public Object[] createMultipleSelectionDialog(String title, int selectoption) throws Exception { // ����һ������ѡ��ȫ���Ķ�ѡ�ļ�ѡ��������
		jdt = title;
		selectop = selectoption;
		isMultiSelect = true;
		isaSaveDg = false;
		doFliter = false;
		new DialogCore().df();
		Object[] sfilepath = multiselectpath;
		return sfilepath;
	}

	public Object[] createMultipleSelectionDialog(String title, int selectoption, String[] sfliter) throws Exception { // ����һ�������˵Ķ�ѡ�ļ�ѡ��������
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
