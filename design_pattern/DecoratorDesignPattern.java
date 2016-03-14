package design_pattern;

public class DecoratorDesignPattern {
	//decorator design pattern adds more functionality to underlying interface

	public class WindowDecoratorTest {
		//	@Test  //import org.junit.Test;
		public void testWindowDecoratorTest() {
			Window decoratedWindow = new VerticalScrollBarDecorator(new SimpleWindow());
			// Print the Window's description
			System.out.println(decoratedWindow.getDescription());
		}
	}

	public interface Window {
		public void draw(); // Draws the Window

		public String getDescription(); // Returns a description of the Window
	}

	// Extension of a simple Window without any scrollbars
	static class SimpleWindow implements Window {
		public void draw() {
			// Draw window
		}

		public String getDescription() {
			return "simple window";
		}
	}

	// abstract decorator class - note that it implements Window
	static abstract class WindowDecorator implements Window {
		protected Window windowToBeDecorated; // the Window being decorated

		public WindowDecorator(Window windowToBeDecorated) {
			this.windowToBeDecorated = windowToBeDecorated;
		}

		public void draw() {
			windowToBeDecorated.draw(); //Delegation
		}

		public String getDescription() {
			return windowToBeDecorated.getDescription(); //Delegation
		}
	}

	// The first concrete decorator which adds vertical scrollbar functionality
	static class VerticalScrollBarDecorator extends WindowDecorator {
		public VerticalScrollBarDecorator(Window windowToBeDecorated) {
			super(windowToBeDecorated);
		}

		@Override
		public void draw() {
			super.draw();
			drawVerticalScrollBar();
		}

		private void drawVerticalScrollBar() {
			// Draw the vertical scrollbar
		}

		@Override
		public String getDescription() {
			return super.getDescription() + ", including vertical scrollbars";
		}
	}

}
