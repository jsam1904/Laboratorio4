package com.example.laboratorio4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.laboratorio4.ui.theme.Laboratorio4Theme
import com.example.laboratorio4.ui.theme.AdoptadoColor
import com.example.laboratorio4.ui.theme.TextoGris

data class Mascota(
    val nombre: String,
    val raza: String,
    val imageResId: Int,
    var adoptado: Boolean = false
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Laboratorio4Theme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    ListaMascotas()
                }
            }
        }
    }
}

@Composable
fun ListaMascotas() {
    val mascotas = remember {
        mutableStateListOf(
            Mascota("Firulas", "Golden Retriever", R.drawable.labrador),
            Mascota("Hope", "Poodle", R.drawable.poodle),
            Mascota("Orion", "Salchicha", R.drawable.salchicha),
            Mascota("Coco", "French Bulldog", R.drawable.bulldog)
        )
    }

    LazyColumn {
        items(mascotas.size) { index ->
            TarjetaMascota(
                mascota = mascotas[index],
                onAdoptarClick = {
                    mascotas[index] = mascotas[index].copy(adoptado = true)
                }
            )
        }
    }
}

@Composable
fun TarjetaMascota(mascota: Mascota, onAdoptarClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = mascota.imageResId),
                contentDescription = mascota.nombre,
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(text = mascota.nombre, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text(text = mascota.raza, fontSize = 14.sp, color = TextoGris)
            }

            Button(
                onClick = onAdoptarClick,
                enabled = !mascota.adoptado,
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (mascota.adoptado) AdoptadoColor else MaterialTheme.colorScheme.primary
                )
            ) {
                Text(if (mascota.adoptado) "¡Adoptado! ❤" else "Adoptar")
            }
        }
    }
}
