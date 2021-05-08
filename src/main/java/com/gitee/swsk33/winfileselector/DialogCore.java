package com.gitee.swsk33.winfileselector;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.filechooser.FileSystemView;

@SuppressWarnings({ "rawtypes", "unchecked", "deprecation" })
class DialogCore { // 对话框处理核心

	// 窗口位置
	static int x;
	static int y;
	// 界面变量
	static String jdt;
	// 常用路径索引
	static String desktop; // 桌面路径
	static String download; // 下载路径
	static String document; // 文档路径
	static String music; // 音乐路径
	static String picture; // 图片路径
	static String video; // 视频路径
	static String appdata; // 应用数据
	// 程序运行时参数
	static String cdpath; // 当前所在路径
	static int viewop; // 视图参数
	static boolean isInaDisk; // 所在路径是否在一个磁盘里面
	static boolean isfrShow = false; // 预览窗是否在显示
	// 下面几个为用户决定性参数
	static boolean isaSaveDg; // 是否为保存对话框
	static boolean isMultiSelect; // 是否多选
	static boolean doFliter; // 是否过滤
	static int selectop; // 选择参数
	static String[] fliter; // 过滤文件（只显示被允许的文件类型的列表）
	// 组件
	static JComboBox jcbidx = new JComboBox(); // 快速索引
	static JComboBox jcbtyp = new JComboBox(); // 文件类型
	static DefaultListModel dfl = new DefaultListModel(); // 文件列表的模型
	static JList fls; // 文件列表
	static JCheckBox isShowPre = new JCheckBox("显示图片预览悬浮窗");
	static JTextField jtn = new JTextField();
	// 用于主视图列表的驱动器信息列表
	static ArrayList<String> driname = new ArrayList<String>();
	static ArrayList<String> dritype = new ArrayList<String>();
	// 用于下拉菜单的驱动器信息列表
	static ArrayList<String> cbbdriname = new ArrayList<String>();
	// 单选、多选获取的文件路径
	static String selectpath;
	static Object[] multiselectpath;
	// 图片常用格式（提供预览图）和其余格式
	final static String[] COMPICFO = { "jpg", "jpeg", "png", "bmp", "gif" };
	final static String[] OTHPICFO = { "psd", "tiff", "iff", "jfif", "svg", "pcx", "dxf", "wmf", "emf", "lic", "eps", "tga", "raw", "ico", "webp", "heic", "spr", "mpo" };
	// 音频文件格式
	final static String[] AUDFO = { "mp3", "mp2", "aiff", "wav", "mid", "wma", "m4a", "ogg", "flac", "amr", "ra", "rm", "rmx", "vqf", "ape", "aac", "cda" };
	// 视频文件格式
	final static String[] VEDFO = { "mp4", "avi", "flv", "wmv", "3gp", "mov", "mkv", "mpg", "asf", "asx", "rm", "rmvb", "m4v", "vob" };
	// 文档格式
	final static String[] DOCFO = { "doc", "docx", "xls", "xlsx", "ppt", "pptx", "pdf", "txt" };
	// 压缩文件格式
	final static String[] ZIPFO = { "zip", "rar", "7z", "jar", "tar", "gz", "xz", "uue", "iso", "apk" };
	// 代码文件和可执行/二进制文件格式
	final static String[] PROGFO = { "c", "o", "cpp", "py", "java", "bat", "go", "js", "html", "css", "dll", "exe", "class" };
	// 程序配置文件路径
	final static String WKDIR = System.getProperty("user.home") + "\\AppData\\Local\\WinFileSelectorJ";
	final static String WKFILE = System.getProperty("user.home") + "\\AppData\\Local\\WinFileSelectorJ\\repath.wfs";

	void idxfileexa() throws Exception { // 路径记录文件自检及初始化
		// 文件自检
		File appdir = new File(WKDIR);
		File reidx = new File(WKFILE);
		if (!reidx.exists()) {
			appdir.mkdir();
			reidx.createNewFile();
			new FileRaWUtils().writeText(reidx.getAbsolutePath(), System.getProperty("user.home"));
			new FileRaWUtils().writeText(reidx.getAbsolutePath(), "0");
		}
		// 全局赋值
		cdpath = new FileRaWUtils().ReadText(reidx.getAbsolutePath(), 1);
		viewop = Integer.parseInt(new FileRaWUtils().ReadText(reidx.getAbsolutePath(), 2));
		// 判断值是否有误
		if (!cdpath.equals("root") && !new File(cdpath).exists()) {
			cdpath = System.getProperty("user.home");
			new FileRaWUtils().replaceLine(reidx.getAbsolutePath(), 1, cdpath);
		}
		if (viewop != 0 && viewop != 1 && viewop != 2) {
			new FileRaWUtils().replaceLine(reidx.getAbsolutePath(), 2, "0");
			viewop = 0;
		}
		// 进入初始化
		String cdt = cdpath; // 下面初始化快速索引菜单的时候会导致路径被选择至索引第一个，故在此创建临时变量后面再设置一次当前路径，保证是上一次记录路径。
		this.setcombobox();
		this.setupjtn();
		this.setupjcbtyp();
		cdpath = cdt;
		if (cdpath.equals("root")) { // 初始化路径状态
			isInaDisk = false;
		} else {
			isInaDisk = true;
		}
		// 开始获取文件以及驱动器列表
		if (selectop == 3) { // 特殊情况：只能选择驱动器时
			jcbidx.setEnabled(false);
			isInaDisk = false;
			cdpath = "root";
			this.getdriveroot();
		} else if (isInaDisk) { // 在磁盘里面时
			jcbidx.setEnabled(true);
			this.refreshfile();
		} else {
			jcbidx.setEnabled(true);
			this.getdriveroot();
		}
	}

	void setupjtn() { // 初始化文件名文本框
		if (isaSaveDg && selectop != 2) {
			jtn.setEditable(true);
		} else {
			jtn.setEditable(false);
		}
	}

	void setupjcbtyp() { // 初始化下拉菜单
		jcbtyp.removeAllItems();
		if (selectop == 3) {
			jcbtyp.addItem("磁盘驱动器(disk)");
		} else if (selectop == 2) { // 只显示文件夹时
			jcbtyp.addItem("文件夹(dir)");
		}
		if (doFliter) { // 如果过滤则要重新设置文件类型下拉菜单
			jcbtyp.removeAllItems();
			if (!isaSaveDg && (selectop == 0 || selectop == 1)) { // 不是保存对话框且只能选择文件时
				String text = "可选文件：";
				for (String st : fliter) {
					text = text + "*." + st + ";";
				}
				jcbtyp.addItem(text);
			} else {
				for (String st : fliter) {
					String typ = "*." + st;
					jcbtyp.addItem(typ);
				}
			}
		} else if (selectop != 2 && selectop != 3) { // 不做任何过滤
			jcbtyp.addItem("所有文件(*.*)");
		}
		jcbtyp.setSelectedIndex(0);
	}

	void refreshfile() { // 获取文件列表
		dfl.removeAllElements();
		File[] filels = new File(cdpath).listFiles();
		try {
			for (File dirs : filels) { // 先获取文件夹列表
				if (dirs.isDirectory()) {
					dfl.addElement(dirs);
				}
			}
			if (selectop == 2) { // 只选择文件夹时
				// 什么也不干
			} else { // 要获取文件时
				if (!doFliter) { // 不做过滤时,获取所有类型文件
					for (File addfiles : filels) {
						if (addfiles.isFile()) {
							dfl.addElement(addfiles);
						}
					}
				} else { // 过滤时，获取过滤类型文件
					if (!isaSaveDg) { // 如果不是保存对话框，同时显示所有指定类型文件
						for (File addfile : filels) {
							boolean shouldadd = false;
							if (addfile.isFile()) {
								String ft = new FileRaWUtils().getFileFormat(addfile.getAbsolutePath());
								for (String cft : fliter) {
									if (cft.equalsIgnoreCase(ft)) {
										shouldadd = true;
										break;
									}
								}
								if (shouldadd) {
									dfl.addElement(addfile);
								}
							}
						}
					} else { // 如果是保存对话框，就要根据下拉菜单内容实时刷新
						for (File addfile : filels) {
							if (addfile.isFile()) {
								String ft = new FileRaWUtils().getFileFormat(addfile.getAbsolutePath()); // 文件类型
								String getIt = jcbtyp.getSelectedItem().toString();
								if (ft.equalsIgnoreCase(getIt.substring(getIt.lastIndexOf(".") + 1))) {
									dfl.addElement(addfile);
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			System.out.println("目录异常！");
		}
	}

	void getdriveroot() { // 获取所有驱动器
		dfl.removeAllElements();
		driname.clear();
		dritype.clear();
		File[] dr = File.listRoots();
		FileSystemView fsv = FileSystemView.getFileSystemView();
		for (File ss : dr) {
			dfl.addElement(ss);
			driname.add(fsv.getSystemDisplayName(ss));
			dritype.add(fsv.getSystemTypeDescription(ss));
		}
	}

	void setcombobox() { // 初始化索引下拉菜单
		jcbidx.removeAllItems();
		cbbdriname.clear();
		desktop = System.getProperty("user.home") + "\\Desktop";
		download = System.getProperty("user.home") + "\\Downloads";
		document = System.getProperty("user.home") + "\\Documents";
		picture = System.getProperty("user.home") + "\\Pictures";
		music = System.getProperty("user.home") + "\\Music";
		video = System.getProperty("user.home") + "\\Videos";
		appdata = System.getProperty("user.home") + "\\AppData";
		File[] dr = File.listRoots();
		FileSystemView fsv = FileSystemView.getFileSystemView();
		jcbidx.addItem(desktop);
		jcbidx.addItem(download);
		jcbidx.addItem(document);
		jcbidx.addItem(picture);
		jcbidx.addItem(music);
		jcbidx.addItem(video);
		jcbidx.addItem(appdata);
		for (int i = 0; i < 7; i++) { // 对齐渲染器的数组
			cbbdriname.add("用户文件夹" + (i + 1));
		}
		for (File ss : dr) {
			jcbidx.addItem(ss.getAbsolutePath());
			cbbdriname.add(fsv.getSystemDisplayName(ss));
		}
		jcbidx.setSelectedIndex(0);
	}

	/**
	 * @throws Exception 文件存在错误或者资源不存在抛出异常
	 */
	public void df() throws Exception {
		this.idxfileexa();
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension sc = kit.getScreenSize();
		JDialog jd = new JDialog();
		jd.setSize(750, 500);
		jd.setLocation(sc.width / 2 - 375, sc.height / 2 - 250);
		jd.setUndecorated(true);
		jd.setModal(true);
		URL bg = DialogCore.class.getResource("/winfileselector/bg.png");
		JLabel bl = new JLabel(new ImageIcon(bg)); // 把上面的图片对象加到一个名为bl的标签里
		bl.setBounds(0, 0, jd.getWidth(), jd.getHeight()); // 设置标签大小
		JPanel imagePanel = (JPanel) jd.getContentPane(); // 把内容窗格转化为JPanel，否则不能用方法setOpaque()来使内容窗格透明 ，使内容窗格透明后才能显示背景图片
		imagePanel.setOpaque(false); // 把背景图片添加到分层窗格的最底层作为背景
		jd.getLayeredPane().add(bl, new Integer(Integer.MIN_VALUE));
		jd.addMouseListener(new MouseAdapter() { // 设置窗口可拖动，添加监听器
			public void mousePressed(MouseEvent e) { // 获取点击鼠标时的坐标
				x = e.getPoint().x;
				y = e.getPoint().y;
			}

			public void mouseReleased(MouseEvent e) {
				jd.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
		jd.addMouseMotionListener(new MouseMotionAdapter() { // 设置拖拽后，窗口的位置
			public void mouseDragged(MouseEvent e) {
				jd.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
				jd.setLocation(e.getXOnScreen() - x, e.getYOnScreen() - y);
			}
		});
		JLabel title = new JLabel(jdt);
		title.setFont(new Font("黑体", Font.BOLD, 18));
		title.setBounds(6, 4, 255, 27);
		title.setToolTipText(jdt);
		JLabel idx = new JLabel("快速索引：");
		idx.setFont(new Font("等线", Font.BOLD, 18));
		idx.setBounds(20, 51, 90, 27);
		JLabel name = new JLabel("文件名：");
		name.setFont(new Font("等线", Font.BOLD, 20));
		name.setBounds(53, 406, 80, 18);
		JLabel type = new JLabel("文件类型：");
		type.setFont(new Font("等线", Font.BOLD, 20));
		type.setBounds(33, 456, 100, 18);
		URL cb = DialogCore.class.getResource("/winfileselector/bt-close.png");
		JButton close = new JButton(new ImageIcon(cb));
		close.addActionListener(new ActionListener() { // 关闭窗口
			public void actionPerformed(ActionEvent arg0) {
				selectpath = "";
				String[] mnul = { "null" };
				multiselectpath = mnul;
				jd.dispose();
			}
		});
		close.setBounds(715, 2, 32, 32);
		close.setBorderPainted(false);
		close.setContentAreaFilled(false);
		close.setToolTipText("关闭窗口");
		URL cmb = DialogCore.class.getResource("/winfileselector/bt-computer.png");
		JButton computer = new JButton(new ImageIcon(cmb));
		computer.addActionListener(new ActionListener() { // 进入“我的电脑”
			public void actionPerformed(ActionEvent e) {
				cdpath = "root";
				isInaDisk = false;
				new DialogCore().getdriveroot();
			}
		});
		computer.setBounds(511, 47, 38, 38);
		computer.setBorderPainted(false);
		computer.setContentAreaFilled(false);
		computer.setToolTipText("进入\"我的电脑\"");
		URL hmb = DialogCore.class.getResource("/winfileselector/bt-home.png");
		JButton home = new JButton(new ImageIcon(hmb));
		home.addActionListener(new ActionListener() { // 进入用户主目录
			public void actionPerformed(ActionEvent e) {
				cdpath = System.getProperty("user.home");
				isInaDisk = true;
				new DialogCore().refreshfile();
			}
		});
		if (selectop == 3) {
			home.setEnabled(false);
		} else {
			home.setEnabled(true);
		}
		home.setBounds(561, 47, 38, 38);
		home.setBorderPainted(false);
		home.setContentAreaFilled(false);
		home.setToolTipText("进入用户目录");
		URL ndb = DialogCore.class.getResource("/winfileselector/bt-newdir.png");
		JButton newDir = new JButton(new ImageIcon(ndb));
		newDir.addActionListener(new ActionListener() { // 新建文件夹按钮
			public void actionPerformed(ActionEvent e) {
				if (isInaDisk) {
					new NewDir().ndfr();
					if (!NewDir.name.equals("")) {
						new File(cdpath + "\\" + NewDir.name).mkdir();
						new DialogCore().refreshfile();
					}
				}
			}
		});
		newDir.setBounds(611, 47, 38, 38);
		newDir.setBorderPainted(false);
		newDir.setContentAreaFilled(false);
		newDir.setToolTipText("新建文件夹");
		URL frb = DialogCore.class.getResource("/winfileselector/bt-front.png");
		JButton front = new JButton(new ImageIcon(frb));
		front.addActionListener(new ActionListener() { // 上一级目录按钮
			public void actionPerformed(ActionEvent arg0) {
				if (isInaDisk) {
					cdpath = new File(cdpath).getParent();
					if (cdpath == null) {
						cdpath = "root";
						isInaDisk = false;
						new DialogCore().getdriveroot();
					} else {
						isInaDisk = true;
						new DialogCore().refreshfile();
					}
				}
			}
		});
		front.setBounds(411, 47, 38, 38);
		front.setContentAreaFilled(false);
		front.setBorderPainted(false);
		front.setToolTipText("进入上一级目录");
		URL rfb = DialogCore.class.getResource("/winfileselector/bt-refresh.png");
		JButton refresh = new JButton(new ImageIcon(rfb));
		refresh.addActionListener(new ActionListener() { // 刷新按钮
			public void actionPerformed(ActionEvent arg0) {
				String cdt = cdpath;
				new DialogCore().setcombobox();
				cdpath = cdt;
				if (isInaDisk) {
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
		URL vib = DialogCore.class.getResource("/winfileselector/bt-view.png");
		JButton view = new JButton(new ImageIcon(vib));
		view.addActionListener(new ActionListener() { // 切换视图
			public void actionPerformed(ActionEvent e) {
				FileRaWUtils fru = new FileRaWUtils();
				if (viewop == 2) {
					viewop = 0;
				} else {
					viewop++;
				}
				if (viewop == 0) {
					fls.setLayoutOrientation(JList.VERTICAL); // 设置竖着排列
				} else if (viewop == 1) {
					fls.setLayoutOrientation(JList.HORIZONTAL_WRAP); // 设置表格分栏排列元素
					fls.setVisibleRowCount(7); // 设置总行数为7行
				} else if (viewop == 2) {
					fls.setLayoutOrientation(JList.VERTICAL_WRAP);
					fls.setVisibleRowCount(2);
				}
				try {
					fru.replaceLine(WKFILE, 2, "" + viewop);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		view.setBounds(661, 47, 38, 38);
		view.setContentAreaFilled(false);
		view.setBorderPainted(false);
		view.setToolTipText("更改视图");
		JButton ok = new JButton("确定");
		ok.addActionListener(new ActionListener() { // 确定
			public void actionPerformed(ActionEvent e) {
				FileRaWUtils fru = new FileRaWUtils();
				if (!isaSaveDg) { // 为选择对话框时
					if (fls.getSelectedIndex() == -1 && (selectop == 1 || selectop == 3)) { // 如果在只能选择文件和驱动器的情况下没有选择文件，就提示请选择文件
						try {
							Runtime.getRuntime().exec("cmd /c echo msgbox \"请选择项目！\",64,\"错误\">alert.vbs && start alert.vbs && ping -n 2 127.1>nul && del alert.vbs");
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					} else if (fls.getSelectedIndex() == -1 && (selectop == 0 || selectop == 2)) { // 如果在可以选择目录的情况下没选择项目，则将当前的路径作为选择返回值
						try {
							fru.replaceLine(WKFILE, 1, cdpath);
							if (isInaDisk) {
								if (!isMultiSelect) { // 单选时
									selectpath = cdpath;
								} else { // 多选时
									Object[] msl = { cdpath };
									multiselectpath = msl;
								}
								jd.dispose();
							} else {
								Runtime.getRuntime().exec("cmd /c echo msgbox \"请进入一个驱动器并选择文件或者文件夹！\",64,\"错误\">alert.vbs && start alert.vbs && ping -n 2 127.1>nul && del alert.vbs");
							}
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					} else { // 选中了项目时
						if (!isMultiSelect) { // 单选时
							if (selectop == 0 || selectop == 2) { // 都可以选择时或者是选择文件夹时
								if (isInaDisk) {
									try {
										fru.replaceLine(WKFILE, 1, cdpath);
										selectpath = fls.getSelectedValue().toString();
										jd.dispose();
									} catch (Exception e1) {
										e1.printStackTrace();
									}
								} else {
									cdpath = fls.getSelectedValue().toString();
									new DialogCore().refreshfile();
									isInaDisk = true;
								}
							} else if (selectop == 1) { // 只能选择文件时
								if (isInaDisk) {
									cdpath = fls.getSelectedValue().toString();
									if (new File(cdpath).isDirectory()) {
										new DialogCore().refreshfile();
									} else {
										try {
											selectpath = cdpath;
											cdpath = new File(cdpath).getParent();
											fru.replaceLine(WKFILE, 1, cdpath);
											jd.dispose();
										} catch (Exception e1) {
											e1.printStackTrace();
										}
									}
								} else {
									cdpath = fls.getSelectedValue().toString();
									new DialogCore().refreshfile();
									isInaDisk = true;
								}
							} else { // 只能选择驱动器时
								selectpath = fls.getSelectedValue().toString();
								jd.dispose();
							}
						} else { // 多选时
							if (selectop == 0 || selectop == 2) { // 都可以选择时或者是选择文件夹时
								if (isInaDisk) {
									try {
										fru.replaceLine(WKFILE, 1, cdpath);
										Object[] msl = fls.getSelectedValues();
										multiselectpath = msl;
										jd.dispose();
									} catch (Exception e1) {
										e1.printStackTrace();
									}
								} else {
									if (fls.getSelectedValues().length == 1) { // 如果选择的对象只有一个
										cdpath = fls.getSelectedValues()[0].toString();
										new DialogCore().refreshfile();
										isInaDisk = true;
									} else { // 有多个被选中时
										fls.setSelectedIndex(0);
									}
								}
							} else if (selectop == 1) { // 只能选择文件时
								if (isInaDisk) {
									Object[] seit = fls.getSelectedValues();
									if (seit.length == 1) { // 如果选择的对象只有一个
										if (new File(seit[0].toString()).isDirectory()) {
											cdpath = seit[0].toString();
											new DialogCore().refreshfile();
										} else {
											try {
												fru.replaceLine(WKFILE, 1, cdpath);
												Object[] msl = fls.getSelectedValues();
												multiselectpath = msl;
												jd.dispose();
											} catch (Exception e1) {
												e1.printStackTrace();
											}
										}
									} else { // 有多个被选中时
										boolean isicldir = false;
										for (Object o : seit) {
											if (new File(o.toString()).isDirectory()) {
												isicldir = true;
												break;
											}
										}
										if (isicldir) { // 多选的内容包含文件夹，但是这不被允许此时
											fls.setSelectedIndex(0);
										} else {
											try {
												fru.replaceLine(WKFILE, 1, cdpath);
												Object[] msl = fls.getSelectedValues();
												multiselectpath = msl;
												jd.dispose();
											} catch (Exception e1) {
												e1.printStackTrace();
											}
										}
									}
								} else {
									if (fls.getSelectedValues().length == 1) { // 如果选择的对象只有一个
										cdpath = fls.getSelectedValues()[0].toString();
										new DialogCore().refreshfile();
										isInaDisk = true;
									} else { // 有多个被选中时
										fls.setSelectedIndex(0);
									}
								}
							} else { // 只能选择驱动器时
								Object[] msl = fls.getSelectedValues();
								multiselectpath = msl;
								jd.dispose();
							}
						}
					}
				} else { // 是保存对话框时
					if (fls.getSelectedIndex() == -1 && !isInaDisk) { // 如果在驱动器根目录的情况下没有选择文件，就提示请选择目录
						try {
							Runtime.getRuntime().exec("cmd /c echo msgbox \"请选择并进入一个磁盘！\",64,\"错误\">alert.vbs && start alert.vbs && ping -n 2 127.1>nul && del alert.vbs");
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					} else { // 其余情况（选择了目录或者已经是选择了文件）
						try {
							if (selectop == 2) { // 只指定保存目录时
								if (fls.getSelectedIndex() == -1) { // 没选择文件夹，则以当前路径为标准
									selectpath = cdpath;
									fru.replaceLine(WKFILE, 1, cdpath);
									jd.dispose();
								} else { // 已经选择了文件夹，则以选择的为结果
									selectpath = fls.getSelectedValue().toString();
									fru.replaceLine(WKFILE, 1, cdpath);
									jd.dispose();
								}
							} else { // 否则
								if (jtn.getText().equals("")) {
									Runtime.getRuntime().exec("cmd /c echo msgbox \"请输入文件名！\",64,\"错误\">alert.vbs && start alert.vbs && ping -n 2 127.1>nul && del alert.vbs");
								} else {
									String sfl = ""; // 最后获取的完整文件路径
									if (cdpath.endsWith("\\")) {
										sfl = cdpath + jtn.getText();
									} else {
										sfl = cdpath + "\\" + jtn.getText();
									}
									if (doFliter) { // 保存对话框且做过滤时，检查并自动补全文件名正确性
										String jcbsel = jcbtyp.getSelectedItem().toString(); // 获取的列表选择项
										String jcbselx = jcbsel.substring(jcbsel.lastIndexOf(".") + 1); // 获取的列表相对应扩展名
										if (sfl.contains(".")) { // 文件有尾缀时，检查其正确性
											String fty = sfl.substring(sfl.lastIndexOf(".") + 1); // 获取文件扩展名
											if (!fty.equalsIgnoreCase(jcbselx)) { // 扩展名不对时
												sfl = sfl + "." + jcbselx;
											}
										} else { // 没有尾缀，就加上去
											sfl = sfl + "." + jcbselx;
										}
									}
									if (new File(sfl).exists()) { // 如果文件存在
										new OverwriteTip().fr();
										if (OverwriteTip.isOvt) {
											selectpath = sfl;
											fru.replaceLine(WKFILE, 1, cdpath);
											jd.dispose();
										} else {
											// 什么都不做
										}
									} else {
										selectpath = sfl;
										fru.replaceLine(WKFILE, 1, cdpath);
										jd.dispose();
									}
								}
							}
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				}
			}
		});
		ok.setForeground(new Color(255, 0, 153));
		ok.setFont(new Font("黑体", Font.BOLD, 20));
		ok.setBounds(605, 398, 88, 35);
		ok.setContentAreaFilled(false);
		JButton cancel = new JButton("取消");
		cancel.addActionListener(new ActionListener() { // 取消按钮
			public void actionPerformed(ActionEvent arg0) {
				selectpath = "";
				String[] mnul = { "null" };
				multiselectpath = mnul;
				jd.dispose();
			}
		});
		cancel.setForeground(new Color(255, 102, 0));
		cancel.setFont(new Font("黑体", Font.BOLD, 20));
		cancel.setBounds(605, 448, 88, 35);
		cancel.setContentAreaFilled(false);
		fls = new JList(dfl);
		fls.setCellRenderer(new WFsCellRender());
		if (viewop == 0) {
			fls.setLayoutOrientation(JList.VERTICAL); // 设置竖着排列
		} else if (viewop == 1) {
			fls.setLayoutOrientation(JList.HORIZONTAL_WRAP); // 设置表格分栏排列元素
			fls.setVisibleRowCount(7); // 设置总行数为7行
		} else if (viewop == 2) {
			fls.setLayoutOrientation(JList.VERTICAL_WRAP);
			fls.setVisibleRowCount(2);
		}
		if (isMultiSelect && !isaSaveDg) { // 单选/多选判断
			fls.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		} else {
			fls.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		}
		fls.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 1) { // 鼠标单击
					if (isInaDisk) {
						if (!isaSaveDg) { // 为选择对话框时
							if (!isMultiSelect) { // 单选时
								String gfn = new File(fls.getSelectedValue().toString()).getName();
								jtn.setText(gfn);
							} else {
								String gfnn = "";
								for (Object n : fls.getSelectedValues()) {
									String fn = new File(n.toString()).getName();
									gfnn = gfnn + fn + " ";
								}
								jtn.setText(gfnn);
							}
						} else { // 为保存对话框时
							if (new File(fls.getSelectedValue().toString()).isFile()) {
								String gfn = new File(fls.getSelectedValue().toString()).getName();
								jtn.setText(gfn);
							} else {
								String gfnn = "";
								jtn.setText(gfnn);
							}
						}
					}
				}
				if (e.getClickCount() == 2) { // 鼠标双击
					int index = fls.locationToIndex(e.getPoint()); // 获取鼠标所在的索引
					if (!isInaDisk) { // 若当前在我的电脑下
						if (selectop == 3) { // 当只允许选择磁盘时
							if (!isMultiSelect) { // 单选时
								selectpath = fls.getSelectedValue().toString();
							} else {
								Object[] asp = { fls.getSelectedValue() };
								multiselectpath = asp;
							}
							jd.dispose();
						} else {
							cdpath = fls.getModel().getElementAt(index).toString();
							new DialogCore().refreshfile();
							isInaDisk = true;
						}
					} else { // 若当前在磁盘里面
						cdpath = fls.getModel().getElementAt(index).toString();
						if (new File(cdpath).isDirectory()) { // 若双击文件夹，则进入
							new DialogCore().refreshfile();
						} else if (new File(cdpath).isFile()) { // 如果是文件
							try {
								FileRaWUtils fru = new FileRaWUtils();
								cdpath = new File(cdpath).getParent();
								if (!isMultiSelect) { // 单选时
									if (isaSaveDg) { // 保存对话框时
										new OverwriteTip().fr();
										if (OverwriteTip.isOvt) { // 提示覆盖的选项
											selectpath = fls.getSelectedValue().toString();
											fru.replaceLine(WKFILE, 1, cdpath);
											jd.dispose();
										} else { // 不然
											// 什么都不做
										}
									} else {
										selectpath = fls.getSelectedValue().toString();
										fru.replaceLine(WKFILE, 1, cdpath);
										jd.dispose();
									}
								} else {
									Object[] asp = { fls.getSelectedValue() };
									fru.replaceLine(WKFILE, 1, cdpath);
									multiselectpath = asp;
									jd.dispose();
								}
								if (isfrShow) {
									PreFrame.jf.dispose();
								}
							} catch (Exception e1) {
								e1.printStackTrace();
							}
						}
					}
				}
			}

			public void mouseEntered(MouseEvent e) { // 鼠标放上去
				if (isShowPre.isSelected()) {
					if (viewop == 0 || viewop == 1) {
						int index = fls.locationToIndex(e.getPoint()); // 获取鼠标所在的索引
						String imgpath = "";
						try {
							imgpath = fls.getModel().getElementAt(index).toString();
						} catch (Exception es) {
							System.out.println("目录为空！");
						}
						String ft = new FileRaWUtils().getFileFormat(imgpath);
						if (ft.equalsIgnoreCase("jpg") || ft.equalsIgnoreCase("jpeg") || ft.equalsIgnoreCase("png") || ft.equalsIgnoreCase("bmp") || ft.equalsIgnoreCase("gif")) {
							int iw;
							int ih;
							ImageIcon preimg = new ImageIcon(imgpath);
							if (preimg.getIconWidth() > preimg.getIconHeight()) {
								iw = 250;
								ih = (int) (((float) 250 / preimg.getIconWidth()) * preimg.getIconHeight());
							} else {
								ih = 156;
								iw = (int) (((float) 156 / preimg.getIconHeight()) * preimg.getIconWidth());
							}
							Image img = preimg.getImage();
							img = img.getScaledInstance(iw, ih, Image.SCALE_AREA_AVERAGING);
							preimg.setImage(img);
							PreFrame.imgp.setIcon(preimg);
							PreFrame.jf.setLocation(e.getXOnScreen() - 183, e.getYOnScreen() - 197);
							if (!isfrShow) {
								new PreFrame().prefr();
								isfrShow = true;
							}
						}
					}
				}
			}

			public void mouseExited(MouseEvent e) { // 鼠标移开事件
				if (isShowPre.isSelected()) {
					PreFrame.jf.dispose();
					isfrShow = false;
				}
			}
		});
		fls.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseMoved(MouseEvent e) { // 鼠标移动事件
				if (isShowPre.isSelected()) {
					if (viewop == 0 || viewop == 1) {
						int index = fls.locationToIndex(e.getPoint()); // 获取鼠标所在的索引
						String imgpath = "";
						try {
							imgpath = fls.getModel().getElementAt(index).toString();
						} catch (Exception es) {
							System.out.println("目录为空！");
						}
						String ft = new FileRaWUtils().getFileFormat(imgpath);
						if (ft.equalsIgnoreCase("jpg") || ft.equalsIgnoreCase("jpeg") || ft.equalsIgnoreCase("png") || ft.equalsIgnoreCase("bmp") || ft.equalsIgnoreCase("gif")) {
							ImageIcon preimg = new ImageIcon(imgpath);
							int iw;
							int ih;
							if (preimg.getIconWidth() > preimg.getIconHeight()) {
								iw = 250;
								ih = (int) (((float) 250 / preimg.getIconWidth()) * preimg.getIconHeight());
							} else {
								ih = 156;
								iw = (int) (((float) 156 / preimg.getIconHeight()) * preimg.getIconWidth());
							}
							Image img = preimg.getImage();
							img = img.getScaledInstance(iw, ih, Image.SCALE_AREA_AVERAGING);
							preimg.setImage(img);
							PreFrame.imgp.setIcon(preimg);
							if (!isfrShow) {
								PreFrame.jf.setLocation(e.getXOnScreen() - 183, e.getYOnScreen() - 197);
								new PreFrame().prefr();
								isfrShow = true;
							} else {
								PreFrame.jf.setLocation(e.getXOnScreen() - 183, e.getYOnScreen() - 197);
							}
						} else { // 移到别的文件
							PreFrame.jf.dispose();
							isfrShow = false;
						}
					}
				}
			}
		});
		JScrollPane jsp = new JScrollPane();
		jsp.setBounds(43, 108, 656, 278);
		jsp.setViewportView(fls);
		jcbidx.setBounds(113, 41, 289, 45);
		jcbidx.addActionListener(new ActionListener() { // 快速索引下拉菜单事件
			public void actionPerformed(ActionEvent arg0) {
				try {
					cdpath = jcbidx.getSelectedItem().toString();
					isInaDisk = true;
					new DialogCore().refreshfile();
				} catch (Exception e1) {
					// 什么都不做
				}
			}
		});
		jcbidx.setRenderer(new CoboBoxRender());
		jcbtyp.setBounds(141, 448, 447, 35);
		if (isaSaveDg && doFliter && selectop != 2 && selectop != 3) { // 为保存对话框且有过滤时，实现选择一个类型只显示该类型文件
			jcbtyp.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					new DialogCore().refreshfile();
				}
			});
		}
		jtn.setBounds(141, 399, 447, 35);
		isShowPre.setFont(new Font("等线", Font.BOLD, 15));
		isShowPre.setSelected(true);
		isShowPre.setBounds(20, 88, 160, 18);
		isShowPre.setOpaque(false);
		JPanel jp = new JPanel();
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