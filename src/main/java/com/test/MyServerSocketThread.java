package com.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Set;

/**
 *服务器的线程，负责接收客户端消息发给另一个客户端
 */
public class MyServerSocketThread extends Thread{
	private String name;

	private Socket s;

	public MyServerSocketThread(String name, Socket s){
		this.name=name;
		this.s=s;
	}
	@Override
	public void run() {
		BufferedReader br = null;
		BufferedWriter bw = null;
		try {
			//创建输入流读取客户端信息
			br=new BufferedReader(new InputStreamReader(s.getInputStream()));
			//读取内容
			String sendContent="";
			while(true){
				//读取信息
				sendContent=br.readLine();
				//获取将要发送对象的名字
				String sendName="";
				//获取发送内容
				String sendmsg="";
				//获取当前所有用户名
				Set<String> onlineNames=	MyServerSocket.map.keySet();
				if (sendContent.startsWith("/")) {//一// 开头 证明要和用户对话
					//获取将要发送的人的名字
					sendName=sendContent.substring(1,sendContent.indexOf(" "));
					//获取发送内容
					sendmsg=sendContent.substring(sendContent.indexOf(" ")+1);
					//匹配在线人数中发送信息的人是否存在
					for (String str : onlineNames) {
						if (sendName.equals(str)) {
							//获取要发送的人的Socket
							Socket	onlinescoket=	MyServerSocket.map.get(str);
							bw=new BufferedWriter(new OutputStreamWriter(onlinescoket.getOutputStream()));
							//将内容发送给接收人
							bw.write(name+":  "+s.getInetAddress().getHostAddress()+"对你说"+sendmsg);
							bw.newLine();
							bw.flush();
						}
					}
				}else if ("show".equalsIgnoreCase(sendContent)) {
					//show 显示所有在线用户
					bw=new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
					bw.write("当前所有在线人数："+MyServerSocket.map.size());
					bw.newLine();
					for (String str2 : onlineNames) {
						Socket onlinesoket=	MyServerSocket.map.get(str2);
						bw.write(str2+onlinesoket.getInetAddress().getHostAddress());
						if (str2.equals(name)) {
							bw.write("自己");
						}
						bw.newLine();
					}
					bw.flush();				
				}
			}
		} catch (Exception e) {
			String downline=name+":   "+s.getInetAddress().getHostAddress()+"下线了";
			System.out.println(downline);
			//将这个客户端从在线人数中删除
			MyServerSocket.map.remove(name);
			Set<String> onlineNames =	MyServerSocket.map.keySet();
			for (String str3 : onlineNames ) {
				Socket onlines=	MyServerSocket.map.get(str3);
				try {
					bw=new BufferedWriter(new OutputStreamWriter(onlines.getOutputStream()));
					bw.write(downline);
					bw.newLine();
					bw.flush();
				} catch (IOException e1) {
					e1.printStackTrace();
				}			
			}
		}finally{
			Util.close(br);
		}
	}
}
