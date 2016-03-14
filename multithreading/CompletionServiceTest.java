package multithreading;

import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import sun.jvmstat.monitor.HostIdentifier;
import sun.jvmstat.monitor.MonitoredHost;
import sun.jvmstat.monitor.MonitoredVm;
import sun.jvmstat.monitor.MonitoredVmUtil;
import sun.jvmstat.monitor.VmIdentifier;

public class CompletionServiceTest {
	public static void main(String[] args) throws Exception {
		ExecutorService executorService = Executors.newFixedThreadPool(8);
		ExecutorCompletionService<Integer> completionService = new ExecutorCompletionService<Integer>(executorService);

		MonitoredHost monitoredHost;
		monitoredHost = MonitoredHost.getMonitoredHost(new HostIdentifier("c999fuunovus.westlan.com"));
		Set<Integer> jvms = monitoredHost.activeVms();
		int c = 0;
		for (Integer integer : jvms) {
			completionService.submit(new CallableThread(monitoredHost, integer.toString()));
			c++;
		}

		executorService.shutdown();
		while (c > 0) {
			c--;
			try {
				Future<Integer> f = completionService.poll(5050, TimeUnit.MILLISECONDS);
				if (null != f) {
					System.out.println(f.get());
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} /*catch (ExecutionException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}*/
		}
		System.out.println("c="+c);
		List<Runnable> awaitingTasks = executorService.shutdownNow();
		System.out.println("awaitingTasks=" + awaitingTasks.size());
	}

	static class CallableThread implements Callable<Integer> {
		int tn;
		String jvm;
		MonitoredHost monitoredHost;

		public CallableThread(int tn) {
			this.tn = tn;
		}

		public CallableThread(MonitoredHost monitoredHost, String jvm) {
			this.jvm = jvm;
			this.monitoredHost = monitoredHost;
		}

		public Integer call() throws Exception {
			System.out.println("I am thread=" + jvm + "  " + Thread.currentThread().getName());
			MonitoredVm mvm = monitoredHost.getMonitoredVm(new VmIdentifier(jvm));
			String jvmArgs = MonitoredVmUtil.jvmArgs(mvm);
			mvm.detach();
			System.out.println(jvmArgs);
			if (Integer.valueOf(jvm) == 22716) {
				try {
					//Thread.sleep(50000);
				} catch (Exception e) {
					e.printStackTrace();
				}
				throw new RuntimeException("Custom Exception");
			}
			if (Integer.valueOf(jvm) == 20397) {
				try {
					Thread.sleep(5000);
					System.out.println("\n\n\n\n\n\n==========Hello==========\n\n\n\n\n");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			
			return tn;
		}
	}

}
