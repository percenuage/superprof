package fr.superprof.command;

public enum CommandEnum {

    CLIENT_SUBSCRIBE_PIRATE ("/I"),
    CLIENT_MOVE_PIRATE ("/D"),
    PIRATE ("/i"),
    DENY_MOVE_PIRATE ("/R"),
    ALLOW_MOVE_PIRATE ("/A"),
    PIRATES ("/P"),
    NEW_PIRATE ("/n"),
    DELETE_PIRATE ("/s"),
    MOVE_PIRATE ("/p"),
    ISLAND ("/C"),
    CRAZY_MONKEYS ("/e"),
    HUNTER_MONKEYS ("/c"),
    RHUMS ("/B"),
    RHUM ("/b"),
    TREASURE("/T"),
    NEW_GAME ("/N");

    private String command;

    CommandEnum(String command) {
        this.command = command;
    }

    public static CommandEnum fromString(String value) {
        for (CommandEnum cmd : values()) {
            if (cmd.command.equals(value)) {
                return cmd;
            }
        }
        throw new NullPointerException("This command does not exist : " + value);
    }

    @Override
    public String toString() {
        return this.command;
    }

}
