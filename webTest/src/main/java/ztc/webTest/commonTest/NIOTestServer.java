package ztc.webTest.commonTest;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class NIOTestServer {
	// 通道管理器 (reactor?)
	private Selector selector;

	/**
	 * 通道初始化
	 * 
	 * @param port
	 */
	public void initChanel(int port) {
		try {
			// open a chanel
			ServerSocketChannel serverSocketChanel = ServerSocketChannel.open();
			// non-blocking
			serverSocketChanel.configureBlocking(false);
			// bind port
			serverSocketChanel.socket().bind(new InetSocketAddress(port));
			// open selector
			this.selector = Selector.open();
			// register server
			serverSocketChanel.register(selector, SelectionKey.OP_ACCEPT);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void listen() {
		try {
			System.out.println("开启监听");

			while (true) {
				selector.select();

				Iterator itr = selector.selectedKeys().iterator();

				while (itr.hasNext()) {
					SelectionKey key = (SelectionKey) itr.next();
					itr.remove();
					if (key.isAcceptable()) {
						ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();

						SocketChannel socketChannel = serverChannel.accept();

						socketChannel.configureBlocking(false);

						socketChannel.write(ByteBuffer.wrap(new String("this is a message!").getBytes()));

						socketChannel.register(selector, SelectionKey.OP_READ);
					} else if (key.isReadable()) {
						read(key);
					}

				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void read(SelectionKey key) {

		try {
			SocketChannel channel = (SocketChannel) key.channel();

			ByteBuffer buffer = ByteBuffer.allocate(100);
			channel.read(buffer);
			byte[] date = buffer.array();
			String message = new String(date).trim();
			System.out.println("Server receive message:" + message);
			ByteBuffer outBuffer = ByteBuffer.wrap(message.getBytes());
			channel.write(outBuffer);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		NIOTestServer server = new NIOTestServer();
		server.initChanel(7011);
		server.listen();
	}
}
