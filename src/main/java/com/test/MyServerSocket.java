package com.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 *服务器
 */
public class MyServerSocket {
	//创建一个map集合储存用户名和对应客户端
	public static Map<String, Socket> map=new HashMap<String, Socket>();
		public static void main(String[] args) {
			ServerSocket ss=null;
			Socket s=null;
			BufferedReader br=null;
			BufferedWriter bw=null;
			//创建一个服务器程序
			try {
				ss=new ServerSocket(13145);
				System.out.println("服务器已经启动！");
				String name="";
				//启动多个客户端
				while(true){//重复监听
					s=ss.accept();
					br=new BufferedReader(new InputStreamReader(s.getInputStream()));
					name=br.readLine();
					System.out.println("用户名："+name+":  "+s.getInetAddress().getHostAddress()+"上线了");
					//服务器不能直接收消息，只做一个监听客户端的作用
					//name：某一个客户端的用户名，s：代表这个客户端程序（Socket）
					Thread msst=new Thread(new MyServerSocketThread(name,s)); 
					msst.start();
					map.put(name, s);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				Util.close(br,bw,s,ss);
			}
		}
}
