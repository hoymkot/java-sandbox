package udp;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.HashSet;
import java.util.Set;

public class DatagramRouteServer {
	private Set<InetSocketAddress> clientAddSet = new HashSet();
	
	public void startServer() throws Exception {
		DatagramSocket socket =new DatagramSocket(13000);
		System.out.println("UDP server waiting for data: " + socket.getLocalSocketAddress());
		while(true) {
			byte[] buffer = new byte[256];
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
			socket.receive(packet);;
			InetAddress clientAdd=packet.getAddress();
			int clientPort= packet.getPort();
			InetSocketAddress address = new InetSocketAddress(clientAdd,clientPort);
			clientAddSet.add(address);
			byte[] recvData= packet.getData();
			String s = new String(recvData).trim();
			
			System.out.println("server receives data: " + s+ " from: " + address);
			for(InetSocketAddress dclien:clientAddSet) {
				String termf= address + ", comes to server to read addresss ";
				ByteArrayOutputStream bous = new ByteArrayOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(bous);
				oos.writeObject(termf);
				oos.flush();
				byte[] data = bous.toByteArray();
				DatagramPacket mp = new DatagramPacket(data, data.length);
				mp.setSocketAddress(dclien);
				socket.send(mp);
			}
			
			ByteArrayOutputStream bous = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(bous);
			oos.writeObject(clientAddSet);
			oos.flush();
			byte[] data = bous.toByteArray();
			DatagramPacket sendP = new DatagramPacket(data, data.length);
			sendP.setSocketAddress(address);
			socket.send(sendP);
		}
	}
	
	public static void main(String args[]) throws Exception {
		DatagramRouteServer receiver = new DatagramRouteServer();
		receiver.startServer();
	}
}
