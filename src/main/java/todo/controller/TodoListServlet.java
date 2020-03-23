package todo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import todo.model.todos.Todo;
import todo.model.todos.TodoList;
import todo.model.todos.TodoNotFoundException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/api/todos/*")
public class TodoListServlet extends HttpServlet {

    private ObjectMapper objectMapper = ObjectMapperFactory.createObjectMapper();
    private TodoList todoList;

    @Override
    public void init() {
        this.todoList = new TodoList();
        //Test data
        this.todoList.addTodo(new Todo(1, "Do my homework", "Study", LocalDate.of(2020, 03, 30)));
        this.todoList.addTodo(new Todo(2, "Go shopping", "Home", LocalDate.of(2020, 03, 24)));
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Todo> todos = todoList.getTodos(); //Will become the List that we'll return to the Client

        //Check if the Client is asking for a specific category if not we'll just return the json
        if (!(request.getParameter("category") == null)) {
            TodoList returnTodo = new TodoList();
            for (Todo todoEntry : todos) {
                if (todoEntry.getCategory().equals(request.getParameter("category")))
                    returnTodo.addTodo(todoEntry);
            }
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json");
            objectMapper.writeValue(response.getOutputStream(), returnTodo);
        } else {
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json");
            objectMapper.writeValue(response.getOutputStream(), todos);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Todo newTodo = objectMapper.readValue(request.getInputStream(), Todo.class); //Reads the json value coming in and transforms it into a java class object
        todoList.addTodo(newTodo);
        List<Todo> todos = todoList.getTodos(); //Will become the List that we'll return to the Client
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");
        objectMapper.writeValue(response.getOutputStream(), todos);
    }

    @Override
    public void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String string = request.getRequestURI();
        int id = Integer.valueOf(string.substring(string.length() - 1)); //To get the id at the end of the URI
        Todo newTodo = objectMapper.readValue(request.getInputStream(), Todo.class);
        newTodo.setId(id);
        try {
            todoList.updateTodo(newTodo);
        } catch (TodoNotFoundException e) {
            e.printStackTrace();
        }
        List<Todo> todos = todoList.getTodos(); //Will become the List that we'll return to the Client
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");
        objectMapper.writeValue(response.getOutputStream(), todos);
    }

    @Override
    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String string = request.getRequestURI();
        int id = Integer.valueOf(string.substring(string.length() - 1)); //To get the id at the end of the URI
        try {
            todoList.removeTodo(id);
        } catch (TodoNotFoundException e) {
            e.printStackTrace();
        }
        List<Todo> todos = todoList.getTodos(); //Will become the List that we'll return to the Client
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");
        objectMapper.writeValue(response.getOutputStream(), todos);
    }

}
