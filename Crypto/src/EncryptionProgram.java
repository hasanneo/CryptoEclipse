import java.math.BigInteger;
import java.security.SecureRandom;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.*; 
import javax.swing.*; 
public class EncryptionProgram {
	public static void initLayout() {
		JFrame frame = new JFrame("Bitch project");
		JTabbedPane tabbedPane = new JTabbedPane();
		JPanel panel1, panel2, panel3, panel4, panel5;
		JButton aliceSendButton;
		panel1 = new JPanel();
		panel2 = new JPanel();
		panel3 = new JPanel();
		panel4 = new JPanel();
		panel5 = new JPanel();
		//text areas for Alice+
		//****Contacts panel
		JPanel contacts= new JPanel();
		contacts.setLayout(new GridLayout(4, 1));
		JLabel contactsLabel=new JLabel("<HTML><U>Contacts:</U></HTML>");
		
		JRadioButton  bobRadioBtn=new JRadioButton ("Timmy");
		JRadioButton  johnRadioBtn=new JRadioButton ("John");
		JRadioButton  adamRadioBtn=new JRadioButton ("Adam");
		contacts.add(contactsLabel);
		contacts.add(bobRadioBtn);
		contacts.add(johnRadioBtn);
		contacts.add(adamRadioBtn);
		//****text panel
		JPanel textsPanel=new JPanel();
		textsPanel.setLayout(new GridLayout(2,1,3,5));
		JScrollPane sp,sp2;
		JTextArea sendText= new JTextArea(10,30);
		JTextArea receieveText= new JTextArea(10,30);
		textsPanel.add(sendText);
		textsPanel.add(receieveText);
		sp = new JScrollPane(sendText);
		sp2= new JScrollPane(receieveText);
		textsPanel.add(sp);
		textsPanel.add(sp2);
		panel1.setLayout(new FlowLayout());
		panel1.add(contacts);
		//panel1.add(sendText);
		panel1.add(textsPanel);
		// Set the line wrap and word wrap to true for the frame
		sendText.setLineWrap(true);
		sendText.setWrapStyleWord(true);
		aliceSendButton=new JButton("Send");
		panel1.add(aliceSendButton);
		tabbedPane.addTab("Alice", panel1);
		tabbedPane.addTab("Bob ", panel2);
		tabbedPane.addTab("John", panel3);
		tabbedPane.addTab("Adam ", panel4);
		frame.setLayout(new FlowLayout());
		//frame.setLayout(new GridLayout(4, 2));
		frame.add(tabbedPane);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setSize(550,350);
	    frame.setVisible(true);
	}
	public static void main(String[] args) {
	/*	RSA rsa=new RSA();
		String message="Hello this is H please let me finish this shit";
		String message2="What is this??";
		System.out.println(message);
		byte[] plainMessage=message.getBytes();
		byte[] plainMessage2=message2.getBytes();
		BigInteger messageSignature =rsa.sign(plainMessage);
		System.out.println(rsa.checkSignature(messageSignature, plainMessage2));
		byte[] encryptedMessage=rsa.encrypt(plainMessage, rsa.getPublicKey());
		byte[] decryptedMessage = rsa.decrypt(encryptedMessage);
		String encryptionResult=new String(encryptedMessage);
		String decriptionResult = new String(decryptedMessage);
		
		System.out.println(encryptionResult);
		System.out.println(decriptionResult);*/
		
		/*SecureRandom random = new SecureRandom();
		int bits=1024;
		BigInteger e=new BigInteger("3");// consider it to be equal to 3;
		CryptoHash hashFunction=new CryptoHash();
		BigInteger p = new BigInteger(bits / 2, 100, random);// first prime number
		BigInteger q = new BigInteger(bits / 2, 100, random);// second prime number
		BigInteger phi = (p.subtract(new BigInteger("1"))).multiply(q.subtract(new BigInteger("1")));
		while (phi.gcd(e).intValue() > 1) {
			e = e.add(new BigInteger("2"));
		}
		SignatureRSA rsa=new SignatureRSA(p, q, e, hashFunction);
		String message="Hello this is H";
		byte[] plainMessage=message.getBytes();
		BigInteger messageSignature =rsa.sign(plainMessage);
		System.out.println("END\n"+rsa.checkSignature(rsa.getPublicKey(), messageSignature, plainMessage, hashFunction));*/
		/*String quote="What is going on here";
		String publicKey="And is there honey still for tea?";
		TEA tea = new TEA(publicKey.getBytes());
		// Run it through the cipher... and back 
		byte[] original = quote.getBytes();
		byte[] crypt = tea.encrypt(original);
		byte[] result = tea.decrypt(crypt);
		String encryptionResult=new String(crypt);
		String decriptionResult = new String(result);
		System.out.println("Encryption:"+encryptionResult);
		System.out.println("Decryption:"+decriptionResult);*/
		initLayout();
	}
}
