import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import org.jivesoftware.smack.chat.Chat;

/**
 * @author piymitta
 *
 */
public class FileHandling {

	/**
	 * Test
	 */
	public static void main(String[] args) {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new FileHandling().getClass().getClassLoader().getResourceAsStream("serverlog_details.txt")));

			ArrayList<String> lines = new ArrayList<String>();
			String tmp = "";
			while ((tmp = br.readLine()) != null) 
				lines.add(tmp);
			

			for (int i = 0; i < lines.size(); i++) 
				System.out.println(lines.get(i));
			

			URL abc = new URL(
					"/net/slcao668.us.abc.com/scratch/fusionapps/localdomain/domains/slcao668.us.abc.com/ProjectsDomain/servers/ProjectsManagementServer_1/logs/ProjectsManagementServer_1.out");
			br = new BufferedReader(new InputStreamReader(abc.openStream()));
			while ((tmp = br.readLine()) != null) 
					System.out.println(br.readLine());
			
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	void printNLines(Chat chat, BufferedReader br, int noLines) throws FileNotFoundException {

		ArrayList<String> lines = new ArrayList<String>();
		String tmp = "";
		int maxLines = 0;
		try {
			while ((tmp = br.readLine()) != null) {
				lines.add(tmp);
				maxLines++;
			}
			String message = "";
			if (noLines <= lines.size()) {
				for (int i = lines.size() - noLines; i < lines.size(); i++) {
					message += lines.get(i) + "\n";
				}
			} else {
				message += "Only " + maxLines + " lines are present in log. Please give lesser number of lines";
			}
			chat.sendMessage(message);
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	void searchText(Chat chat, BufferedReader br, String text) throws FileNotFoundException {

		ArrayList<String> lines = new ArrayList<String>();
		String tmp = "";
		int maxLines = 0;
		String message = "";
		text = text.replace("\"", "");
		try {
			while ((tmp = br.readLine()) != null && maxLines < 100) {
				if (tmp.contains(text)) {
					lines.add(tmp);
					maxLines++;
				}
			}
			if (maxLines > 0)
				if (maxLines == 1)
					message += "Text <" + text + ">" + "is present in " + maxLines + " line of log file.\n\n";
				else if (maxLines < 100)
					message += "Text <" + text + ">" + "is present in " + maxLines + " lines of log file.\n\n";
				else
					message += "Text <" + text + ">" + "is present in more than " + maxLines
							+ " lines of log file.\n\n";
			if (maxLines > 0) {
				for (int i = 0; i < lines.size(); i++) {
					message += lines.get(i) + "\n";
				}
			} else {
				message += "Text :" + " <" + text + "> " + "is not present in log file.";
			}
			chat.sendMessage(message);
		} catch (Exception e) {
			System.out.println(e);
		}

	}
}
