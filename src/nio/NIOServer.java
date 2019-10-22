package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class NIOServer {

	private Selector selector;

	public void initServer(int port) throws IOException {
		ServerSocketChannel server = ServerSocketChannel.open();
		server.socket().bind(new InetSocketAddress(port));
		server.configureBlocking(false);
		selector = Selector.open();
		server.register(selector, SelectionKey.OP_ACCEPT);
	}

	public void ioAction() {
		try {
			while (true) {
				System.out.println("Waiting for IO");
				selector.select();
				System.out.println("IO happens");
				Iterator iter = selector.selectedKeys().iterator();
				while (iter.hasNext()) {
					SelectionKey key = (SelectionKey) iter.next();
					iter.remove();
					System.out.println("process this io");
					if (key.isAcceptable()) {
						System.out.println("entering a event");
						ServerSocketChannel server = (ServerSocketChannel) key.channel();
						SocketChannel channel = server.accept();
						channel.configureBlocking(false);
						String outMsg = " Hello welcome";
						ByteBuffer resBuffer = ByteBuffer.wrap(outMsg.getBytes());
						channel.write(resBuffer);
						channel.register(selector, SelectionKey.OP_READ);
						System.out.println("connection entering: " + channel.socket().toString());

					} else if (key.isReadable()) {
						System.out.println("data event coming");
						processRead(key);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected void processRead(SelectionKey key) throws IOException {
		SocketChannel channel = (SocketChannel) key.channel();
		ByteBuffer buffer = ByteBuffer.allocate(10000);
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
			NIOServer server = new NIOServer();
			server.initServer(port);
			System.out.println("server on " + port);
			server.ioAction();
		}catch(
	
		Exception e)
		{
			e.printStackTrace();
		}
	}

}
