package com.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 *客户端线程 负责收消息
 */
public class MySocketThread extends Thread{
	private Socket s;
	
	public MySocketThread (Socket s){
		this.s=s;
		
	}
	@Override
	public void run() {
		BufferedReader br=null;
		try {
			br=new BufferedReader(new InputStreamReader(s.getInputStream()));
			String reciveMsg="";
			while(true){
				reciveMsg=br.readLine();
				System.out.println(reciveMsg);
				if (reciveMsg==null) {
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			Util.close(br);
		}
	}
}
