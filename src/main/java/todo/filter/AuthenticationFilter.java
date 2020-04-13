package todo.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import todo.controller.ObjectMapperFactory;
import todo.model.users.InvalidCredentialsException;
import todo.model.users.User;
import todo.model.users.UserAdmin;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static todo.filter.AuthenticationHelper.authenticateUser;

@WebFilter(urlPatterns = "/api/todos/*")
public class AuthenticationFilter extends HttpFilter {

    private UserAdmin userAdmin = UserAdmin.getInstance(); //Singleton implementation

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        User newUser = null;
        try {
            newUser = authenticateUser(request);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            e.printStackTrace();
            return; //Good enuff
        }
        //User loggedInUser;
        //loggedInUser = userAdmin.loginUser(newUser.getName(), newUser.getPassword());
        request.setAttribute("user", newUser);
        response.setStatus(HttpServletResponse.SC_OK);
        chain.doFilter(request, response);
    }

}
