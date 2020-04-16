package com.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 *客户端
 */
public class MySocket {
		public static void main(String[] args) {
			BufferedReader br=null;
			BufferedWriter bw=null;
			Socket s=null;
			
			try {
				 //创建一个客户端程序
				s=new Socket("127.0.0.1",13145);
				//创建一个线程
				Thread mst=new Thread(new MySocketThread (s));
				mst.start();
				System.out.println("客户端启动成功！请输入用户名：");
				//获取从客控制台输入的内容
				br=new BufferedReader(new InputStreamReader(System.in));
				String name=br.readLine();//读取控制台输入的用户名
				//将用户名写入Socket套接字输出流
				bw=new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
				bw.write(name);
				bw.newLine();
				bw.flush();
				//无限向服务器发送消息
				String sendContent="";//内容
				while(true){
					System.out.println("请输入要发送的消息：模板为：/用户名 发送内容， show代表显示当前在线用户");
					sendContent=br.readLine();//接收控制台输入的内容 没输入的时候停在这里
					bw.write(sendContent);//将内容写入Socket套接字中
					bw.newLine();
					bw.flush();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				Util.close(br, bw, s, null);
			}
		}
}
