package org.zxd.utils;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.Sha1Hash;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.crypto.hash.Sha384Hash;
import org.apache.shiro.crypto.hash.Sha512Hash;

import static java.util.Objects.requireNonNull;

public class CryptUtil {

	private static String MSG = "encrypt source not empty";

	public static final String base64Encrypt(String source) {
		requireNonNull(source, MSG);
		return Base64.encodeToString(source.getBytes());
	}

	public static final String base64Decrypt(String source) {
		requireNonNull(source, MSG);
		return Base64.decodeToString(source);
	}

	public static final String hexEncrypt(String source) {
		requireNonNull(source, MSG);
		return Hex.encodeToString(source.getBytes());
	}

	public static final String hexDecrypt(String source) {
		requireNonNull(source, MSG);
		return new String(Hex.decode(source));
	}

	public static final String md5Hash(String source, Object salt, int hashIterations) {
		return encrpyt(source, salt, hashIterations, () -> new Md5Hash(source, salt, hashIterations).toHex());
	}

	public static final String sha1Hash(String source, Object salt, int hashIterations) {
		return encrpyt(source, salt, hashIterations, () -> new Sha1Hash(source, salt, hashIterations).toHex());
	}

	public static final String sha256Hash(String source, Object salt, int hashIterations) {
		return encrpyt(source, salt, hashIterations, () -> new Sha256Hash(source, salt, hashIterations).toHex());
	}

	public static final String sha256Hash2048(String source, Object salt) {
		return sha256Hash(source, salt, 2048);
	}

	public static final String sha384Hash(String source, Object salt, int hashIterations) {
		return encrpyt(source, salt, hashIterations, () -> new Sha384Hash(source, salt, hashIterations).toHex());
	}

	public static final String sha512Hash(String source, Object salt, int hashIterations) {
		return encrpyt(source, salt, hashIterations, () -> new Sha512Hash(source, salt, hashIterations).toHex());
	}

	public static final String encryptPwd(String password, Object salt) {
		return sha256Hash(password, salt, 3);
	}

	private static final String encrpyt(String source, Object salt, int hashIterations, doCrypt doCrpyt) {
		requireNonNull(source, MSG);
		requireNonNull(salt, "salt not empty");
		requireNonNull(source, "hashIterations not empty");

		if (salt instanceof byte[] || salt instanceof char[] || salt instanceof String) {
			return doCrpyt.encrypt();
		}

		throw new IllegalArgumentException("");
	}

	static interface doCrypt {
		String encrypt();
	}
}
