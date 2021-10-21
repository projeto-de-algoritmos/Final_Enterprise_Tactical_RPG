package graphs.exceptions;

public class InexistentNode extends GraphException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2413249606271630639L;

	/**
	 * 
	 */
	public InexistentNode() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public InexistentNode(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public InexistentNode(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public InexistentNode(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public InexistentNode(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
