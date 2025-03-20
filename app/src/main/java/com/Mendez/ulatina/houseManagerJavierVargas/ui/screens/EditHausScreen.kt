package com.Mendez.ulatina.houseManagerJavierVargas.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextFieldColors
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.Mendez.ulatina.houseManagerJavierVargas.model.HouseJavier
import com.Mendez.ulatina.houseManagerJavierVargas.viewmodel.HouseViewModel

@Composable
fun HausEditScreen(
    item: HouseJavier,
    viewModel: HouseViewModel = hiltViewModel(),
    navController: NavController
) {
    var houseName by remember { mutableStateOf(item.name) }
    var houseSqrtMeter by remember { mutableDoubleStateOf(item.sqrMeters) }
    var desc by remember { mutableStateOf(item.descripcionHouse) }
    var apellido2 by remember { mutableStateOf(item.Apellido2)}

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        Text("Nombre de la casa")
        OutlinedTextField(
            value = houseName,
            onValueChange = { houseName = it },
            modifier = Modifier.fillMaxWidth()
        )
        Text("Descripcion")
        OutlinedTextField(
            value = desc,
            onValueChange = { desc = it },
            modifier = Modifier.fillMaxWidth(),
        )
        Text("Tamaño")
        OutlinedTextField(
            value = houseSqrtMeter.toString(),
            onValueChange = {
                houseSqrtMeter = it.toDoubleOrNull() ?: 0.0
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
        )
        Text("Apellido")
        OutlinedTextField(
            value = apellido2,
            onValueChange = { apellido2 = it },
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                viewModel.update(item.copy(name = houseName, sqrMeters = houseSqrtMeter , descripcionHouse = desc, Apellido2 = apellido2))
                navController.popBackStack()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar cambios")
        }
    }
}