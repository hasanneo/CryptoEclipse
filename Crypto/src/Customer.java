import java.math.BigInteger;
import java.security.SecureRandom;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;

public class Customer {
	private BigInteger privateKey;
	private BigInteger mutualKey;
	private SignatureRSA rsaSignature;
	public String name;
	public String decryptedMessage;
	private CryptoHash hashFunc;

	/**
	 * Generate private key for the customer
	 */
	public Customer(String name, BigInteger p, BigInteger q, BigInteger e, CryptoHash hashFunction) {
		SecureRandom random = new SecureRandom();
		int bits = 1024;
		privateKey = new BigInteger(bits / 2, 100, random);// first
		/*
		 * SecureRandom random = new SecureRandom(); int bits = 1024; privateKey = new
		 * BigInteger(bits / 2, 100, random);// first // prepare RSA signature
		 * SecureRandom random2 = new SecureRandom(); BigInteger e = new
		 * BigInteger("3");// consider it to be equal to 3; CryptoHash hashFunction =
		 * new CryptoHash(); BigInteger p = new BigInteger(bits / 2, 100, random2);//
		 * first BigInteger q = new BigInteger(bits / 2, 100, random2);// second
		 * BigInteger phi = (p.subtract(new BigInteger("1"))).multiply(q.subtract(new
		 * BigInteger("1"))); while (phi.gcd(e).intValue() > 1) { e = e.add(new
		 * BigInteger("2")); }
		 */
		rsaSignature = new SignatureRSA(p, q, e, hashFunction);
		this.name = name;
		hashFunc = hashFunction;
	}

	/**
	 * Modular exponentiation
	 *
	 * @param g number
	 * @param z power
	 * @param m module
	 * @return g^z mod m
	 */
	public BigInteger power(BigInteger a, BigInteger m) {
		BigInteger a1 = a;
		BigInteger z1 = privateKey;
		BigInteger x = BigInteger.ONE;
		while (!z1.equals(BigInteger.ZERO)) { // if z1 != 0
			while (z1.mod(BigInteger.valueOf(2)).equals(BigInteger.ZERO)) { // z % 2 = 0
				z1 = z1.divide(BigInteger.valueOf(2)); // z1 = z1 /2
				a1 = a1.multiply(a1).mod(m); // a1 = (a1 * a1) % m;
			}
			z1 = z1.add(BigInteger.valueOf(-1)); // z1 = z1 - 1;
			x = x.multiply(a1).mod(m); // x =(x * a1) % m;
		}
		return x;
	}

	public void setMutualKey(BigInteger key) {
		this.mutualKey = key;
	}

	public byte[] encryptMesssageTEA(String originalText) {
		byte[] teaKey = mutualKey.toByteArray();
		byte[] message = originalText.getBytes();
		TEA tea = new TEA(teaKey);
		byte[] encryptedMessage = tea.encrypt(message);
		return encryptedMessage;
	}

	public BigInteger signMessage(byte[] textToSign) {
		// sign the message
		return rsaSignature.sign(textToSign);
	}

	public String receiveMessage(byte[] message, BigInteger signature) {
		RSAPublicKey publicKey = rsaSignature.getPublicKey();
		if (!SignatureRSA.checkSignature(publicKey, signature, message, hashFunc)) {
			System.out.println(this.name + " didn't accept signature");
			return null;
		}
		// decrypt message
		byte[] teaKey = mutualKey.toByteArray();
		TEA tea = new TEA(teaKey);
		decryptedMessage = new String(tea.decrypt(message));
		return decryptedMessage;
	}
}
