package me.alfread.sshpool;

import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.GenericObjectPool;

import com.jcraft.jsch.Session;

public class SessionPool extends GenericObjectPool<Session>{

	public SessionPool(PooledObjectFactory<Session> factory) {
		super(factory);
		// TODO Auto-generated constructor stub
	}

}
