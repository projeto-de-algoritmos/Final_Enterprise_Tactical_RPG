/**
 * 
 */
package graphs.exceptions;

/**
 * @author davi
 *
 */
public class NodeAlreadyExists extends GraphException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5090462153780451402L;

	/**
	 * 
	 */
	public NodeAlreadyExists() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public NodeAlreadyExists(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public NodeAlreadyExists(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public NodeAlreadyExists(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public NodeAlreadyExists(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
