package me.alfread.sshpool;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class ConnectionPoolFactory {
	
	private static final Logger log = LoggerFactory.getLogger(ConnectionPoolFactory.class);
	
	private GenericObjectPool<Session> pool;
	
	public ConnectionPoolFactory(GenericObjectPoolConfig config,String userName, String password, String connectionIP , int port ,int timeout) {
		ConnectionFactory factory = new ConnectionFactory(userName, password, connectionIP, port, timeout);
		pool = new GenericObjectPool<Session>(factory,config);
	}
	
	
	public Session getSession()throws Exception{
		return pool.borrowObject();
	}

	
	public void releaseSession(Session session){
		pool.returnObject(session);
	}
	
	
	class ConnectionFactory extends BasePooledObjectFactory<Session>{
		

		private JSch jsch;
		private Session sessConnection;
		
		private String username;
		private String password;
		private String connectionIP;
		private int port;
		private int timeout;
		

		public ConnectionFactory(String userName, String password, String connectionIP , int port ,int timeout) {
			this.jsch = new JSch();

			this.username = userName;
			this.password = password;
			this.connectionIP = connectionIP;
			this.port = port;
			this.timeout = timeout;
		}
		
		
		
		@Override
		public Session create() throws Exception {
			try {
				sessConnection = jsch.getSession(username, connectionIP, port);
				sessConnection.setPassword(password);
				sessConnection.setConfig("StrictHostKeyChecking", "no");
				sessConnection.connect(timeout);
			} catch (JSchException jschX) {
				log.error(jschX.getMessage());
			}
			return sessConnection;
		}

		@Override
		public PooledObject<Session> wrap(Session arg0) {
			return new DefaultPooledObject<Session>(arg0);
		}
		
	}
}
