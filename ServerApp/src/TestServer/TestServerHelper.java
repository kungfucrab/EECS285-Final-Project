package TestServer;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Dictionary;

import GameServer.ServerHelper;

public class TestServerHelper {
	static public void main(String[] args) throws Exception {
		//user login tests
		System.out.println(ServerHelper.userLogin("bob"));
		System.out.println(ServerHelper.userLogin("jill"));
		
		//update score for users
		System.out.println(ServerHelper.updateUserScore("dfd"));
		System.out.println(ServerHelper.updateUserScore("bob"));
		
		//get leaderboard
		System.out.println(ServerHelper.getLeaderboard());
		
		//get best tower
		Dictionary dict = ServerHelper.getBestTower();
		System.out.println(dict.get("towername"));
		System.out.println(dict.get("towerdata"));
		System.out.println(dict.get("loses"));
		System.out.println(dict.get("wins"));
		
		//add new tower for user
		System.out.println(ServerHelper.addNewUserTower("bob", "legotower", "towerdatayoyo"));
	}
}
