package design_pattern;

import java.util.ArrayList;
import java.util.List;

public class ObserverDesignPattern {

	
	
	public static class App {

		/**
		 * Program entry point
		 * @param args command line args
		 */
		public static void main(String[] args) {

			Weather weather = new Weather();
			weather.addObserver(new Orcs());
			weather.addObserver(new Hobbits());

			weather.timePasses();
			weather.timePasses();
			weather.timePasses();
			weather.timePasses();
		}
	}
	
	// Weather contains all Observer and if there is change in whether it call speicfic methods on all observers(notify)
	public static class Weather {

		private WeatherType currentWeather;
		private List<WeatherObserver> observers;

		public Weather() {
			observers = new ArrayList<>();
			currentWeather = WeatherType.SUNNY;
		}

		public void addObserver(WeatherObserver obs) {
			observers.add(obs);
		}

		public void removeObserver(WeatherObserver obs) {
			observers.remove(obs);
		}

		public void timePasses() {
			WeatherType[] enumValues = WeatherType.values();
			currentWeather = enumValues[(currentWeather.ordinal() + 1) % enumValues.length]; // changing the whether
			System.out.println("The weather changed to " + currentWeather + ".");
			notifyObservers();
		}

		private void notifyObservers() {
			for (WeatherObserver obs : observers) {
				obs.update(currentWeather);
			}
		}
	}
	
	
	public interface WeatherObserver {

		void update(WeatherType currentWeather);

	}
	
	
	public static class Orcs implements WeatherObserver {

		@Override
		public void update(WeatherType currentWeather) {
			System.out.println("Orcs got notified  Current wheather is "+currentWeather);
		}

	}
	
	public static class Hobbits implements WeatherObserver {

		@Override
		public void update(WeatherType currentWeather) {
			System.out.println("Hobbits is got notified Current wheather is "+currentWeather);
		}

	}
	public enum WeatherType {

		SUNNY, RAINY, WINDY, COLD;

		@Override
		public String toString() {
			return this.name().toLowerCase();
		}

	}
	
}
