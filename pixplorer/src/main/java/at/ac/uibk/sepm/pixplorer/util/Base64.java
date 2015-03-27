package at.ac.uibk.sepm.pixplorer.util;

import java.util.Arrays;

/**
 * Own Implementation of Base64 converter due to missing class inside standard
 * Java package.
 */
class Base64 {
	private static final char[] CA = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();
	private static final int[] IA = new int[256];

	static {
		Arrays.fill(IA, -1);
		for (int i = 0, iS = CA.length; i < iS; i++) {
			IA[CA[i]] = i;
		}

		IA['='] = 0;
	}

	/**
	 * Private constructor because this is a pure utility class
	 */
	private Base64() {
	}

	/**
	 * Encodes a raw byte array into a BASE64 <code>char[]</code> representation
	 * i accordance with RFC 2045.
	 *
	 * @param sArr
	 *            The bytes to convert. If <code>null</code> or length 0 an
	 *            empty array will be returned.
	 *
	 * @return A BASE64 encoded array. Never <code>null</code>.
	 */
	public static final char[] encodeToChar(byte[] sArr) {
		// Check special case
		int sLen = sArr != null ? sArr.length : 0;
		if (sLen == 0) {
			return new char[0];
		}

		int eLen = (sLen / 3) * 3; // Length of even 24-bits.
		int cCnt = ((sLen - 1) / 3 + 1) << 2; // Returned character count
		int dLen = cCnt; // Length of returned array
		char[] dArr = new char[dLen];

		// Encode even 24-bits
		for (int s = 0, d = 0; s < eLen;) {
			// Copy next three bytes into lower 24 bits of int, paying attension
			// to sign.
			int i = (sArr[s++] & 0xff) << 16 | (sArr[s++] & 0xff) << 8 | (sArr[s++] & 0xff);

			// Encode the int into four chars
			dArr[d++] = CA[(i >>> 18) & 0x3f];
			dArr[d++] = CA[(i >>> 12) & 0x3f];
			dArr[d++] = CA[(i >>> 6) & 0x3f];
			dArr[d++] = CA[i & 0x3f];
		}

		// Pad and encode last bits if source isn't even 24 bits.
		int left = sLen - eLen; // 0 - 2.
		if (left > 0) {
			// Prepare the int
			int i = ((sArr[eLen] & 0xff) << 10) | (left == 2 ? ((sArr[sLen - 1] & 0xff) << 2) : 0);

			// Set last four chars
			dArr[dLen - 4] = CA[i >> 12];
			dArr[dLen - 3] = CA[(i >>> 6) & 0x3f];
			dArr[dLen - 2] = left == 2 ? CA[i & 0x3f] : '=';
			dArr[dLen - 1] = '=';
		}

		return dArr;
	}

	/**
	 * Encodes a raw byte array into a BASE64 <code>String</code> representation
	 * i accordance with RFC 2045.
	 *
	 * @param sArr
	 *            The bytes to convert. If <code>null</code> or length 0 an
	 *            empty array will be returned.
	 *
	 * @return A BASE64 encoded array. Never <code>null</code>.
	 */
	public static final String encodeToString(byte[] sArr) {
		// Reuse char[] since we can't create a String incrementally anyway and
		// StringBuffer/Builder would be slower.
		return new String(encodeToChar(sArr));
	}

	/**
	 * Decodes a BASE64 encoded <code>String</code>. All illegal characters will
	 * be ignored.
	 *
	 * @param str
	 *            The source string. <code>null</code> or length 0 will return
	 *            an empty array.
	 *
	 * @return The decoded array of bytes. May be of length 0. Will be
	 *         <code>null</code> if the legal characters (including '=') isn't
	 *         divideable by 4. (I.e. definitely corrupted).
	 */
	public static final byte[] decode(String str) {
		// Check special case
		int sLen = str != null ? str.length() : 0;
		if (sLen == 0) {
			return new byte[0];
		}

		// Check so that legal chars (including '=') are evenly divideable by 4
		// as specified in RFC 2045.
		if (sLen % 4 != 0) {
			return null; // NOSONAR
		}

		// Count '=' at end
		int pad = 0;
		for (int i = sLen; i > 1 && IA[str.charAt(--i)] <= 0;) {
			if (str.charAt(i) == '=') {
				pad++;
			}
		}

		int len = (sLen * 6 >> 3) - pad;
		byte[] dArr = new byte[len]; // Preallocate byte[] of exact length

		for (int s = 0, d = 0; d < len;) {
			// Assemble three bytes into an int from four "valid" characters.
			int i = 0;
			for (int j = 0; j < 4; j++) // j only increased if a valid char was
										// found.
			{
				int c = IA[str.charAt(s++)];
				if (c >= 0) {
					i |= c << (18 - j * 6);
				} else {
					j--;
				}
			}

			// Add the bytes
			dArr[d++] = (byte) (i >> 16);
			if (d < len) {
				dArr[d++] = (byte) (i >> 8);
				if (d < len) {
					dArr[d++] = (byte) i;
				}
			}
		}

		return dArr;
	}
}