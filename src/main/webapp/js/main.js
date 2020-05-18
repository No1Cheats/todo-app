import router from './router.js';
import todos from './components/todos.js';
import addTodo from './components/add-todo.js';

router.register('/todos', todos);
router.register('/add-todo', addTodo);

router.go('/todos');
