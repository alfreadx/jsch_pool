package me.alfread.sshpool;

import org.apache.commons.pool2.impl.DefaultPooledObject;

import com.jcraft.jsch.Session;

public class PooledSession extends DefaultPooledObject<Session> {

	public PooledSession(Session object) {
		super(object);
	}

	
	
}
