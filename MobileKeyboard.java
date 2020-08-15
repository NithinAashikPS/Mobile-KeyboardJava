import javax.swing.*;
import java.net.*;
import java.io.*; 
import java.awt.*; 
import java.awt.event.KeyEvent; 
import javax.swing.border.EmptyBorder; 

public class MobileKeyboard extends JFrame{
	
	static Socket socket;
	static ServerSocket serverSocket;
	static InputStreamReader inputStreamReader;
	static BufferedReader bufferedReader;
    static Robot robot;
	static InetAddress localhost;
	static JFrame frame = new JFrame("Mobile Keyboard");
	static JLabel label;
	MobileKeyboard() {
		try {
			localhost = InetAddress.getLocalHost(); 
			
			label = new JLabel("Server IP Address : " + (localhost.getHostAddress()).trim());		
			label.setForeground (new Color(0, 92, 142));	
			label.setFont(new Font("Myanmar Text", Font.BOLD, 20));
			label.setHorizontalAlignment(SwingConstants.CENTER);
						
			JPanel panel = new JPanel();
			panel.setLayout(new BorderLayout());
			panel.setBackground(new Color(0, 0, 0, 0));
			panel.setBounds(0, 0, 500, 260);
			panel.add(label, BorderLayout.CENTER);
			panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
			
			ImageIcon background_image = new ImageIcon("images/background.png");
			ImageIcon icon = new ImageIcon("images/icon.png");
			Image img = background_image.getImage();
			Image temp_img = img.getScaledInstance(500, 300, Image.SCALE_SMOOTH);
			background_image = new ImageIcon(temp_img);
			JLabel background = new JLabel("",background_image, JLabel.CENTER);			
			background.add(panel, BorderLayout.CENTER);
			background.setBounds(0, 0, 500, 300);
			
			frame.getContentPane();
			frame.add(background);
			frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			frame.setIconImage(icon.getImage());
			frame.setSize(500, 300);
			frame.setResizable(false);
			frame.setVisible(true);
			
		} catch(Exception e) {
			System.out.println("Server Error");
		}		
	}
	
	public static void main(String args[]) throws IOException, AWTException, InterruptedException{
		new MobileKeyboard();
		serverSocket = new ServerSocket(6000);
		robot = new Robot();
		while(true) {
			socket = serverSocket.accept();
			inputStreamReader = new InputStreamReader(socket.getInputStream());
			bufferedReader = new BufferedReader(inputStreamReader);
			String status = bufferedReader.readLine();
			System.out.println(status);
			if(status.equals("Connected")) {
				label.setForeground (new Color(0, 165, 225));
				label.setText(status);
				frame.setSize(501, 301);
				frame.setSize(500, 300);
			} else {
				String[] data = status.split("=");
				if(data[0].equals("Press")) {
					robot.keyPress(Integer.parseInt(data[1]));
				}
				if(data[0].equals("Release")) {
					robot.keyRelease(Integer.parseInt(data[1]));
				}
			}
		}
	}
}