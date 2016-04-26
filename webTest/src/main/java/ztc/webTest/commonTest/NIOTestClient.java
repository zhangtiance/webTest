package ztc.webTest.commonTest;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class NIOTestClient {
	private Selector selector;

	public void initClient(String ip, int port) {
		try {
			SocketChannel channel = SocketChannel.open();
			channel.configureBlocking(false);
			selector = Selector.open();
			channel.connect(new InetSocketAddress(ip, port));
			channel.register(selector, SelectionKey.OP_CONNECT);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ������ѯ�ķ�ʽ����selector���Ƿ�����Ҫ������¼�������У�����д���
	 * 
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public void listen() throws IOException {
		// ��ѯ����selector
		while (true) {
			selector.select();
			// ���selector��ѡ�е���ĵ�����
			Iterator ite = this.selector.selectedKeys().iterator();
			while (ite.hasNext()) {
				SelectionKey key = (SelectionKey) ite.next();
				// ɾ����ѡ��key,�Է��ظ�����
				ite.remove();
				// �����¼�����
				if (key.isConnectable()) {
					SocketChannel channel = (SocketChannel) key.channel();
					// ����������ӣ����������
					if (channel.isConnectionPending()) {
						channel.finishConnect();

					}
					// ���óɷ�����
					channel.configureBlocking(false);

					// ��������Ը�����˷�����ϢŶ
					channel.write(ByteBuffer.wrap(new String("�����˷�����һ����Ϣ").getBytes()));
					// �ںͷ�������ӳɹ�֮��Ϊ�˿��Խ��յ�����˵���Ϣ����Ҫ��ͨ�����ö���Ȩ�ޡ�
					channel.register(this.selector, SelectionKey.OP_READ);

					// ����˿ɶ����¼�
				} else if (key.isReadable()) {
					read(key);
				}

			}

		}
	}

	/**
	 * �����ȡ����˷�������Ϣ ���¼�
	 * 
	 * @param key
	 * @throws IOException
	 */
	public void read(SelectionKey key) throws IOException {
		// �ͷ���˵�read����һ��
	}

	/**
	 * �����ͻ��˲���
	 * 
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		NIOTestClient client = new NIOTestClient();
		client.initClient("localhost", 7011);
		client.listen();
	}
}
