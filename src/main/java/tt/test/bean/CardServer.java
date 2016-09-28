package tt.test.bean;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class CardServer extends Thread {

	int port;
	ServerSocket serverSocket;

	public CardServer(int port) throws Exception {
		super();
		this.port = port;
		this.start();
	}

	@Override
	public void run() {
		if (!open())
			return;
		while (!isInterrupted()) {
			try {
				serverSocket.accept();
			} catch (Exception e) {
				e.printStackTrace(System.err);
			}
		}
		close();
	}

	private boolean open() {
		try {
			close();
			this.serverSocket = new ServerSocket();
			this.serverSocket.setReuseAddress(true);
			this.serverSocket.setReceiveBufferSize(4096);
			this.serverSocket.bind(new InetSocketAddress(port));
			System.out.println(String.format("server opened(%d).", port));
			return true;
		} catch (Exception e) {
			e.printStackTrace(System.err);
			return false;
		}
	}

	public void close() {
		if (serverSocket != null) {
			if (!serverSocket.isClosed()) {
				try {
					serverSocket.close();
					System.out.println(String.format("server closed(%d).", port));
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
			serverSocket = null;
		}
	}

	public void shutdown() {
		System.out.println(String.format("Trying to shutdown CardServer(%s) .... ", port));
		interrupt();
		try {
			sleep(3000);
			close();
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
	}

	public static class Handler extends Thread {

		Socket socket;

		public Handler(Socket socket) {
			super();
			this.socket = socket;
		}
	}
}
