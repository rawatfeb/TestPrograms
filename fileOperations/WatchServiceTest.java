package fileOperations;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;

public class WatchServiceTest {
	public static void main(String[] args) throws Exception {
		WatchService watchService = FileSystems.getDefault().newWatchService();
		Path path = Paths.get("Watchable");
		path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE,
				StandardWatchEventKinds.ENTRY_MODIFY);
		while (true) {
			WatchKey key = watchService.take();
			List<WatchEvent<?>> events = key.pollEvents();
			for (WatchEvent<?> watchEvent : events) {
				System.out.println(watchEvent.kind());
				System.out.println(watchEvent.context());
			}
		}
	}
}
