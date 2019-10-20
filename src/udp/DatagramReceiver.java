package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;

public class DatagramReceiver {

	private SocketAddress localAddr;
	private DatagramSocket dSender;

	public DatagramReceiver() throws Exception {
		localAddr = new InetSocketAddress("localhost", 14000);

		dSender = new DatagramSocket(localAddr);
		startRecvThread();
	}

	public void startRecvThread() {
		new Thread(new Runnable(){
			public void run() {
				try {
					revMsg();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
		
	}

	protected void revMsg() throws IOException {
		System.out.println("server - receiver thread started ... ");
		while(true) {
			byte[] recvData = new byte[100];
			
			DatagramPacket recvPacket = new DatagramPacket(recvData, recvData.length);
			dSender.receive(recvPacket);
			NetJavaMsg recvMsg = new NetJavaMsg(recvPacket.getData());
			System.out.println("server - receive datagram " + recvMsg);
			
			NetJavaRespMsg resp = new NetJavaRespMsg(recvMsg.getId(), (byte)0, System.currentTimeMillis());
			byte[] data= resp.toBytes(); 
			DatagramPacket dp = new DatagramPacket(data, data.length, recvPacket.getSocketAddress());
			dSender.send(dp);
			System.out.println("server sent ack: " + resp);
		}
	}
	
	public static void main(String arg[]) throws Exception{
		new DatagramReceiver();
	}

}
