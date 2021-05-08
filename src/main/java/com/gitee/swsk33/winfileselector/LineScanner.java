package com.gitee.swsk33.winfileselector;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

class LineScanner {

	public int GetLinage(String filepath) throws Exception { // 获取文本文档行数
		int linage = 0;
		File f = new File(filepath);
		FileInputStream fis = new FileInputStream(f);
		InputStreamReader isr = new InputStreamReader(fis);
		BufferedReader br = new BufferedReader(isr);
		for (int i = 1; br.readLine() != null; i++) {
			linage = i;
		}
		br.close();
		return linage;
	}

}