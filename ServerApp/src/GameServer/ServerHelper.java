package GameServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Dictionary;
import java.util.Hashtable;


public class ServerHelper {
	private static String baseurl = "http://www.ayarger.com:7777";
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
		String url = baseurl + "/Scores/";
		
		try {
			URLConnection connection = new URL(url).openConnection();
			connection.setRequestProperty("Accept-Charset", charset);
			InputStream response = connection.getInputStream();
			String data = getStringFromInputStream(response);
			
			String[] parts = data.split(";");
			
			for(int i = 0; i < parts.length; i++) {
				leaderBoardString += parts[i] + "\n";
			}
		}
		catch(Exception e) {
			System.out.println(e.toString());
		}
		
		return leaderBoardString;
	}
	
//------------------------------------------------------------------------------------------------

	public static String getUserTowers(String username) {
		String userTowerDataString ="";
		String url = baseurl + "/GetTower/";
		
		try {
			String query = "username=" + 
				     URLEncoder.encode(username, charset);
			
			URLConnection connection = new URL(url + "?" + query).openConnection();
			connection.setRequestProperty("Accept-Charset", charset);
			InputStream response = connection.getInputStream();
			String data = getStringFromInputStream(response);
			
			String[] parts = data.split(";");
			
			for(int i = 0; i < parts.length; i++) {
				userTowerDataString += parts[i] + "\n";
			}
		}
		catch(Exception e) {
			System.out.println(e.toString());
		}
		
		return userTowerDataString;
	}
		
//------------------------------------------------------------------------------------------------
	
	public static Boolean updateUserScore(String username, int valueInc) {
		try {
			String url = baseurl + "/Scores/";
			String query = "username=" + 
				     URLEncoder.encode(username, charset);
			
			URLConnection connection = new URL(url + "?" + query).openConnection();
			connection.setRequestProperty("Accept-Charset", charset);
			InputStream response = connection.getInputStream();
			
			String data = getStringFromInputStream(response);
			
			//user score was found
			if(data != null && !data.isEmpty()) {
				String postURL = baseurl + "/Scores/";
				String postQuery = "username=" + 
					     URLEncoder.encode(username, charset);
				int score = Integer.parseInt(data);
				score += valueInc;
				postQuery += "&score=" + 
					     URLEncoder.encode(Integer.toString(score), charset);
				
				connection = new URL(postURL + "?" + postQuery).openConnection();
				connection.setDoOutput(true); // Triggers POST.
				connection.setRequestProperty("Accept-Charset", charset);
				connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);

				try (OutputStream output = connection.getOutputStream()) {
				    output.write(postQuery.getBytes(charset));
				}

				response = connection.getInputStream();
				data = getStringFromInputStream(response);
				
				//updated score for user
				if(data.equals("User Score Updated Success")) {
					return true;
				}
				//failed to update score for user
				else {
					System.out.println(data);
					return false;
				}
			}
			else {
				return false;
			}
		}
		catch(Exception e) {
			System.out.println(e.toString());
		}
		
		
		return false;
	}
	
//------------------------------------------------------------------------------------------------
	
	public static Dictionary<String, Object> getBestTower() {
		Dictionary<String, Object> dict = new Hashtable<String, Object>();
		
		String url = baseurl + "/GetTower/";
		
		try {
			URLConnection connection = new URL(url).openConnection();
			connection.setRequestProperty("Accept-Charset", charset);
			InputStream response = connection.getInputStream();
			String data = getStringFromInputStream(response);

			String[] parts = data.split("@");
			
    		dict.put("towername", parts[0]);
    		dict.put("towerdata", (parts[3]));
    		dict.put("loses", Integer.parseInt(parts[1]));
    		dict.put("wins", Integer.parseInt(parts[2]));
		}
		catch(Exception e) {
			System.out.println(e.toString());
		}
		
		return dict;
	}

//------------------------------------------------------------------------------------------------
	
	public static Boolean addNewUserTower(String username, String towername, String towerDataInput) {
		String url = baseurl + "/AddNewTower/";
		
		try {
			String postQuery = "username=" + 
				     URLEncoder.encode(username, charset);
			postQuery += "&towername=" + 
				     URLEncoder.encode(towername, charset);
			postQuery += "&towerdata=" + 
				     URLEncoder.encode(towerDataInput, charset);
			
			URLConnection connection = new URL(url + "?" + postQuery).openConnection();
			connection.setDoOutput(true); // Triggers POST.
			connection.setRequestProperty("Accept-Charset", charset);
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);
	
			try (OutputStream output = connection.getOutputStream()) {
			    output.write(postQuery.getBytes(charset));
			}
	
			InputStream response = connection.getInputStream();
			String data = getStringFromInputStream(response);
			System.out.println("result: " + data);
			if(data.equals("Tower Insert Success")) {
				return true;
			}
			else {
				System.out.println(data);
				return false;
			}
		}
		catch(Exception e) {
			System.out.println(e.toString());
		}
		
		return false;
	}
//------------------------------------------------------------------------------------------------	
	public static boolean updateTowerLose(String towername) {
		return updateTower(towername, "0", "-1");
	}
	
//------------------------------------------------------------------------------------------------	
	
	public static boolean updateTowerWin(String towername) {
		return updateTower(towername, "1", "0");
	}
	
//------------------------------------------------------------------------------------------------	
//	http://localhost:7777/UpdateTower/?towername=worktower&wins=1&loses=-1
	private static boolean updateTower(String towername, String wins, String loses) {
		String url = baseurl + "/UpdateTower/";
		
		try {
			String postQuery = "towername=" + 
				     URLEncoder.encode(towername, charset);
			postQuery += "&wins=" + 
				     URLEncoder.encode(wins, charset);
			postQuery += "&loses=" + 
				     URLEncoder.encode(loses, charset);
			
			URLConnection connection = new URL(url + "?" + postQuery).openConnection();
			connection.setDoOutput(true); // Triggers POST.
			connection.setRequestProperty("Accept-Charset", charset);
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);
	
			try (OutputStream output = connection.getOutputStream()) {
			    output.write(postQuery.getBytes(charset));
			}
	
			InputStream response = connection.getInputStream();
			String data = getStringFromInputStream(response);
			System.out.println("result: " + data);
			if(data.equals("Tower Updated Success")) {
				return true;
			}
			else {
				System.out.println(data);
				return false;
			}
		}
		catch(Exception e) {
			System.out.println(e.toString());
		}
		
		return false;
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
