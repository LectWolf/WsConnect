package cc.mcii.mc.wsconnect.websocket.command;

import cc.mcii.mc.wsconnect.websocket.WSServer;
import com.google.gson.JsonObject;
import org.java_websocket.WebSocket;

/**
 * WSCommand 命令接口
 *
 * @author LectWolf
 * @version 2024/02/18 02:55
 **/
public interface WSCommand {
    /**
     * 接口
     *
     * @param wsServer WebSocket
     * @param conn     客户
     * @param params   参数
     * @param data     数据
     */
    void execute(WSServer wsServer, WebSocket conn, String params, JsonObject data);
}
