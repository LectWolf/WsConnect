package cc.mcii.mc.wsconnect.websocket.response;

import com.google.gson.JsonObject;

/**
 * UnknownCommand 未知命令
 *
 * @author LectWolf
 * @version 2024/02/18 03:07
 **/
public class UnknownCommand implements JSONOutput {
    private final String message;
    private final String respondsTo;

    public UnknownCommand(String message, String respondsTo) {
        this.message = message;
        this.respondsTo = respondsTo;
    }

    @Override
    public int getStatusCode() {
        return 400;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public String getRespondsTo() {
        return respondsTo;
    }

    @Override
    public String toJSON() {
        JsonObject object = new JsonObject();
        object.addProperty("status", getStatusCode());
        object.addProperty("statusDescription", "Unknown Command");
        object.addProperty("respondsTo", getRespondsTo());
        object.addProperty("message", getMessage());
        return object.toString();
    }
}