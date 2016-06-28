import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.TreeMap;

import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.chat.Chat;

/**
 * @author piymitta
 *
 */
public class FilterServerLogs {

	void logs(Chat chat, String env, TreeMap<String, String> serverInfo, String lines) throws NotConnectedException {
		URL url = null;
		String path = "";

		if (lines.equals("0")) {
			String info = "Please follow below link/path for complete logs:\n" + serverInfo.get(env);
			try {
				chat.sendMessage(info);
			} catch (NotConnectedException e) {
				e.printStackTrace();
			}
		} else {
			try {
				if (serverInfo.get(env).contains("/net"))
					path = serverInfo.get(env);
				else
					url = new URL(serverInfo.get(env));

			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			BufferedReader br = null;
			try {
				if (!path.equals(""))
					br = new FTPReader().readFTPFile(path);
				else
					br = new BufferedReader(new InputStreamReader(url.openStream()));
			} catch (IOException e) {
				e.printStackTrace();
			}

			int maxLines = Integer.parseInt(lines);
			if (maxLines > 300) {
				sendCompleteLink(chat, env, serverInfo);
				return;
			}
			if (maxLines < 0) {
				printError(chat);
				return;
			}
			try {
				new FileHandling().printNLines(chat, br, maxLines);
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	void searchLogs(Chat chat, String env, TreeMap<String, String> serverInfo, String text)
			throws NotConnectedException {
		URL url = null;
		String path = "";
		try {
			if (serverInfo.get(env).contains("/net"))
				path = serverInfo.get(env);
			else
				url = new URL(serverInfo.get(env));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		BufferedReader br = null;
		try {
			if (!path.equals(""))
				br = new FTPReader().readFTPFile(path);
			else
				br = new BufferedReader(new InputStreamReader(url.openStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			new FileHandling().searchText(chat, br, text);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			br.close();
		} catch (IOException e) {
				e.printStackTrace();
		}

	}

	void printError(Chat chat) {
		String info = "ERROR: This is an unkown command: lines can't be less then 0";
		try {
			chat.sendMessage(info);
		} catch (NotConnectedException e) {
			e.printStackTrace();
		}
	}

	void sendCompleteLink(Chat chat, String env, TreeMap<String, String> serverInfo) {
		String info = "Please follow below link/path for complete logs:\n" + serverInfo.get(env);
		try {
			chat.sendMessage(info);
		} catch (NotConnectedException e) {
			e.printStackTrace();
		}
	}
}
