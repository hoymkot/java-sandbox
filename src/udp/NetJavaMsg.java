package udp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.SocketAddress;

public class NetJavaMsg {
	private int totalLen;
	private int id;
	private byte[] data;
	private SocketAddress recvRespAdd; // sender address for getting ack message
	private SocketAddress destAdd; // destination address for sening message
	private int sendCount = 0;
	private long lastSendTime;

	public int getTotalLen() {
		return totalLen;
	}

	public void setTotalLen(int totalLen) {
		this.totalLen = totalLen;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public SocketAddress getRecvRespAdd() {
		return recvRespAdd;
	}

	public void setRecvRespAdd(SocketAddress recvRespAdd) {
		this.recvRespAdd = recvRespAdd;
	}

	public SocketAddress getDestAdd() {
		return destAdd;
	}

	public void setDestAdd(SocketAddress destAdd) {
		this.destAdd = destAdd;
	}

	public int getSendCount() {
		return sendCount;
	}

	public void setSendCount(int sendCount) {
		this.sendCount = sendCount;
	}

	public long getLastSendTime() {
		return lastSendTime;
	}

	public void setLastSendTime(long lastSendTime) {
		this.lastSendTime = lastSendTime;
	}

	public NetJavaMsg(int id, byte[] data) {
		this.id = id;
		this.data = data;
		totalLen = 4 + 4 + data.length;
	}

	public NetJavaMsg(byte[] udpData) {
		try {

			ByteArrayInputStream bins = new ByteArrayInputStream(udpData);
			DataInputStream dins = new DataInputStream(bins);
			this.totalLen = dins.readInt();
			this.id = dins.readInt();
			data = new byte[totalLen - 4 - 4];
			dins.readFully(this.data);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public byte[] toBytes() {
		try {
			ByteArrayOutputStream bous = new ByteArrayOutputStream();
			DataOutputStream dous = new DataOutputStream(bous);
			dous.writeInt(totalLen);
			dous.writeInt(id);
			dous.write(data);
			dous.flush();
			return bous.toByteArray();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	public String toString() {
		return "id: " + id + "content: " + new String(data) + "totalLen: " + totalLen + "\r\n senderAdd:" + recvRespAdd
				+ " destAdd: " + this.destAdd;
	}
}
