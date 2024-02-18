package cc.mcii.mc.wsconnect.websocket.command;

import cc.mcii.mc.wsconnect.websocket.WSServer;
import cc.mcii.mc.wsconnect.wsauth.LoginManager;
import com.google.gson.JsonObject;
import org.java_websocket.WebSocket;

/**
 * LogInCommand 登陆
 *
 * @author LectWolf
 * @version 2024/02/18 03:00
 **/
public class LogInCommand implements WSCommand {

    @Override
    public void execute(WSServer wsServer, WebSocket conn, String params, JsonObject data) {
        // 已登陆,不处理
        if (LoginManager.getInstance().isSocketConnected(conn.getRemoteSocketAddress())) {
            return;
        }
        //没写完,记录点
    }
}
