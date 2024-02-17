package cc.mcii.mc.wsconnect;

import cc.mcii.mc.wsconnect.config.ConfigManager;
import cc.mcii.mc.wsconnect.util.Internationalization;
import cc.mcii.mc.wsconnect.websocket.WSServer;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.java_websocket.server.DefaultSSLWebSocketServerFactory;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;

/**
 * WsConnect Minecraft插件载入入口
 *
 * @author LectWolf
 * @version 2024/02/17 20:01
 **/
public class WsConnect extends JavaPlugin {
    private static WsConnect instance;
    //Websocket 服务和线程
    private WSServer wsServer;
    private Thread wsThread;

    public static WsConnect getInstance() {
        return instance;
    }

    public WSServer getWsServer() {
        return wsServer;
    }


    /**
     * 插件启用
     */
    @Override
    public void onEnable() {
        instance = this;
        //设置语言文件
        Internationalization.setCurrentLocale(ConfigManager.getInstance().getLanguage());

        //启动 WebSocket 服务
        try {
            startWebSocket();
        } catch (Exception e) {
            Bukkit.getLogger().warning(Internationalization.getPhrase("websocket-starterror"));
            e.printStackTrace();
        }
    }

    /**
     * 插件卸载
     */
    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    /**
     * 启动 WebSocket 服务
     */
    private void startWebSocket() throws Exception {
        wsServer = new WSServer(ConfigManager.getInstance().getSocketAddress());

        //启用SSL
        if (ConfigManager.getInstance().isSslEnabled()) {
            String STORETYPE = ConfigManager.getInstance().getStoreType();
            String KEYSTORE = ConfigManager.getInstance().getKeyStore();
            String STOREPASSWORD = ConfigManager.getInstance().getSocketPassword();
            String KEYPASSWORD = ConfigManager.getInstance().getKeyPassword();

            KeyStore ks = KeyStore.getInstance(STORETYPE);
            File kf = new File(KEYSTORE);
            ks.load(new FileInputStream(kf), STOREPASSWORD.toCharArray());

            KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
            kmf.init(ks, KEYPASSWORD.toCharArray());
            TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
            tmf.init(ks);

            SSLContext sslContext;
            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);

            wsServer.setWebSocketFactory(new DefaultSSLWebSocketServerFactory(sslContext));

        }
        //启动WebSocket 服务
        wsThread = new Thread(() -> wsServer.run());
        wsThread.start();
    }

}
