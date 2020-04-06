package todo.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.owasp.html.Sanitizers;
import todo.controller.ObjectMapperFactory;
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
        if (!isValid(request.getContentType())) {
            response.setStatus(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
            return;
        }
        if (!isValid(request.getHeader("Accept"))) {
            response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
            return;
        }
        chain.doFilter(request, response);
    }

    private boolean isValid(String mediaType) {
        return mediaType == null || mediaType.contains("*/*") || mediaType.contains("application/json");
    }
}
