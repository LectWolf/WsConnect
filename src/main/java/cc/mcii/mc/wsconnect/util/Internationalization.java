package cc.mcii.mc.wsconnect.util;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Internationalization 语言文件
 *
 * @author LectWolf
 * @version 2024/02/17 23:01
 **/
public class Internationalization {
    private static ResourceBundle messages = ResourceBundle.getBundle("languages", new Locale("zh_CN"));

    /**
     * 设置语言文件
     *
     * @param locale 语言名
     */
    public static void setCurrentLocale(String locale) {
        messages = ResourceBundle.getBundle("languages", new Locale(locale));
    }


    /**
     * 获取当前语种
     *
     * @return 当前语种
     */
    public static Locale getCurrentLocale() {
        return messages.getLocale();
    }


    /**
     * 获取翻译语言
     *
     * @return 翻译语言
     */
    public static String getPhrase(String phrase, Object... params) {
        String msg = messages.getString(phrase);
        msg = MessageFormat.format(msg, params);
        return msg;
    }
}
