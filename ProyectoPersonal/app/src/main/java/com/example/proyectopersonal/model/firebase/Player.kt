package com.example.proyectopersonal.model.firebase

//Esta data class rep la info de RTDB de Firebase:
data class Player(
    val artist: Artist? = null, //Saber si tengo un artista seleccionado, de clase Artist(Con todos sus campos)
    val play: Boolean? = null //Saber si estoy reproduciendolo o no
)

//EN Firebase generamos entonces un json en RTDB, con los sgtes campos correspondientes este data class:
/* "player":  El player no se copia porque ya esta en el RTDB, pero si sus campos:
COPIAR:

{
   "artist":{
       "name": "John Doe",
       "description": "A popular Artist",
       "image": "http://example.com/image.jpg"
   },
   "play": false
 }
 */
