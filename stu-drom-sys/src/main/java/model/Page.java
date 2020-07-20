package model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Setter
@Getter
@ToString
public class Page {
    private String searchText; //搜索的内容
    private String sortOrder; //排序的方式：升序还是降序
    private Integer pageSize; //每页的数量
    private Integer pageNumber; //当前的页码

    /**
     * 1. request输入流只能够获取请求体中的数据, 依赖程序自己解析
     * (如果使用输入流获取, 获取到的是空, 因为数据是在url中)
     * 2. request.getParameter()方法可以获取url和请求体中的数据(数据的格式必须为: k1=v1&k2=v2...)
     * @param req
     * @return
     */

    public static Page parse(HttpServletRequest req) {
        Page page = new Page();
        page.searchText = req.getParameter("searchText");
        page.sortOrder = req.getParameter("sortOrder");
        page.pageSize = Integer.parseInt(req.getParameter("pageSize"));
        page.pageNumber = Integer.parseInt(req.getParameter("pageNumber"));
        return page;
    }
}
