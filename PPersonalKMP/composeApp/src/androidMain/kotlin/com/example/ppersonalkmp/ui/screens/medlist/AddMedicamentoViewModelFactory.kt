package com.example.proyectopersonal.ui.screens.medlist

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.proyectopersonal.room.MedRepository
import com.example.proyectopersonal.ui.screens.addMedicamentoScreen.AddMedicamentoViewModel

/*class AddMedicamentoViewModelFactory(private val repository: MedRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddMedicamentoViewModel::class.java)) {
           return AddMedicamentoViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }


}*/
