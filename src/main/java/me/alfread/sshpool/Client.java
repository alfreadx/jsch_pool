package me.alfread.sshpool;

public class Client {
	
	public static void main(String[] args) {
		
		String result = SSHUtils.sendCommand("cmd /c dir");
		
		System.out.println("result: " + result);
		
	}
	

}
