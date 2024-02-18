package cc.mcii.mc.wsconnect.websocket.command;

import java.util.HashMap;

/**
 * WSCommandFactory 命令集
 *
 * @author LectWolf
 * @version 2024/02/18 02:58
 **/
public class WSCommandFactory {
    /**
     * WebSocket命令集
     *
     * @return 命令集合
     */
    public static HashMap<String, WSCommand> getCommandsHashMap() {
        HashMap<String, WSCommand> commands = new HashMap<>();
        commands.put("login", new LogInCommand());
        return commands;
    }
}
