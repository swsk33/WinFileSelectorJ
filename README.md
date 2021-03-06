# WinFileSelectorJ
### 这是一个简单的包，用于java开发者简易的创建一个文件选择对话框。
*曾经开发的软件中用的是java自带的文件选择器包，没多久就有人说这个程序的文件选择功能不好用。于是我便开发了这样一个简易的文件选择器包，简化了代码操作，并加了一些小功能等等。*
### 功能和特点：
1，创建一个文件选择窗口，支持单选、多选，只显示指定的文件类型（过滤）。<br>
2，创建一个文件保存路径选择的对话框，支持单选，过滤。<br>
3，带有图片预览功能。<br>
4，自己重写了扁平化的界面。<br>
5，支持可以选择文件或者文件夹、只能选择文件、只能选择文件夹和只能选择驱动器四种选择模式。<br>
### 下载地址:[点击进入下载jar包](https://gitee.com/swsk33/WinFileSelectorJ/releases)
#### 一、界面说明
通过相应的代码即可唤出文件选择器，语法（用法）将会在下面说明。界面如下：<br>
![主界面](https://file.moetu.org/images/2020/04/22/167dfe41f67dbe32f.jpg)<br>
![快速索引](https://file.moetu.org/images/2020/04/22/2e0c0a878f4b3929c.jpg)<br>
![图片预览](https://file.moetu.org/images/2020/04/22/342e94d0172982875.jpg)<br>
选择文件很简单，选择文件点击确定或者直接双击该文件即可。可以多选时，按住ctrl即可多选。<br>
每次选择完成文件之后会记录上一次的位置，配置文件生成于用户文件夹的AppData\Local\WinFileSelectorJ目录下（```C:\Users\%username%\AppData\Local\WinFileSelectorJ```）。
#### 二、开发用法
### 1，添加依赖，有下列两种情况：
①Eclipse直接添加jar：先下载这个jar包并把这个包导入到IDE里面，例如eclipse。不知道如何导入请查看教程：[eclipse导入外部jar包](https://blog.csdn.net/czbqoo01/article/details/72803450)<br>
②Maven工程：在项目的配置文件pom.xml中的```<dependencies>```标签里加入下列依赖，此操作无需在上面手动下载jar包（推荐）：<br>
```
<dependency>
    <groupId>com.gitee.swsk33</groupId>
    <artifactId>win-file-selector</artifactId>
    <version>2.0.0</version>
</dependency>
```
### 2，导入com.gitee.swsk33.winfileselector下所有类或者需要的类。（import com.gitee.swsk33.winfileselector.*;）
### 3，语法：
**文件选择器的方法返回值基本上就是String或者Object[],单选窗口的返回值就是所选的文件路径，多选窗口的返回值是多个被选中的文件路径被存放在一个Object数组。如果是点击了“取消”按钮或者是关闭按钮，也会有返回值，其中单选时会返回一个空字符串（""），多选时会返回一个长度为1的Object数组，里面只有一个为"null"的值（{"null"}）。**<br>
1，创建一个不带过滤的文件选择器单选窗口(返回值String):<br>
```FileSelectDialog.createSingleSelectionDialog("窗口的标题",文件选择模式);```<br>
**文件选择模式有四个内容如下：**<br>
```FileSelectDialog.ALL_FILES_ALLOW```:既可以选择文件也可以选择文件夹。<br>
```FileSelectDialog.FILE_ONLY```:只能选择文件。<br>
```FileSelectDialog.DIR_ONLY```:只能选择文件夹。<br>
```FileSelectDialog.DRIVE_ONLY```:只能选择驱动器。<br>
**例如：创建一个标题为“选择文件”、只能选择文件的文件选择器对话框并输出其选择的文件路径**<br>
```
String s=new FileSelectDialog().createSingleSelectionDialog("选择文件", FileSelectDialog.FILE_ONLY);
System.out.println(s);
```
2，创建一个带过滤的文件选择器单选窗口(返回值String):<br>
```FileSelectDialog.createSingleSelectionDialog("窗口的标题",文件选择模式,要显示的文件扩展名的字符串数组);```<br>
这个是带过滤显示的文件选择器窗口，他只会显示你制定类型的文件。<br>
先自己定一个String数组，里面放入你要指定显示的文件类型扩展名，再把该String数组对象放入第三个参数。<br>
**例如：创建一个标题为“选择文件”、只能选择文件的文件选择器对话框，且只显示png和jpg文件，并输出其选择的文件路径**<br>
```
String[] type = {"png", "jpg"};		//指定显示文件的字符串数组
String s = new FileSelectDialog().createSingleSelectionDialog("选择文件", FileSelectDialog.FILE_ONLY,type);
System.out.println(s);
```
3，创建一个不带过滤的文件选择器多选窗口(返回值Object[]):<br>
```FileSelectDialog.createMultipleSelectionDialog("窗口标题",文件选择模式);```<br>
**例如：创建一个标题为“选择文件”、只能选择文件的多选文件选择器对话框，并逐个输出其选择的文件路径**<br>
```
Object[] o = new FileSelectDialog().createMultipleSelectionDialog("选择文件", FileSelectDialog.FILE_ONLY);
for (Object name : o) {
	System.out.println(name.toString());
}
```
4，创建一个带过滤的文件选择器多选窗口(返回值Object[]):<br>
```FileSelectDialog.createMultipleSelectionDialog("窗口标题",文件选择模式,要显示的文件扩展名的字符串数组);```<br>
**例如：创建一个标题为“选择文件”、只能选择文件的多选文件选择器对话框，且只显示png和jpg文件，并逐个输出其选择的文件路径**<br>
```
String[] type = {"png", "jpg"};		//指定显示文件的字符串数组
Object[] o = new FileSelectDialog().createMultipleSelectionDialog("选择文件", FileSelectDialog.FILE_ONLY, type);
for (Object name : o) {
	System.out.println(name.toString());
}
```
5，创建一个不带过滤的文件保存路径选择对话框(返回值String):<br>
```FileSaveDialog.createSaveDialog("对话框标题",文件选择模式);```<br>
*和上述的选择对话框不同，文件保存路径选择的对话框只能单选，并且可以在文件类型下拉菜单选择相应的文件类型，选择相应的文件类型时只会显示文件夹和选中的相应类型的文件，当前目录下存在输入的文件名时会提示是否覆盖。方法返回值即为文件保存的完整路径。*<br>
**文件选择模式有两个内容如下：**<br>
```FileSaveDialog.FILE_ONLY```:指定保存的文件夹及其文件名。<br>
```FileSaveDialog.DIR_ONLY```:只指定保存的文件夹。<br>
6，创建一个带过滤的文件保存路径选择对话框(返回值String):<br>
```FileSaveDialog.createSaveDialog("对话框标题",文件选择模式,指定显示的文件类型数组);```<br>
参数以及使用和上面文件选择窗口类似。<br>

**详细的使用可以在调用类的方法时查看，IDE中会显示其中的详细文档**<br>
>最后更新：2021.5.8