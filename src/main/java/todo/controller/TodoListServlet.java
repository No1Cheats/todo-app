package todo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import todo.model.todos.Todo;
import todo.model.todos.TodoList;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/api/todos")
public class TodoListServlet extends HttpServlet {

    private ObjectMapper objectMapper = ObjectMapperFactory.createObjectMapper();
    private TodoList todoList;

    @Override
    public void init(){
        this.todoList = new TodoList();
        //test data
        this.todoList.addTodo(new Todo(1,"Do my homework","Study", LocalDate.of(2020,03,30)));
        this.todoList.addTodo(new Todo(2,"Go shopping","Home", LocalDate.of(2020,03,24)));
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Todo> todos = todoList.getTodos();

        if(!(request.getParameter("category") == null)){
            TodoList returnTodo = new TodoList();
            for(Todo todoEntry : todos){
                String entry = "<" + todoEntry.getCategory() + ">";
                if(entry.equals(request.getParameter("category")))
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

    public void doPost(HttpServletRequest request, HttpServletResponse response){
        
    }

    public void doPut(HttpServletRequest request, HttpServletResponse response){

    }

    public void doDelete(HttpServletRequest request, HttpServletResponse response){

    }

}
