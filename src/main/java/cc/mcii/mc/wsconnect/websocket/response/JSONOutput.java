package cc.mcii.mc.wsconnect.websocket.response;

/**
 * JSONOutput WebSocket传输json
 *
 * @author LectWolf
 * @version 2024/02/18 01:16
 **/
public interface JSONOutput {
    /**
     * 获取状态码
     *
     * @return 状态码
     */
    int getStatusCode();


    /**
     * 状态码注释信息
     *
     * @return 注释信息
     */
    String getMessage();


    /**
     * 需要通过WebSocket发送的json数据
     *
     * @return json数据
     */
    String toJSON();
}
