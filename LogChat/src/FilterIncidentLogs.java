import java.net.MalformedURLException;
import java.net.URL;
import java.util.TreeMap;

import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.chat.Chat;

/**
 * @author piymitta
 *
 */
public class FilterIncidentLogs {

	void logs(Chat chat, String env, TreeMap<String, String> serverInfo, String lines) throws NotConnectedException {

		URL url = null;
		try {
			url = new URL(serverInfo.get(env) + "incdir_" + lines + "%2Freadme.txt");
		} catch (MalformedURLException e) {
			printError(chat);
		}
		String info = "Press below link for complete incident logs:\n" + serverInfo.get(env) + "incdir_" + lines;
		try {
			chat.sendMessage(info);
		} catch (NotConnectedException e) {
			e.printStackTrace();
		}
	}

	void printError(Chat chat) {
		String info = "ERROR: Incident not present";
		try {
			chat.sendMessage(info);
		} catch (NotConnectedException e) {
			e.printStackTrace();
		}
	}
}
