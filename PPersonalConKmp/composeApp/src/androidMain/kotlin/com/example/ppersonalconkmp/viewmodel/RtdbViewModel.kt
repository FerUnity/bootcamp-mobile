package com.example.proyectopersonal.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectopersonal.model.firebase.Player
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch

//Este VModel realiza la ref  de la BD RTDB
// y obtiene la lista de artistas y su estado de reprod(play), desde la API.
// OJO: Ver la estructura del json en archivo Player.kt.
//Luego este VModel es inicializado desde la pantalla... :

class RtdbViewModel: ViewModel() {
    private val rtDB = Firebase.database
    //    Esta var es privada y es modificable solo desde este viewmodel:
    //    Es la lista de artistas y estados de repro,
    private val _player = MutableStateFlow<Player?>(null)
    //Que empiece nulo

    //     Luego pasaremos esa lista y estado(_player), a la pantalla para que la muestre,
//     pero como val de solo lectura:
    val player: StateFlow<Player?> = _player


    init {

        getPlayer()
    }

    //    Obtendra la lista de artistas y su estado de reprod(play), desde la fun collectPlayer(),
//    para convertir esa info DataSnapshot en un objeto que nos sirva,
//    que sera un objeto de clase Player.kt(data class):
    private fun getPlayer() {
        viewModelScope.launch {
            collectPlayer().collect {
                val playerCollected = it.getValue(Player::class.java)
//                Log.i("aris logg", "Player: $player")
                _player.value = playerCollected
                //O sea igualamos el valor de la lista var a mostrar por pantalla _player,
                // con lo colectado desde el RTDB: playerCollected
            }
        }

    }

//    fun que obtiene la lista de artistas y estado de play, directo desde la BD RTDB:

    private fun collectPlayer(): Flow<DataSnapshot> = callbackFlow {
//        Crwamos un listener de la RTDB de Fbase:
        val listener = object: ValueEventListener {
            //            fun que captura los cambios y los guarda en un snapshot como parametro:
            override fun onDataChange(snapshot: DataSnapshot) {
                trySend(snapshot).isSuccess //Se usa trySend y no solo Send porque puede haber errores
            }
            //            Sobreesc la fun que capta el error:
            override fun onCancelled(error: DatabaseError) {
                Log.i("aris logg", "Error: ${error.message}")
//    Cerranos el listener tambien y cerramos con una excepcion:
                close(error.toException())
            }
        }

//        Fuera del listener indicamos que queremos llegar al objeto player, del RTDB,
//        para eso creamos la var ref que obtiene ese objeto player:
        val ref = rtDB.reference.child("player")
        ref.addValueEventListener(listener) //Luego le asignamos el listener

//        Una vez finalizado el trabajo cerramos el listener:
        awaitClose {
            ref.removeEventListener(listener)
        }


    }

    //    Fun para cambiar simbolo de play a pause y viceversa, llamando a Firebase.
//    Esta fun se llama desde la vista RtdbScreen.kt:
    fun onPlaySelected() {
        if (player.value != null) {
//            Cuando presiono el boton se cambie al valor inv (pause)
            val currentPlayer = _player.value?.copy(play = !player.value?.play!!)
//    De nuevo llamamos a la RTDB, indicamos que queremos llegar al objeto player, del RTDB,
////        para eso creamos la var ref que repr ese objeto player:
            val ref = rtDB.reference.child("player")
//            Y le asignamos el valor de currentPlayer, al objeto player
            ref.setValue(currentPlayer)
        }

    }

    //
    //    Esta fun tb se llama desde la vista RtdbScreen.kt:
    fun onCancelSelected(){
//      POR AHORA NO HARA NADA:
        // Hacer null al obj player del RTDB, o sea destruir el obj player del RTDB:
        /*val ref = rtDB.reference.child("player")
        ref.setValue(null)*/
    }

}