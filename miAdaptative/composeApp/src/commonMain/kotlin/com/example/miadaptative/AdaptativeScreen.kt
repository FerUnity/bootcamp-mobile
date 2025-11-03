package com.example.miadaptative

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable

//Este archivo que esta en commonMain,
// rep el Layout de distribucion adaptativa, y se llama desde App.kt.
// Por ende rep la Vista en todas las plataformas.
// Segun el BoxWithConstraints que es el espacio de la pantalla,(resolucion en dp) disponible para el Layout:
// Desktop o Mobile(O web o IOS si hubiera):
@Composable
fun AdaptativeScreen() {
//    Creamos una Lista de 12 productos:
    val products = List(12){"Producto ${it + 1}"}
    BoxWithConstraints {
        val width = this.maxWidth //Se refiere al espacio disponible para el Layout:
        //Para aclarar que el width se ref al contexto del BoxWithConstraints,
        // no dentro de la Column u otro widget:
        Column {
//            Caso 1: Mobile: Llamamos al fun getWindowSizeClass
            //y pasamos como param el maxWidth de nuestro scope:
           /* if (getWindowSizeClass(scope.maxWidth) == WindowSize.COMPACT) {
//                En el caso que sea COMPACT, llamamos al Layout del archivo CompactLayout.kt:
                CompactLayout()
            }
//            Caso 2: Tablet o Desktop:
            else {
//                 En el caso que sea MEDIUM, llamamos al Layout del archivo MediumLayout.kt:
                MediumLayout()
            }*/

//            Otra forma de exponerlo sin if como arriba, es con un when:
//            Llamamos al fun getWindowSizeClass(maxWidth)
//            //y pasamos como param el maxWidth de nuestro scope:
            when (getWindowSizeClass(width)) {
                WindowSize.COMPACT -> CompactLayout(products)
                WindowSize.MEDIUM -> MediumLayout(products)
                WindowSize.EXPANDED -> ExpandedLayout(products)
            }

//        Despues de la Col y Row ponemos 2 textos que nos muestren,
//        las dimensiones disponibles de la pantalla para Layout,
//        segun sea la pantalla que estemos usando: jvm o mobile
           /* Text("minWidth: ${scope.minWidth}, maxWidth: ${scope.maxWidth}")
            Text("minHeight: ${scope.minHeight}, maxHeight: ${scope.maxHeight}")*/

        }
    }
}