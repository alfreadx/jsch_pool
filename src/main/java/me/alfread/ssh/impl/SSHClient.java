package me.alfread.ssh.impl;

public class SSHClient {

	public static void main(String[] args) {

		SSHManager sshMgr = new SSHManager("mes", "a123456", "127.0.0.1", "D:\\knowHostFile");
		
		sshMgr.connect();
		
		String result = sshMgr.sendCommand("cmd /c dir");
		
		sshMgr.close();
		
		System.out.println("result:" + result);
	}

}
