package com.example.micalendariov4.view.layouts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.micalendariov4.model.MonthObject
import com.example.micalendariov4.view.components.MonthComponent

@Composable
fun ExpandedLayout(monthQty: Int, monthColumnQty: Int) {
    val actualDate = java.time.LocalDate.now()
    var monthDate = actualDate
    var totalMonths = 0
    val monthRows = monthQty / monthColumnQty

    // Esta es la vista de 3 meses en pantalla:
    Column {
        for (i in 1.. monthRows) {
            Row {
                for (i in 1..monthColumnQty) {
                    if (totalMonths < monthQty) {
                        val month = monthDate.month
                        val year = monthDate.year
                        val monthObject = MonthObject(month, year)
                        MonthComponent(monthObject)
                        Spacer(Modifier.width(8.dp))
                        totalMonths += 1
                        monthDate = monthDate.plusMonths(1)
                    }
                }
            }
//            Una separacion de 8 dp por cada fila: O sea por cada 3 meses:
            Spacer(Modifier.width(8.dp))
        }
    }

}