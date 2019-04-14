package com.myf.lzwcompress1227;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class Compress {

	HashMap<String, Integer> map = new HashMap<>();//ѹ��ʱ�ı����
	String pre = ""; //ǰ׺
	String suf = ""; //��׺
	String add = "";  //�����ַ�������Ϊ�м����
	int code = 256; //��256��ʼ���룬255~65535
	/**
	 * ��ȡ�ļ����ݣ���ȡ�������ַ���
	 */
	public String read(File file1)
	{
		String str="";
		try
		{
			InputStream is = new FileInputStream(file1);
			byte[] buffer = new byte[is.available()];//�����ֽ�������������
			is.read(buffer); //һ���԰��ļ�����buffer������
			str = new String(buffer); //�ֽ�����ת�ַ���		
			is.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		return str;
	}

	/**
	 * ���ַ�����������LZW�����ͬʱд��ѹ���ļ�
	 */
	public void write(String str,File file2)    
	{
		for(int i=0;i<256;i++)  //��256���ַ��������
		{
			String a = (char)i+"";
			map.put(a, i);
		}
		try
		{
			OutputStream out = new FileOutputStream(file2); //����ת��	
			DataOutputStream dos = new DataOutputStream(out); //���������
			pre = str.charAt(0)+"";  //�ѵ�һ���ַ���Ϊǰ׺
			for(int i=1;i<str.length();i++)
			{
				if(code==65535)  //�����������࣬��Ҫ���map
				{
					System.out.println("����");
					dos.writeInt(65535);   //дһ���źű�ʾ����
					map.clear();  //���
					code=256; //������256
					pre="";   //ǰ׺�ÿ�
					for(int j=0;j<256;j++)  //��256���ַ��������
					{
						String a = (char)j+"";
						map.put(a, j);
					}
				}
				suf=str.charAt(i)+""; //��ȡ��׺�ַ�
				add=pre+suf; //����
				if(map.get(add)==null)   ///map�ڲ����ڸ��ַ�������Ѹ��ַ���д��
				{
					System.out.println("�ϳ��ַ�����"+add);
					System.out.println("��Ӧ���룺"+code);
					map.put(add,code);  //�ѵ�ǰ�ַ�����Ϊ�ֵ�����ϣ����256��ʼ
					add=""; //����м��ַ���
					System.out.println("д���ǰ׺��"+pre);
					System.out.println("���ı��룺"+map.get(pre));	
//					Set <String> set = map.keySet();
//					Iterator<String> it = set.iterator();
//					int add=0;
//					while(it.hasNext()&&add<=255)
//					{
//						System.out.println(map.get(it.next()));
//						add++;
//					}
					if(pre.length()==1)
						dos.writeChar(pre.charAt(0));
					else
				        dos.writeChar(map.get(pre));
				    pre=suf;
				    code++;
				}
				else
				{
			        pre=add;  //�Ѵ�����map��������жϣ�������Ϊǰ׺
				}
				if(i==str.length()-1)
				{
					System.out.println("�����ַ���"+pre);
					System.out.println("д��ı��룺"+map.get(pre));
					if(pre.length()==1)					
						dos.writeChar(pre.charAt(0));
					else
				        dos.writeChar(map.get(pre));
				}
			}
			dos.close();
			//System.out.print("������"+map.toString());
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	/**
	 * ѹ���ļ��Ķ���ӿ�
	 */
	public void compress(File file1,File file2)
	{
		String str =read(file1);
		System.out.println("���ļ��ж�ȡ���ַ�����"+str);
		write(str,file2);
		
	}
	
	
	
	
	
}
