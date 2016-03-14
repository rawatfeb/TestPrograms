package design_pattern;

import java.io.IOException;
import java.io.InputStreamReader;

public class StateDesignPattern {
	static class Chain {
		private State current;

		public Chain() {
			current = new Off();
		}

		public void setState(State s) {
			current = s;
		}

		public void pull() {
			current.pull(this);
		}
	}

	abstract static class State {
		public void pull(Chain wrapper) {
			wrapper.setState(new Off());
			System.out.println("   turning off");
		}
	}

	static class Off extends State {
		public void pull(Chain wrapper) {
			wrapper.setState(new Low());
			System.out.println("   low speed");
		}
	}

	static class Low extends State {
		public void pull(Chain wrapper) {
			wrapper.setState(new Medium());
			System.out.println("   medium speed");
		}
	}

	static class Medium extends State {
		public void pull(Chain wrapper) {
			wrapper.setState(new High());
			System.out.println("   high speed");
		}
	}

	static class High extends State {
	}

	public static class StateDisc {
		public static void main(String[] args) throws IOException {
			InputStreamReader is = new InputStreamReader(System.in);
			int ch;
			Chain chain = new Chain();
			while (true) {
				System.out.print("Press 'Enter'");
				ch = is.read();
				ch = is.read();
				chain.pull();
			}
		}
	}
}
