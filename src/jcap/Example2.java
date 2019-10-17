// $Id: Example1.java,v 1.3 2002/02/18 15:33:00 pcharles Exp $

/***************************************************************************
 * Copyright (C) 2001, Patrick Charles and Jonas Lehmann                   *
 * Distributed under the Mozilla Public License                            *
 *   http://www.mozilla.org/NPL/MPL-1.1.txt                                *
 ***************************************************************************/

package jcap;

import net.sourceforge.jpcap.capture.*;
import net.sourceforge.jpcap.net.*;

public class Example2
{
  private static final int INFINITE = -1;
  private static final int PACKET_COUNT = 10; 

  // BPF filter for capturing any packet
  private static final String FILTER = "";

  private PacketCapture m_pcap;
  private String m_device;

  public Example2() throws Exception {
    // Step 1:  Instantiate Capturing Engine
    m_pcap = new PacketCapture();

    String[] devices = m_pcap.lookupDevices(); 
    
    for (int i = 0 ; i<devices.length ; i++) {
    	System.out.println(devices[i]);
    }
    
  }

  public static void main(String[] args) {
    try {
      Example2 example = new Example2();
    } catch(Exception e) {
      e.printStackTrace();
      System.exit(1);
    }
  }
}


