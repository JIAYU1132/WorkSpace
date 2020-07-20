package filter;

import model.Response;
import util.JSONUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

// 过滤器: http请求的url匹配过滤器路径规则,才会过滤(调用Filter中的方法)
@WebFilter("/*")
public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest)request;
        HttpServletResponse httpServletResponse = (HttpServletResponse)response;
        /**
         *页面的静态资源, 后台服务
         *访问首页需要处理的敏感资源:
         * 1. 首页: /public/page/main.html. 没有登陆重定向到登录页面
         * 2. 后端的服务资源: 除登陆接口 /user/login 之外, 其他接口没有登录的时候, 返回没有登录时候的json信息
         */

        // 获取session信息
        HttpSession session = httpServletRequest.getSession(false); //没有session的时候, 返回null
        if (session == null) { //没有登录
            //获取当前http请求的路径
            String uri = httpServletRequest.getServletPath();
            if ("/public/page/main.html".equals(uri)) { //首页没有登录, 就进行重定向
                String scheme = httpServletRequest.getScheme(); //获取http
                String host = httpServletRequest.getServerName(); //获取服务器域名或者ip
                int port = httpServletRequest.getServerPort(); //获取服务器端口号
                String contextPath = httpServletRequest.getContextPath(); //获取项目部署名
                String basePath = scheme + "://" + host + ":" + port + contextPath;
                httpServletResponse.sendRedirect(basePath + "/public/index.html");
                return;
            } else if (!"/user/login".equals(uri) && !uri.startsWith("/public/")
                            && !uri.startsWith("/static/")) {
                httpServletRequest.setCharacterEncoding("UTF-8");//设置请求数据的编码格式
                httpServletResponse.setCharacterEncoding("UTF-8");//设置响应数据的编码格式
                httpServletResponse.setContentType("application/json");//设置响应的数据格式
                Response r = new Response();
                r.setCode("301"); //这个301不是响应的状态码, 而是响应体的字段
                r.setMessage("未授权的http请求");
                PrintWriter pw = httpServletResponse.getWriter();
                pw.println(JSONUtil.write(r));
                pw.flush();
                return;
            }
        }
        chain.doFilter(request, response); //过滤器向下调用, 再次过滤

    }

    @Override
    public void destroy() {

    }
}
