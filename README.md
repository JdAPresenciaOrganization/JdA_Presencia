# JdA_Presencia

jdA Presència versió 2\
-> Se hace toda la conexión con SQLite en vez de ficheros.\
-> Usuarios por defecto (admin, 1234) y (worker, test).\
-> Este usuario administrador puede hacer CRUD.

jdA Presència versió 1\
-> Se ha hecho el login en MVP, éste lleva al Navigation Drawer que utiliza MVVM.\
-> La App sólo tiene 3 usuarios: (super, 1234), (admin, 1234), (worker, 1234).\
-> El trabajador worker 1234 la primera vez que le da al Check In hace que se generen unos registros inventados para que el recylcerView no sea vacío. La segunda vez que se hace Check In si que hace el registro real del día y hora actual. A partir de este segundo Check In sólo se podrá hacer el registro de entrada y salida una vez al día.\
-> El usuario administrador contiene un buscador de trabajadores. Al entrar se listan todos, pero también se puede buscar uno en concreto por nombre. También hay la opción de ver el detalle de la búsqueda con todos los registros del trabajador.
