package com.februy.IoChat.Server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.log4j.Logger;
import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;

import com.februy.IoChat.Client.Client;

public class Server implements Runnable{
	private static Logger log=Logger.getLogger(Server.class);
	private static ServerSocket serverSocket;
	public static	Vector<Socket> clients=new Vector<>();
	private  Socket client;
	private static PrintWriter pw;
	private static String msg;
	private BufferedReader br;
	private static Vector<String> msgs=new Vector<>();
	public  Server(Socket client) throws IOException {
		this.client=client;
	}
	@Override
	public void run() {		
		while(true) {
			try {
				br=new BufferedReader(new InputStreamReader(client.getInputStream()));
				String data=null;
				if((data=br.readLine())!=null) {
					msgs.add(data);
				}
			}
			catch (IOException e) {
			e.printStackTrace();
			}
		}
	}
	public static void main(String[] args) throws IOException {
			serverSocket=new ServerSocket(7978);
			// 开一个线程，当消息列表中有消息时，就挨个儿发送给客户端
			new Thread(new Runnable() {
				@Override
				public void run() {
					while(true) {
						try {
							if(clients.size()>0) {
								if(msgs.size()>0) {
									for(Socket client:clients) {
										pw=new PrintWriter(client.getOutputStream());
										pw.println(msgs.get(0));
										pw.flush();
									}
									msgs.remove(0);
								}
							}
						}
						catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}).start();
			while(true) {
				// 每次有新的客户端连接服务端时，都开启一个线程来服务它，在这个
				// 线程内可以收到客户端发来的信息，然后加入消息列表中
				Socket client=serverSocket.accept();
				msgs.add(client.toString()+"is coming");
				clients.add(client); 
				new Thread(new Server(client)).start();
			}		
	}
}
