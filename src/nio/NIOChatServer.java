package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class NIOChatServer {

	private Map<SocketChannel, ByteBuffer> chanels = new HashMap();
	private Selector selector;

	public NIOChatServer(int port) throws IOException {
		ServerSocketChannel server = ServerSocketChannel.open();
		server.socket().bind(new InetSocketAddress(port));
		server.configureBlocking(false);
		selector = Selector.open();
		server.register(selector, SelectionKey.OP_ACCEPT);

	}

	public void connAction() {
		int i = 0;
		try {
			while (true) {
				System.out.println("waiting for IO to come");
				selector.select();
				System.out.println("some I/O is happening");
				Iterator iter = selector.selectedKeys().iterator();
				while (iter.hasNext()) {
					SelectionKey key = (SelectionKey) iter.next();
					iter.remove();
					System.out.println("Process this IO event");
					if (key.isAcceptable()) {

						ServerSocketChannel server = (ServerSocketChannel) key.channel();
						SocketChannel channel = server.accept();
						channel.configureBlocking(false);
						String outMsg = " Hello welcome  \r\n";
						ByteBuffer resBuffer = ByteBuffer.wrap(outMsg.getBytes());
						channel.write(resBuffer);
						channel.register(selector, SelectionKey.OP_READ);
						System.out.println("connection entering: " + channel.socket().toString());

						ByteBuffer readBuffer = ByteBuffer.allocate(1024);
						chanels.put(channel, readBuffer);
					} else if (key.isReadable()) {
						processRead(key);
					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void processRead(SelectionKey key) throws IOException {
		SocketChannel channel = (SocketChannel) key.channel();
		ByteBuffer readB = chanels.get(channel);
		int count = channel.read(readB);
		byte[] data = readB.array();
		String msg = new String(data).trim();
		msg = msg + "\r\n";
		System.out.println("read message " + msg);
		if (readB.get(readB.position() - 1) == (byte) '\n') {
			readB.compact();
			readB.position(0);
			System.out.println("send to other client tunnel ");
			ByteBuffer outBuffer = ByteBuffer.wrap(msg.getBytes());
			for (SocketChannel otherChannel : chanels.keySet()) {
				if (otherChannel == channel) continue;
				otherChannel.write(outBuffer);
				System.out.println(msg + " send data to " + otherChannel.socket());
			}
		}

	}

	public static void main(String[] args) {
		int port = 8888;
		try {
			NIOChatServer server = new NIOChatServer(port);
			System.out.println("server waiting on " + port);
			server.connAction();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
