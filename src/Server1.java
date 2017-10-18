import java.io.*;
import java.net.*;
import java.util.*;

public class Server1 {
	ServerSocket ss = null;
	Socket s = null;
	List clients = new ArrayList<CThread>();
	
	public static void main(String[] args) {
		new Server1().start();
	}
	
	public void start() {
			try {
				ss = new ServerSocket(9999);
			} catch (IOException e) {
				e.printStackTrace();
			}
			while(true) {
				try {
					s = ss.accept();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				System.out.println("a client connected!");
				CThread c = new CThread(s);
				clients.add(c);
				new Thread(c).start();
			}
	}
	
	class CThread implements Runnable {
		private Socket s = null;
		private String str = null;
		private DataInputStream dis = null;
		private DataOutputStream dos = null;
		CThread(Socket s) {
			this.s = s;
		}
		
		public void send(String str) {
			try {
				dos.writeUTF(str);
			} catch (IOException e) {
				clients.remove(this);
				System.out.println("对不起我已经退出了！");
				//e.printStackTrace();
			}
		}
		
		public void run() {
			boolean bConnect = true;
			try {
				while(bConnect) {
					dis = new DataInputStream(s.getInputStream());
					dos = new DataOutputStream(s.getOutputStream());
					str = dis.readUTF();
					//System.out.println(str);
					Iterator<CThread> it = clients.iterator();
					while(it.hasNext()) {
						CThread ct = it.next();
						ct.send(str);
					}
				}
			} catch (IOException e) {
				System.out.println("a client exited!");
			} finally {
				try {
					if(dis != null) dis.close();
					if(dos != null) dos.close(); 
					if(s != null) s.close();
					} catch (IOException e1) {
					e1.printStackTrace();
					}
			}
		}
		
	}
	
}
