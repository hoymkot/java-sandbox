// $Id: Example1.java,v 1.3 2002/02/18 15:33:00 pcharles Exp $

/***************************************************************************
 * Copyright (C) 2001, Patrick Charles and Jonas Lehmann                   *
 * Distributed under the Mozilla Public License                            *
 *   http://www.mozilla.org/NPL/MPL-1.1.txt                                *
 ***************************************************************************/

package jcap;

import java.io.IOException;

import net.sourceforge.jpcap.capture.*;
import net.sourceforge.jpcap.net.*;

/**
 * jpcap Tutorial - Example 1
 *
 * @author Jonas Lehmann and Patrick Charles
 * @version $Revision: 1.3 $
 * @lastModifiedBy $Author: pcharles $
 * @lastModifiedAt $Date: 2002/02/18 15:33:00 $
 */
public class Example1 {
	private static final int INFINITE = -1;
	private static final int PACKET_COUNT = INFINITE;

	// BPF filter for capturing any packet
	private static final String FILTER = "";

	private PacketCapture m_pcap;
	private String m_device;

	public Example1() throws Exception {
		// Step 1: Instantiate Capturing Engine
		m_pcap = new PacketCapture();

		// Step 2: Check for devices
//		m_device = m_pcap.findDevice();  
//		System.out.println(m_device);
//		String[] d = m_pcap.lookupDevices();
//		for (int i = 0 ; i< d.length; i ++) {
//			System.out.println(d[i]);
//		}
//		System.exit(1);
//		int end_idx = m_device.indexOf("}");
//		m_device = m_device.substring(0, end_idx + 1);

		// Step 3: Open Device for Capturing (requires root)
		m_pcap.open("\\Device\\NPF_{07834256-A055-4544-BF78-0DFB7F85C083}", true);

		// Step 4: Add a BPF Filter (see tcpdump documentation)
		m_pcap.setFilter(FILTER, true);

		// Step 5: Register a Listener for Raw Packets
		m_pcap.addPacketListener(new PacketHandler(m_device));

		// Step 6: Capture Data (max. PACKET_COUNT packets)
		m_pcap.capture(PACKET_COUNT);
	}

	public static void main(String[] args) {
		try {
			Example1 example = new Example1();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	class PacketHandler implements PacketListener {
		private int counter = 0;

		public PacketHandler(String name) {
			this.name = name;
		}

		public void packetArrived(Packet packet) {
			counter++;

//			if (packet instanceof TCPPacket) {
//				TCPPacket tcp = (TCPPacket) packet;
//				String s = "TCPPacket:|dest_ip " + tcp.getDestinationAddress() + ":" + tcp.getDestinationPort()
//						+ "| src_ip " + tcp.getSourceAddress() + ":" + tcp.getSourcePort() + " |len: "
//						+ tcp.getLength();
//				System.out.println(s);
//				System.err.println(bytesToHex(tcp.getData()));
//			}
//			if (packet instanceof UDPPacket) {
//				UDPPacket udp = (UDPPacket) packet;
//				String s = "UDPPacket:|dest_ip " + udp.getDestinationAddress() + ":" + udp.getDestinationPort()
//						+ "| src_ip " + udp.getSourceAddress() + ":" + udp.getSourcePort() + " |len: "
//						+ udp.getLength();
//				System.out.println(s);
//				System.err.println(bytesToHex(udp.getData()));
//			}
//			
//			
			if (packet instanceof ICMPPacket) {
				System.out.println("ICMP Packet Received ");
			}
			
			if (packet instanceof ARPPacket) {
				ARPPacket arp = (ARPPacket) packet;
				String s = "ARPPacket:|dest_ip " + arp.getSourceHwAddress() 
				+ "| src_ip " + arp.getSourceHwAddress() + " |len: " + arp.getData().length;
				System.out.println(s);
				System.err.println(bytesToHex(arp.getARPHeader()));
			}
//			
			if (packet instanceof EthernetPacket) { 
				EthernetPacket ep = (EthernetPacket) packet;
				
			}
				
			
			
//			   String type = packet.getClass().getName();
//			    System.out.println(name + ": Packet(" + counter + 
//			                       ") is of type " + type + ".");
//			    System.err.println(packet);
		}

		String name;
	}

	private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

	public static String bytesToHex(byte[] bytes) {
		String s = "";
		for (int j = 0; j < bytes.length; j++) {
			s = s+ " " + bytes[j];
		}
		return new String(s);
	}
}
