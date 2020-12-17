
public class TEA {

	private final static int DELTA = 0x9E3779B9;
	private final static int CUPS = 32;
	private final static int SUM = 0xC6EF3720;

	private int[] S = new int[4];

	/**
	 * Initialize the cipher for encryption or decryption.
	 * 
	 * @param key a 16 byte (128-bit) key
	 */
	public TEA(byte[] key) {
		if (key == null)
			throw new RuntimeException("Invalid key: Key was null");
		if (key.length < 16)
			throw new RuntimeException("Invalid key: Length was less than 16 bytes");
		//create private key for encryption
		for (int off = 0, i = 0; i < 4; i++) {
			S[i] = ((key[off++] & 0xff)) | ((key[off++] & 0xff) << 8) | ((key[off++] & 0xff) << 16)
					| ((key[off++] & 0xff) << 24);
		}
	}

	/**
	 * Encrypt an array of bytes.
	 * 
	 * @param clear the clear text to encrypt
	 * @return the encrypted text
	 */
	public byte[] encrypt(byte[] clear) {
		int paddedSize = ((clear.length / 8) + (((clear.length % 8) == 0) ? 0 : 1)) * 2;
		int[] buffer = new int[paddedSize + 1];
		buffer[0] = clear.length;
		pack(clear, buffer, 1);//prepare for encryption
		brew(buffer);
		return unpack(buffer, 0, buffer.length * 4);
	}

	/**
	 * Decrypt an array of bytes.
	 * 
	 * @param ciper the cipher text to decrypt
	 * @return the decrypted text
	 */
	public byte[] decrypt(byte[] crypt) {
		assert crypt.length % 4 == 0;
		assert (crypt.length / 4) % 2 == 1;
		int[] buffer = new int[crypt.length / 4];
		pack(crypt, buffer, 0);
		unbrew(buffer);
		return unpack(buffer, 1, buffer[0]);
	}
	//encrypt
	void brew(int[] buf) {
		assert buf.length % 2 == 1;
		int i, v0, v1, sum, n;
		i = 1;
		while (i < buf.length) {
			n = CUPS;
			v0 = buf[i];
			v1 = buf[i + 1];
			sum = 0;
			while (n-- > 0) {
				sum += DELTA;
				v0 += ((v1 << 4) + S[0] ^ v1) + (sum ^ (v1 >>> 5)) + S[1];
				v1 += ((v0 << 4) + S[2] ^ v0) + (sum ^ (v0 >>> 5)) + S[3];
			}
			buf[i] = v0;
			buf[i + 1] = v1;
			i += 2;
		}
	}
	//decrypt
	void unbrew(int[] buf) {
		assert buf.length % 2 == 1;
		int i, v0, v1, sum, n;
		i = 1;
		while (i < buf.length) {
			n = CUPS;
			v0 = buf[i];
			v1 = buf[i + 1];
			sum = SUM;
			while (n-- > 0) {
				v1 -= ((v0 << 4) + S[2] ^ v0) + (sum ^ (v0 >>> 5)) + S[3];
				v0 -= ((v1 << 4) + S[0] ^ v1) + (sum ^ (v1 >>> 5)) + S[1];
				sum -= DELTA;
			}
			buf[i] = v0;
			buf[i + 1] = v1;
			i += 2;
		}
	}

	void pack(byte[] src, int[] dest, int destOffset) {
		assert destOffset + (src.length / 4) <= dest.length;
		int i = 0, shift = 24;
		int j = destOffset;
		dest[j] = 0;
		while (i < src.length) {
			dest[j] |= ((src[i] & 0xff) << shift);
			if (shift == 0) {
				shift = 24;
				j++;
				if (j < dest.length)
					dest[j] = 0;
			} else {
				shift -= 8;
			}
			i++;
		}
	}

	byte[] unpack(int[] src, int srcOffset, int destLength) {
		assert destLength <= (src.length - srcOffset) * 4;
		byte[] dest = new byte[destLength];
		int i = srcOffset;
		int count = 0;
		for (int j = 0; j < destLength; j++) {
			dest[j] = (byte) ((src[i] >> (24 - (8 * count))) & 0xff);
			count++;
			if (count == 4) {
				count = 0;
				i++;
			}
		}
		return dest;
	}

	// Simple usage example //
	//public static String quote = "Now rise, and show your strength. Be eloquent, and deep, and tender; see, with a clear eye, into Nature, and into life:  spread your white wings of quivering thought, and soar, a god-like spirit, over the whirling world beneath you, up through long lanes of flaming stars to the gates of eternity!";
	public static String quote="What is going on here";
	public static void main(String[] args) {
		//Create a cipher using the first 16 bytes of the pass phrase 
		String teaKey="And is there honey still for tea?";
		byte[] teaTestKey=teaKey.getBytes();
		byte[] teaByte= {0, -77, 126, 41, -86, 6, 9, -79, 68, -27, 50, 119, 24, -84, -122, 14, -28, -116, -128, 103, 113, -60, -93, 44, -51, 59, 13, 8, 78, -67, -84, 77, -62, 58, 32, 82, -9, -93, 98, 52, 5, 88, -89, 118, -97, 64, 92, 75, -42, -38, 105, -51, -9, -104, 109, 31, -69, -114, 19, 36, -96, -79, -3, 121, -11};
		TEA tea = new TEA(teaByte);

		byte[] original = quote.getBytes();

		// Run it through the cipher... and back 
		byte[] crypt = tea.encrypt(original);
		byte[] result = tea.decrypt(crypt);

		// Ensure that all went well 
		String encryptionResult=new String(crypt);
		String decriptionResult = new String(result);
		if (!decriptionResult.equals(quote))
			throw new RuntimeException("Fail");
		System.out.println("Encryption:"+encryptionResult);
		System.out.println("Decryption:"+decriptionResult);
		
	}
}
