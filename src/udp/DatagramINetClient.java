package udp;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class DatagramINetClient extends Thread {

	private SocketAddress destAdd = new InetSocketAddress("localhost", 13000);

	private DatagramSocket sendSocket;
	private JTextArea jta_receive = new JTextArea(10, 25);
	private JComboBox jcb_addList = new JComboBox();

	public DatagramINetClient() {
		try {
			sendSocket = new DatagramSocket();

		} catch (Exception ef) {
			ef.printStackTrace();
		}
	}

	public void run() {
		try {
			while (true) {
				byte[] recvData = new byte[1024];
				DatagramPacket recvPacket = new DatagramPacket(recvData, recvData.length);
				System.out.println("client: waiting for data to come");
				sendSocket.receive(recvPacket);
				byte[] data = recvPacket.getData();
				System.out.println("received data: " + new String(data).trim());

				ByteArrayInputStream bins = new ByteArrayInputStream(data);
				ObjectInputStream oins = new ObjectInputStream(bins);
				Object dataO = oins.readObject();
				System.out.println("received object: " + dataO);

				if (dataO instanceof Set) {
					Set<InetSocketAddress> othersAdds = (Set) dataO;
					jcb_addList.removeAllItems();
					for (InetSocketAddress it : othersAdds) {
						jcb_addList.addItem(it);
					}
				} else if (dataO instanceof String) {
					String s = (String) dataO;
					jta_receive.append(s + "\r\n");
				} else {
					String s = "unknown msg: " + dataO;
					jta_receive.append(s + "\r\n");
				}
			}

		} catch (Exception ef) {
			ef.printStackTrace();
		}
	}

	public void sendP2PMsg(String msg, InetSocketAddress dest) {
		try {
			
			ByteArrayOutputStream bous = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(bous);
			oos.writeObject(msg);
			oos.flush();
			byte[] data = bous.toByteArray();
			DatagramPacket dp = new DatagramPacket(data, data.length, dest);
			sendSocket.send(dp);
			System.out.println("P2P message sent to: " + dest + "  " + msg);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void sendRequestMsg(String msg) {
		try {
			byte buffer[] = msg.getBytes();
			DatagramPacket dp = new DatagramPacket(buffer, buffer.length, destAdd);
			sendSocket.send(dp);
			System.out.println("sent to server: " + msg);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setUpUI() {
		JFrame jf = new JFrame();
		jf.setTitle("P2P test -client");
		FlowLayout fl = new FlowLayout(0);
		jf.setLayout(fl);
		jf.setSize(300,300);
		JButton jb_get = new JButton("Get the addresses of other clients");
		jf.add(jb_get);
		jf.add(jcb_addList);
		jb_get.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				sendRequestMsg("got addresses");
				
			}
			
		});
		
		JLabel la_name = new JLabel ("received message: ");
		JLabel la_users = new JLabel("sent to: ");
		
		final JTextField jtf_sned = new JTextField(20);
		JButton bu_send = new JButton("send");
		jf.add(la_name);
		jf.add(jta_receive);
		jf.add(la_users);
		jf.add(jtf_sned);
		jf.add(bu_send);
		
		ActionListener sendListenner = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String msg = jtf_sned.getText();
				InetSocketAddress dest = (InetSocketAddress) jcb_addList.getSelectedItem();
				sendP2PMsg(msg,dest);
				jtf_sned.setText("");
				
			}
			
		};
		bu_send.addActionListener(sendListenner);
		jtf_sned.addActionListener(sendListenner);
		jf.setVisible(true);
		jf.setDefaultCloseOperation(3);
		
		
		
		
	}
	
	public static void main(String a[]) {
		DatagramINetClient sender = new DatagramINetClient();
		sender.start();
		sender.setUpUI();
	}
}
