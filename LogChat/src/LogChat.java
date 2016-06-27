import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.SSLSocketFactory;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.chat.ChatManagerListener;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;

/**
 * @author piymitta
 *
 */
public class LogChat implements MessageListener {

	@Override
	public void processMessage(Message arg0) {

	}

	static AbstractXMPPConnection connection;

	public static void main(String args[]) throws SmackException, IOException, XMPPException, KeyManagementException,
			NoSuchAlgorithmException, InterruptedException {
		// Create the configuration for this new connection

		XMPPTCPConnectionConfiguration.Builder configBuilder = XMPPTCPConnectionConfiguration.builder();
		// configBuilder.setDebuggerEnabled(true);
		configBuilder.setSendPresence(true);
		configBuilder.setUsernameAndPassword("piyush.m.mittal", "pass");
		configBuilder.setResource("Piyushs-MacBook-Pro");
		configBuilder.setServiceName("abc.com");
		// configBuilder.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);
		configBuilder.setHost("stbeehive.abc.com");
		// TLSUtils.acceptAllCertificates(configBuilder);
		configBuilder.setPort(5223);
		// configBuilder.setConnectTimeout(25000000);
		configBuilder.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);
		configBuilder.setSocketFactory(SSLSocketFactory.getDefault());

		connection = new XMPPTCPConnection(configBuilder.build());
		// Connect to the server
		System.out.println("Configuration done");
		connection.connect();
		System.out.println("Connection Established");
		// Log into the server
		connection.login();

		// Configure ServerInfo

		// Assume we've created an XMPPConnection name "connection"._
		ChatManager chatmanager = ChatManager.getInstanceFor(connection);
		Chat chat = chatmanager.createChat("piyush.m.mittal@abc.com");
		chat.sendMessage("Testing Done");

		chatmanager.addChatListener(new ChatManagerListenerImpl());

		chatmanager.createChat("piyush.m.mittal@abc.com", new ChatMessageListener() {
			public void processMessage(Chat chat, Message message) {
				if (message != null && message.getBody() != null && message.getBody().equals("serverlog")) {
					try {
						chat.sendMessage("Howdy old!");
					} catch (NotConnectedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});

		// idle for 20 seconds
		final long start = System.nanoTime();
		while (true)// (System.nanoTime() - start) / 1000000 < 200000000) // do
					// for 20 seconds
		{
			Thread.sleep(500);
		}
	}
}

class ChatManagerListenerImpl implements ChatManagerListener {

	@Override
	public void chatCreated(final Chat chat, final boolean createdLocally) {
		chat.addMessageListener(new ChatMessageListener() {
			public void processMessage(Chat chat, Message message) {
				try {
					new FilterLogs().function(chat, message);
				} catch (NotConnectedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				;

			}

		});
	}

}