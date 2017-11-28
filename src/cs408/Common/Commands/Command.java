package cs408.Common.Commands;

public interface Command {
    String getName();

    void act();

    void setFullInput(String fullInput);
}
