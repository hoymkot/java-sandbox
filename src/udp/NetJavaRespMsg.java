package udp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.SocketAddress;


// no data
public class NetJavaRespMsg {
	private int totalLen;
	private int respId;
	private byte state = 0;
	private long resTime;
	
	
	public NetJavaRespMsg(int respId, byte state, long resTime) {
		this.respId = respId;
		this.state = state;
		this.resTime = resTime;
		totalLen = 4 + 4 + +1 +8;
	}

	public int getTotalLen() {
		return totalLen;
	}

	public void setTotalLen(int totalLen) {
		this.totalLen = totalLen;
	}

	public int getRespId() {
		return respId;
	}

	public void setRespId(int respId) {
		this.respId = respId;
	}

	public byte getState() {
		return state;
	}

	public void setState(byte state) {
		this.state = state;
	}

	public long getResTime() {
		return resTime;
	}

	public void setResTime(long resTime) {
		this.resTime = resTime;
	}

	public NetJavaRespMsg(byte[] udpData) {
		try {

			ByteArrayInputStream bins = new ByteArrayInputStream(udpData);
			DataInputStream dins = new DataInputStream(bins);
			this.totalLen = dins.readInt();
			this.respId = dins.readInt();
			this.state = dins.readByte();
			this.resTime = dins.readLong();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public byte[] toBytes() {
		try {
			ByteArrayOutputStream bous = new ByteArrayOutputStream();
			DataOutputStream dous = new DataOutputStream(bous);
			dous.writeInt(totalLen);
			dous.writeInt(respId);
			dous.writeByte(this.state);
			dous.writeLong(this.resTime);
			dous.flush();
			return bous.toByteArray();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	public String toString() {
		return "respId: " + this.respId+ "respTime: " + this.resTime + "totalLen: " + totalLen + "state: " + this.state;
	}
}
