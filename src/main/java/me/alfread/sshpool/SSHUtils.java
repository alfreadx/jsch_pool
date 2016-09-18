package me.alfread.sshpool;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class SSHUtils {
	
	private static final Logger log = LoggerFactory.getLogger(SSHUtils.class);
	
	private static ConnectionPoolFactory connPoolFactory ;
	
	private static Random rand = null;
	
	static {
		init();
		initRandom();
	}
	
	private static void init(){
		GenericObjectPoolConfig config = new GenericObjectPoolConfig();
		config.setMaxTotal(15);
		config.setMaxWaitMillis(1000);
		config.setMinIdle(3);
		connPoolFactory = 
				new ConnectionPoolFactory(config, 
						"mes", "P@ssw0rd", "10.1.21.197", 22, 1800);
	}
	
	private static void initRandom(){
		rand = new Random(System.currentTimeMillis());
	}
	
	public static String sendCommand(String command){
		StringBuilder outputBuffer = new StringBuilder();
		Session session = null;
		try {
			session = connPoolFactory.getSession();
			Channel channel = session.openChannel("exec");
			((ChannelExec) channel).setCommand(command);
			InputStream commandOutput = channel.getInputStream();
			channel.connect();
			int readByte = commandOutput.read();
			while (readByte != 0xffffffff) {
				outputBuffer.append((char) readByte);
				readByte = commandOutput.read();
			}
			channel.disconnect();
			
		} catch (IOException ioX) {
			log.warn(ioX.getMessage());
			return null;
		} catch (JSchException jschX) {
			log.warn(jschX.getMessage());
			return null;
		} catch (Exception e) {
			log.warn(e.getMessage());
			return null;
		} finally{
			if(session != null){
				connPoolFactory.releaseSession(session);
			}
		}
		return outputBuffer.toString();
	}

	
	public static String sshexecute(String command){
		StringBuilder outputBuffer = new StringBuilder();
		Session session = null;
		try {
			session = connPoolFactory.getSession();
			Channel channel = session.openChannel("exec");
			((ChannelExec) channel).setCommand(command);
			InputStream commandOutput = channel.getInputStream();
			channel.connect();
			int readByte = commandOutput.read();
			while (readByte != 0xffffffff) {
				outputBuffer.append((char) readByte);
				readByte = commandOutput.read();
			}
			outputBuffer.append("\n\n\n\n session id:").append(session.toString());
			
			Thread.sleep(3000);
			channel.disconnect();
			
		} catch (IOException ioX) {
			log.warn(ioX.getMessage());
			return null;
		} catch (JSchException jschX) {
			log.warn(jschX.getMessage());
			return null;
		} catch (Exception e) {
			log.warn(e.getMessage());
			return null;
		} finally{
			if(session != null){
				connPoolFactory.releaseSession(session);
			}
		}
		return outputBuffer.toString();
	}
	
	public static String getSessionId(){
		Session session = null;
		String sid = "";
		try {
			session = connPoolFactory.getSession();
			sid = session.toString();
			long sleepMs = getSleepMillis();
			log.info("sleep {},sid:{}",sleepMs,sid);
			Thread.sleep(sleepMs);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			if(session != null){
				connPoolFactory.releaseSession(session);
			}
		}
		
		
		return sid;
	}
	
	
	private static long getSleepMillis(){
		return ( rand.nextInt(10) + 1 ) * 1000;
	}
}
