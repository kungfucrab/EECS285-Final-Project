package TestServer;

import GameServer.ServerHelper;

public class TestServerHelper {
	static public void main(String[] args) throws Exception {
		System.out.println(ServerHelper.userLogin("bob"));
		System.out.println(ServerHelper.userLogin("jill"));
	}
}
