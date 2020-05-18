import service from '../service.js';
import router from "../router";

export default {

    getTitle: function() {
        return "Login";
    },

    render: function() {
        //Our HTML template
        const $comp = $($('#tpl-login').html());

        // If we hit cancel then we'll go back to the main page
        $('[data-action=cancel]', $comp).click(e => {
            e.preventDefault();
            router.go('/todos');
        });

        //Login which is technically just register...
        $('[data-action=add]', $comp).click(e => {
            e.preventDefault();

            user = getFormData();

            service.postTodo(user)
                .then(data => router.go('/users'))
                .catch(jqXHR => $('[data-field=error]', $comp).html("Registering Failed!"));
        });

        return $comp;
    }
};
