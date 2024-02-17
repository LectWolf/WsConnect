package cc.mcii.mc.wsconnect.cloudexpansion;

import cc.mcii.mc.wsconnect.WsConnect;

/**
 * CloudExpansionManager 云拓展管理器
 *
 * @author LectWolf
 * @version 2024/02/17 21:17
 **/
public class CloudExpansionManager {
    private static final String API_URL = "http://api.mcii.cc/v1/";
    private final WsConnect plugin;


    public CloudExpansionManager(WsConnect plugin) {
        this.plugin = plugin;
    }
}
