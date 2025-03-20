package com.Mendez.ulatina.houseManagerJavierVargas.ui.screens

import android.service.autofill.OnClickAction
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.test.isSelected

import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.core.util.rangeTo
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.Mendez.ulatina.houseManagerJavierVargas.model.HouseJavier
import com.Mendez.ulatina.houseManagerJavierVargas.viewmodel.HouseViewModel
import com.google.gson.Gson
import kotlinx.coroutines.launch
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import kotlin.math.abs

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun hausListScreen(
    navController: NavController = rememberNavController(),
    viewModel: HouseViewModel = hiltViewModel()
) {
    val houseList by viewModel.HouseList.observeAsState(emptyList<HouseJavier>())

    var HouseToDelete by remember { mutableStateOf<HouseJavier?>(null) }
    var cancelSwipeAction by remember { mutableStateOf<(() -> Unit)?>(null) }
    var isRefreshing by remember { mutableStateOf(false) }
    var wipe by remember { mutableStateOf(false) }


    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = {
            isRefreshing = true
            isRefreshing = false
        }
    )
    
    @Composable
    fun ItemCard(
        item: HouseJavier,
        onClick: () -> Unit,
        onSwipe: () -> Unit,
        onEditClick: () -> Unit,
        onCancelSwipe: (() -> Unit) -> Unit
    ) {
        val offsetX = remember { Animatable(0f) }
        val scope = rememberCoroutineScope()
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
                .offset { IntOffset(offsetX.value.toInt(), 0) }
                .pointerInput(Unit) {
                    detectHorizontalDragGestures(
                        onDragEnd = {
                            if (abs(offsetX.value) > 300f) {
                                scope.launch {
                                    offsetX.animateTo(
                                        targetValue = if (offsetX.value > 0) 1000f else -1000f,
                                        animationSpec = tween(durationMillis = 300)
                                    )
                                    onSwipe()
                                }
                            } else {
                                scope.launch {
                                    offsetX.animateTo(0f, animationSpec = tween(300))
                                }
                            }
                        }
                    ) { _, dragAmount ->
                        scope.launch {
                            offsetX.snapTo(offsetX.value + dragAmount) // Mueve la tarjeta con el gesto
                        }
                    }
                }
                .clickable { onClick() },
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            colors = CardDefaults.cardColors(
                containerColor = if (item.IsSelected) Color.LightGray else Color.Gray
            ),
            border = CardDefaults.outlinedCardBorder()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Nombre: " + item.name,
                    modifier = Modifier.weight(2f),
                    color = Color.Black
                )
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Editar",
                    modifier = Modifier
                        .clickable { onEditClick() }
                        .padding(start = 8.dp)
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth().padding( 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Tamaño: " + item.sqrMeters.toBigDecimal().toPlainString(),
                    modifier = Modifier.weight(2f),
                    color = Color.Black
                )
            }
        }
        LaunchedEffect(key1 = item) {
            onCancelSwipe {
                scope.launch { offsetX.animateTo(0f, tween(300)) }
            }
        }
        onCancelSwipe {
            scope.launch { offsetX.animateTo(0f, tween(300)) }
        }
    }




    @Composable
    fun DeleteAllAlert(){
        if (houseList.count() == 0){
            AlertDialog(
                onDismissRequest = { wipe = false},
                title = { Text("Alerta") },
                text = { Text("Porfavor ingrese almenos una casa antes de poder borrarla") },
                confirmButton = { TextButton(onClick = {wipe = false}) {Text("Aceptar") }},
                textContentColor = Color.White
            )
        } else {
        AlertDialog(
            onDismissRequest = { HouseToDelete = null },
            title = { Text(text = "Borrar Objeto") },
            text = { Text(text = "Esta seguro que desea borrar todas las casas guardadas?") },
            confirmButton = {
                TextButton(
                    onClick = {
                            viewModel.deathforathousandyears()
                            wipe = false
                    }
                ) {
                    Text("Confirmar")
                }
            },
            textContentColor = Color.White,
            dismissButton = {
                TextButton(
                    onClick = {
                        cancelSwipeAction?.invoke()
                        wipe = false
                    }
                ) { Text("Cancelar")
                }
            }
        )
            }
    }
    if (wipe){
        DeleteAllAlert()
    }

    if (HouseToDelete != null) {
        AlertDialog(
            onDismissRequest = { HouseToDelete = null },
            title = { Text(text = "Borrar Objeto") },
            text = { Text(text = "Esta seguro que quire borrar esta casa?") },
            textContentColor = Color.White,
            confirmButton = {
                TextButton(
                    onClick = {
                        HouseToDelete?.let { item ->
                            viewModel.deleteHaus(item)
                            HouseToDelete = null
                        }
                    }
                ) {
                    Text("Confirmar")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        HouseToDelete = null
                        cancelSwipeAction?.invoke()
                    }
                ) { Text("Cancelar")
                }
            }
        )
    }




    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate("AddHouseScreen")
                },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Agregar ítem"
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Button(
                onClick = { wipe = true},
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            ) {
                Text("Eliminar todos los elementos")
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .pullRefresh(pullRefreshState)
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    items(houseList) { item ->
                        ItemCard(
                            item = item,
                            onClick = { viewModel.update(item.copy(IsSelected = !item.IsSelected)) },
                            onSwipe = {
                                HouseToDelete = item
                                cancelSwipeAction = null
                            },
                            onEditClick = {
                                val json = Gson().toJson(item)
                                val encodedJson = URLEncoder.encode(json, StandardCharsets.UTF_8.toString())
                                navController.navigate("editHouseScreen/$encodedJson")
                            },
                            onCancelSwipe = { cancelSwipeAction = it }
                        )
                    }
                }
                PullRefreshIndicator(
                    refreshing = isRefreshing,
                    state = pullRefreshState,
                    modifier = Modifier.align(Alignment.TopCenter)
                )
            }


        }
    }
}

