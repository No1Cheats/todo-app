
// Private helpers

const $main = $('main');

const routes = Object.create(null); // {};

function render() {
	const path = location.hash.replace('#', '');
	if (!routes[path]) {
		$main.html("<h2>404 Not Found</h2><p>Sorry, page not found!</p>");
		return;
	}
	const component = routes[path];
	const $comp = component.render();
	$main.empty().append($comp);
	document.title = "Todo App" + (component.getTitle ? " - " + component.getTitle() : "");
}

$(window).on('hashchange', render);

// Public interface
export default {

	register: function(path, component) {
		routes[path] = component;
	},

	go: function(path) {
		if (location.hash !== '#' + path) {
			location.hash = path;
		} else {
			render();
		}
	}

};
