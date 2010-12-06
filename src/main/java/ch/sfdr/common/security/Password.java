package ch.sfdr.common.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * password utilities
 * @author S.Freihofer
 */
public class Password
{
	static final byte[] HEX_CHARS = {
		'0', '1', '2', '3', '4', '5', '6', '7',
		'8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
	};

	/**
	 * returns the hex string from a byte array
	 * @param raw input bytes
	 * @return hex string
	 */
	public static String getHexString(byte[] raw)
	{
		byte[] hex = new byte[2 * raw.length];
		int i = 0;
		for (byte b : raw) {
			int v = b & 0xFF;
			hex[i++] = HEX_CHARS[v >>> 4];
			hex[i++] = HEX_CHARS[v & 0xF];
		}
		return new String(hex);
	}

	/**
	 * returns the hash for a password
	 * @param pw
	 * @return the SHA-1 hash as hex string
	 */
	public static String getPasswordHash(String pw)
	{
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("SHA1");
		} catch (NoSuchAlgorithmException e) {
			return "";
		}
		byte[] tmp = pw.getBytes();
		digest.update(tmp, 0, tmp.length);

		return getHexString(digest.digest());
	}
}
