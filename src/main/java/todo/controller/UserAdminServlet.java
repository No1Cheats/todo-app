package todo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import todo.model.todos.Todo;
import todo.model.users.User;
import todo.model.users.UserAdmin;
import todo.model.users.UserAlreadyExistsException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/api/users/*")
public class UserAdminServlet extends HttpServlet {

    private UserAdmin userAdmin = UserAdmin.getInstance(); //Singleton implementation
    private ObjectMapper objectMapper = ObjectMapperFactory.createObjectMapper();

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            User newUser = objectMapper.readValue(request.getInputStream(), User.class);
            userAdmin.registerUser(newUser.getName(), newUser.getPassword());
            objectMapper.writeValue(response.getOutputStream(), "User " + newUser.getName() + " successfully created");
        } catch (UserAlreadyExistsException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType("application/json");
            e.printStackTrace();
            return;
        }
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");
    }

}
