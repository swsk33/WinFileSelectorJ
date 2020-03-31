package swsk33.WFs;

import javax.swing.*;
import javax.swing.filechooser.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class DialogCore {
	
	//窗口位置
	static int x;
	static int y;
	//界面变量
	static String oks="确定";
	static String jdt="选择文件";
	//常用路径索引
	static String desktop;		//桌面路径
	static String download;		//下载路径
	static String document;		//文档路径
	static String music;		//音乐路径
	static String picture;		//图片路径
	static String video;		//视频路径
	static String appdata;		//应用数据
	
	static String cdpath;		//当前所在路径
	static int viewop;		//视图参数
	static boolean isInaDisk;		//所在路径是否在一个磁盘里面
	static boolean isfrShow=false;		//预览窗是否在显示
	//下面四个为用户决定性参数
	static boolean isaSaveDg;		//是否为保存对话框
	static boolean isMultiSelect;		//是否多选
	static boolean doFliter;		//是否过滤
	static int selectop;		//选择参数
	static String[] fliter;		//过滤文件（只显示的文件类型的列表）
	
	static JComboBox jcbidx=new JComboBox();		//快速索引
	static JComboBox jcbtyp=new JComboBox();		//文件类型
	static DefaultListModel dfl=new DefaultListModel();		//文件列表的模型
	static JList fls;		//文件列表
	static JCheckBox isShowPre=new JCheckBox("显示图片预览悬浮窗");
	static JTextField jtn=new JTextField();
	
	//用于主视图列表的驱动器信息列表
	static ArrayList<String> driname=new ArrayList<String>();
	static ArrayList<String> dritype=new ArrayList<String>();
	//用于下拉菜单的驱动器信息列表
	static ArrayList<String> cbbdriname=new ArrayList<String>();
	//图片常用格式（提供预览图）和其余格式
	static String[] compicfo={"jpg","jpeg","png","bmp","gif"};
	static String[] othpicfo={"psd","tiff","iff","jfif","svg","pcx","dxf","wmf","emf","lic","eps","tga","raw","ico","webp","heic","spr","mpo"};
	//音频文件格式
	static String[] audfo={"mp3","mp2","aiff","wav","mid","wma","m4a","ogg","flac","amr","ra","rm","rmx","vqf","ape","aac","cda"};
	//视频文件格式
	static String[] vedfo={"mp4","avi","flv","wmv","3gp","mov","mkv","mpg","asf","asx","rm","rmvb","m4v","vob"};
	//文档格式
	static String[] docfo={"doc","docx","xls","xlsx","ppt","pptx","pdf","txt"};
	//压缩文件格式
	static String[] zipfo={"zip","rar","7z","jar","tar","gz","xz","uue","iso","apk"};
	//代码文件和可执行/二进制文件格式
	static String[] progfo={"c","o","cpp","py","java","bat","go","js","html","css","dll","exe","class"};
	
	//单选、多选获取的文件路径
	static String selectpath;
	static Object[] multiselectpath;
	
	public final int ALL_FILES_ALLOW=0;
	public final int FILE_ONLY=1;
	public final int DIR_ONLY=2;
	public final int DRIVE_ONLY=3;
	
	void idxfileexa() throws Exception {		//路径记录文件自检及初始化
		//文件自检
		File appdir=new File(System.getProperty("user.home")+"\\AppData\\Local\\WinFileSelectorJ");
		File reidx=new File(System.getProperty("user.home")+"\\AppData\\Local\\WinFileSelectorJ\\repath.wfs");
		if(!reidx.exists()) {
			appdir.mkdir();
			reidx.createNewFile();
			new FileRaWUtils().writeText(reidx.getAbsolutePath(),System.getProperty("user.home"));
			new FileRaWUtils().writeText(reidx.getAbsolutePath(),"0");
		}
		//全局赋值
		cdpath=new FileRaWUtils().ReadText(reidx.getAbsolutePath(),1);
		viewop=Integer.parseInt(new FileRaWUtils().ReadText(reidx.getAbsolutePath(),2));
		//判断值是否有误
		if(!cdpath.equals("root")&&!new File(cdpath).exists()) {
			cdpath=System.getProperty("user.home");
			new FileRaWUtils().replaceLine(reidx.getAbsolutePath(),1,cdpath);
		}
		if(viewop!=0&&viewop!=1&&viewop!=2) {
			new FileRaWUtils().replaceLine(reidx.getAbsolutePath(),2,"0");
			viewop=0;
		}
		//进入初始化
		if(cdpath.equals("root")) {		//初始化路径状态
			isInaDisk=false;
		} else {
			isInaDisk=true;
		}
		this.setcombobox();
		if(!isaSaveDg) {		//判断是否为保存对话框
			jtn.setEditable(false);
		}
		if(isInaDisk) {		//在磁盘里面时
			this.refreshfile();
			if(selectop==3) {		//特殊情况：只能选择驱动器时
				jcbtyp.removeAllItems();
				jcbtyp.addItem("磁盘驱动器(disk)");
				jcbidx.setEnabled(false);
				jtn.setEnabled(false);
				isInaDisk=false;
				cdpath="root";
				this.getdriveroot();
			}
		} else {
			this.getdriveroot();
		}
	}
	
	@SuppressWarnings("unchecked")
	void refreshfile() {		//获取文件列表
		if(selectop==2) {		//只能选择文件夹时
			dfl.removeAllElements();
			File[] filels=new File(cdpath).listFiles();
			try {
				for(File dirs:filels) {
					if(dirs.isDirectory()) {
						dfl.addElement(dirs);
					}
				}
			} catch(Exception e) {
				System.out.println("目录异常！");
			}
			jcbtyp.removeAllItems();
			jcbtyp.addItem("文件夹(dir)");
		} else {
			if(!doFliter) {		//不做过滤时
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
					System.out.println("目录异常！");
				}
				jcbtyp.removeAllItems();
				jcbtyp.addItem("所有文件(*.*)");
			} else {		//过滤时
				dfl.removeAllElements();
				File[] filels=new File(cdpath).listFiles();
				try {
					for(File adddirs:filels) {
						if(adddirs.isDirectory()) {
							dfl.addElement(adddirs);
						}
					}
					for(File addfile:filels) {
						boolean shouldadd=false;
						if(addfile.isFile()) {
							String ft=new FileRaWUtils().getFileFormat(addfile.getAbsolutePath());
							for(String cft:fliter) {
								if(cft.equals(ft)) {
									shouldadd=true;
									break;
								}
							}
							if(shouldadd) {
								dfl.addElement(addfile);
							}
						}
					}
				} catch(Exception e) {
					System.out.println("目录异常！");
				}
				jcbtyp.removeAllItems();
				String text="可选文件：";
				for(String st:fliter) {
					text=text+"*."+st+";";
				}
				jcbtyp.addItem(text);
			}
		}
	}
	
	void getdriveroot() {		//获取所有驱动器
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
	
	void setcombobox() {		//初始化索引下拉菜单
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
		for(int i=0;i<7;i++) {		//对齐渲染器的数组
			cbbdriname.add("用户文件夹"+(i+1));
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
		selectop=0;
		isMultiSelect=false;
		isaSaveDg=false;
		doFliter=false;
		String[] a={"png","jpg","exe"};
		fliter=a;
		this.idxfileexa();
		Toolkit kit=Toolkit.getDefaultToolkit();
		Dimension sc=kit.getScreenSize();
		JDialog jd=new JDialog();
		jd.setSize(750,500);
		jd.setLocation(sc.width/2-375,sc.height/2-250);
		jd.setUndecorated(true);
		jd.setModal(true);
		URL bg=DialogCore.class.getResource("res/bg.png");
		JLabel bl=new JLabel(new ImageIcon(bg));          // 把上面的图片对象加到一个名为bl的标签里  
		bl.setBounds(0,0,jd.getWidth(),jd.getHeight());        //设置标签大小
		JPanel imagePanel=(JPanel)jd.getContentPane();        // 把内容窗格转化为JPanel，否则不能用方法setOpaque()来使内容窗格透明 ，使内容窗格透明后才能显示背景图片 
		imagePanel.setOpaque(false);          // 把背景图片添加到分层窗格的最底层作为背景  
		jd.getLayeredPane().add(bl,new Integer(Integer.MIN_VALUE));
		jd.addMouseListener(new MouseAdapter() {        //设置窗口可拖动，添加监听器
		    public void mousePressed(MouseEvent e) {        //获取点击鼠标时的坐标
		        x=e.getPoint().x;
		        y=e.getPoint().y;
		    }
		});      
		jd.addMouseMotionListener(new MouseMotionAdapter() {        //设置拖拽后，窗口的位置
		    public void mouseDragged(MouseEvent e) {
		        jd.setLocation(e.getXOnScreen()-x,e.getYOnScreen()-y);		
		    }
		});
		JLabel title=new JLabel(jdt);
		title.setFont(new Font("黑体", Font.BOLD, 18));
		title.setBounds(6, 4, 255, 27);
		title.setToolTipText(jdt);
		JLabel idx=new JLabel("快速索引：");
		idx.setFont(new Font("等线", Font.BOLD, 18));
		idx.setBounds(20, 51, 90, 27);
		JLabel name=new JLabel("文件名：");
		name.setFont(new Font("等线", Font.BOLD, 20));
		name.setBounds(53, 406, 80, 18);
		JLabel type=new JLabel("文件类型：");
		type.setFont(new Font("等线", Font.BOLD, 20));
		type.setBounds(33, 456, 100, 18);
		URL cb=DialogCore.class.getResource("res/bt-close.png");
		JButton close=new JButton(new ImageIcon(cb));
		close.addActionListener(new ActionListener() {		//关闭窗口
			public void actionPerformed(ActionEvent arg0) {
				jd.dispose();
			}
		});
		close.setBounds(715, 2, 32, 32);
		close.setBorderPainted(false);
		close.setContentAreaFilled(false);
		close.setToolTipText("关闭窗口");
		URL cmb=DialogCore.class.getResource("res/bt-computer.png");
		JButton computer=new JButton(new ImageIcon(cmb));
		computer.addActionListener(new ActionListener() {		//进入“我的电脑”
			public void actionPerformed(ActionEvent e) {
				cdpath="root";
				isInaDisk=false;
				new DialogCore().getdriveroot();
			}
		});
		computer.setBounds(511, 47, 38, 38);
		computer.setBorderPainted(false);
		computer.setContentAreaFilled(false);
		computer.setToolTipText("进入\"我的电脑\"");
		URL hmb=DialogCore.class.getResource("res/bt-home.png");
		JButton home=new JButton(new ImageIcon(hmb));
		home.addActionListener(new ActionListener() {		//进入用户主目录
			public void actionPerformed(ActionEvent e) {
				cdpath=System.getProperty("user.home");
				isInaDisk=true;
				new DialogCore().refreshfile();
			}
		});
		if(selectop==3) {
			home.setEnabled(false);
		}
		home.setBounds(561, 47, 38, 38);
		home.setBorderPainted(false);
		home.setContentAreaFilled(false);
		home.setToolTipText("进入用户目录");
		URL ndb=DialogCore.class.getResource("res/bt-newdir.png");
		JButton newDir=new JButton(new ImageIcon(ndb));
		newDir.addActionListener(new ActionListener() {		//新建文件夹按钮
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
		newDir.setToolTipText("新建文件夹");
		URL frb=DialogCore.class.getResource("res/bt-front.png");
		JButton front=new JButton(new ImageIcon(frb));
		front.addActionListener(new ActionListener() {		//上一级目录按钮
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
		front.setToolTipText("进入上一级目录");
		URL rfb=DialogCore.class.getResource("res/bt-refresh.png");
		JButton refresh=new JButton(new ImageIcon(rfb));
		refresh.addActionListener(new ActionListener() {		//刷新按钮
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
		refresh.setToolTipText("刷新列表");
		URL vib=DialogCore.class.getResource("res/bt-view.png");
		JButton view=new JButton(new ImageIcon(vib));
		view.addActionListener(new ActionListener() {		//切换视图
			public void actionPerformed(ActionEvent e) {
				FileRaWUtils fru=new FileRaWUtils();
				if(viewop==2) {
					viewop=0;
				} else {
					viewop++;
				}
				if(viewop==0) {
					fls.setLayoutOrientation(JList.VERTICAL);		//设置竖着排列
				} else if(viewop==1) {
					fls.setLayoutOrientation(JList.HORIZONTAL_WRAP);		//设置表格分栏排列元素
					fls.setVisibleRowCount(7);		//设置总行数为7行
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
		view.setToolTipText("更改视图");
		JButton ok=new JButton(oks);
		ok.addActionListener(new ActionListener() {		//确定
			public void actionPerformed(ActionEvent e) {
				FileRaWUtils fru=new FileRaWUtils();
				if(!isMultiSelect) {		//单选时
					if(selectop==0||selectop==2) {		//都可以选择时或者是选择文件夹时
						if(isInaDisk) {
							try {
								fru.replaceLine(System.getProperty("user.home")+"\\AppData\\Local\\WinFileSelectorJ\\repath.wfs",1,cdpath);
								selectpath=fls.getSelectedValue().toString();
								jd.dispose();
							} catch(Exception e1) {
								e1.printStackTrace();
							}
						} else {
							cdpath=fls.getSelectedValue().toString();
							new DialogCore().refreshfile();
							isInaDisk=true;
						}
					} else if(selectop==1) {		//只能选择文件时
						if(isInaDisk) {
							cdpath=fls.getSelectedValue().toString();
							if(new File(cdpath).isDirectory()) {
								new DialogCore().refreshfile();
							} else {
								try {
									selectpath=cdpath;
									cdpath=new File(cdpath).getParent();
									fru.replaceLine(System.getProperty("user.home")+"\\AppData\\Local\\WinFileSelectorJ\\repath.wfs",1,cdpath);
									jd.dispose();
								} catch(Exception e1) {
									e1.printStackTrace();
								}
							}
						} else {
							cdpath=fls.getSelectedValue().toString();
							new DialogCore().refreshfile();
							isInaDisk=true;
						}
					} else {		//只能选择驱动器时
						selectpath=fls.getSelectedValue().toString();
						jd.dispose();
					}
				}
			}
		});
		ok.setForeground(new Color(255, 0, 153));
		ok.setFont(new Font("黑体", Font.BOLD, 20));
		ok.setBounds(605, 398, 88, 35);
		ok.setContentAreaFilled(false);
		JButton cancel=new JButton("取消");
		cancel.addActionListener(new ActionListener() {		//取消按钮
			public void actionPerformed(ActionEvent arg0) {
				jd.dispose();
			}
		});
		cancel.setForeground(new Color(255, 102, 0));
		cancel.setFont(new Font("黑体", Font.BOLD, 20));
		cancel.setBounds(605, 448, 88, 35);
		cancel.setContentAreaFilled(false);
		fls=new JList(dfl);
		fls.setCellRenderer(new WFsCellRender());
		if(viewop==0) {
			fls.setLayoutOrientation(JList.VERTICAL);		//设置竖着排列
		} else if(viewop==1) {
			fls.setLayoutOrientation(JList.HORIZONTAL_WRAP);		//设置表格分栏排列元素
			fls.setVisibleRowCount(7);		//设置总行数为7行
		} else if(viewop==2) {
			fls.setLayoutOrientation(JList.VERTICAL_WRAP);
			fls.setVisibleRowCount(2);
		}
		if(isMultiSelect) {		//单选/多选判断
			fls.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		} else {
			fls.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		}
		fls.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount()==1) {		//鼠标单击
					if(isInaDisk) {
						if(!isMultiSelect) {
							String gfn=new File(fls.getSelectedValue().toString()).getName();
							jtn.setText(gfn);
						}
					}
				}
				if(e.getClickCount()==2) {		//鼠标双击
					int index=fls.locationToIndex(e.getPoint());		//获取鼠标所在的索引
					if(!isInaDisk) {		//若当前在我的电脑下
						if(selectop==3) {		//当只允许选择磁盘时
							selectpath=fls.getSelectedValue().toString();
							jd.dispose();
						} else {
							cdpath=fls.getModel().getElementAt(index).toString();
							new DialogCore().refreshfile();
							isInaDisk=true;
						}
					} else {		//若当前在磁盘里面
						cdpath=fls.getModel().getElementAt(index).toString();
						if(new File(cdpath).isDirectory()) {		//若双击文件夹，则进入		
							cdpath=fls.getModel().getElementAt(index).toString();
							new DialogCore().refreshfile();
						} else if(new File(cdpath).isFile()) {		//如果是文件
							try {
								FileRaWUtils fru=new FileRaWUtils();
								cdpath=new File(cdpath).getParent();
								selectpath=fls.getSelectedValue().toString();
								fru.replaceLine(System.getProperty("user.home")+"\\AppData\\Local\\WinFileSelectorJ\\repath.wfs",1,cdpath);
								jd.dispose();
								if(isfrShow) {
									PreFrame.jf.dispose();
								}
							} catch(Exception e1) {
								e1.printStackTrace();
							}
						}
					}
				}
			}
			public void mouseEntered(MouseEvent e) {		//鼠标放上去
				if(isShowPre.isSelected()) {
					if(viewop==0||viewop==1) {
						int index=fls.locationToIndex(e.getPoint());		//获取鼠标所在的索引
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
			public void mouseExited(MouseEvent e) {		//鼠标移开事件
				if(isShowPre.isSelected()) {
					PreFrame.jf.dispose();
					isfrShow=false;
				}
			}
		});
		fls.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseMoved(MouseEvent e) {		//鼠标移动事件
				if(isShowPre.isSelected()) {
					if(viewop==0||viewop==1) {
						int index=fls.locationToIndex(e.getPoint());		//获取鼠标所在的索引
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
						} else {		//移到别的文件
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
		jcbidx.addActionListener(new ActionListener() {		//快速索引下拉菜单事件
			public void actionPerformed(ActionEvent arg0) {
				cdpath=jcbidx.getSelectedItem().toString();
				isInaDisk=true;
				new DialogCore().refreshfile();
			}
		});
		jcbidx.setRenderer(new CoboBoxRender());
		jcbtyp.setBounds(141, 448, 447, 35);
		jtn.setBounds(141, 399, 447, 35);
		isShowPre.setFont(new Font("等线", Font.BOLD, 15));
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
