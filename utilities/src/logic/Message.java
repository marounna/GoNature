package logic;

import java.io.Serializable;

public class Message implements Serializable {
    private static final long serialVersionUID = 1L; // Simple version identifier
    private String command;
    private Object msg;

    // Constructor
    public Message(String command, Object payload) {
        this.command = command;
        this.msg = payload;
    }

    // Getters
    public String getCommand() {
        return command;
    }

    public Object getPayload() {
        return msg;
    }
}

