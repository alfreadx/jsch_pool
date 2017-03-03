package me.alfread.sshpool;

import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;

import com.jcraft.jsch.Session;

public class SessionPoolableFactory implements PooledObjectFactory<PooledSession>  {

	public void activateObject(PooledObject<PooledSession> arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void destroyObject(PooledObject<PooledSession> arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public PooledObject<PooledSession> makeObject() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public void passivateObject(PooledObject<PooledSession> arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public boolean validateObject(PooledObject<PooledSession> arg0) {
		// TODO Auto-generated method stub
		return false;
	}



}
