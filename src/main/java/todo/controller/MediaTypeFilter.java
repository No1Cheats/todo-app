package todo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.owasp.html.Sanitizers;
import todo.model.todos.Todo;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

@WebFilter(urlPatterns = "/api/todos/*")
public class MediaTypeFilter extends HttpFilter {

    private ObjectMapper objectMapper = ObjectMapperFactory.createObjectMapper();

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request.getMethod().equals("POST")) {
            request.setCharacterEncoding("UTF-8");
            String value = request.getContentType();
            //Don't know where to add Accept???
            if (!Sanitizers.FORMATTING.and(Sanitizers.LINKS).sanitize(value).equals(value)) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.sendRedirect("");
                return;
            }
        }
        chain.doFilter(request, response);
    }
}
