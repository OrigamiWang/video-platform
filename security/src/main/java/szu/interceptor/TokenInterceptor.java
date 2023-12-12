package szu.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import szu.common.service.RedisService;
import szu.model.User;
import szu.util.AuthUtil;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * JWT过滤器
 */
@Component
@Slf4j
public class TokenInterceptor extends OncePerRequestFilter {

    private static final String tokenHeader = "Authorization"; // token所在的header

    @Value("${redis.user_prefix}")
    private String REDIS_USER_PREFIX;

    @Resource
    private RedisService redisService;

    /**
     * 对带token的request进行user的读取，以便AuthUtil进行使用
     *
     * @param request 请求
     * @param response 响应
     * @param chain 过滤器链
     */
    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request,
                                    @Nonnull HttpServletResponse response,
                                    @Nonnull FilterChain chain) throws ServletException, IOException {

        if (AuthUtil.getCurrentUser().isEmpty()) {
            // 当前SecurityContext中没有用户信息，则需要读取request中的token进行数据获取
            String token = request.getHeader(tokenHeader);
            if (StringUtils.hasLength(token)) {
                // token非空的情况，从redis获取user
                User user = (User) redisService.get(REDIS_USER_PREFIX + token);
                // 如果redis中有user，则将user放入SecurityContext中
                if (user != null) {
                    log.info("用户载入成功，token: {}", token);
                    AuthUtil.setCurrentUser(token, user);
                }
                // 其余情况暂不做处理
            }
        }
        chain.doFilter(request, response);
    }
}


