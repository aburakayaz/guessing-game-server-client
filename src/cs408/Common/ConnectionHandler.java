package cs408.Common;

public interface ConnectionHandler {
	/**
	 * Just an empty interface to decide what will happen when connection state has changed.
	 */
	public void changeState(boolean connected);
}
