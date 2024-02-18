package cc.mcii.mc.wsconnect.websocket.response;

import cc.mcii.mc.wsconnect.config.UserType;
import com.google.gson.JsonObject;


/**
 * LoggedIn 重新登陆
 *
 * @author LectWolf
 * @version 2024/02/18 01:32
 **/
public class LoggedIn implements JSONOutput {
    private final String message;
    private String respondsTo;
    private String username;
    private UserType userType;
    private String token;

    public LoggedIn(String message) {
        this.message = message;
    }

    public LoggedIn(String message, String respondsTo, String username, UserType userType, String token) {
        this.message = message;
        this.respondsTo = respondsTo;
        this.username = username;
        this.userType = userType;
        this.token = token;
    }

    public String getRespondsTo() {
        return respondsTo;
    }

    public String getUsername() {
        return username;
    }

    public String getUserType() {
        switch (userType) {
            case ROBOT:
                return "ROBOT";
            case ADMIN:
                return "ADMIN";
            case USER:
                return "USER";
            default:
                return "VIEWER";
        }
    }

    public String getToken() {
        return token;
    }

    @Override
    public int getStatusCode() {
        return 200;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String toJSON() {
        JsonObject object = new JsonObject();
        object.addProperty("status", getStatusCode());
        object.addProperty("statusDescription", "LoggedIn");
        object.addProperty("respondsTo", getRespondsTo());
        object.addProperty("username", getUsername());
        object.addProperty("usertype", getUserType());
        object.addProperty("token", getToken());
        object.addProperty("message", getMessage());
        return object.toString();
    }
}
