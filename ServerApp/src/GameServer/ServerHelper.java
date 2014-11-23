package GameServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;


public class ServerHelper {
	private static String baseurl = "http://localhost:7777";
	private static String charset = "UTF-8";
	
	//check is user already exists, if not it will create an account for the new username
	public static Boolean userLogin(String username) {
		try {
			String url = baseurl + "/UserAccount/";
			String query = "username=" + 
				     URLEncoder.encode(username, charset);
			
			URLConnection connection = new URL(url + "?" + query).openConnection();
			connection.setRequestProperty("Accept-Charset", charset);
			InputStream response = connection.getInputStream();
			
			String data = getStringFromInputStream(response);

			//user does exist
			if(data.equals("1")) {
				return true;
			}
			//user DNE, create new account
			else {
				connection = new URL(url).openConnection();
				connection.setDoOutput(true); // Triggers POST.
				connection.setRequestProperty("Accept-Charset", charset);
				connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);

				try (OutputStream output = connection.getOutputStream()) {
				    output.write(query.getBytes(charset));
				}

				response = connection.getInputStream();
				data = getStringFromInputStream(response);
				
				//new user was added
				if(data.equals("User Insert Success")) {
					return true;
				}
				//failed to add new user
				else {
					System.out.println(data);
					return false;
				}
			}
		}
		catch(Exception e) {
			System.out.println(e.toString());
		}
		
		
		return false;
	}
	
//------------------------------------------------------------------------------------------------	

	public static String getLeaderboard() {
		String leaderBoardString ="";
		
		
		
		return leaderBoardString;
	}
	
//------------------------------------------------------------------------------------------------
	// convert InputStream to String
	private static String getStringFromInputStream(InputStream is) {
 
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
 
		String line;
		try {
 
			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
 
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
 
		return sb.toString();
 
	}

}
