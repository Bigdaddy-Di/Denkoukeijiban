package net.tokyo_ct.meister2015.jellyfish.mail;

import javax.mail.Message;

public abstract class MailReceivedListener {
	public abstract void mailReceived(Message m);
}
