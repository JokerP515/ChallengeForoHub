# Challenge Foro Hub JokerP515

Hola, esta es mi propuesta para el challenge Foro Hub propuesto por Alura Latam.

El programa dispone de las siguientes caracteristicas, su tipo y ubicación:

- Registrarse como nuevo usuario (POST, "/register")
- Logearse como usuario existente (POST, "/login")
- Registrar nuevo topico en el foro (POST, "/topicos")
- Actualizar topico existente (PUT, "/topicos/{id}")
- Mostrar topico por ID (GET, "/topicos/{id}")
- Listar topicos existentes (GET, "/topicos")
- Mostrar topicos hechos por el usuario logeado (GET, "/usuarios") PD: Este método está sin implementar correctamente
- Borrar un topico por ID existente (DELETE, "/topicos/{id}")

Para poder usar todas las opciones referentes al topico, es necesario que el usuario se registre e inicie sesión 
para obtener un bearer-token que puede usar para disfrutar el resto de componentes del programa

De igual manera, puede visitar la página generada por el programa con ruta "/swagger-ui.html" para ver los métodos disponibles y el cuerpo JSON necesario para la solicitud
e interactuar con estos, aunque también el usuario puede hacer las solicitudes desde aplicaciones como "Postman" o "Insomnia"
