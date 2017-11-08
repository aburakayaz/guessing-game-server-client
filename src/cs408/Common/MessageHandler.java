package cs408.Common;

public interface MessageHandler{
    /**
     * Just an empty interface to decide what will happen when a information message to be printed.
     * It can be GUI, it can be println etc.
     * We will use the GUI in this homework.
     */
    public void showMessage(String message);
}