import service from '../service.js';
import router from '../router.js';

// Private Helpers

function getFormData() {
	const form = document.forms[0];
	return {
		title: form.title.value,
		category: form.category.value,
		dueDate: form.date.value
	};
}

// Public Interface
export default {

	getTitle: function() {
		return "Add Todo";
	},

	render: function() {
		const $comp = $($('#tpl-add-todo').html());
		$('[data-action=cancel]', $comp).click(e => {
			e.preventDefault();
			router.go('/todos');
		});

		$('[data-action=add]', $comp).click(e => {
			e.preventDefault();

			const todo = getFormData();

			service.postTodo(user, todo)
				.then(data => router.go('/todos'))
				.catch(jqXHR => $('[data-field=error]', $comp).html("Adding todo failed!"));
		});

		return $comp;
	}
};
