# JdA_Presencia

jdA Presència versió 3 - **minSdkVersion 26**\
-> **Usuarios por defecto (admin, 1234) y (worker, test).**\
-> SQLite pasa a usar ROOM.\
-> Se implementa la integración de un servidor en PostgreSQL.\
-> El login, el check out y check in se puede hacer tanto en PostgreSQL, el servidor, como en SQLite, la BBDD local del terminal en caso de que no haya conexión con el servidor.\
-> Estos datos se sincronizan entre las dos BBDD.

jdA Presència versió 2 - **minSdkVersion 26**\
-> Se hace toda la conexión con SQLite en vez de ficheros.\
-> **Usuarios por defecto (admin, 1234) y (worker, test).**\
-> Este usuario administrador puede hacer CRUD.\
-> Se han encriptado todas las contraseñas.
