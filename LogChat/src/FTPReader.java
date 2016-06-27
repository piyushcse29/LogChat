import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTPClient;

/**
 * @author piymitta
 *
 */
public class FTPReader {

	public static void main(String[] args) throws SocketException, IOException {
		FTPClient ftpClient = new FTPClient();
		ftpClient.connect("indl124121.in.abc.com");
		ftpClient.login("piymitta", "pass");

		BufferedReader reader = null;
		String firstLine = null;

		String str = "/net/slcao668.us.oracle.com/scratch/fusionapps/localdomain/domains/slcao668.us.oracle.com/ProjectsDomain/servers/ProjectsManagementServer_1/logs/ProjectsManagementServer_1.out";
		System.out.println(str.contains("/net"));
		// try {
		// InputStream stream =
		// ftpClient.retrieveFileStream("/scratch/piyush.txt");
		// reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
		// firstLine = reader.readLine();
		// System.out.println(firstLine);
		// } finally {
		// if (reader != null) try { reader.close(); } catch (IOException
		// logOrIgnore) {}
		// }
	}

	BufferedReader readFTPFile(String path) throws SocketException, IOException {
		FTPClient ftpClient = new FTPClient();
		ftpClient.connect("indl124121.in.oracle.com");
		ftpClient.login("piymitta", "Divya@27");

		BufferedReader reader = null;

		try {
			InputStream stream = ftpClient.retrieveFileStream(path);
			return new BufferedReader(new InputStreamReader(stream, "UTF-8"));

		} finally {
			if (reader != null)
				try {
					reader.close();
				} catch (IOException logOrIgnore) {
				}
		}
	}
}
