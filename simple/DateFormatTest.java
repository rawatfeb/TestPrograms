package simple;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

public class DateFormatTest {
	public static void main(String[] args) throws Exception {
		// standardDateFormat();
		_24HourFormat();
	}

	public static void standardDateFormat() throws Exception {
		System.out.println(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(new Date()));
	}

	public static void _24HourFormat() throws Exception {
		System.out.println(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").format(new Date()));
		// java 8 UTC time
		Instant currentUTCTimeInstant = Instant.now();
		System.out.println(currentUTCTimeInstant.toString());
		LocalTime localTime = LocalTime.from(currentUTCTimeInstant.atZone(ZoneId.systemDefault()));
		LocalDate localDate = LocalDate.from(currentUTCTimeInstant.atZone(ZoneId.systemDefault()));
		System.out.print(localDate);
		System.out.print(" ");
		System.out.println(localTime);

	}
}
