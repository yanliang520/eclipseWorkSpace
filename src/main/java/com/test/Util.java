package com.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Util {
	/**
	 * 关闭高效字符输入流
	 */
	public static void close(BufferedReader br){
		if (br!=null) {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 关闭高效字符输出流
	 */
	public static void close(BufferedWriter bw){
		if (bw!=null) {
			try {
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 关闭输入输出流
	 */
	public static void colse(BufferedWriter bw){
		try {
			if (bw != null) {
				bw.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 关闭所有流
	 */
	public static void close(BufferedReader br, BufferedWriter bw,Socket s, ServerSocket ss){
		try {
			//关闭所有流
			if (bw != null) {
				bw.close();
			}
			if (br != null) {
				br.close();
			}
			if (s != null) {
				s.close();
			}
			if (ss != null) {
				ss.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
}
