package servlets.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

/**
 * PortalFilter
 * Created on: May 13, 2018
 * Author: marc
 */
@WebFilter("*")
public class PortalFilter implements Filter {
    private static final Set<String> ALLOWED_PATHS = Collections.unmodifiableSet(new HashSet<String>(
            Arrays.asList("/signin", "/signup")));

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        HttpSession session = httpServletRequest.getSession(false);
        String path = httpServletRequest.getRequestURI().substring(httpServletRequest.getContextPath().length()).replaceAll("[/]+$", "");
        boolean loggedIn = (session != null && session.getAttribute("userId") != null);
        boolean allowedPath = ALLOWED_PATHS.contains(path);

        if (loggedIn || allowedPath || path.startsWith("/assets")) {
            filterChain.doFilter(servletRequest, servletResponse);
        }
        else {
            httpServletResponse.sendRedirect("/signin");
        }
    }

    public void destroy() {

    }
}
