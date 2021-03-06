package cn.liuyiyou.blog.interceptor;

import cn.liuyiyou.blog.constant.WebConst;
import cn.liuyiyou.blog.dto.Types;
import cn.liuyiyou.blog.model.Vo.OptionVo;
import cn.liuyiyou.blog.model.Vo.UserVo;
import cn.liuyiyou.blog.service.IOptionService;
import cn.liuyiyou.blog.service.IUserService;
import cn.liuyiyou.blog.utils.AdminCommons;
import cn.liuyiyou.blog.utils.Commons;
import cn.liuyiyou.blog.utils.IPKit;
import cn.liuyiyou.blog.utils.MapCache;
import cn.liuyiyou.blog.utils.TaleUtils;
import cn.liuyiyou.blog.utils.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义拦截器
 * Created by BlueT on 2017/3/9.
 */
@Component
public class BaseInterceptor implements HandlerInterceptor {
    private static final Logger LOGGE = LoggerFactory.getLogger(BaseInterceptor.class);
    private static final String USER_AGENT = "user-agent";

    @Resource
    private IUserService userService;

    @Resource
    private IOptionService optionService;

    private MapCache cache = MapCache.single();

    @Resource
    private Commons commons;

    @Resource
    private AdminCommons adminCommons;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        String contextPath = request.getContextPath();
        String uri = request.getRequestURI();
        LOGGE.info("UserAgent: {}", request.getHeader(USER_AGENT));
        LOGGE.info("用户访问地址: {}, 来路地址: {}", uri, IPKit.getIpAddrByRequest(request));
        //请求拦截处理
        UserVo user = TaleUtils.getLoginUser(request);
        if (null == user) {
            Integer uid = TaleUtils.getCookieUid(request);
            if (null != uid) {
                user = userService.queryUserById(uid);
                request.getSession().setAttribute(WebConst.LOGIN_SESSION_KEY, user);
            }
        }
        if(uri.endsWith(".js") || uri.endsWith(".images")){
            return true;
        }
        if (uri.startsWith(contextPath + "/admin") && !uri.startsWith(contextPath + "/admin/login") && null == user) {
            response.sendRedirect(request.getContextPath() + "/admin/login");
            return false;
        }
        //设置get请求的token
        if (request.getMethod().equals("GET")) {
            String csrf_token = UUID.UU64();
            // 默认存储30分钟
            cache.hset(Types.CSRF_TOKEN.getType(), csrf_token, uri, 30 * 60);
            request.setAttribute("_csrf_token", csrf_token);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        OptionVo ov = optionService.getOptionByName("site_record");
        httpServletRequest.setAttribute("commons", commons);//一些工具类和公共方法
        httpServletRequest.setAttribute("option", ov);
        httpServletRequest.setAttribute("adminCommons", adminCommons);
    }
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
