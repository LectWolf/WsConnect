package cc.mcii.mc.wsconnect.config;

import cc.mcii.mc.wsconnect.WsConnect;
import cc.mcii.mc.wsconnect.util.EncryptSha256Util;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import java.net.InetSocketAddress;

/**
 * ConfigManager 配置文件管理器
 *
 * @author LectWolf
 * @version 2024/02/17 21:38
 **/
public class ConfigManager {
    private static ConfigManager instance;
    private final WsConnect plugin = (WsConnect) Bukkit.getPluginManager().getPlugin("WsConnect");
    private final FileConfiguration config = plugin.getConfig();


    public static ConfigManager getInstance() {
        if (instance == null) {
            instance = new ConfigManager();
        }
        return instance;
    }

    private ConfigManager() {
        loadConfig();
    }


    /**
     * 初始化配置文件
     */
    private void loadConfig() {
        //设置随机密码
        config.addDefault("websocket.password", EncryptSha256Util.getSalt(16));
        config.options().copyDefaults(true);
        plugin.saveConfig();
    }


    public boolean isSslEnabled() {
        return config.getBoolean("websocket.useSSL");
    }

    public String getStoreType() {
        return config.getString("websocket.StoreType");
    }

    public String getKeyStore() {
        return config.getString("websocket.KeyStore");
    }

    public String getStorePassword() {
        return config.getString("websocket.StorePassword");
    }

    public String getKeyPassword() {
        return config.getString("websocket.KeyPassword");
    }

    public String getSocketPassword() {
        return config.getString("websocket.password");
    }

    public String getLanguage() {
        return config.getString("config.language");
    }

    public InetSocketAddress getSocketAddress() {
        return new InetSocketAddress(config.getString("websocket.host"), config.getInt("websocket.port"));
    }
}
