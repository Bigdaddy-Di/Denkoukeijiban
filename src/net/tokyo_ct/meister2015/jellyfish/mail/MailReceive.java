package net.tokyo_ct.meister2015.jellyfish.mail;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Properties;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;

public class MailReceive {
	private String host;
	private int port;

	private String username;
	private byte[] password;
	private String targetFolder = "INBOX";

	private Date latestMailReceived = new Date();

	private MailReceivedListener listener;

	private final static byte[] key = "12345678910".getBytes();

	public static byte[] encrypt(String text) throws NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeyException,
			IllegalBlockSizeException, BadPaddingException {
		Cipher cipher = Cipher.getInstance("Blowfish");
		cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, "Blowfish"));
		return cipher.doFinal(text.getBytes());
	}

	public static String decrypt(byte[] b) throws NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeyException,
			IllegalBlockSizeException, BadPaddingException {
		Cipher cipher = Cipher.getInstance("Blowfish");
		cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, "Blowfish"));
		return new String(cipher.doFinal(b));
	}

	public MailReceive(String host, int port, String username, byte[] password,
			String targetFolder) {
		this.host = host;
		this.port = port;
		this.username = username;
		this.password = password;
		this.targetFolder = targetFolder;
	}

	public void setListener(MailReceivedListener listener) {
		this.listener = listener;
	}

	public void getMails() throws InvalidKeyException,
			NoSuchAlgorithmException, NoSuchPaddingException,
			IllegalBlockSizeException, BadPaddingException, MessagingException {
		if (listener == null) {
			throw new NullPointerException();
		}
		Properties props = System.getProperties();
		Session sess = Session.getInstance(props);

		Store st = sess.getStore("imaps");
		st.connect(host, port, username, decrypt(password));
		Folder folder = st.getFolder(targetFolder);

		if (folder.exists()) {
			folder.open(Folder.READ_ONLY);
			for (Message m : folder.getMessages()) {
				Date d = m.getReceivedDate();
				if (latestMailReceived.before(d)) {
					listener.mailReceived(m);
				}
			}

		}
	}
}
