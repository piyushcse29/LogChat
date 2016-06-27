import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;

import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.packet.Message;

/**
 * @author piymitta
 *
 */
public class FilterLogs {
	/**
	 * @param chat
	 * @param message
	 * @throws NotConnectedException
	 */
	void function(Chat chat, Message message) throws NotConnectedException {
		TreeMap<String, String> serverlogInfo = new ServerLogDetails().loadServerInfo();
		TreeMap<String, String> incidentlogInfo = new IncidentLogDetails().loadServerInfo();
		if (message == null || message.getBody() == null)
			return;
		Scanner sc = new Scanner(message.getBody());
		sc.useDelimiter("\\s* \\s*");

		String info = "";
		ArrayList<String> arr = new ArrayList<String>();
		String temp = sc.next();
		arr.add(temp);
		if (temp.equalsIgnoreCase("serverlog")) {
			int i = 1;
			while (sc.hasNext() && i < 3) {
				arr.add(sc.next());
				i++;
			}
			if (sc.hasNext()) {
				StringBuffer text = new StringBuffer();
				while (sc.hasNext())
					text.append(sc.next() + " ");

				text.deleteCharAt(text.length() - 1);

				arr.add(text.toString());
			}
			if (arr.get(0).equalsIgnoreCase("serverlog")) {
				if (arr.size() == 1) {
					String available_servers = "";
					for (String servers : serverlogInfo.keySet())
						available_servers += servers + "\n";
					info += "Usage:\n"
							+ "Print the logs: serverlog <environment> <Number of lines from bottom of log. Max limit 300>\n"
							+ "Search the logs: serverlog <environment> <search> <\"Text\">\n"
							+ "Available enviroment:\n" + available_servers;
					// printError(chat,message);
					try {
						chat.sendMessage(info);

					} catch (NotConnectedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else if (arr.size() == 2) {
					if (arr.size() == 2) {
						if (serverlogInfo.get(arr.get(1)) != null)
							new FilterServerLogs().logs(chat, arr.get(1), serverlogInfo, "30");
						else
							printError(chat, arr.get(1) + " environment not present");
					}
				} else if (arr.size() == 3) {
					try {
						if (serverlogInfo.get(arr.get(1)) != null) {
							Integer.parseInt(arr.get(2));
							new FilterServerLogs().logs(chat, arr.get(1), serverlogInfo, arr.get(2));
						} else
							printError(chat, arr.get(1) + " environment not present");
					} catch (Exception e) {
						printError(chat, message);
					}
				} else if (arr.size() == 4) {
					try {
						if (serverlogInfo.get(arr.get(1)) != null) {
							if (arr.get(2).equalsIgnoreCase("search")) {
								new FilterServerLogs().searchLogs(chat, arr.get(1), serverlogInfo, arr.get(3));
							} else {
								printError(chat, message);
							}
						} else
							printError(chat, arr.get(1) + " environment not present");
					} catch (Exception e) {
						printError(chat, message);
					}
				} else
					printError(chat, message);
			}
		} else if (temp.equalsIgnoreCase("incident")) {
			while (sc.hasNext()) {
				arr.add(sc.next());
			}
			int size = arr.size();
			if (size == 1) {
				String available_servers = "";
				for (String servers : incidentlogInfo.keySet())
					available_servers += servers + "\n";
				info += "Usage: incident <environment> <incident number>\n" + "Available enviroment:\n"
						+ available_servers;
				try {
					chat.sendMessage(info);

				} catch (NotConnectedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return;
			}
			if (size != 3) {
				printError(chat, message);
				return;
			} else {
				try {
					if (incidentlogInfo.get(arr.get(1)) != null) {
						Integer.parseInt(arr.get(2));
						new FilterIncidentLogs().logs(chat, arr.get(1), incidentlogInfo, arr.get(2));
					} else
						printError(chat, arr.get(1) + " environment not present");
				} catch (Exception e) {
					printError(chat, message);
				}
			}

		} else
			return;
	}

	/**
	 * @param chat
	 * @param message
	 */
	void printError(Chat chat, Message message) {
		String info = "ERROR: This is an unkown command: " + message.getBody();
		try {
			chat.sendMessage(info);
		} catch (NotConnectedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @param chat
	 * @param message
	 */
	void printError(Chat chat, String message) {
		String info = "ERROR: This is an unkown command: " + message;
		try {
			chat.sendMessage(info);
		} catch (NotConnectedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
