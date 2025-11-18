package com.example.ppersonalconkmp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectopersonal.model.firebase.Artist
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

//Este VModel realiza la ref  de la BD Firestore y obtiene la lista de artistas desde esa API.
//Luego este VModel es inicializado desde la pantalla Home (HomeScreen):
class HomeViewmodel : ViewModel() {
    private var db: FirebaseFirestore = Firebase.firestore //Para ref a la BD Firestore

    //    Esta var es privada y es modificable solo desde el viewmodel: Es la lista de artistas que mostraremos en la pantalla:
    private val _artists = MutableStateFlow<List<Artist>>(emptyList())

    //    Esta var es la que se puede ver desde afuiera pero no se puede modificar, es la lectura de _artists:
    val artist: StateFlow<List<Artist>> = _artists


    init {
        getArtists()
    }

    //Fun para obtener la info del Firestore usando la fun getAllArtists().
//    Luego esta fun se llama al iniciar este ViewModel: HomeViewmodel
    private fun getArtists() {
        viewModelScope.launch {
            val result: List<Artist> = withContext(Dispatchers.IO) {
                getAllArtists()
            }
            _artists.value =
                result //En _artists se almacena la lista de artistas obtenida desde el FireStore
        }

    }

    //    Fun para obrener la lista de Artistas directo desde la coleccion artists del Firestore:
    private suspend fun getAllArtists(): List<Artist> {
        return try {
            db.collection("artists")
                .get()
                .await()
                .documents
                .mapNotNull { snapshot ->
                    snapshot.toObject(Artist::class.java)

                }
        } catch (e: Exception) {
            emptyList()
        }


    }


}