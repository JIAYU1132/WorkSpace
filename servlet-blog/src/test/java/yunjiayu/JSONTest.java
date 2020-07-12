package yunjiayu;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import yunjiayu.model.Article;
import yunjiayu.model.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JSONTest {

    /**
     * 模拟 http 接收的 json 数据: json 格式解析/反序列化为 java 对象
     * 响应 http 的 json 格式数据: java 对象封装/序列化为 json 数据类型
     * 使用 jackson 框架做 json 序列化和反序列化操作
     */

    @Test
    public void t1() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> map = new HashMap<>();
        map.put("1", "a");
        map.put("2", "b");
        map.put("3", "c");
        String s = mapper.writeValueAsString(map); // 把 java 对象序列化为 json 数据类型
        System.out.println(s);
        Map m = mapper.readValue(s, Map.class); // 把 json 格式反序列化为 java 对象
        System.out.println(m);
//        Article a1 = new Article();
//        a1.setId(1);
//        a1.setTitle("标题1");
//        a1.setContent("内容1");
//        Article a2 = new Article();
//        a1.setId(1);
//        a1.setTitle("标题2");
//        a1.setContent("内容2");
//        List<Article> articles = new ArrayList<>();
//        articles.add(a1);
//        articles.add(a2);
//        Response r = new Response();
//        r.setData(articles);
//
//        String s =mapper.writerWithDefaultPrettyPrinter().writeValueAsString(r);
//        System.out.println(s);

    }
}
