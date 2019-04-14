package com.myf.lzwcompress1227;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class Uncompress {
	HashMap< String,Integer> map = new HashMap<>();  //�洢�ڽ�ѹ���������ɵ��ֵ� ,�ַ��������
    HashMap<Integer, String> map1 = new HashMap<>();  //�洢�ڽ�ѹ���������ɵ��ֵ䣬������ַ���
    ArrayList<Integer> highlist = new ArrayList<>(); //��Ÿ߰�λ��Ӧ����ֵ
    ArrayList<Integer> numlist = new ArrayList<>(); //��Ż�õ�ÿһ���ַ������ݣ���ŵ���intֵ
	String pw = "";  //ǰ׺
	String cw ="";   //��׺
	String add="";  //�ϳ��ַ���
	int code=256;
	
	/**
	 * ��ѹ���ļ��л�ԭ�õ��ַ����͹�ϣ��
	 * @param file2
	 */
	public byte[] getStr(File file2)
	{
		String str="";
		try
		{
		   InputStream is = new FileInputStream(file2);
		   byte[] buffer = new byte[is.available()];
		   is.read(buffer);
		   str = new String(buffer); 
		   System.out.println("��õ��ַ�����"+str);
		   is.close();
		   return buffer;
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		return null;	
	}
	
	/**
	 * ͨ���ַ�����ȡ�ֵ䲢��ѹ��д��Ŀ���ļ�
	 */
	public void writeStr(File file3,byte[] buffer)
	{
		int count=0;
		for(int i=0;i<256;i++)  //��ӻ���ascii�ַ��������
		{
			String a = (char)i+"";
			map1.put(i, a);
			map.put(a, i); 
		}	
        for(int i=0;i<buffer.length;i+=2) //�洢�߰�λ
        {
        	int a = buffer[i];
        	highlist.add(a);
        }
		for(int i=1;i<buffer.length;i+=2) //����ȡ���Ͱ�λ,��ϸ߰�λ��Ϣת��Ϊ��Ӧʵ���ַ���Ӧ����ֵ
		{
			if(buffer[i]==-1&&buffer[i-1]==-1)//���������Ϣ
			{
				System.out.println("���������Ϣ");
				numlist.add(65525);
			}
			else
			{
				if(highlist.get(count)>0)  //�߰�λ������,����Ϊ��������
				{
					System.out.println("�߰�λ������");
					System.out.println("�߰�λ���ݣ�"+highlist.get(count));
					System.out.println("�Ͱ�λ���ݣ�"+buffer[i]);
					if(buffer[i]>=0)  //�ֽ�����Ϊ��������ֱ���ã��Ͱ�λ���ϸ߰�λ*256
					{
						System.out.println("�ֽ�����Ϊ��");
						int a=buffer[i]+256*highlist.get(count);
						numlist.add(a);
						System.out.println("������ݣ�"+a);
						System.out.println("");
					}
					else      //�ֽ�����Ϊ��������256תΪ�����ټӸ�λ����
					{
						System.out.println("�ֽ�����Ϊ��");
						int a=buffer[i]+256+256*highlist.get(count);
						numlist.add(a);
						System.out.println("������ݣ�"+a);
					}
					
					
				}
				else  //�߰�λȫΪ0������Ϊ��ͨASCII����
				{
					System.out.println("�߰�λȫΪ0");
					if(buffer[i]>0)  //�ֽ�����Ϊ��������ֱ����
					{
						System.out.println("�ֽ�����Ϊ��");
						int a=buffer[i];
						numlist.add(a);
						System.out.println("������ݣ�"+a);
					}
					else      //�ֽ�����Ϊ��������256תΪ��
					{
						System.out.println("�ֽ�����Ϊ��");
						int a=buffer[i]+256;
						numlist.add(a);
						System.out.println("������ݣ�"+a);
					}
				}			
			}
			count++;			
		}		
		try
		{
			OutputStream os = new FileOutputStream(file3);  //��ȡ�ļ������     
			for(int i=0;i<numlist.size();i++)  //���ȡ���ַ����ݣ��������
			{
				int n = numlist.get(i);  //��ȡ�ַ���Ӧ��ֵ
				System.out.println("�������ַ�ֵ��"+n);
				if(map.containsValue(n))  //�ֵ��д���
				{
					System.out.println("�ֵ��д���");
					cw=map1.get(n);   //��ȡ��Ӧ�ַ�����Ϊ��ǰ�ַ���
					if(pw!="")  //�ж�ǰ׺��Ϊ��
					{
						System.out.println("ǰ׺��Ϊ��");
						System.out.println("��ѯ��ǰ�ֵ�");
	                    
						add=pw+cw.charAt(0); //�ϳ���ϴ�
						System.out.println("��һ����"+pw);
						System.out.println("��ǰ��"+cw);
						System.out.println("�ϳɣ�"+add);
						System.out.println("code:"+code);
						map1.put(code,add ); //���´ʵ�
						map.put(add, code);
						code++;
						os.write(cw.getBytes("GBK")); //д��ǰ�������Ǹ��ַ���
						System.out.println("д�룺"+cw);
	
					}
					else    //ǰ׺Ϊ�գ��ǵ�һ���ַ���ֱ��д
					{
						System.out.println("ǰ׺Ϊ�գ���һ���ַ�");
						os.write(cw.getBytes("GBK")); //���ַ����������ֽڵĵ�λд��ȥ
						System.out.println("д�룺"+cw);
					} 					
				}
				else     //�ֵ��в����ڣ����ǰ׺�л�ȡ��Ϣ�õ���ǰ���ֶ�Ӧ���ַ�����д���ļ���д���ֵ�
				{
					System.out.println("�ֵ��в�����");
					System.out.println("pw:"+pw);
					cw=pw+pw.charAt(0); 
					map.put(cw, code);  //�����Ӧ�����ֵ�����
					map1.put(code, cw);
					code++;
					os.write(cw.getBytes("GBK")); //д����ǰ�ַ�������	
					System.out.println("д�룺"+cw);
				}	
				pw=cw;  //��ǰ���ַ�����Ϊ��һ��ѭ����ǰ׺
				
				if(map1.size()==65535||map.size()==65535) //������������ù�ϣ��
				{
					map1.clear();
					map.clear();
					for(int j=0;j<256;j++)
					{
						map.put((char)j+"", j);
						map1.put(j, (char)j+"");
					}
					code=256;
					pw="";
				}
			}
			os.close(); //�ر���Դ
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
//		pre=str.charAt(0)+"";
//		suf=str.charAt(1)+"";  //��ȡǰ�����ַ�
//		add=pre+suf;
//		try
//		{				
//				map1.put(code, add); //ǰ�����ַ�Ϊ��һ���ַ���
//				map.put(add, code); 
//				System.out.println("��ӵ�һ���ֵ�ԣ�"+"code:"+code+"  add:"+add);
//				code++;
//				dos.writeChar(pre.charAt(0));
//				System.out.println("д�ַ����ļ���"+pre.charAt(0));
//				pre=str.charAt(0)+"";
//				
//				for(int i=0;i<numlist.size();i++)   //���ȡ���ַ����ݣ��������
//				{
//					int n =numlist.get(i);
//					if(str.charAt(i)>255)   //����255������Ҫת��Ϊ�ַ���
//					{
//						System.out.println("�ַ�����255");
//						get=map1.get(str.charAt(i)); //�ַ���Ӧ������תΪ�ַ���
//						System.out.println("��ȡ��Ӧ���ַ�����"+get);
//						char x = get.charAt(0);  //��ȡ��һ���ַ�
//						suf=x+"";				
//						add=pre+suf;
//							if(map1.get(add)==null) //���map�в�����ǰ׺�Ӻ�׺��һ���ַ�������ӽ��ֵ�
//							{
//								System.out.println("�ֵ��в����ڸ��ַ�����Ӧ�ļ�ֵ��");
//								map1.put(code, add); //��ӽ��ֵ�
//								map.put(add, code);
//								System.out.println("��Ӽ�ֵ�ԣ�"+"code:"+code+"  add:"+add);
//								dos.writeChars(pre);   //дǰ׺
//								System.out.println("дǰ׺���ļ���"+pre);
//								code++;
//							}
//							else     //�����������һ���ַ����ж�
//							{
//								System.out.println("�ֵ����и��ַ����ļ�ֵ��");
//								pre=add;
//							}
//					}
//					else   //����һ����ͨ���ַ�
//					{
//						System.out.println("����һ��С��255����ͨ�ַ�");
//						suf=str.charAt(0)+"";
//						add=pre+suf;
//						if(map.get(add)==null)  //���ַ���key���Ҳ�����Ӧ��ֵ��
//						{				
//							System.out.println("�ֵ��в����ڸ��ַ�����Ӧ�ļ�ֵ��");
//							map1.put(code, add);
//							map.put(add, code);
//							System.out.println("��Ӽ�ֵ�ԣ�"+"code:"+code+"  add:"+add);
//							dos.writeChars(pre);
//							System.out.println("дǰ׺���ļ���"+pre);
//							code++;
//						}
//						else
//						{
//							System.out.println("�ֵ����и��ַ����ļ�ֵ��");
//							pre=add;
//						}
//					}			
//				}
//			   dos.close();
//		}
//		catch(IOException e)
//		{
//			e.printStackTrace();
//		}
	}
	

	
	
	public void uncompress(File file2,File file3)
	{
		byte[] buffer = getStr(file2); //��ѹ�ļ�,��ȡ�ַ���
		writeStr(file3,buffer);  //д�ļ�	
	}
}
