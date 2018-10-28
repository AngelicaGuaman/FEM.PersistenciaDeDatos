# Asignatura: *Front-end para Móviles*
En este proyecto se trabajará sobre los aspectos relacionados con la persistencia de datos (empleando preferencias, ficheros y SQLite). Para ello se recomienda comenzar con la lectura de los documentos Settings y Saving Files. Para trabajar sobre estos aspectos se ha desarrollado el juego conocido -entre otros nombres- como Solitario Celta, que consiste en ir saltando piezas en horizontal o en vertical con el objetivo de que finalmente quede el menor número de piezas en el tablero.
> #### [Máster en Ingeniería Web por la U.P.M.](http://miw.etsisi.upm.es)

## Tecnologías necesarias
* Java
* Gradle
* Android Studio
* GitHub

## Tareas
Partiendo del proyecto Solitario Celta, se deberá continuar con el desarrollo de la aplicación implementando las siguientes opciones:
#### * Reiniciar partida
* Al pulsar el botón "reiniciar" se mostrará un diálogo de confirmación. En caso de respuesta afirmativa se procederá a reiniciar la partida actual.

#### * Guardar partida
* Esta opción permite guardar la situación actual del tablero. Sólo es necesario guardar una única partida y se empleará el sistema de ficheros del dispositivo.

#### * Recuperar partida
* Si existe, recupera el estado de una partida guardada (si se hubiera modificado la partida actual, se solicitará confirmación).

#### * Guardar puntuación
* Al finalizar cada partida se deberá guardar la información necesaria para generar un listado de resultados. Dicho listado deberá incluir -al menos- el nombre del jugador, el día y hora de la partida y el número de piezas que quedaron en el tablero. La información se deberá almacenar en una base de datos.

#### * Mejores resultados
* Esta opción mostrará el histórico con los mejores resultados obtenidos ordenados por número de piezas. Incluirá una opción -con confirmación- para eliminar todos los resultados guardados.

#### OPCIONAL
* Se propone mostrar el número de fichas que quedan en el tablero, añadir un cronómetro a la partida (y/o guardar el tiempo empleado), permitir guardar más de una partida, filtrar los mejores resultados por número de fichas o por jugador, añadir preferencias para modificar los colores empleados por la app, etc.


#### ACLARACIONES
Solo se podrá guardar una única partida y se cargará la misma, en el caso que el usuario quiera cargar la partida.
La tareas que he decidido impletar son: mostrar el nombre del jugador y el número de fichas que le quedan para terminar la partida.