package com.Mendez.ulatina.houseManagerJavierVargas.ui.screens


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.Mendez.ulatina.houseManagerJavierVargas.model.HouseJavier
import com.Mendez.ulatina.houseManagerJavierVargas.viewmodel.HouseViewModel


@Composable
fun HausAddScreen(navController: NavController, viewModel: HouseViewModel = hiltViewModel())
{
    var house by remember { mutableStateOf("") }
    var size by remember { mutableStateOf( "")}
    var descripcion by remember { mutableStateOf("")}
    var Apellido2 by remember { mutableStateOf( "" ) }


    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp).padding(vertical = 40.dp)
    ) {
        Text("Ingrese el nombre del local")
        OutlinedTextField(
            value = house,
            onValueChange = { house = it },
            label = { Text("'Casa Playa | Finca | Apartamento'", color = Color.Gray) },
            modifier = Modifier.fillMaxWidth()
        )
        Text("Ingrese una descripcion de la casa")
        OutlinedTextField(
            value = descripcion,
            onValueChange = {descripcion = it},
            modifier = Modifier.fillMaxWidth(),
            label = { Text("'Casa de 2 pisos, 3 baños.'", color = Color.Gray)}
        )
        Text("Ingrese el tamaño de la casa en metros cuadrados")
        OutlinedTextField(
            value = size,
            onValueChange = { size = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = { Text("'12.34'", color = Color.Gray)},
            modifier = Modifier.fillMaxWidth()
        )
        Text("Ingrese su segundo apellido")
        OutlinedTextField(
            value = Apellido2,
            onValueChange = { Apellido2 = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("'Mendez'", color = Color.Gray)
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                if (house.isNotBlank()) {
                    val newItem = HouseJavier(name = house, sqrMeters = size.toDouble(), descripcionHouse = descripcion, Apellido2 = Apellido2 )
                    viewModel.insert(newItem)
                    navController.popBackStack()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Agregar casa")
        }
    }
}
