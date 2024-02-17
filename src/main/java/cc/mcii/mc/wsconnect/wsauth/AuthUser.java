package cc.mcii.mc.wsconnect.wsauth;

import cc.mcii.mc.wsconnect.config.UserType;

import java.net.InetSocketAddress;

/**
 * AuthUser 登陆用户
 *
 * @author LectWolf
 * @version 2024/02/18 00:44
 **/
public class AuthUser {
    private final String username;
    private final InetSocketAddress socketAddress;
    private final String token;
    private final UserType userType;

    public AuthUser(InetSocketAddress socketAddress, String username, String token, UserType userType) {
        this.username = username;
        this.socketAddress = socketAddress;
        this.token = token;
        this.userType = userType;
    }

    public String getUsername() {
        return username;
    }

    public InetSocketAddress getSocketAddress() {
        return socketAddress;
    }

    public String getToken() {
        return token;
    }

    public UserType getUserType() {
        return userType;
    }
}
