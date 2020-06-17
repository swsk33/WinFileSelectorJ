package swsk33.WFs;

import java.io.*;
class FileRaWUtils {
	public String getFileFormat(String filepath) {		//获取文件格式
		String ffr="";
		File f=new File(filepath);
		String fn=f.getName();
		if(fn.contains(".")) {
			ffr=fn.substring(fn.lastIndexOf(".")+1);
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
	
	public void replaceLine(String filepath,int whichline,String Text) throws Exception {		//用指定内容替换文件指定行
		int fl=new LineScanner().GetLinage(filepath);
        if(0<whichline&&whichline<=fl) {
        	File f=new File(filepath);
        	String sumstr;
        	String front="";
        	FileInputStream fisf=new FileInputStream(f);
        	InputStreamReader isrf=new InputStreamReader(fisf);
        	BufferedReader brf=new BufferedReader(isrf);
        	for(int ifr=0;ifr<whichline-1;ifr++) {
        		front=front+brf.readLine()+"\r\n";
        	}
        	sumstr=front+Text+"\r\n";
        	brf.close();
        	String after="";
        	FileInputStream fisa=new FileInputStream(f);
        	InputStreamReader isra=new InputStreamReader(fisa);
        	BufferedReader bra=new BufferedReader(isra);
        	for(int ia1=0;ia1<whichline;ia1++) {
        		bra.readLine();
        	}
        	for(int ia2=0;ia2<fl-whichline;ia2++) {
        		after=after+bra.readLine()+"\r\n";
        	}
        	sumstr=sumstr+after;
        	bra.close();
        	FileOutputStream fos=new FileOutputStream(f);
        	OutputStreamWriter osw=new OutputStreamWriter(fos);
        	BufferedWriter bw=new BufferedWriter(osw);
        	bw.write(sumstr);
        	bw.close();
        }else if(whichline>fl){
        	System.out.println("错误！超过文件最大行数！");
        }else if(whichline<=0) {
        	System.out.println("错误！指定行数不可小于等于0！");
        }
	}	
}
