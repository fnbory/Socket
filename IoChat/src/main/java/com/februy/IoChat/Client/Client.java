package com.februy.IoChat.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {
		Socket client=new Socket("localhost", 7978);
		BufferedReader br=new BufferedReader(new InputStreamReader(client.getInputStream()));
		PrintWriter pw=new PrintWriter(new OutputStreamWriter(client.getOutputStream()));
		// 开一个线程，随时都能获得服务端发来的消息
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					String da=null;
					try {
						while((da=br.readLine())!=null) {
							System.out.println(da);
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
				}
			}
		}).start();
		
		// 模拟发送消息
		for(int i=0;i<3;i++) {
			Thread.sleep(1000);
			pw.println("shit");
			pw.flush();
		}
		
		
	}
}
