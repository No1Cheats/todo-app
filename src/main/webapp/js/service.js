const BASE_URL = 'api/';

function getAuthHeader(user) {
	return 'Basic ' + btoa(user.name + ':' + user.password);
}

export default {
	getTodos: function(user) {
		return $.ajax({
			url: BASE_URL + 'todos',
			type: 'GET',
			headers: {
				'Authorization': getAuthHeader(user),
				'accept': 'application/json'
			}
		}).fail(jqXHR => console.log("ERROR: " + jqXHR.status));
	},

	postTodo: function(user, todo) {
		return $.ajax({
			url: BASE_URL + 'todos',
			type: 'POST',
			headers: {
				'Authorization': getAuthHeader(user),
			},
			data: JSON.stringify(todo),
			contentType: 'application/json'
		}).fail(jqXHR => console.log("ERROR: " + jqXHR.status));
	}

	// ...
}
