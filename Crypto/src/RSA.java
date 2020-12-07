import java.math.BigInteger;
import java.security.SecureRandom;

public class RSA {
	/*private BigInteger n;
	private BigInteger d;
	private BigInteger e;// small exponent

	private PrivateKey privateKey;
	private PublicKey publicKey;

	public static class PublicKey {
		public byte[] getE() {
			return e;
		}

		public byte[] getN() {
			return n;
		}

		private final byte[] e;
		private final byte[] n;

		public PublicKey(byte[] e, byte[] n) {
			this.e = e;
			this.n = n;
		}
	}

	public static class PrivateKey {
		public byte[] getD() {
			return d;
		}

		public byte[] getN() {
			return n;
		}

		private final byte[] d;
		private final byte[] n;

		public PrivateKey(byte[] n, byte[] d) {
			this.n = n;
			this.d = d;
		}
	}

	public RSA() {
		generateKeyPair(1024);
	}

	public RSA(int bits) {
		generateKeyPair(bits);
	}

	public final void generateKeyPair(int bits) {

		SecureRandom random = new SecureRandom();

		BigInteger p = new BigInteger(bits / 2, 100, random);// first prime number
		BigInteger q = new BigInteger(bits / 2, 100, random);// second prime number
		BigInteger phi = (p.subtract(new BigInteger("1"))).multiply(q.subtract(new BigInteger("1")));// phi(n) =
																										// (P-1)(Q-1)

		n = p.multiply(q);
		e = new BigInteger("3");// consider it to be equal to 3

		while (phi.gcd(e).intValue() > 1) {
			e = e.add(new BigInteger("2"));
		}

		System.out.println(e.toString());
		System.out.println(n.toString());

		d = e.modInverse(phi);// d = (k*phi(n) + 1) / e for some integer k
		publicKey = new PublicKey(e.toByteArray(), n.toByteArray());// Public Key is made of n and e
		privateKey = new PrivateKey(d.toByteArray(), n.toByteArray());
	}

	public byte[] encrypt(byte[] plainData, PublicKey publicKey) {
		return ((new BigInteger(plainData)).modPow(new BigInteger(publicKey.getE()), new BigInteger(publicKey.getN())))
				.toByteArray();
	}

	public byte[] decrypt(byte[] encryptedData) {
		return ((new BigInteger(encryptedData)).modPow(d, n)).toByteArray();//using the private key pair
	}

	// (d, n)
	public PrivateKey getPrivateKey() {
		return privateKey;
	}

	// (e, n)
	public PublicKey getPublicKey() {
		return publicKey;
	}
	
	/********************************************/
	/**
	 * Signs the message and returns the digital signature
	 * 
	 * @param message bytes of message
	 * @return digital signature
	 */
	/*public BigInteger sign(byte[] message) {
		//System.out.println("Message hash:"+CryptoHash.getHash(message));
		//BigInteger hash = CryptoHash.getIntHash(message);
		//return SignatureMath.power(hash, privateKey.getD(), privateKey.getR()); // return hash^d mod r
		//return SignatureMath.power(hash, new BigInteger(privateKey.getD()), new BigInteger(privateKey.getN())); // return hash^d mod r
	}*/
	
	/**
	 * Verifies signature validity
	 * 
	 * @param key          public key of the user who should own the signature
	 * @param s            digital signature of user
	 * @param message      bytes of message
	 * @param hashFunction The object to get the hash function (Must implement the
	 *                     interface CryptoHash)
	 * @return True if the signature is correct
	 */
	/*public boolean checkSignature(BigInteger s, byte[] message) {	
		//BigInteger hashFromSign = SignatureMath.power(s, new BigInteger(publicKey.getE()), new BigInteger(publicKey.getN()));
		//BigInteger actualHash = CryptoHash.getIntHash(message);
		//return hashFromSign.equals(actualHash);
	}*/
}
