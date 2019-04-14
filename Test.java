package com.myf.lzwcompress1227;

import java.io.File;

public class Test {

	
	public static void main(String[] args)
	{
		Compress com = new Compress();
		Uncompress ucom = new Uncompress();
		
		File file1 = new File("E:\\workspace\\mayifan\\src\\com\\myf\\lzwcompress1227\\data1.txt"); //带压缩的文件
		File file2 = new File("E:\\workspace\\mayifan\\src\\com\\myf\\lzwcompress1227\\data2.txt"); //压缩生成文件
		File file3 = new File("E:\\workspace\\mayifan\\src\\com\\myf\\lzwcompress1227\\data3.txt"); //解压目录文件
		
		com.compress(file1,file2); //文件压缩的方法
		ucom.uncompress(file2, file3);//文件解压方法
		
	}
	
	
	
	
	
}
