package cc.mcii.mc.wsconnect.wsauth;

import java.net.InetSocketAddress;
import java.util.ArrayList;

/**
 * LoginManager WebSocket用户认证
 *
 * @author LectWolf
 * @version 2024/02/18 00:42
 **/
public class LoginManager {
    //登陆用户
    private final ArrayList<AuthUser> loggedInUsers = new ArrayList<>();
    private static LoginManager instance;

    public static LoginManager getInstance() {
        if (instance == null) {
            instance = new LoginManager();
        }
        return instance;
    }


    /**
     * 添加登陆用户
     *
     * @param user 登陆用户
     */
    public void loginIn(AuthUser user) {
        loggedInUsers.add(user);
    }


    /**
     * 登陆用户登出
     *
     * @param address 登出用户地址
     */
    public void logOut(InetSocketAddress address) {
        loggedInUsers.removeIf(user -> user.getSocketAddress().equals(address));
    }


    /**
     * 取用户
     *
     * @param address 地址
     * @return 用户
     */
    public AuthUser getUser(InetSocketAddress address) {
        for (AuthUser user : loggedInUsers) {
            if (user.getSocketAddress().equals(address)) {
                return user;
            }
        }
        return null;
    }


    /**
     * 检查是否登陆
     *
     * @param address 地址
     * @param token   token
     * @return 是否登陆
     */
    public boolean isLoggedIn(InetSocketAddress address, String token) {
        for (AuthUser user : loggedInUsers) {
            if (user.getSocketAddress().equals(address) && user.getToken().equals(token)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 检查地址是否登陆
     *
     * @param address 地址
     * @return 是否登陆
     */
    public boolean isSocketConnected(InetSocketAddress address) {
        for (AuthUser user : loggedInUsers) {
            if (user.getSocketAddress().equals(address)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 获取所有登陆用户
     *
     * @return 所有登陆用户
     */
    public ArrayList<AuthUser> getLoggedInUsers() {
        return loggedInUsers;
    }
}
