package design_pattern;

import java.util.Stack;

public class MementoDesignPattern {

	public static class App {

		public static void main(String[] args) {
			Stack<StarMemento> states = new Stack<>();

			Star star = new Star(StarType.SUN, 10000000, 500000);
			System.out.println(star);
			states.add(star.getMemento());
			star.timePasses();
			System.out.println(star);
			states.add(star.getMemento());
			star.timePasses();
			System.out.println(star);
			states.add(star.getMemento());
			star.timePasses();
			System.out.println(star);
			states.add(star.getMemento());
			star.timePasses();
			System.out.println(star);
			while (states.size() > 0) {
				star.setMemento(states.pop());
				System.out.println(star);
			}
		}
	}
	
	public static class Star {

		private StarType type;
		private int ageYears;
		private int massTons;

		public Star(StarType startType, int startAge, int startMass) {
			this.type = startType;
			this.ageYears = startAge;
			this.massTons = startMass;
		}

		public void timePasses() {
			ageYears *= 2;
			massTons *= 8;
			switch (type) {
			case RED_GIANT:
				type = StarType.WHITE_DWARF;
				break;
			case SUN:
				type = StarType.RED_GIANT;
				break;
			case SUPERNOVA:
				type = StarType.DEAD;
				break;
			case WHITE_DWARF:
				type = StarType.SUPERNOVA;
				break;
			case DEAD:
				ageYears *= 2;
				massTons = 0;
				break;
			default:
				break;
			}
		}

		StarMemento getMemento() {

			StarMementoInternal state = new StarMementoInternal();
			state.setAgeYears(ageYears);
			state.setMassTons(massTons);
			state.setType(type);
			return state;

		}

		void setMemento(StarMemento memento) {

			StarMementoInternal state = (StarMementoInternal) memento;
			this.type = state.getType();
			this.ageYears = state.getAgeYears();
			this.massTons = state.getMassTons();

		}

		@Override
		public String toString() {
			return String.format("%s age: %d years mass: %d tons", type.toString(),
					ageYears, massTons);
		}
		
		/**
		 * 
		 * StarMemento implementation
		 * 
		 */
		private static class StarMementoInternal implements StarMemento {

			private StarType type;
			private int ageYears;
			private int massTons;

			public StarType getType() {
				return type;
			}

			public void setType(StarType type) {
				this.type = type;
			}

			public int getAgeYears() {
				return ageYears;
			}

			public void setAgeYears(int ageYears) {
				this.ageYears = ageYears;
			}

			public int getMassTons() {
				return massTons;
			}

			public void setMassTons(int massTons) {
				this.massTons = massTons;
			}
		}
	}
	public interface StarMemento {

	}
	public enum StarType {

		SUN("sun"), RED_GIANT("red giant"), WHITE_DWARF("white dwarf"), SUPERNOVA("supernova"), DEAD("dead star"), UNDEFINED("");

	    private String title;

	    StarType(String title) {
	        this.title = title;
	    }

	    @Override
		public String toString() {
			return title;
		}
	}
	
}
