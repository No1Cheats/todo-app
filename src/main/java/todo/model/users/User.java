package todo.model.users;

import todo.model.todos.TodoList;

public class User {

	private String name;
	private String password;
	private TodoList todoList;

	public User() {
	}

	public User(String name, String password) {
		this.name = name;
		this.password = password;
		this.todoList = new TodoList();
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}

	public TodoList getTodoList() {
		return todoList;
	}
}
