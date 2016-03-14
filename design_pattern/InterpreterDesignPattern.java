package design_pattern;

import java.util.Stack;

public class InterpreterDesignPattern {

	
	
	public static class App {

		/**
		 * 
		 * Program entry point.
		 * <p>
		 * Expressions can be evaluated using prefix, infix or postfix notations
		 * This sample uses postfix, where operator comes after the operands
		 * 
		 * @param args command line args
		 * 
		 */
		public static void main(String[] args) {
			String tokenString = "4 3 2 - 1 + *";
			Stack<Expression> stack = new Stack<>();

			String[] tokenList = tokenString.split(" ");
			for (String s : tokenList) {
				if (isOperator(s)) {
					Expression rightExpression = stack.pop();
					Expression leftExpression = stack.pop();
					System.out
							.println(String.format(
									"popped from stack left: %d right: %d",
									leftExpression.interpret(),
									rightExpression.interpret()));
					Expression operator = getOperatorInstance(s, leftExpression,
							rightExpression);
					System.out.println(String.format("operator: %s", operator));
					int result = operator.interpret();
					NumberExpression resultExpression = new NumberExpression(result);
					stack.push(resultExpression);
					System.out.println(String.format("push result to stack: %d",
							resultExpression.interpret()));
				} else {
					Expression i = new NumberExpression(s);
					stack.push(i);
					System.out.println(String.format("push to stack: %d",
							i.interpret()));
				}
			}
			System.out
					.println(String.format("result: %d", stack.pop().interpret()));
		}

		public static boolean isOperator(String s) {
			return s.equals("+") || s.equals("-") || s.equals("*");
		}

		public static Expression getOperatorInstance(String s, Expression left,
				Expression right) {
			switch (s) {
			case "+":
				return new PlusExpression(left, right);
			case "-":
				return new MinusExpression(left, right);
			case "*":
				return new MultiplyExpression(left, right);
			}
			return null;
		}
	}
	
	
	public static abstract class Expression {

		public abstract int interpret();

		@Override
		public abstract String toString();
	}
	public static class MinusExpression extends Expression {

		private Expression leftExpression;
		private Expression rightExpression;

		public MinusExpression(Expression leftExpression, Expression rightExpression) {
			this.leftExpression = leftExpression;
			this.rightExpression = rightExpression;
		}

		@Override
		public int interpret() {
			return leftExpression.interpret() - rightExpression.interpret();
		}

		@Override
		public String toString() {
			return "-";
		}

	}

	public static class MultiplyExpression extends Expression {

		private Expression leftExpression;
		private Expression rightExpression;

		public MultiplyExpression(Expression leftExpression,
				Expression rightExpression) {
			this.leftExpression = leftExpression;
			this.rightExpression = rightExpression;
		}

		@Override
		public int interpret() {
			return leftExpression.interpret() * rightExpression.interpret();
		}

		@Override
		public String toString() {
			return "*";
		}

	}
	
	public static class NumberExpression extends Expression {

		private int number;

		public NumberExpression(int number) {
			this.number = number;
		}

		public NumberExpression(String s) {
			this.number = Integer.parseInt(s);
		}

		@Override
		public int interpret() {
			return number;
		}

		@Override
		public String toString() {
			return "number";
		}

	}
	public static class PlusExpression extends Expression {

		private Expression leftExpression;
		private Expression rightExpression;

		public PlusExpression(Expression leftExpression, Expression rightExpression) {
			this.leftExpression = leftExpression;
			this.rightExpression = rightExpression;
		}

		@Override
		public int interpret() {
			return leftExpression.interpret() + rightExpression.interpret();
		}

		@Override
		public String toString() {
			return "+";
		}

	}
}
