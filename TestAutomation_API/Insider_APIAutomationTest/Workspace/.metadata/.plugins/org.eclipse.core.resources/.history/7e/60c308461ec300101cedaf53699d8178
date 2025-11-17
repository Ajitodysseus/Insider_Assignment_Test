package Utility.configuration;

import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import PO.CommonFunctionality.BaseClass;
import Utility.ExtentReports.ExtentRepotEx;

//Author: Ajit Nakum - This utility is for send email but I don't have any SMTP details so not configured.

public class SendEmailConfiguration extends BaseClass {
	FolderZipClass fz = new FolderZipClass();

	public static void sendEmailWithAttachments(final String userName, String password, String host, String mailFrom,
			String[] toAddress, String subject, String message, String[] attachFiles) throws Exception {
		// sets SMTP server properties
		Properties properties = new Properties();
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", 25);
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "false");
		// properties.put("mail.smtp.starttls.required", "true");
		properties.put("mail.smtp.ssl.trust", "*");
		// properties.put("mail.smtp.ssl.enable", "false");
		// properties.put("mail.debug", "true");
		// properties.put("mail.user", userName);
		// properties.put("mail.password", password);

		// creates a new session with an authenticator

		disableCertificateValidation();

		Session session = Session.getInstance(properties, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(userName, password);
			}
		});

		// creates a new e-mail message
		Message msg = new MimeMessage(session);

		msg.setFrom(new InternetAddress(mailFrom));
		InternetAddress[] toAddresses1 = new InternetAddress[toAddress.length];
		for (int i = 0; i < toAddress.length; i++) {
			toAddresses1[i] = new InternetAddress(toAddress[i]);
		}
		msg.setRecipients(Message.RecipientType.TO, toAddresses1);
		msg.setSubject(subject);
		msg.setSentDate(new Date());

		// creates message part
		MimeBodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setContent(message, "text/html");

		// creates multi-part
		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(messageBodyPart);

		// adds attachments
		if (attachFiles != null && attachFiles.length > 0) {
			for (String filePath : attachFiles) {
				MimeBodyPart attachPart = new MimeBodyPart();

				try {
					attachPart.attachFile(filePath);
				} catch (Exception ex) {
					ex.printStackTrace();
				}

				multipart.addBodyPart(attachPart);
			}
		}

		// sets the multi-part as e-mail's content
		msg.setContent(multipart);

		// sends the e-mail
		Transport.send(msg);

	}

	private static void disableCertificateValidation() throws Exception {
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			public void checkClientTrusted(X509Certificate[] certs, String authType) {
			}

			public void checkServerTrusted(X509Certificate[] certs, String authType) {
			}

			public X509Certificate[] getAcceptedIssuers() {
				return new X509Certificate[0];
			}
		} };

		SSLContext sc = SSLContext.getInstance("TLS");
		sc.init(null, trustAllCerts, new java.security.SecureRandom());
		HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

		// Optional: Disable hostname verification too
		HostnameVerifier allHostsValid = (hostname, session) -> true;
		HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
	}

	public void sendEmail() {
		// SMTP info just for your reference I put the dummy data. not actual SMTP
		// details.
		String host = "smtp.office365.com";
		String port = "25";
		String mailFrom = "nakumajit@gmail.com";
		String username = "ajit";
		String password = "ajit";

		String toaddress = properties.getProperty("emailaddress");
		String[] mailTo = toaddress.split(";");
		String subject = "Insider Web Automation Report";
		String message = "This is an Insider Automation Report..!! ";

		// attachments
		String reportPath = properties.getProperty("reportPath") + "\\Report_" + ExtentRepotEx.timestamp;
		String[] attachFiles = new String[1];
		attachFiles[0] = reportPath + "." + "zip";

		try {
			fz.ZipFolder_method();
			Thread.sleep(3000);
			sendEmailWithAttachments(username, password, host, mailFrom, mailTo, subject, message, attachFiles);
			System.out.println("Email sent.");
		} catch (Exception ex) {
			System.out.println("Could not send email.");
			ex.printStackTrace();
		}
	}
}
