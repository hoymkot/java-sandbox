package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class DatagramSender {

	private SocketAddress localAddr;
	private DatagramSocket dSender;
	private SocketAddress destAddr;

	private Map<Integer, NetJavaMsg> msgQueue = new HashMap<Integer, NetJavaMsg>();

	public DatagramSender() throws SocketException {
		localAddr = new InetSocketAddress("localhost", 13000);
		dSender = new DatagramSocket(localAddr);
		destAddr = new InetSocketAddress("localhost", 14000);
		startSendThread();
		startRecvResponseThread();
		startResendThread();
	}

	private void startResendThread() {
		new Thread(new Runnable() {
			public void run() {
				try {
					System.out.println(" client: loss resend is started ");
					resendMsg();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}).start();;		
	}

	private void startRecvResponseThread() {
		new Thread(new Runnable() {
			public void run() {
				try {
					recvResponse();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}).start();;
	}

	private void startSendThread() {
		new Thread(new Runnable() {
			public void run() {
				try {
					send();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}).start();;
	}
	
	protected void recvResponse() throws IOException {
		System.out.println("client - receive data thread started");
		while(true) {
			byte[] recvData = new byte[100];
			
			DatagramPacket recvPacket = new DatagramPacket(recvData, recvData.length);
			dSender.receive(recvPacket);
			
			NetJavaRespMsg resp = new NetJavaRespMsg(recvPacket.getData());

			System.out.println("client - receive ack message");
			// convert package to bytes 
			int respID = resp.getRespId();
			NetJavaMsg msg = msgQueue.get(new Integer(respID));
			if (msg != null) {
				System.out.println("client - acknowledge the receipt of message : " + respID);
				msgQueue.remove(resp);
			}
			
		}
	}	

	private void send() throws IOException {
		System.out.println("client - send data thread started");
		int id = 0;
		while(true) {
			id++;
			byte[] msgData = (id+"-hello").getBytes();
			
			// packing up a udp payload (msgData) 
			NetJavaMsg sendMsg = new NetJavaMsg(id,msgData);
			
			// convert package to bytes 
			byte buffer[] = sendMsg.toBytes();
			
			DatagramPacket dp = new DatagramPacket(buffer, buffer.length, destAddr);
			dSender.send(dp);
			
			sendMsg.setRecvRespAdd(localAddr);
			sendMsg.setDestAdd(destAddr);
			sendMsg.setSendCount(1);
			sendMsg.setLastSendTime(System.currentTimeMillis());
			msgQueue.put(id,  sendMsg); // put the message in the queue, waiting for response
			System.out.println("client: data sent: " + sendMsg);
		}
	}

	public void resendMsg() {
		Set<Integer> keySet = msgQueue.keySet();
		Iterator<Integer> it = keySet.iterator();
		while (it.hasNext()) {
			Integer key = it.next();
			NetJavaMsg msg = msgQueue.get(key);
			if(msg.getSendCount() >= 4) {
				it.remove();
				System.out.println(" client -- detected loss of message " + msg);
			}
			
			long cTime = System.currentTimeMillis();
			if ((cTime - msg.getLastSendTime() )>3000 && msg.getSendCount() <4) {
				byte buffer[] = msg.toBytes();
				try {
					DatagramPacket dp = new DatagramPacket(buffer, buffer.length, msg.getDestAdd());
					dSender.send(dp);
					msg.setSendCount(msg.getSendCount() +1);
					System.out.println("client -- resend message : "+ msg);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	public static void main(String[] args) throws Exception {
		new DatagramSender();
	}

}
