import service from '../service.js';

export default {

	getTitle: function() {
		return "My Todos";
	},

	render: function() {
		const $comp = $($('#tpl-todos').html());

		service.getTodos(user)
			.then((data, status, jqXHR) => {
				data.forEach((item, index) => {
					$('ul', $comp).append(`<li>${item.id} <b>${item.title}</b> ${item.dueDate || ''}</li>`);
				});
			});

		return $comp;
	}
};
