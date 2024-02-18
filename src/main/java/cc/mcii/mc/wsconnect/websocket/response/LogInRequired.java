package cc.mcii.mc.wsconnect.websocket.response;

import com.google.gson.JsonObject;

/**
 * LoginRequired 登陆请求
 *
 * @author LectWolf
 * @version 2024/02/18 01:48
 **/
public class LogInRequired implements JSONOutput {
    private final String message;

    public LogInRequired(String message) {
        this.message = message;
    }

    @Override
    public int getStatusCode() {
        return 401;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String toJSON() {
        JsonObject object = new JsonObject();
        object.addProperty("status", getStatusCode());
        object.addProperty("statusDescription", "Login Required");
        object.addProperty("message", getMessage());
        return object.toString();
    }
}
