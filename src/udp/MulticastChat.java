package udp;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class MulticastChat extends Thread {
	private static int portTem=9999;
	private static String multiAddr="230.0.0.1";
	private InetAddress inetAddress;
	private MulticastSocket multicastSocket;
	
	private JTextArea jta_receive = new JTextArea(10,25);
	
	
	
	public MulticastChat() {
		try {
			inetAddress = InetAddress.getByName(multiAddr);
			multicastSocket = new MulticastSocket();
			setupUI();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	private void setupUI() {
		JFrame jf = new JFrame();
		jf.setTitle("Multicast Chatroom");
		FlowLayout fl = new FlowLayout(0);
		jf.setLayout(fl);
		jf.setSize(300,400);
		JLabel la_name = new JLabel("message received");
		JLabel la_users = new JLabel("your name");
		
		final JTextField jtf_name = new JTextField(5);
		final JTextField jtf_sned= new JTextField(20);
		
		JButton bu_send = new JButton("Send");
		
		jf.add(la_name);
		jf.add(jta_receive);
		jf.add(la_users);
		jf.add(jtf_name);
		jf.add(jtf_sned);
		jf.add(bu_send);

		
		ActionListener sendListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String name = jtf_name.getText();
				String msg = jtf_sned.getText();
				msg = name+ " say: "+ msg;
				sendMsg(msg);
				jtf_sned.setText("");
			}
			
		};
		
		bu_send.addActionListener(sendListener);
		jtf_sned.addActionListener(sendListener);
		jf.setVisible(true);
		jf.setDefaultCloseOperation(3);
	}
	protected void sendMsg(String msg) {
		try {
			byte[] data = msg.getBytes();
			DatagramPacket datagramPacket = new DatagramPacket(data, data.length, inetAddress, portTem);
			multicastSocket.send(datagramPacket);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void run() {
		try {
			MulticastSocket recvSocket = new MulticastSocket(portTem);
			recvSocket.joinGroup(inetAddress);
			while(true) {
				byte[] data = new byte[100];
				DatagramPacket datagramPacket = new DatagramPacket(data, data.length);
				recvSocket.receive(datagramPacket);
				String input = new String(data).trim();
				jta_receive.append(input+ "\r\n"); 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
	
		MulticastChat mc = new MulticastChat();
		mc.start();

	}

}
