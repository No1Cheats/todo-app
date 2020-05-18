import router from './router.js';
import todos from './components/todos.js';
import addTodo from './components/add-todo.js';
import login from './components/login.js'

router.register('/todos', todos);
router.register('/add-todo', addTodo);
router.register('/login', login);


router.go('/todos');
