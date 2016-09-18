package me.alfread.sshpool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Client {
	
	private static final Logger log = LoggerFactory.getLogger(Client.class);
	
	public static void main(String[] args) {
		
		final Client client = new Client();
		
		final int loopCount = 10;
		int threadCount = 10;
		
		List<Thread> threads = new ArrayList<Thread>();
		
		for(int i=0;i < threadCount;i ++){
			threads.add(new Thread(client.getWorker(loopCount)));
		}
		
		log.info("Thread add done.");
		
		try {
			log.info("start");
			for(Thread t : threads){
				t.start();
				Thread.sleep(1000);
			}
			
		} catch (Exception e) {
			log.error("start thread error",e);
		}
	}
	
	
	public void runTest(){
		String result = "";
//		result = SSHUtils.sshexecute("cmd /c dir D:\\mock_fs");
//		result = SSHUtils.sshexecute("ls /");
		result = SSHUtils.getSessionId();
		log.info("result:{}" , result);
	}
	
	public Runnable getWorker(final int loopCount){
		Runnable worker =  new Runnable() {
			
			public void run() {
				for(int i=0;i<loopCount;i++)
					runTest();
			}
		};
		return worker;
	}

	public void runWorker(final int loopCount){
		getWorker(loopCount).run();
	}
	
	
	public Callable<Long> getWorker2(final int loopCount){
		Callable<Long> c = new Callable<Long>() {
			
			public Long call() throws Exception {
				long start = System.currentTimeMillis();
				for(int i=0;i<loopCount;i++){
					runTest();
				}
				long end = System.currentTimeMillis();
				return end - start;
			}
		};
		
		return c;
	}
	
}
