import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;
import java.util.Set;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.*;
import javax.swing.*;

public class EncryptionProgram {
	static BigInteger p;
	static BigInteger g;
	static JFrame frame = new JFrame("TEA");
	static JTabbedPane tabbedPane = new JTabbedPane();
	static SingleSelectionModel selectionModel = tabbedPane.getModel();
	static JPanel alicePanel;
	static JPanel bobPanel;
	static JPanel jhonPanel;
	static JPanel adamPanel;
	static JPanel mutualKeyPanel;
	static JTextArea mutualKey;
	// Alice
	static JTextArea aliceSendText;
	static JTextArea aliceReceieveText;
	static JButton aliceSendButton;
	static JPanel aliceContacts;
	static JRadioButton aliceBobRadioBtn;
	static JRadioButton aliceJohnRadioBtn;
	static JRadioButton aliceAdamRadioBtn;
	// bob
	static JTextArea bobSendText;
	static JTextArea bobReceieveText;
	static JButton bobSendButton;
	static JPanel bobContacts;
	static JRadioButton bobAliceRadioBtn;
	static JRadioButton bobJohnRadioBtn;
	static JRadioButton bobAdamRadioBtn;
	// John
	static JTextArea jhonSendText;
	static JTextArea jhonReceieveText;
	static JButton jhonSendButton;
	static JPanel jhonContacts;
	static JRadioButton jhonAliceRadioBtn;
	static JRadioButton jhonBobRadioBtn;
	static JRadioButton jhonAdamRadioBtn;
	// adam
	static JTextArea adamSendText;
	static JTextArea adamReceieveText;
	static JButton adamSendButton;
	static JPanel adamContacts;
	static JRadioButton adamAliceRadioBtn;
	static JRadioButton adamJohnRadioBtn;
	static JRadioButton adamBobRadioBtn;

	static Customer alice;
	static Customer bob;
	static Customer john;
	static Customer adam;

	public static void createAlicePanel() {
		ButtonGroup radioButtonsGroup = new ButtonGroup();
		// ****Contacts panel
		aliceContacts = new JPanel();
		aliceContacts.setLayout(new GridLayout(4, 1));
		JLabel contactsLabel = new JLabel("<HTML><U>Contacts:</U></HTML>");
		aliceBobRadioBtn = new JRadioButton("Bob");
		aliceJohnRadioBtn = new JRadioButton("John");
		aliceAdamRadioBtn = new JRadioButton("Adam");
		radioButtonsGroup.add(aliceBobRadioBtn);
		radioButtonsGroup.add(aliceJohnRadioBtn);
		radioButtonsGroup.add(aliceAdamRadioBtn);
		aliceContacts.add(contactsLabel);
		aliceContacts.add(aliceBobRadioBtn);
		aliceContacts.add(aliceJohnRadioBtn);
		aliceContacts.add(aliceAdamRadioBtn);
		// ****text panel
		JPanel textsPanel = new JPanel();
		textsPanel.setLayout(new GridLayout(2, 1, 3, 5));
		JScrollPane scrollPanel1, scrollPanel2;
		aliceSendText = new JTextArea(10, 30);
		aliceReceieveText = new JTextArea(10, 30);
		textsPanel.add(aliceSendText);
		textsPanel.add(aliceReceieveText);
		scrollPanel1 = new JScrollPane(aliceSendText);
		scrollPanel2 = new JScrollPane(aliceReceieveText);
		textsPanel.add(scrollPanel1);
		textsPanel.add(scrollPanel2);
		alicePanel.setLayout(new FlowLayout());
		alicePanel.add(aliceContacts);
		alicePanel.add(textsPanel);
		// Set the line wrap and word wrap to true for the frame
		aliceSendText.setLineWrap(true);
		aliceSendText.setWrapStyleWord(true);
		aliceSendButton = new JButton("Send");
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if (aliceBobRadioBtn.isSelected() || aliceJohnRadioBtn.isSelected() || aliceAdamRadioBtn.isSelected()) {
					// VerifySenderFrame senderFrame = new VerifySenderFrame();
					String str = event.getActionCommand();
					System.out.println("Clicked = " + str);
					System.out.println(aliceSendText.getText());
					System.out.println(aliceBobRadioBtn.getText());
					int selectedContact = getAliceSelectedContact();
					Customer selectedCustomer = getSelectedCustomer();
					if (selectedCustomer == null)
						return;
					// send ecrypted message
					String plainMessage = aliceSendText.getText().toString();
					byte[] textInBytes = plainMessage.getBytes();
					byte[] encryptedMsg = alice.encryptMesssageTEA(plainMessage);
					BigInteger msgSignature = alice.signMessage(encryptedMsg);
					sendMessageToCustomer(encryptedMsg, msgSignature, selectedCustomer, selectedContact, alice);
					selectionModel.setSelectedIndex(selectedContact);
				}

			}

			private Customer getSelectedCustomer() {
				Customer selectedCustomer = null;
				selectedCustomer = (aliceBobRadioBtn.isSelected()) ? bob : selectedCustomer;
				selectedCustomer = (aliceJohnRadioBtn.isSelected()) ? john : selectedCustomer;
				selectedCustomer = (aliceAdamRadioBtn.isSelected()) ? adam : selectedCustomer;
				return selectedCustomer;
			}

			private int getAliceSelectedContact() {
				// TODO Auto-generated method stub
				int selectedIndex = 0;
				selectedIndex = (aliceBobRadioBtn.isSelected()) ? 1 : selectedIndex;
				selectedIndex = (aliceJohnRadioBtn.isSelected()) ? 2 : selectedIndex;
				selectedIndex = (aliceAdamRadioBtn.isSelected()) ? 3 : selectedIndex;
				return selectedIndex;
			}
		};
		aliceSendButton.addActionListener(actionListener);
		alicePanel.add(aliceSendButton);
	}

	public static void createBobPanel() {
		ButtonGroup radioButtonsGroup = new ButtonGroup();
		// Contacts panel
		bobContacts = new JPanel();
		bobContacts.setLayout(new GridLayout(4, 1));
		JLabel contactsLabel = new JLabel("<HTML><U>Contacts:</U></HTML>");
		bobAliceRadioBtn = new JRadioButton("Alice");
		bobJohnRadioBtn = new JRadioButton("John");
		bobAdamRadioBtn = new JRadioButton("Adam");
		radioButtonsGroup.add(bobAliceRadioBtn);
		radioButtonsGroup.add(bobJohnRadioBtn);
		radioButtonsGroup.add(bobAdamRadioBtn);
		bobContacts.add(contactsLabel);
		bobContacts.add(bobAliceRadioBtn);
		bobContacts.add(bobJohnRadioBtn);
		bobContacts.add(bobAdamRadioBtn);
		// text panel
		JPanel textsPanel = new JPanel();
		textsPanel.setLayout(new GridLayout(2, 1, 3, 5));
		JScrollPane scrollPanel1, scrollPanel2;
		bobSendText = new JTextArea(10, 30);
		bobReceieveText = new JTextArea(10, 30);
		textsPanel.add(bobSendText);
		textsPanel.add(bobReceieveText);
		scrollPanel1 = new JScrollPane(bobSendText);
		scrollPanel2 = new JScrollPane(bobReceieveText);
		textsPanel.add(scrollPanel1);
		textsPanel.add(scrollPanel2);
		bobPanel.setLayout(new FlowLayout());
		bobPanel.add(bobContacts);
		bobPanel.add(textsPanel);
		// Set the line wrap and word wrap to true for the frame
		bobSendText.setLineWrap(true);
		bobSendText.setWrapStyleWord(true);
		bobSendButton = new JButton("Send");
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if (bobAliceRadioBtn.isSelected() || bobJohnRadioBtn.isSelected() || bobAdamRadioBtn.isSelected()) {
					// VerifySenderFrame senderFrame = new VerifySenderFrame();
					String str = event.getActionCommand();
					System.out.println("Clicked = " + str);
					System.out.println(bobSendText.getText());
					System.out.println(aliceBobRadioBtn.getText());
					int selectedContact = getBobSelectedContact();
					Customer selectedCustomer = getSelectedCustomer();
					if (selectedCustomer == null)
						return;
					// send ecrypted message
					String plainMessage = bobSendText.getText().toString();
					byte[] encryptedMsg = bob.encryptMesssageTEA(plainMessage);
					BigInteger msgSignature = bob.signMessage(encryptedMsg);
					sendMessageToCustomer(encryptedMsg, msgSignature, selectedCustomer, selectedContact, bob);
					selectionModel.setSelectedIndex(selectedContact);
				}

			}

			private Customer getSelectedCustomer() {
				Customer selectedCustomer = null;
				selectedCustomer = (bobAliceRadioBtn.isSelected()) ? alice : selectedCustomer;
				selectedCustomer = (bobJohnRadioBtn.isSelected()) ? john : selectedCustomer;
				selectedCustomer = (bobAdamRadioBtn.isSelected()) ? adam : selectedCustomer;
				return selectedCustomer;
			}

			private int getBobSelectedContact() {
				// TODO Auto-generated method stub
				int selectedIndex = 1;
				selectedIndex = (bobAliceRadioBtn.isSelected()) ? 0 : selectedIndex;
				selectedIndex = (bobJohnRadioBtn.isSelected()) ? 2 : selectedIndex;
				selectedIndex = (bobAdamRadioBtn.isSelected()) ? 3 : selectedIndex;
				return selectedIndex;
			}
		};
		bobSendButton.addActionListener(actionListener);
		bobPanel.add(bobSendButton);
	}

	public static void createJohnPanel() {
		ButtonGroup radioButtonsGroup = new ButtonGroup();
		// ****Contacts panel
		jhonContacts = new JPanel();
		jhonContacts.setLayout(new GridLayout(4, 1));
		JLabel contactsLabel = new JLabel("<HTML><U>Contacts:</U></HTML>");
		jhonAliceRadioBtn = new JRadioButton("Alice");
		jhonBobRadioBtn = new JRadioButton("Bob");
		jhonAdamRadioBtn = new JRadioButton("Adam");
		radioButtonsGroup.add(jhonAliceRadioBtn);
		radioButtonsGroup.add(jhonBobRadioBtn);
		radioButtonsGroup.add(jhonAdamRadioBtn);
		jhonContacts.add(contactsLabel);
		jhonContacts.add(jhonAliceRadioBtn);
		jhonContacts.add(jhonBobRadioBtn);
		jhonContacts.add(jhonAdamRadioBtn);
		// ****text panel
		JPanel textsPanel = new JPanel();
		textsPanel.setLayout(new GridLayout(2, 1, 3, 5));
		JScrollPane scrollPanel1, scrollPanel2;
		jhonSendText = new JTextArea(10, 30);
		jhonReceieveText = new JTextArea(10, 30);
		textsPanel.add(jhonSendText);
		textsPanel.add(jhonReceieveText);
		scrollPanel1 = new JScrollPane(jhonSendText);
		scrollPanel2 = new JScrollPane(jhonReceieveText);
		textsPanel.add(scrollPanel1);
		textsPanel.add(scrollPanel2);
		jhonPanel.setLayout(new FlowLayout());
		jhonPanel.add(jhonContacts);
		jhonPanel.add(textsPanel);
		// Set the line wrap and word wrap to true for the frame
		jhonSendText.setLineWrap(true);
		jhonSendText.setWrapStyleWord(true);
		jhonSendButton = new JButton("Send");
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if (jhonAliceRadioBtn.isSelected() || jhonBobRadioBtn.isSelected() || jhonAdamRadioBtn.isSelected()) {
					// VerifySenderFrame senderFrame = new VerifySenderFrame();
					String str = event.getActionCommand();
					System.out.println("Clicked = " + str);
					System.out.println(bobSendText.getText());
					System.out.println(aliceBobRadioBtn.getText());
					int selectedContact = getJohnSelectedContact();
					Customer selectedCustomer = getSelectedContact();
					if (selectedCustomer == null)
						return;
					// send ecrypted message
					String plainMessage = jhonSendText.getText().toString();
					byte[] encryptedMsg = john.encryptMesssageTEA(plainMessage);
					BigInteger msgSignature = john.signMessage(encryptedMsg);
					sendMessageToCustomer(encryptedMsg, msgSignature, selectedCustomer, selectedContact, john);
					selectionModel.setSelectedIndex(selectedContact);
				}

			}

			private Customer getSelectedContact() {
				Customer selectedCustomer = null;
				selectedCustomer = (jhonAliceRadioBtn.isSelected()) ? alice : selectedCustomer;
				selectedCustomer = (jhonBobRadioBtn.isSelected()) ? bob : selectedCustomer;
				selectedCustomer = (jhonAdamRadioBtn.isSelected()) ? adam : selectedCustomer;
				return selectedCustomer;
			}

			private int getJohnSelectedContact() {
				// TODO Auto-generated method stub
				int selectedIndex = 2;
				selectedIndex = (jhonAliceRadioBtn.isSelected()) ? 0 : selectedIndex;
				selectedIndex = (jhonBobRadioBtn.isSelected()) ? 1 : selectedIndex;
				selectedIndex = (jhonAdamRadioBtn.isSelected()) ? 3 : selectedIndex;
				return selectedIndex;
			}
		};
		jhonSendButton.addActionListener(actionListener);
		jhonPanel.add(jhonSendButton);
	}

	public static void createAdamPanel() {
		ButtonGroup radioButtonsGroup = new ButtonGroup();
		// ****Contacts panel
		adamContacts = new JPanel();
		adamContacts.setLayout(new GridLayout(4, 1));
		JLabel contactsLabel = new JLabel("<HTML><U>Contacts:</U></HTML>");
		adamAliceRadioBtn = new JRadioButton("Alice");
		adamBobRadioBtn = new JRadioButton("Bob");
		adamJohnRadioBtn = new JRadioButton("John");
		radioButtonsGroup.add(adamAliceRadioBtn);
		radioButtonsGroup.add(adamBobRadioBtn);
		radioButtonsGroup.add(adamJohnRadioBtn);
		adamContacts.add(contactsLabel);
		adamContacts.add(adamAliceRadioBtn);
		adamContacts.add(adamBobRadioBtn);
		adamContacts.add(adamJohnRadioBtn);
		// ****text panel
		JPanel textsPanel = new JPanel();
		textsPanel.setLayout(new GridLayout(2, 1, 3, 5));
		JScrollPane scrollPanel1, scrollPanel2;
		adamSendText = new JTextArea(10, 30);
		adamReceieveText = new JTextArea(10, 30);
		textsPanel.add(adamSendText);
		textsPanel.add(adamReceieveText);
		scrollPanel1 = new JScrollPane(adamSendText);
		scrollPanel2 = new JScrollPane(adamReceieveText);
		textsPanel.add(scrollPanel1);
		textsPanel.add(scrollPanel2);
		adamPanel.setLayout(new FlowLayout());
		adamPanel.add(adamContacts);
		adamPanel.add(textsPanel);
		// Set the line wrap and word wrap to true for the frame
		adamSendText.setLineWrap(true);
		adamSendText.setWrapStyleWord(true);
		adamSendButton = new JButton("Send");
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if (adamAliceRadioBtn.isSelected() || adamBobRadioBtn.isSelected() || adamJohnRadioBtn.isSelected()) {
					// VerifySenderFrame senderFrame = new VerifySenderFrame();
					String str = event.getActionCommand();
					System.out.println("Clicked = " + str);
					System.out.println(bobSendText.getText());
					System.out.println(aliceBobRadioBtn.getText());
					int selectedContact = getAdamSelectedContact();
					Customer selectedCustomer = getSelectedContact();
					if (selectedCustomer == null)
						return;
					// send ecrypted message
					String plainMessage = adamSendText.getText().toString();
					byte[] encryptedMsg = adam.encryptMesssageTEA(plainMessage);
					BigInteger msgSignature = adam.signMessage(encryptedMsg);
					sendMessageToCustomer(encryptedMsg, msgSignature, selectedCustomer, selectedContact, adam);
					selectionModel.setSelectedIndex(selectedContact);
				}

			}

			private Customer getSelectedContact() {
				Customer selectedCustomer = null;
				selectedCustomer = (adamAliceRadioBtn.isSelected()) ? alice : selectedCustomer;
				selectedCustomer = (adamBobRadioBtn.isSelected()) ? bob : selectedCustomer;
				selectedCustomer = (adamJohnRadioBtn.isSelected()) ? john : selectedCustomer;
				return selectedCustomer;
			}

			private int getAdamSelectedContact() {
				// TODO Auto-generated method stub
				int selectedIndex = 3;
				selectedIndex = (adamAliceRadioBtn.isSelected()) ? 0 : selectedIndex;
				selectedIndex = (adamBobRadioBtn.isSelected()) ? 1 : selectedIndex;
				selectedIndex = (adamJohnRadioBtn.isSelected()) ? 2 : selectedIndex;
				return selectedIndex;
			}
		};
		adamSendButton.addActionListener(actionListener);
		adamPanel.add(adamSendButton);
	}

	public static void initLayout() {
		alicePanel = new JPanel();
		bobPanel = new JPanel();
		jhonPanel = new JPanel();
		adamPanel = new JPanel();
		mutualKeyPanel = new JPanel(new FlowLayout());
		mutualKey = new JTextArea(3, 40);
		mutualKey.setEditable(false);
		JScrollPane keyScroll = new JScrollPane(mutualKey);
		mutualKeyPanel.add(new JLabel("Mutual key:"));
		mutualKeyPanel.add(keyScroll);
		createAlicePanel();
		createBobPanel();
		createJohnPanel();
		createAdamPanel();
		tabbedPane.addTab("Alice", alicePanel);
		tabbedPane.addTab("Bob ", bobPanel);
		tabbedPane.addTab("John", jhonPanel);
		tabbedPane.addTab("Adam ", adamPanel);
		frame.setLayout(new FlowLayout());
		frame.add(tabbedPane);
		// frame.add(mutualKeyPanel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600, 500);
		frame.setResizable(false);
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		int bits = 1024;
		// prepare RSA signature
		SecureRandom random = new SecureRandom();
		BigInteger e = new BigInteger("3");// consider it to be equal to 3;
		BigInteger firstNumber = new BigInteger(bits / 2, 100, random);// first
		BigInteger secondNumber = new BigInteger(bits / 2, 100, random);// second
		BigInteger phi = (firstNumber.subtract(new BigInteger("1")))
				.multiply(secondNumber.subtract(new BigInteger("1")));
		while (phi.gcd(e).intValue() > 1) {
			e = e.add(new BigInteger("2"));
		}
		alice = new Customer("Alice", firstNumber, secondNumber, e, new CryptoHash());
		bob = new Customer("Bob", firstNumber, secondNumber, e, new CryptoHash());
		john = new Customer("John", firstNumber, secondNumber, e, new CryptoHash());
		adam = new Customer("Adam", firstNumber, secondNumber, e, new CryptoHash());
		// DeffieHelman numbers
		p = new BigInteger(512, 100, new Random());
		p = p.nextProbablePrime();
		BigInteger a = generateUniform(p);
		g = p.subtract(a.multiply(a).mod(p));
		generateMutualKey(alice, bob, john, adam);
		initLayout();

	}

	/**
	 * Generate mutual key based on Deffie helman
	 * 
	 * @param alice
	 * @param bob
	 * @param john
	 * @param adam
	 */
	public static void generateMutualKey(Customer alice, Customer bob, Customer john, Customer adam) {
		BigInteger alice_pub_key = alice.power(g, p);// Y_A=g^X_A
		BigInteger bob_pub_key = bob.power(g, p);// Y_B=g^X_B
		BigInteger john_pub_key = john.power(g, p);// Y_C=g^X_C
		BigInteger adam_pub_key = adam.power(g, p);// Y_D=g^X_D
		// Alice calculates Z_DA=(Y_D)^X_A
		BigInteger Z_DA = alice.power(adam_pub_key, p);// Z_DA=(Y_D)^X_A
		// bob calculates Z_CA=(Y_C)^X_A
		BigInteger Z_AB = bob.power(alice_pub_key, p);// Z_CA=(YC)^X_A
		// john calculates Z_CA=(Y_A)^X_B
		BigInteger Z_BC = john.power(bob_pub_key, p);// Z_CA=(YC)^X_A
		// adam calculates Z_BC=(Y_B)^X_C
		BigInteger Z_CD = adam.power(john_pub_key, p);// Z_CA=(YC)^X_A
		// ONE MORE SENDING STEP

		BigInteger Z_DAA = alice.power(Z_CD, p);// Z_DA=(Y_D)^X_A
												// A calculates Z_CA=(Y_C)^X_A
		BigInteger Z_ABB = bob.power(Z_DA, p);// Z_CA=(YC)^X_A
												// B calculates Z_CA=(Y_A)^X_B
		BigInteger Z_BCC = john.power(Z_AB, p);// Z_CA=(YC)^X_A
												// D calculates Z_BC=(Y_B)^X_C
		BigInteger Z_CDC = adam.power(Z_BC, p);// Z_CA=(YC)^X_A

		// KEYS GENERATED
		BigInteger key_generated_at_alice_site = alice.power(Z_CDC, p);
		BigInteger key_generated_at_bob_site = bob.power(Z_DAA, p);
		BigInteger key_generated_at_john_site = john.power(Z_ABB, p);
		BigInteger key_generated_at_adam_site = adam.power(Z_BCC, p);
		alice.setMutualKey(key_generated_at_alice_site);
		bob.setMutualKey(key_generated_at_bob_site);
		john.setMutualKey(key_generated_at_john_site);
		adam.setMutualKey(key_generated_at_adam_site);
		System.out.println(key_generated_at_alice_site.equals(key_generated_at_bob_site)
				&& key_generated_at_bob_site.equals(key_generated_at_john_site)
				&& key_generated_at_john_site.equals(key_generated_at_adam_site));
		System.out.println(p);
		System.out.println(g);
		System.out.println("key:" + key_generated_at_john_site.toString());
		System.out.println("bits:" + key_generated_at_john_site.toString().getBytes().length);
	}

	/**
	 * Method that generates a number uniformly chosen between 2 and n - 2.
	 */
	public static BigInteger generateUniform(BigInteger n) {
		BigInteger TWO = new BigInteger("2");
		BigInteger FOUR = new BigInteger("4");
		Random RND = new Random();
		n = n.subtract(FOUR);
		BigInteger a;
		do {
			a = new BigInteger(n.bitLength(), RND);
		} while (a.compareTo(n) >= 0);
		return a.add(TWO);
	}

	/**
	 * 
	 * @param message
	 * @param signature
	 * @param receiveCustomer
	 * @param selectionIndex
	 * @param sendingCustomer
	 */
	public static void sendMessageToCustomer(byte[] message, BigInteger signature, Customer receiveCustomer,
			int selectionIndex, Customer sendingCustomer) {
		String receivedMsg = receiveCustomer.receiveMessage(message, signature);
		if (receivedMsg == null) {
			JOptionPane.showMessageDialog(null, receiveCustomer.name + " did not accept signature", "InfoBox: ",
					JOptionPane.ERROR_MESSAGE);
			return;

		}
		JOptionPane.showMessageDialog(null, receiveCustomer.name + " accepted signature", "InfoBox: ",
				JOptionPane.INFORMATION_MESSAGE);
		switch (selectionIndex) {
		case 0:
			aliceReceieveText.append(sendingCustomer.name + " : " + receivedMsg + "\n");
			break;
		case 1:
			bobReceieveText.append(sendingCustomer.name + " : " + receivedMsg + "\n");
			break;
		case 2:
			jhonReceieveText.append(sendingCustomer.name + " : " + receivedMsg + "\n");
			break;
		case 3:
			adamReceieveText.append(sendingCustomer.name + " : " + receivedMsg + "\n");
			break;
		}
	}
}
