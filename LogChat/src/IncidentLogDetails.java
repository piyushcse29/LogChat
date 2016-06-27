import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * @author piymitta
 *
 */
public class IncidentLogDetails {

	TreeMap<String, String> loadServerInfo() {

		TreeMap<String, String> hm = new TreeMap<String, String>();
		try {
			// FileInputStream fin=new
			// FileInputStream("\\incidentlog_details.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(
					this.getClass().getClassLoader().getResourceAsStream("incidentlog_details.txt")));

			ArrayList<String> lines = new ArrayList<String>();
			int n = 4;
			String tmp = "";
			while ((tmp = br.readLine()) != null) {
				Scanner sc = new Scanner(tmp);
				sc.useDelimiter("\\s* \\s*");
				// if(tmp.contains("r13dev"))
				hm.put(sc.next(), sc.next());
			}

			// System.out.println(hm);

		} catch (Exception e) {
			System.out.println(e);
		}
		return hm;
	}

	void updateServerInfo(String env) {

		TreeMap<String, String> hm = new TreeMap<String, String>();
		try {
			File file = new File("src/incidentlog_details.txt");

			// FileWriter fw = new FileWriter(new
			// FileWriter("src/server_details.txt", true));
			BufferedWriter bw = new BufferedWriter(new FileWriter("src/server_details.txt", true));
			bw.write(env);
			bw.close();

		} catch (Exception e) {
			System.out.println(e);
		}

	}

	void addServerInfo(String env) {

		TreeMap<String, String> hm = new TreeMap<String, String>();
		try {
			File file = new File("src/incidentlog_details.txt");

			// FileWriter fw = new FileWriter(new
			// FileWriter("src/server_details.txt", true));
			BufferedWriter bw = new BufferedWriter(new FileWriter("src/server_details.txt", true));
			bw.write(env);
			bw.close();

		} catch (Exception e) {
			System.out.println(e);
		}

	}

	void deleteServerInfo(String env) {

		TreeMap<String, String> hm = new TreeMap<String, String>();
		try {
			File file = new File("src/incidentlog_details.txt");

			// FileWriter fw = new FileWriter(new
			// FileWriter("src/server_details.txt", true));
			BufferedWriter bw = new BufferedWriter(new FileWriter("src/server_details.txt", true));
			bw.write(env);
			bw.close();

		} catch (Exception e) {
			System.out.println(e);
		}

	}

	public static void main(String args[]) {
		new IncidentLogDetails().loadServerInfo();
		// new ServerDetails().addServerInfo("r13dev
		// http://slc11fgu.us.oracle.com:8081/env_viewer.jsp?sort=1&file=%2Fslot%2Fems16824%2Fappmgr%2FAPPTOP%2Finstance%2Fdomains%2Fslc11fgu.us.oracle.com%2FProjectsDomain%2Fservers%2FProjectsManagementServer_1%2Flogs%2FProjectsManagementServer_1.out");

	}
}
