package todo.controller;

import org.owasp.html.Sanitizers;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

@WebFilter(urlPatterns = "/api/todos/*")
public class MediaTypeFilter extends HttpFilter {

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request.getMethod().equals("POST")) {
            request.setCharacterEncoding("UTF-8");
            Enumeration<String> e = request.getParameterNames();
            while (e.hasMoreElements()) {
                String value = request.getParameter(e.nextElement());
                if (!Sanitizers.FORMATTING.and(Sanitizers.LINKS).sanitize(value).equals(value)) {
                    response.sendRedirect("error.html");
                    return;
                }
            }
        }
        chain.doFilter(request, response);
    }
}
