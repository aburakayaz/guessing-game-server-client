package cs408.Common.Commands;

import java.util.ArrayList;

public class Commands extends ArrayList<Command> {
    public Commands() {
    }

    public Command find(String name) {
        return this.stream().filter(
                command -> command.getName().equals(name)
        ).findFirst().orElse(null);
    }
}
