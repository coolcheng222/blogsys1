package com.sealll.shiro.filter;

import com.sealll.constant.ParameterConstants;
import org.apache.shiro.util.ThreadContext;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author sealll
 * @time 2021/4/25 16:29
 */
public class JedisCloserFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        chain.doFilter(request, response);
        Object o = ThreadContext.get(ParameterConstants.JEDIS);
        Object p = ThreadContext.get(ParameterConstants.PIPELINE);
        if(o != null){
            ((Pipeline)p).sync();
            ((Jedis)o).close();
        }
    }

    @Override
    public void destroy() {

    }
}
