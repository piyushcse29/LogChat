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
public class Temp {

	public static void main(String[] args) throws SocketException, IOException {
		FTPClient ftpClient = new FTPClient();
		ftpClient.connect("indl124121.in.oracle.com");
		ftpClient.login("piymitta", "pass");

		BufferedReader reader = null;
		String firstLine = null;

		try {
			InputStream stream = ftpClient.retrieveFileStream("/scratch/piyush.txt");
			reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
			firstLine = reader.readLine();
			System.out.println(firstLine);
		} finally {
			if (reader != null)
				try {
					reader.close();
				} catch (IOException logOrIgnore) {
				}
		}
	}

}
