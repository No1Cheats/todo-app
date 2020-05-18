package todo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import todo.model.users.User;
import todo.model.users.UserAdmin;
import todo.model.users.UserAlreadyExistsException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/api/users/*")
public class UserAdminServlet extends HttpServlet {

    private ObjectMapper objectMapper = ObjectMapperFactory.createObjectMapper();

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            User user = objectMapper.readValue(request.getInputStream(), User.class);
            UserAdmin userAdmin = UserAdmin.getInstance(); //Singleton implementation
            userAdmin.registerUser(user.getName(), user.getPassword());
            objectMapper.writeValue(response.getOutputStream(), "User " + user.getName() + " successfully created");
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json");
        } catch (JsonProcessingException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType("application/json");
            e.printStackTrace();
        } catch (UserAlreadyExistsException e){
            response.setStatus(HttpServletResponse.SC_CONFLICT);
            response.setContentType("application/json");
            e.printStackTrace();
        }

    }

}
