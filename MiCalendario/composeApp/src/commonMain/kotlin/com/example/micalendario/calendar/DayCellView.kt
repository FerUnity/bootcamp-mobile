package com.example.micalendario.calendar

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.datetime.LocalDate
import kotlin.toString

@Composable
fun DayCellView(
    cell: DayCell,
    modifier: Modifier = Modifier,
    onClick: (LocalDate?) -> Unit
) {
    val isActiveDay = cell.date != null

    // Colores de texto
    val textColor = when {
        cell.isToday -> MaterialTheme.colorScheme.onPrimary
        cell.isHoliday || cell.isSunday -> Color.Red
        else -> MaterialTheme.colorScheme.onSurface
    }

    // Fondo
    val backgroundColor = when {
        cell.isToday -> MaterialTheme.colorScheme.primary.copy(alpha = 0.25f) // hoy
        isActiveDay -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.15f)
        else -> Color.Transparent
    }

    // Borde solo para hoy
    val border = if (cell.isToday) {
        BorderStroke(2.dp, MaterialTheme.colorScheme.primary)
    } else null

    Surface(
        modifier = modifier
            .padding(2.dp)
            .then(
                if (isActiveDay) Modifier.clickable { onClick(cell.date) } else Modifier
            ),
        color = backgroundColor,
        shape = RoundedCornerShape(8.dp),
        border = border
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = cell.date?.day?.toString() ?: "",
                style = MaterialTheme.typography.bodyMedium,
                color = textColor
            )
        }
    }
}
