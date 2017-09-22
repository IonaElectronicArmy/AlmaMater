package com.rosalind.framework.util;



import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.rosalind.framework.Constants;


/**
 * 
 * @author Sajin_K
 *
 */
public class Security {
	private static Security security = new Security();
	private static Object mutex = new Object();
	private static SecretKey key;
	String strkey = "MY KEY";
	private Cipher encryptCipher;
	private Cipher decryptCipher;

	/**
	 * 
	 * @return
	 */
	public static Security getSecurity() {
		return security;
	}

	/**
	 * 
	 */
	private Security() {
		;
	}

	/**
	 * 
	 * @return
	 */
	public synchronized SecretKey getKey() throws RosalindException {
		if (key == null) {			
			try {
				key = new SecretKeySpec(strkey.getBytes("UTF-8"), "HmacSHA256");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				throw new RosalindException(e);
			}
		}
		return key;
	}

	/**
	 * 
	 * @param data
	 * @return
	 * @throws RTBMException
	 */
	public String encrypt(String data) throws RosalindException {
		synchronized (mutex) {
			Cipher cipher = getCipher(Constants.ENCRYPT_MODE);
			String encodedString = null;
			try {
				encodedString = Base64.getEncoder().encodeToString(cipher.doFinal(data.getBytes("UTF-8")));
			} catch (IllegalBlockSizeException e) {
				e.printStackTrace();
				throw new RosalindException(e);
			} catch (BadPaddingException e) {
				e.printStackTrace();
				throw new RosalindException(e);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				throw new RosalindException(e);
			}
			return encodedString;
		}
	}

	/**
	 * 
	 * @param data
	 * @return
	 * @throws RTBMException
	 */
	public String decrypt(String data) throws RosalindException {
		synchronized (mutex) {
			Cipher cipher = getCipher(Constants.DECRYPT_MODE);
			String decryptedString = null;
			try {
				decryptedString = new String(cipher.doFinal(Base64.getDecoder().decode(data.getBytes("UTF-8"))));
			} catch (IllegalBlockSizeException e) {
				e.printStackTrace();
				throw new RosalindException(e);
			} catch (BadPaddingException e) {
				e.printStackTrace();
				throw new RosalindException(e);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				throw new RosalindException(e);
			}
			return decryptedString;
		}
	}

	/**
	 * 
	 * @param constant
	 * @return
	 * @throws RTBMException
	 */
	private Cipher getCipher(Constants constant) throws RosalindException {
		synchronized (mutex) {
			if (Constants.ENCRYPT_MODE == constant) {
				if (encryptCipher == null) {
					try {
						SecretKeySpec secretkey = new SecretKeySpec(strkey.getBytes("UTF-8"), "Blowfish");
						encryptCipher = Cipher.getInstance("Blowfish");
						encryptCipher.init(Cipher.ENCRYPT_MODE, secretkey);
					} catch (InvalidKeyException e) {
						e.printStackTrace();
						throw new RosalindException(e);
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
						throw new RosalindException(e);
					} catch (NoSuchAlgorithmException e) {
						e.printStackTrace();
						throw new RosalindException(e);
					} catch (NoSuchPaddingException e) {
						e.printStackTrace();
						throw new RosalindException(e);
					}
				}
				return encryptCipher;
			} else if (Constants.DECRYPT_MODE == constant) {
				if (decryptCipher == null) {
					try {
						SecretKeySpec secretkey = new SecretKeySpec(strkey.getBytes("UTF-8"), "Blowfish");
						decryptCipher = Cipher.getInstance("Blowfish");
						decryptCipher.init(Cipher.DECRYPT_MODE, secretkey);
					} catch (InvalidKeyException e) {
						e.printStackTrace();
						throw new RosalindException(e);
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
						throw new RosalindException(e);
					} catch (NoSuchAlgorithmException e) {
						e.printStackTrace();
						throw new RosalindException(e);
					} catch (NoSuchPaddingException e) {
						e.printStackTrace();
						throw new RosalindException(e);
					}
				}
				return decryptCipher;
			} else {
				throw new RosalindException("Encryption mode not supported");
			}

		}
	}

}

