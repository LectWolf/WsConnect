package cc.mcii.mc.wsconnect.util;


import com.google.gson.*;

/**
 * JsonUtils WebSocket数据解析
 *
 * @author LectWolf
 * @version 2024/02/18 02:12
 **/
public class JsonUtils {

    public final static String COMMAND_PROPERTY = "command";
    public final static String TOKEN_PROPERTY = "token";
    public final static String PARAMS_PROPERTY = "params";
    public final static String DATA_PROPERTY = "data";

    /**
     * 检查是否为JSON格式数据
     *
     * @param data 检查数据
     * @return 是否正确
     */
    public static boolean isValidJson(String data) {
        Gson gson = new Gson();
        try {
            Object jsonObject = gson.fromJson(data, Object.class).getClass();
            return !jsonObject.equals(String.class);
        } catch (JsonSyntaxException e) {
            return false;
        }
    }


    /**
     * 检查json数据是否包含某参数
     *
     * @param data     json数据
     * @param property 参数
     * @return 是否包含
     */
    public static boolean containsProperty(String data, String property) {
        if (!isValidJson(data)) {
            return false;
        }
        JsonObject object = JsonParser.parseString(data).getAsJsonObject();
        JsonElement elem = object.get(property);
        return elem != null;
    }


    /**
     * 检查json数据是否包含某参数且为String
     *
     * @param data     json数据
     * @param property 参数
     * @return 是否包含
     */
    public static boolean containsStringProperty(String data, String property) {
        if (!isValidJson(data)) {
            return false;
        }
        JsonObject object = JsonParser.parseString(data).getAsJsonObject();
        JsonElement elem = object.get(property);
        if (elem == null) {
            return false;
        }
        try {
            elem.getAsString();
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    /**
     * 取json数据的某个参数值
     *
     * @param data     json数据
     * @param property 参数
     * @return 值
     */
    public static String getStringProperty(String data, String property) {
        JsonObject object = JsonParser.parseString(data).getAsJsonObject();
        JsonElement elem = object.get(property);
        if (elem != null) {
            return elem.getAsString();
        }
        return "";
    }


    /**
     * 取json数据的某个参数object
     *
     * @param data     json数据
     * @param property 参数
     * @return object
     */
    public static JsonObject getObjectProperty(String data, String property) {
        JsonObject object = JsonParser.parseString(data).getAsJsonObject();
        JsonElement elem = object.get(property);
        if (elem != null) {
            return elem.getAsJsonObject();
        }
        return null;
    }
}
