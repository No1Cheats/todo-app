package todo.model.users;

import java.util.ArrayList;
import java.util.List;

public class UserAdmin {

	private static UserAdmin instance = new UserAdmin();
	private List<User> users = new ArrayList<>();

	public static UserAdmin getInstance() {
		return instance;
	}

	private UserAdmin() {
	}

	public void registerUser(String username, String password) throws UserAlreadyExistsException {
		if (findUser(username) != null) {
			throw new UserAlreadyExistsException();
		}
		User user = new User(username, password);
		users.add(user);
	}

	public User loginUser(String username, String password) throws InvalidCredentialsException {
		User user = findUser(username);
		if (user == null || !user.getPassword().equals(password)) {
			throw new InvalidCredentialsException();
		}
		return user;
	}

	private User findUser(String username) {
		return users.stream().filter(user -> user.getName().equals(username)).findFirst().orElse(null);
	}
}
