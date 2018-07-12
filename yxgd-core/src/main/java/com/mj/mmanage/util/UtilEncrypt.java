package com.mj.mmanage.util;



import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


/**
 * 加/解密工具类
 * 
 * @author LiAnDong
 * @version 1.0, 2012-8-3
 */
public class UtilEncrypt {

	private static Logger logger = LogManager.getLogger(UtilEncrypt.class);
	public synchronized static String md5Encrypt(String text, String charsetName) {
		return MD5.encrypt(text, charsetName);
	}

	private static class MD5 {

		private static MessageDigest digest = null;

		public static String encrypt(String text, String charsetName) {
			if (digest == null) {
				try {
					digest = MessageDigest.getInstance("MD5");
				} catch (NoSuchAlgorithmException ex) {
					logger.error("Failed to load the MD5 MessageDigest. "
							+ "Logistic will be unable to function normally.",ex);
				}
			}
			if (text == null) {
				text = "";
			}
			try {
				if (charsetName.isEmpty()) {
					digest.update(text.getBytes());
				} else {
					digest.update(text.getBytes(charsetName));
				}
			} catch (UnsupportedEncodingException e) {
				logger.error(e.getMessage());
			}
			return byteToHex(digest.digest());
		}
	}

	//对字节串返回16进制
	private static final String byteToHex(byte[] hash) {
		StringBuffer buf = new StringBuffer(hash.length * 2);
		int i = 0;
		for (i = 0; i < hash.length; i++) {
			if (((int) hash[i] & 0xff) < 0x10) {
				buf.append("0");
			}
			buf.append(Long.toString((int) hash[i] & 0xff, 16));
		}
		return buf.toString();
	}

	private static class AES {

		public synchronized static String encrypt(String pwd, String text) throws Exception {
			return null;
		}

	}
public static void main(String[] args) {
	System.out.println(md5Encrypt("123456",Constants.CODE_TYPE));
}
}
