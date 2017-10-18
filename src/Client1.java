import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class Client1 extends Frame {
	TextArea ta = new TextArea();
	TextField tf = new TextField();
	Socket st = null;
	DataOutputStream dos = null;
	DataInputStream dis = null;
	
	public static void main(String[] args) {
			new Client1().launch();
	}
	public void launch() {
		this.add(ta,BorderLayout.NORTH);
		this.add(tf,BorderLayout.SOUTH);
		this.setSize(300, 300);
		this.setLocation(300, 300);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				disconnect();
				System.exit(0);
			}
			
		});
		tf.addActionListener(new TFListener());
		this.pack();
		this.setVisible(true);
		connect();
	}
	
	public void connect() {
		 try {
			st = new Socket("127.0.0.1",9999);
			System.out.println("已经连上了！");
			dos = new DataOutputStream(st.getOutputStream());
			dis = new DataInputStream(st.getInputStream());
			new Thread(new Recv()).start();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void disconnect() {
		try {
			dos.flush();
			if(dos != null) dos.close();
			if(dos != null) st.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("我退出了！");
	}
	
	class TFListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String s = tf.getText();
			try {
				dos.writeUTF(s);
				dos.flush();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			//ta.setText(ta.getText() + s + '\n');
			tf.setText("");
		}	
	}
	
	class Recv implements Runnable{
		public void run() {
			boolean bl = true;
			while(bl) {
				try {
					String str = dis.readUTF();
					ta.setText(ta.getText() + str + '\n');
				} catch (IOException e) {
					System.out.println("退出了，bye!");
					bl = false;
				}
			}
		}
		
	}

}
