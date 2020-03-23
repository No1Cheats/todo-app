package todo.filter;

import todo.model.users.User;
import todo.model.users.UserAdmin;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;

import static java.nio.charset.StandardCharsets.ISO_8859_1;

public class AuthenticationHelper {

	private static final String AUTH_HEADER = "Authorization";
	private static final String AUTH_SCHEME = "Basic";

	public static User authenticateUser(HttpServletRequest request) throws Exception {
		String authHeader = request.getHeader(AUTH_HEADER);
		String[] headerTokens = authHeader.split(" ");
		if (!headerTokens[0].equals(AUTH_SCHEME)) return null;
		byte[] decoded = Base64.getDecoder().decode(headerTokens[1]);
		String[] credentials = new String(decoded, ISO_8859_1).split(":");
		UserAdmin userAdmin = UserAdmin.getInstance();
		return userAdmin.loginUser(credentials[0], credentials[1]);
	}
}
