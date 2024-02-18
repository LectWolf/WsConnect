package cc.mcii.mc.wsconnect.websocket;


import cc.mcii.mc.wsconnect.util.Internationalization;
import cc.mcii.mc.wsconnect.util.JsonUtils;
import cc.mcii.mc.wsconnect.websocket.command.WSCommand;
import cc.mcii.mc.wsconnect.websocket.command.WSCommandFactory;
import cc.mcii.mc.wsconnect.websocket.response.JSONOutput;
import cc.mcii.mc.wsconnect.websocket.response.LogInRequired;
import cc.mcii.mc.wsconnect.websocket.response.LoggedIn;
import cc.mcii.mc.wsconnect.websocket.response.UnknownCommand;
import cc.mcii.mc.wsconnect.wsauth.LoginManager;
import com.google.gson.JsonObject;
import org.bukkit.Bukkit;
import org.java_websocket.WebSocket;
import org.java_websocket.exceptions.WebsocketNotConnectedException;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.util.HashMap;

/**
 * WSServer Websocket 服务
 *
 * @author LectWolf
 * @version 2024/02/17 20:20
 **/
public class WSServer extends WebSocketServer {

    private final HashMap<String, WSCommand> commands = WSCommandFactory.getCommandsHashMap();

    public WSServer(InetSocketAddress address) {
        super(address);
        setReuseAddr(true);
    }


    /**
     * WebSocket启动
     */
    @Override
    public void onStart() {
        Bukkit.getLogger().info(Internationalization.getPhrase("started-websocket"));
    }


    /**
     * 收到连接
     *
     * @param conn      客户
     * @param handshake 握手包
     */
    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        //检查是否登陆
        if (LoginManager.getInstance().isSocketConnected(conn.getRemoteSocketAddress())) {
            sendToClient(conn, new LoggedIn(Internationalization.getPhrase("connection-resumed-message")));
            Bukkit.getLogger().info(Internationalization.getPhrase("connection-resumed-console", conn.getRemoteSocketAddress()));
        } else {
            sendToClient(conn, new LogInRequired(Internationalization.getPhrase("connection-login-message")));
            Bukkit.getLogger().info(Internationalization.getPhrase("connection-login-console", conn.getRemoteSocketAddress()));
        }
    }


    /**
     * 断开连接
     *
     * @param conn   客户
     * @param code   断开代码
     * @param reason 原因
     * @param remote 是否为客户断开
     */
    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        LoginManager.getInstance().logOut(conn.getRemoteSocketAddress());
        Bukkit.getLogger().info(Internationalization.getPhrase("connection-closed-console", conn.getRemoteSocketAddress()));
    }


    /**
     * 连接错误
     *
     * @param conn 客户
     * @param ex   导致错误的异常
     */
    @Override
    public void onError(WebSocket conn, Exception ex) {
        Bukkit.getLogger().warning(Internationalization.getPhrase("connection-error-console", conn.getRemoteSocketAddress(), ex));
    }


    /**
     * 接收到数据
     *
     * @param conn    客户
     * @param message 数据
     */
    @Override
    public void onMessage(WebSocket conn, String message) {
        //排除 不包含command 或 不包含 token 且不为登陆请求 的 用户
        if (!JsonUtils.containsStringProperty(message, JsonUtils.COMMAND_PROPERTY)
                || (!JsonUtils.containsStringProperty(message, JsonUtils.TOKEN_PROPERTY) && !JsonUtils.getStringProperty(message, JsonUtils.COMMAND_PROPERTY).equals("login"))
        ) {
            return;
        }
        String wsCommand = JsonUtils.getStringProperty(message, JsonUtils.COMMAND_PROPERTY);
        String wsToken = JsonUtils.getStringProperty(message, JsonUtils.TOKEN_PROPERTY);
        String wsParams = JsonUtils.getStringProperty(message, JsonUtils.PARAMS_PROPERTY);
        JsonObject wsData = JsonUtils.getObjectProperty(message, JsonUtils.DATA_PROPERTY);

        //处理命令
        WSCommand command = commands.get(wsCommand);

        if (command == null) {
            //不存在的命令
            sendToClient(conn, new UnknownCommand(Internationalization.getPhrase("unknown-command-message"), message));
            Bukkit.getLogger().info(Internationalization.getPhrase("unknown-command-console", wsCommand));
        } else if (!wsCommand.equals("login")
                && !LoginManager.getInstance().isLoggedIn(conn.getRemoteSocketAddress(), wsToken)) {
            sendToClient(conn, new LogInRequired(Internationalization.getPhrase("forbidden-message")));
            Bukkit.getLogger().warning(Internationalization.getPhrase("forbidden-console", conn.getRemoteSocketAddress(), wsCommand));
        } else {
            command.execute(this, conn, wsParams, wsData);
        }

    }


    /**
     * 向客户发送数据
     *
     * @param conn    客户
     * @param content 数据
     */
    public void sendToClient(WebSocket conn, JSONOutput content) {
        try {
            conn.send(content.toJSON());
        } catch (WebsocketNotConnectedException e) {
            LoginManager.getInstance().logOut(conn.getRemoteSocketAddress());
            Bukkit.getLogger().warning(Internationalization.getPhrase("error-disconnected-client"));
        }

    }
}
