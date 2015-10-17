package net.tokyo_ct.meister2015.jellyfish.test;

import static org.junit.Assert.*;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.mail.Message;
import javax.mail.MessagingException;

import net.tokyo_ct.meister2015.jellyfish.mail.MailReceive;
import net.tokyo_ct.meister2015.jellyfish.mail.MailReceivedListener;

import org.junit.Test;

public class MailTest {

	@Test
	public void test() {
		try {
			MailReceive mr = new MailReceive("imap.gmail.com", 993,
					"jelly.fish.meister.big.daddy@gmail.com",
					MailReceive.encrypt("PASS"), "INBOX");
			mr.setListener(new MailReceivedListener() {

				@Override
				public void mailReceived(Message m) {
					try {
						System.out.println(m.getFrom());
					} catch (MessagingException e) {
						// TODO 自動生成された catch ブロック
						e.printStackTrace();
					}

				}

			});
			for (int i = 0; i < 10; i++) {
				System.out.println("Connect");
				mr.getMails();
				Thread.sleep(600000);//10分待つ
			}
		} catch (InvalidKeyException | NoSuchAlgorithmException
				| NoSuchPaddingException | IllegalBlockSizeException
				| BadPaddingException | MessagingException
				| InterruptedException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}


}
