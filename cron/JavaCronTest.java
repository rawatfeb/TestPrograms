package cron;

import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public class JavaCronTest {

	public static void main(String... args) {

		//timerTaskTest();

		//	schedulerTest();

		sheduleExecutoeService2();
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	private static void sheduleExecutoeService2() {
		ScheduledExecutorService scheduledExecutorService = Executors
				.newSingleThreadScheduledExecutor(new ThreadFactory() {
					@Override
					public Thread newThread(Runnable r) {
						Thread thread = new Thread(r);
						thread.setDaemon(true);
						return thread;
					}
				});
		scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				int i=0;
				i++;
				System.out.println("Hi see you after 1 sec "+i);
			}
		}, 0, 1, TimeUnit.SECONDS);
	}

	private static void schedulerTest() {
		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
		Runnable beeper = new Runnable() {
			public void run() {
				System.out.println("beep");
			}
		};
		final ScheduledFuture<?> beeperHandle = scheduler.scheduleAtFixedRate(beeper, 10, 10, SECONDS);
		scheduler.schedule(new Runnable() {
			public void run() {
				beeperHandle.cancel(true);
			}
		}, 60 * 60, SECONDS);
	}

	private static void timerTaskTest() {
		Timer t = new Timer();
		MyTask mTask = new MyTask();
		// This task is scheduled to run every 10 seconds
		t.scheduleAtFixedRate(mTask, 0, 10000);
	}

	public static class MyTask extends TimerTask {

		@Override
		public void run() {
			System.out.println("Hi see you after 10 sec");

		}

	}
}
