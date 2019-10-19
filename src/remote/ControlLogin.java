package remote;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class ControlLogin extends JFrame {

	public static void main(String[] args) {
		ControlLogin start = new ControlLogin();
		start.showFrame();
		
	}

	private void showFrame() {
		this.setTitle("Remote Control Client");
		this.setLayout(new FlowLayout());
		JLabel la_ip = new JLabel("remote ip address");
		final JTextField jta_ip = new JTextField("localhost");
		JLabel la_port = new JLabel("remote port");
		final JTextField jta_port = new JTextField("9090");
		JButton jb_start = new JButton("connect!");

		this.add(la_ip);
		this.add(jta_ip);
		this.add(la_port);
		this.add(jta_port);
		this.add(jb_start);
		
		
		jb_start.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String remoteIP = jta_ip.getText();
				String sPort = jta_port.getText();
				int port = Integer.parseInt(sPort);
				ControlThread ct = new ControlThread(remoteIP,port);
				new Thread(ct).start();
				
				
			}
			
			
		});
		
		this.setSize(230,120);
		this.setDefaultCloseOperation(3);
		this.setVisible(true);
		
		
	}

}
