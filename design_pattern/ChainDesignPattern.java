package design_pattern;

public class ChainDesignPattern {

	
	
	public static class App {

		/**
		 * Program entry point
		 * @param args command line args
		 */
		public static void main(String[] args) {

			OrcKing king = new OrcKing();
			king.makeRequest(new Request(RequestType.COLLECT_TAX, "collect tax"));
			king.makeRequest(new Request(RequestType.DEFEND_CASTLE, "defend castle"));
			king.makeRequest(new Request(RequestType.TORTURE_PRISONER,
					"torture prisoner"));
			king.makeRequest(new Request(RequestType.COLLECT_TAX, "collect tax"));

		}
	}
	
	public static class OrcKing {

		RequestHandler chain;

		public OrcKing() {
			buildChain();
		}
		//each RequestHandler implementor will have reference to child or low level object initilized in constructor using super
		private void buildChain() {
			chain = new OrcCommander(new OrcOfficer(new OrcSoldier(null)));
		}

		public void makeRequest(Request req) {
			chain.handleRequest(req);
		}

	}
	
	public static abstract class RequestHandler {

		private RequestHandler next;

		public RequestHandler(RequestHandler next) {
			this.next = next;
		}

		public void handleRequest(Request req) {
			if (next != null) {
				next.handleRequest(req);
			}
		}

		protected void printHandling(Request req) {
			System.out.println(this + " handling request \"" + req + "\"");
		}

		@Override
		public abstract String toString();
	}
	
	public static class OrcCommander extends RequestHandler {

		public OrcCommander(RequestHandler handler) {
			super(handler);
		}

		@Override
		public void handleRequest(Request req) {
			if (req.getRequestType().equals(RequestType.DEFEND_CASTLE)) {
				printHandling(req);
			} else {
				super.handleRequest(req);
			}
		}

		@Override
		public String toString() {
			return "Orc commander";
		}
	}
	
	public static class OrcOfficer extends RequestHandler {

		public OrcOfficer(RequestHandler handler) {
			super(handler);
		}

		@Override
		public void handleRequest(Request req) {
			if (req.getRequestType().equals(RequestType.TORTURE_PRISONER)) {
				printHandling(req);
			} else {
				super.handleRequest(req);
			}
		}

		@Override
		public String toString() {
			return "Orc officer";
		}

	}
	
	public static class OrcSoldier extends RequestHandler {

		public OrcSoldier(RequestHandler handler) {
			super(handler);
		}

		@Override
		public void handleRequest(Request req) {
			if (req.getRequestType().equals(RequestType.COLLECT_TAX)) {
				printHandling(req);
			} else {
				super.handleRequest(req);
			}
		}

		@Override
		public String toString() {
			return "Orc soldier";
		}
	}	

public static class Request {

	private String requestDescription;
	private RequestType requestType;

	public Request(RequestType requestType, String requestDescription) {
		this.setRequestType(requestType);
		this.setRequestDescription(requestDescription);
	}

	public String getRequestDescription() {
		return requestDescription;
	}

	public void setRequestDescription(String requestDescription) {
		this.requestDescription = requestDescription;
	}

	public RequestType getRequestType() {
		return requestType;
	}

	public void setRequestType(RequestType requestType) {
		this.requestType = requestType;
	}

	@Override
	public String toString() {
		return getRequestDescription();
	}
}
 enum RequestType {

	DEFEND_CASTLE, TORTURE_PRISONER, COLLECT_TAX

}
 }