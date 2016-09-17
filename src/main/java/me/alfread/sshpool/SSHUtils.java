package me.alfread.sshpool;

import java.io.IOException;
import java.io.InputStream;

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
	
	static {
		init();
	}
	
	private static void init(){
		GenericObjectPoolConfig config = new GenericObjectPoolConfig();
		config.setMaxTotal(10);
		connPoolFactory = 
				new ConnectionPoolFactory(config, 
						"mes", "a123456", "127.0.0.1", 22, 1800);
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

}
