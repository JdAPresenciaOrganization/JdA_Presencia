# JdA_Presencia

jdA Presència versió 4 - **minSdkVersion 26**\
-> Todos los usuarios pueden insertar o modificar sus datos de perfil (imagen selfie, nombre, número y email).\
-> Directorio de trabajadores creado (Buscador trabajadores), visible para usuarios administradores.\
-> Desde el directorio (Buscador trabajadores) se puede ver el perfil y los registros de todos los trabajadores.\
-> En el perfil se carga, si el usuario previamente ha insertado los datos en su perfil, sus datos, recuperados en JSON desde Firebase.\
-> Añadido apartado multimedia con una canción y un video de muestra.

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
