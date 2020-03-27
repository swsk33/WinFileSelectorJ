package swsk33.WFs;

import javax.swing.*;
import javax.swing.filechooser.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class DialogCore {
	
	static int x;
	static int y;
	static int viewop;		//��ͼ����
	static int selectop;		//ѡ�����
	//�������
	static String oks="ȷ��";
	static String jdt="ѡ���ļ�";
	//����·������
	static String desktop;		//����·��
	static String download;		//����·��
	static String document;		//�ĵ�·��
	static String music;		//����·��
	static String picture;		//ͼƬ·��
	static String video;		//��Ƶ·��
	static String appdata;		//Ӧ������
	
	static String cdpath;		//��ǰ����·��
	static boolean isInaDisk;		//����·���Ƿ���һ����������
	static boolean isMultiSelect;		//�Ƿ��ѡ
	static boolean doFliter;		//�Ƿ����
	static boolean isfrShow=false;		//Ԥ�����Ƿ�����ʾ
	static JComboBox jcbidx=new JComboBox();		//��������
	static JComboBox jcbtyp=new JComboBox();		//�ļ�����
	static DefaultListModel dfl=new DefaultListModel();		//�ļ��б��ģ��
	static JList fls;		//�ļ��б�
	static JCheckBox isShowPre=new JCheckBox("��ʾͼƬԤ��������");
	static JTextField jtn=new JTextField();
	//��������ͼ�б����������Ϣ�б�
	static ArrayList<String> driname=new ArrayList<String>();
	static ArrayList<String> dritype=new ArrayList<String>();
	//���������˵�����������Ϣ�б�
	static ArrayList<String> cbbdriname=new ArrayList<String>();
	//ͼƬ���ø�ʽ���ṩԤ��ͼ���������ʽ
	static String[] compicfo={"jpg","jpeg","png","bmp","gif"};
	static String[] othpicfo={"psd","tiff","iff","jfif","svg","pcx","dxf","wmf","emf","lic","eps","tga","raw","ico","webp","heic","spr","mpo"};
	//��Ƶ�ļ���ʽ
	static String[] audfo={"mp3","mp2","aiff","wav","mid","wma","m4a","ogg","flac","amr","ra","rm","rmx","vqf","ape","aac","cda"};
	//��Ƶ�ļ���ʽ
	static String[] vedfo={"mp4","avi","flv","wmv","3gp","mov","mkv","mpg","asf","asx","rm","rmvb","m4v","dat","vob"};
	//�ĵ���ʽ
	static String[] docfo={"doc","docx","xls","xlsx","ppt","pptx","pdf","txt"};
	//ѹ���ļ���ʽ
	static String[] zipfo={"zip","rar","7z","jar","tar","gz","xz","uue","iso","apk"};
	//�����ļ��Ϳ�ִ��/�������ļ���ʽ
	static String[] progfo={"c","o","cpp","py","java","bat","go","js","html","css","dll","exe","class"};
	
	public final int ALL_FILES_ALLOW=0;
	public final int FILE_ONLY=1;
	public final int DIR_ONLY=2;
	public final int DRIVE_ONLY=3;
	
	void idxfileexa() throws Exception {		//·����¼�ļ��Լ켰��ʼ��
		//�ļ��Լ�
		File appdir=new File(System.getProperty("user.home")+"\\AppData\\Local\\WinFileSelectorJ");
		File reidx=new File(System.getProperty("user.home")+"\\AppData\\Local\\WinFileSelectorJ\\repath.wfs");
		if(!reidx.exists()) {
			appdir.mkdir();
			reidx.createNewFile();
			new FileRaWUtils().writeText(reidx.getAbsolutePath(),System.getProperty("user.home"));
			new FileRaWUtils().writeText(reidx.getAbsolutePath(),"0");
		}
		//ȫ�ָ�ֵ
		cdpath=new FileRaWUtils().ReadText(reidx.getAbsolutePath(),1);
		viewop=Integer.parseInt(new FileRaWUtils().ReadText(reidx.getAbsolutePath(),2));
		//�ж�ֵ�Ƿ�����
		if(!cdpath.equals("root")&&!new File(cdpath).exists()) {
			cdpath=System.getProperty("user.home");
			new FileRaWUtils().replaceLine(reidx.getAbsolutePath(),1,cdpath);
		}
		if(viewop!=0&&viewop!=1&&viewop!=2) {
			new FileRaWUtils().replaceLine(reidx.getAbsolutePath(),2,"0");
			viewop=0;
		}
		//�����ʼ��
		if(cdpath.equals("root")) {
			isInaDisk=false;
		} else {
			isInaDisk=true;
		}
		if(isInaDisk) {
			this.refreshfile();
		} else {
			this.getdriveroot();
		}
		this.setcombobox();
	}
	
	@SuppressWarnings("unchecked")
	void refreshfile() {		//��ȡ�ļ��б�(�޹���)
		dfl.removeAllElements();
		File[] filels=new File(cdpath).listFiles();
		try {	
			for(File adddirs:filels) {
				if(adddirs.isDirectory()) {
					dfl.addElement(adddirs);
				}
			}
			for(File addfiles:filels) {
				if(addfiles.isFile()) {
					dfl.addElement(addfiles);
				}
			}
		} catch(Exception e) {
			System.out.println("Ŀ¼�쳣��");
		}
	}
	
	@SuppressWarnings({ "unchecked" })
	void refreshfile(String[] hidefiles) {		//��ȡ�ļ��б�(���ù���)
		dfl.removeAllElements();
		File[] filels=new File(cdpath).listFiles();
		try {
			for(File adddirs:filels) {
				if(adddirs.isDirectory()) {
					dfl.addElement(adddirs);
				}
			}
			for(File addfile:filels) {
				boolean shouldadd=true;
				if(addfile.isFile()) {
					String ft=new FileRaWUtils().getFileFormat(addfile.getAbsolutePath());
					for(String cft:hidefiles) {
						if(cft.equals(ft)) {
							shouldadd=false;
							break;
						}
					}
					if(shouldadd) {
						dfl.addElement(addfile);
					}
				}
			}
		} catch(Exception e) {
			System.out.println("Ŀ¼�쳣��");
		}
	}
	
	void getdriveroot() {		//��ȡ����������
		dfl.removeAllElements();
		driname.clear();
		dritype.clear();
		File[] dr=File.listRoots();
		FileSystemView fsv=FileSystemView.getFileSystemView();
		for(File ss:dr) {
			dfl.addElement(ss);
			driname.add(fsv.getSystemDisplayName(ss));
			dritype.add(fsv.getSystemTypeDescription(ss));
		}
	}
	
	void setcombobox() {		//��ʼ�����������˵�
		jcbidx.removeAllItems();
		cbbdriname.clear();
		desktop=System.getProperty("user.home")+"\\Desktop";
		download=System.getProperty("user.home")+"\\Downloads";
		document=System.getProperty("user.home")+"\\Documents";
		picture=System.getProperty("user.home")+"\\Pictures";
		music=System.getProperty("user.home")+"\\Music";
		video=System.getProperty("user.home")+"\\Videos";
		appdata=System.getProperty("user.home")+"\\AppData";
		File[] dr=File.listRoots();
		FileSystemView fsv=FileSystemView.getFileSystemView();
		jcbidx.addItem(desktop);
		jcbidx.addItem(download);
		jcbidx.addItem(document);
		jcbidx.addItem(picture);
		jcbidx.addItem(music);
		jcbidx.addItem(video);
		jcbidx.addItem(appdata);
		for(int i=0;i<7;i++) {		//������Ⱦ��������
			cbbdriname.add("�û��ļ���"+(i+1));
		}
		for(File ss:dr) {
			jcbidx.addItem(ss.getAbsolutePath());
			cbbdriname.add(fsv.getSystemDisplayName(ss));
		}
	}
	
	/**
	 * @throws Exception 
	 * @wbp.parser.entryPoint
	 */
	@SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
	public void df() throws Exception {
		this.idxfileexa();
		Toolkit kit=Toolkit.getDefaultToolkit();
		Dimension sc=kit.getScreenSize();
		JDialog jd=new JDialog();
		jd.setSize(750,500);
		jd.setLocation(sc.width/2-375,sc.height/2-250);
		jd.setUndecorated(true);
		jd.setModal(true);
		JLabel bl=new JLabel(new ImageIcon(DialogCore.class.getResource("res\\bg.png")));          // �������ͼƬ����ӵ�һ����Ϊbl�ı�ǩ��  
		bl.setBounds(0,0,jd.getWidth(),jd.getHeight());        //���ñ�ǩ��С
		JPanel imagePanel=(JPanel)jd.getContentPane();        // �����ݴ���ת��ΪJPanel���������÷���setOpaque()��ʹ���ݴ���͸�� ��ʹ���ݴ���͸���������ʾ����ͼƬ 
		imagePanel.setOpaque(false);          // �ѱ���ͼƬ��ӵ��ֲ㴰�����ײ���Ϊ����  
		jd.getLayeredPane().add(bl,new Integer(Integer.MIN_VALUE));
		jd.addMouseListener(new MouseAdapter() {        //���ô��ڿ��϶�����Ӽ�����
		    public void mousePressed(MouseEvent e) {        //��ȡ������ʱ������
		        x=e.getPoint().x;
		        y=e.getPoint().y;
		    }
		});      
		jd.addMouseMotionListener(new MouseMotionAdapter() {        //������ק�󣬴��ڵ�λ��
		    public void mouseDragged(MouseEvent e) {
		        jd.setLocation(e.getXOnScreen()-x,e.getYOnScreen()-y);		
		    }
		});
		JLabel title=new JLabel(jdt);
		title.setFont(new Font("����", Font.BOLD, 18));
		title.setBounds(6, 4, 255, 27);
		title.setToolTipText(jdt);
		JLabel idx=new JLabel("����������");
		idx.setFont(new Font("����", Font.BOLD, 18));
		idx.setBounds(20, 51, 90, 27);
		JLabel name=new JLabel("�ļ�����");
		name.setFont(new Font("����", Font.BOLD, 20));
		name.setBounds(53, 406, 80, 18);
		JLabel type=new JLabel("�ļ����ͣ�");
		type.setFont(new Font("����", Font.BOLD, 20));
		type.setBounds(33, 456, 100, 18);
		JButton close=new JButton(new ImageIcon(DialogCore.class.getResource("res\\bt-close.png")));
		close.addActionListener(new ActionListener() {		//�رմ���
			public void actionPerformed(ActionEvent arg0) {
				jd.dispose();
			}
		});
		close.setBounds(715, 2, 32, 32);
		close.setBorderPainted(false);
		close.setContentAreaFilled(false);
		close.setToolTipText("�رմ���");
		JButton computer=new JButton(new ImageIcon(DialogCore.class.getResource("res\\bt-computer.png")));
		computer.addActionListener(new ActionListener() {		//���롰�ҵĵ��ԡ�
			public void actionPerformed(ActionEvent e) {
				cdpath="root";
				isInaDisk=false;
				new DialogCore().getdriveroot();
			}
		});
		computer.setBounds(511, 47, 38, 38);
		computer.setBorderPainted(false);
		computer.setContentAreaFilled(false);
		computer.setToolTipText("����\"�ҵĵ���\"");
		JButton home=new JButton(new ImageIcon(DialogCore.class.getResource("res\\bt-home.png")));
		home.addActionListener(new ActionListener() {		//�����û���Ŀ¼
			public void actionPerformed(ActionEvent e) {
				cdpath=System.getProperty("user.home");
				isInaDisk=true;
				new DialogCore().refreshfile();
			}
		});
		home.setBounds(561, 47, 38, 38);
		home.setBorderPainted(false);
		home.setContentAreaFilled(false);
		home.setToolTipText("�����û�Ŀ¼");
		JButton newDir=new JButton(new ImageIcon(DialogCore.class.getResource("res\\bt-newdir.png")));
		newDir.addActionListener(new ActionListener() {		//�½��ļ��а�ť
			public void actionPerformed(ActionEvent e) {
				if(isInaDisk) {
					new NewDir().ndfr();
					if(!NewDir.name.equals("")) {
						new File(cdpath+"\\"+NewDir.name).mkdir();
						new DialogCore().refreshfile();
					}
				}
			}
		});
		newDir.setBounds(611, 47, 38, 38);
		newDir.setBorderPainted(false);
		newDir.setContentAreaFilled(false);
		newDir.setToolTipText("�½��ļ���");
		JButton front=new JButton(new ImageIcon(DialogCore.class.getResource("res\\bt-front.png")));
		front.addActionListener(new ActionListener() {		//��һ��Ŀ¼��ť
			public void actionPerformed(ActionEvent arg0) {
				if(isInaDisk) {
					cdpath=new File(cdpath).getParent();
					if(cdpath==null) {
						cdpath="root";
						isInaDisk=false;
						new DialogCore().getdriveroot();
					} else {
						isInaDisk=true;
						new DialogCore().refreshfile();
					}
				}
			}
		});
		front.setBounds(411, 47, 38, 38);
		front.setContentAreaFilled(false);
		front.setBorderPainted(false);
		front.setToolTipText("������һ��Ŀ¼");
		JButton refresh=new JButton(new ImageIcon(DialogCore.class.getResource("res\\bt-refresh.png")));
		refresh.addActionListener(new ActionListener() {		//ˢ�°�ť
			public void actionPerformed(ActionEvent arg0) {
				if(isInaDisk) {
					new DialogCore().refreshfile();
				} else {
					new DialogCore().getdriveroot();
				}
			}
		});
		refresh.setBounds(461, 47, 38, 38);
		refresh.setContentAreaFilled(false);
		refresh.setBorderPainted(false);
		refresh.setToolTipText("ˢ���б�");
		JButton view=new JButton(new ImageIcon(DialogCore.class.getResource("res\\bt-view.png")));
		view.addActionListener(new ActionListener() {		//�л���ͼ
			public void actionPerformed(ActionEvent e) {
				FileRaWUtils fru=new FileRaWUtils();
				if(viewop==2) {
					viewop=0;
				} else {
					viewop++;
				}
				if(viewop==0) {
					fls.setLayoutOrientation(JList.VERTICAL);		//������������
				} else if(viewop==1) {
					fls.setLayoutOrientation(JList.HORIZONTAL_WRAP);		//���ñ���������Ԫ��
					fls.setVisibleRowCount(7);		//����������Ϊ7��
				} else if(viewop==2) {
					fls.setLayoutOrientation(JList.VERTICAL_WRAP);
					fls.setVisibleRowCount(2);
				}
				try {
					fru.replaceLine(System.getProperty("user.home")+"\\AppData\\Local\\WinFileSelectorJ\\repath.wfs",2,""+viewop);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		view.setBounds(661, 47, 38, 38);
		view.setContentAreaFilled(false);
		view.setBorderPainted(false);
		view.setToolTipText("������ͼ");
		JButton ok=new JButton(oks);
		ok.setForeground(new Color(255, 0, 153));
		ok.setFont(new Font("����", Font.BOLD, 20));
		ok.setBounds(605, 398, 88, 35);
		ok.setContentAreaFilled(false);
		JButton cancel=new JButton("ȡ��");
		cancel.setForeground(new Color(255, 102, 0));
		cancel.setFont(new Font("����", Font.BOLD, 20));
		cancel.setBounds(605, 448, 88, 35);
		cancel.setContentAreaFilled(false);
		fls=new JList(dfl);
		fls.setCellRenderer(new WFsCellRender());
		if(viewop==0) {
			fls.setLayoutOrientation(JList.VERTICAL);		//������������
		} else if(viewop==1) {
			fls.setLayoutOrientation(JList.HORIZONTAL_WRAP);		//���ñ���������Ԫ��
			fls.setVisibleRowCount(7);		//����������Ϊ7��
		} else if(viewop==2) {
			fls.setLayoutOrientation(JList.VERTICAL_WRAP);
			fls.setVisibleRowCount(2);
		}
		fls.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {		//���˫��
				if(e.getClickCount()==2) {
					int index=fls.locationToIndex(e.getPoint());		//��ȡ������ڵ�����
					if(!isInaDisk) {		//����ǰ���ҵĵ�����
						cdpath=fls.getModel().getElementAt(index).toString();
						new DialogCore().refreshfile();
						isInaDisk=true;
					} else {		//����ǰ�ڴ�������
						cdpath=fls.getModel().getElementAt(index).toString();
						if(new File(cdpath).isDirectory()) {		//��˫���ļ��У������		
							cdpath=fls.getModel().getElementAt(index).toString();
							new DialogCore().refreshfile();
						} else if(new File(cdpath).isFile()) {		//������ļ�
							cdpath=new File(cdpath).getParent();
							System.out.println("���ļ���");
						}
					}
				}
			}
			public void mouseEntered(MouseEvent e) {		//������ȥ
				if(isShowPre.isSelected()) {
					if(viewop==0||viewop==1) {
						int index=fls.locationToIndex(e.getPoint());		//��ȡ������ڵ�����
						String imgpath=fls.getModel().getElementAt(index).toString();
						String ft=new FileRaWUtils().getFileFormat(imgpath);
						if(ft.equalsIgnoreCase("jpg")||ft.equalsIgnoreCase("jpeg")||ft.equalsIgnoreCase("png")||ft.equalsIgnoreCase("bmp")||ft.equalsIgnoreCase("gif")) {
							int iw;
							int ih;
							ImageIcon preimg=new ImageIcon(imgpath);
							if(preimg.getIconWidth()>preimg.getIconHeight()) {
								iw=250;
								ih=(int)(((float)250/preimg.getIconWidth())*preimg.getIconHeight());
							} else {
								ih=156;
								iw=(int)(((float)156/preimg.getIconHeight())*preimg.getIconWidth());
							}
							Image img=preimg.getImage();
							img=img.getScaledInstance(iw,ih,Image.SCALE_AREA_AVERAGING);
							preimg.setImage(img);
							PreFrame.imgp.setIcon(preimg);
							PreFrame.jf.setLocation(e.getXOnScreen()-183,e.getYOnScreen()-197);
							if(!isfrShow) {
								new PreFrame().prefr();
								isfrShow=true;
							}
						} 
					}
				}
			}
			public void mouseExited(MouseEvent e) {		//����ƿ��¼�
				if(isShowPre.isSelected()) {
					PreFrame.jf.dispose();
					isfrShow=false;
				}
			}
		});
		fls.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseMoved(MouseEvent e) {		//����ƶ��¼�
				if(isShowPre.isSelected()) {
					if(viewop==0||viewop==1) {
						int index=fls.locationToIndex(e.getPoint());		//��ȡ������ڵ�����
						String imgpath=fls.getModel().getElementAt(index).toString();
						String ft=new FileRaWUtils().getFileFormat(imgpath);
						if(ft.equalsIgnoreCase("jpg")||ft.equalsIgnoreCase("jpeg")||ft.equalsIgnoreCase("png")||ft.equalsIgnoreCase("bmp")||ft.equalsIgnoreCase("gif")) {
							ImageIcon preimg=new ImageIcon(imgpath);
							int iw;
							int ih;
							if(preimg.getIconWidth()>preimg.getIconHeight()) {
								iw=250;
								ih=(int)(((float)250/preimg.getIconWidth())*preimg.getIconHeight());
							} else {
								ih=156;
								iw=(int)(((float)156/preimg.getIconHeight())*preimg.getIconWidth());
							}
							Image img=preimg.getImage();
							img=img.getScaledInstance(iw,ih,Image.SCALE_AREA_AVERAGING);
							preimg.setImage(img);
							PreFrame.imgp.setIcon(preimg);
							if(!isfrShow) {
								PreFrame.jf.setLocation(e.getXOnScreen()-183,e.getYOnScreen()-197);
								new PreFrame().prefr();
								isfrShow=true;
							} else {
								PreFrame.jf.setLocation(e.getXOnScreen()-183,e.getYOnScreen()-197);
							}
						} else {		//�Ƶ�����ļ�
							PreFrame.jf.dispose();
							isfrShow=false;
						}
					}
				}
			}
		});
		JScrollPane jsp=new JScrollPane();
		jsp.setBounds(43, 108, 656, 278);
		jsp.setViewportView(fls);
		jcbidx.setBounds(113, 41, 289, 45);
		jcbidx.addActionListener(new ActionListener() {		//�������������˵��¼�
			public void actionPerformed(ActionEvent arg0) {
				cdpath=jcbidx.getSelectedItem().toString();
				isInaDisk=true;
				new DialogCore().refreshfile();
			}
		});
		jcbidx.setRenderer(new CoboBoxRender());
		jcbtyp.setBounds(141, 448, 447, 35);
		jtn.setBounds(141, 399, 447, 35);
		isShowPre.setFont(new Font("����", Font.BOLD, 15));
		isShowPre.setSelected(true);
		isShowPre.setBounds(20, 88, 160, 18);
		isShowPre.setOpaque(false);
		JPanel jp=new JPanel();
		jp.setOpaque(false);
		jp.setLayout(null);
		jp.add(title);
		jp.add(idx);
		jp.add(name);
		jp.add(type);
		jp.add(close);
		jp.add(computer);
		jp.add(home);
		jp.add(newDir);
		jp.add(front);
		jp.add(refresh);
		jp.add(view);
		jp.add(ok);
		jp.add(cancel);
		jp.add(jsp);
		jp.add(jcbidx);
		jp.add(jcbtyp);
		jp.add(jtn);
		jp.add(isShowPre);
		jd.getContentPane().add(jp);
		jd.show();
	}
}
