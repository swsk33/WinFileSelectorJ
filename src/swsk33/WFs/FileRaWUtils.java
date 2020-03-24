package swsk33.WFs;

import java.io.*;
import java.math.*;
import java.security.*;

class FileRaWUtils {
	public String getFileFormat(String filepath) {		//获取文件格式
		String ffr="";
		File f=new File(filepath);
		String fn=f.getName();
		if(fn.contains(".")) {
			ffr=fn.substring(fn.lastIndexOf(".")+1);
		} else {
			System.out.println("文件没有扩展名！");
		}
		return ffr;
	}
	
	public String ReadText(String filepath,int line) throws Exception {		//读取文本文档指定行
		String result=null;
		File f=new File(filepath);
		FileInputStream fis=new FileInputStream(f);
		InputStreamReader isr=new InputStreamReader(fis);
		BufferedReader br=new BufferedReader(isr);
		for(int i=0;i<line;i++) {
			result=br.readLine();
		}
		br.close();
		return result;
	}
	
	public void writeText(String filepath,String Text) throws Exception {		//写入指定内容至文件
		File f=new File(filepath);
		int line=new LineScanner().GetLinage(filepath);
		String old="";
		if(!(line==0)) {
			for(int i=0;i<line;i++) {
				old=old+new FileRaWUtils().ReadText(filepath,i+1)+"\r\n";
			}
			FileOutputStream fos=new FileOutputStream(f);
			OutputStreamWriter osw=new OutputStreamWriter(fos);
			BufferedWriter bw=new BufferedWriter(osw);
			bw.write(old);
			bw.write(Text);
			bw.close();
		} else {
			FileOutputStream fos=new FileOutputStream(f);
			OutputStreamWriter osw=new OutputStreamWriter(fos);
			BufferedWriter bw=new BufferedWriter(osw);
			bw.write(Text);
			bw.close();
		}
		
	}
	
}
