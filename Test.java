package com.myf.lzwcompress1227;

import java.io.File;

public class Test {

	
	public static void main(String[] args)
	{
		Compress com = new Compress();
		Uncompress ucom = new Uncompress();
		
		File file1 = new File("E:\\workspace\\mayifan\\src\\com\\myf\\lzwcompress1227\\data1.txt"); //��ѹ�����ļ�
		File file2 = new File("E:\\workspace\\mayifan\\src\\com\\myf\\lzwcompress1227\\data2.txt"); //ѹ�������ļ�
		File file3 = new File("E:\\workspace\\mayifan\\src\\com\\myf\\lzwcompress1227\\data3.txt"); //��ѹĿ¼�ļ�
		
		com.compress(file1,file2); //�ļ�ѹ���ķ���
		ucom.uncompress(file2, file3);//�ļ���ѹ����
		
	}
	
	
	
	
	
}
