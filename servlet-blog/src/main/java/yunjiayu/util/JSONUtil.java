package yunjiayu.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

public class JSONUtil {

    private static volatile ObjectMapper MAPPER;

    private static ObjectMapper getMapper() {
        if (MAPPER == null) {
            synchronized (JSONUtil.class) {
                if (MAPPER == null) {
                    MAPPER = new ObjectMapper();
                }
            }
        }
        return MAPPER;
    }

    /**
     * 对象序列化为json字符串
     */

    public static String serialize(Object o) {
        try {
            return getMapper().writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("序列化对象出错: " + o, e);
        }
    }

    /**
     * 反序列化输入流中的json字符串为java对象
     * http请求发送json数据, 请求头中 Content-Type = application/json
     * HttpServletRequest获取json字符串, 只能通过输入流获取
     */
    public static <T> T deserialize(InputStream is, Class<T> valueType) {
        try {
            return getMapper().readValue(is, valueType);
        } catch (IOException e) {
            throw new RuntimeException("反序列化json字符串出错", e);
        }
    }
}
