package todo.model.todos;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

public class TodoList {

	private List<Todo> todos = new ArrayList<>();
	private int lastId = 0;

	public List<Todo> getTodos() {
		return todos;
	}

	public List<Todo> getTodos(String category) {
		return todos.stream()
				.filter(todo -> Objects.equals(todo.getCategory(), category))
				.collect(toList());
	}

	public void addTodo(Todo todo) {
		todo.setId(++lastId);
		todos.add(todo);
	}

	public void updateTodo(Todo todo) throws TodoNotFoundException {
		removeTodo(todo.getId());
		todos.add(todo);
	}

	public void removeTodo(int id) throws TodoNotFoundException {
		if (!todos.removeIf(todo -> Objects.equals(todo.getId(), id))) {
			throw new TodoNotFoundException();
		}
	}
}
