package jcap;

import net.sourceforge.jpcap.capture.RawPacketListener;
import net.sourceforge.jpcap.net.RawPacket;

class RawPacketHandler implements RawPacketListener 
{
  private static int m_counter = 0;

  public void rawPacketArrived(RawPacket data) {
    m_counter++;
    System.out.println("Received packet (" + m_counter + ")");
  }
}