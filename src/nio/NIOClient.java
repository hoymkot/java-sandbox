package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class NIOClient {

	private Selector selector;

	public void initConn(String serverIp, int port) throws IOException {
		InetSocketAddress address = new InetSocketAddress(serverIp,8888);
		SocketChannel sc= SocketChannel.open();
		sc.configureBlocking(false);
		selector = Selector.open();
		sc.register(selector, SelectionKey.OP_CONNECT);
		sc.connect(address);
	}

	
	public void listen() {
		int i=0;
		try {
			while (true) {
				System.out.println("waiting for IO to come");
				selector.select();
				System.out.println("some I/O is happening");
				Iterator iter = selector.selectedKeys().iterator();
				while(iter.hasNext()) {
					SelectionKey key = (SelectionKey) iter.next();
					iter.remove();
					System.out.println("Process this IO event");
					if(key.isConnectable()) {
						SocketChannel channel = (SocketChannel) key.channel();
						if(channel.isConnectionPending()) {
							channel.finishConnect();
						}
						channel.configureBlocking(false);
						String outMsg = "Hello this is NIO Client";
						ByteBuffer buffer = ByteBuffer.wrap(outMsg.getBytes());
						channel.write(buffer);
						channel.register(selector, SelectionKey.OP_READ);
						System.out.println("connect success " + channel.socket().getRemoteSocketAddress());
					}
					else if(key.isReadable()) {
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
		ByteBuffer buffer = ByteBuffer.allocate(1000);
		int count = channel.read(buffer);
		byte[] data = buffer.array();
		String msg = new String(data).trim();
		msg = msg + "\r\n";
		System.out.println("read message " + msg);
		ByteBuffer outBuffer = ByteBuffer.wrap(msg.getBytes());
		channel.write(outBuffer);

	}

	public static void main(String[] args) {
		int port =8888;
		try {
			NIOClient client = new NIOClient();
			client.initConn("localhost", port);
			System.out.println("listening on " + port);
			client.listen();
		}catch(
	
		Exception e)
		{
			e.printStackTrace();
		}
	}

}
