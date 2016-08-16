package utils;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Service;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

public class EmailUtil {
	private static final Session EMAIL_SESSION = Session.getDefaultInstance(new Properties() {
		private static final long serialVersionUID = -3667579298765090300L;

		{
			this.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			this.setProperty("mail.smtp.socketFactory.fallback", "false");
			this.setProperty("mail.smtp.auth", "true");
		}
	});

	/**
	 * 
	 * @param host
	 *            邮箱地址 smtp.alibaba-inc.com
	 * @param port
	 *            端口 SSL 465
	 * @param account
	 *            发送者账号
	 * @param password
	 *            发送者密码
	 * @param address
	 *            接收者邮箱地址, 多个的话用逗号分隔
	 * @param title
	 *            邮件标题
	 * @param content
	 *            邮件内容
	 * @throws IOException
	 * @throws MessagingException
	 */
	public static void sendEmail(String host, int port, String account, String password, String address, String title, String content, List<File> attechmentList) throws IOException, MessagingException {
		Transport transport = null;
		if (null != address && address.trim().length() > 0) {
			try {
				MimeMessage message = new MimeMessage(EMAIL_SESSION);
				message.setFrom(new InternetAddress(account));
				for (String addr : address.trim().split(";")) {
					if (null != addr && addr.trim().length() > 0)
						message.addRecipient(Message.RecipientType.TO, new InternetAddress(addr.trim()));
				}
				message.setSubject(title);
				Multipart part = new MimeMultipart();
				BodyPart contentPart = new MimeBodyPart();
				contentPart.setContent(content, "text/html; charset=UTF-8");
				part.addBodyPart(contentPart);
				// email attachment list
				if (null != attechmentList) {
					for (File file : attechmentList) {
						BodyPart attachmentPart = new MimeBodyPart();
						attachmentPart.setDataHandler(new DataHandler(new FileDataSource(file)));
						attachmentPart.setFileName("=?GBK?B?" + Base64.encode(file.getName().getBytes()).replaceAll("\r", "").replaceAll("\n", "") + "?=");
						part.addBodyPart(attachmentPart);
					}
				}
				message.setContent(part);
				transport = EMAIL_SESSION.getTransport("smtp");
				transport.connect(host, port, account, password);
				message.saveChanges();
				transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
			} finally {
				close(transport);
			}
		}
	}

	public static void close(Service s) { // transport, store
		if (null != s) {
			try {
				s.close();
				Thread.sleep(500);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}
}
