package multithreading;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CallableFutureTest implements Callable {
	private String resource;
	private Set<String> hosts;
	Map<String, Map<String, Map<String, Integer>>> serverServicesMap = new HashMap<String, Map<String, Map<String, Integer>>>();
	private String likelyServicesHint;

	public CallableFutureTest() {
	}

	public CallableFutureTest(Set<String> hosts, String likelyServicesHint, String resource) {
		this.resource = resource;
		this.hosts = hosts;
	}

	public Map<String, Map<String, Map<String, Integer>>> returning_main() throws Exception {
		ExecutorService executorService = Executors.newFixedThreadPool(3);
		ArrayList<Future> futures = new ArrayList<Future>();
		for (String host : hosts) {
			Set<String> oneValueHostSet = new HashSet<String>();
			oneValueHostSet.add(host);
			Callable callable = new CallableFutureTest(oneValueHostSet, likelyServicesHint, resource);
			futures.add(executorService.submit(callable));
		}
		
		
		int count = 0;
		while (true) {
			Object resp = null;
			resp = futures.get(count).get();
			serverServicesMap.putAll((Map<String, Map<String, Map<String, Integer>>>) resp);
			System.out.println("resp:" + serverServicesMap);
			count++;
			if (count == futures.size())
				break;
		}

		/*
		 * try { System.out.println(future.isDone()); resp = future.get();
		 * serverServicesMap = (Map<String, Map<String, Map<String, Integer>>>)
		 * resp; System.out.println("resp:"+serverServicesMap);
		 * System.out.println(future.isDone()); } catch (InterruptedException |
		 * ExecutionException e) { e.printStackTrace(); }
		 */

		executorService.shutdown();
		return getServerServicesMap();
	}

	public static void main(String... args) throws Exception, ExecutionException {
		Set<String> hosts = new HashSet<String>();
		hosts.add("localhost");
		hosts.add("localhost2");
		String likelyServicesHint = "all";
		String resource = "dll";
		CallableFutureTest callableFutureTest = new CallableFutureTest(hosts, likelyServicesHint, resource);
		Map<String, Map<String, Map<String, Integer>>> resp = callableFutureTest.returning_main();
		System.out.println("from returning_main:" + resp);
		//test();

	}

	private static void test() throws Exception, ExecutionException {
		ExecutorService executorservice = Executors.newFixedThreadPool(4);
		Callable arg0 = new CallableFutureTest();
		Callable arg1 = new CallableFutureTest();
		Callable arg2 = new CallableFutureTest();

		Future f = executorservice.submit(arg0);
		Future f1 = executorservice.submit(arg1);
		Future f2 = executorservice.submit(arg2);

		Object res = f.get();
		System.out.println(res);

		res = f1.get();
		System.out.println(res);

		res = f2.get();
		System.out.println(res);
	}

	@Override
	public Object call() throws Exception {
		System.out.println("from call: " + Thread.currentThread());
		//Thread.sleep(10000);
		HashMap<String, Map<String, Integer>> arg1 = new HashMap<String, Map<String, Integer>>();
		String arg0 = Thread.currentThread().getName()+hosts;
		serverServicesMap.put(arg0, arg1);
		return serverServicesMap;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public Map<String, Map<String, Map<String, Integer>>> getServerServicesMap() {
		return serverServicesMap;
	}

	public void setServerServicesMap(Map<String, Map<String, Map<String, Integer>>> serverServicesMap) {
		this.serverServicesMap = serverServicesMap;
	}

	public Set<String> getHosts() {
		return hosts;
	}

	public void setHosts(Set<String> hosts) {
		this.hosts = hosts;
	}

}
