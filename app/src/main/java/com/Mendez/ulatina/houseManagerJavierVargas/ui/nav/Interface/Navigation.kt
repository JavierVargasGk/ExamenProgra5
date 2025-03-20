package com.Mendez.ulatina.houseManagerJavierVargas.userinterfaz.nav.Interface


import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.Mendez.ulatina.houseManagerJavierVargas.model.HouseJavier
import com.Mendez.ulatina.houseManagerJavierVargas.ui.screens.HausAddScreen
import com.Mendez.ulatina.houseManagerJavierVargas.ui.screens.HausEditScreen
import com.Mendez.ulatina.houseManagerJavierVargas.ui.screens.hausListScreen
import com.Mendez.ulatina.houseManagerJavierVargas.viewmodel.HouseViewModel
import com.google.gson.Gson

@Composable
fun Navigation(viewModelStoreOwner: ViewModelStoreOwner = LocalViewModelStoreOwner.current!!) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "HouseDataList"
    ){
        composable("HouseDataList"){
            val viewModel: HouseViewModel = hiltViewModel(viewModelStoreOwner)
            hausListScreen(navController = navController, viewModel)
        }
        composable("AddHouseScreen"){
            val viewModel: HouseViewModel = hiltViewModel(viewModelStoreOwner)
            HausAddScreen(navController = navController, viewModel)
        }
        composable(
            route = "EditHouseScreen/{itemJson}",
            arguments = listOf(
                navArgument("itemJson") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val json = backStackEntry.arguments?.getString("itemJson")
            val item = Gson().fromJson(json, HouseJavier::class.java)

            val viewModel: HouseViewModel = hiltViewModel(viewModelStoreOwner)
            if (item != null) {
                HausEditScreen(
                    item = item,
                    viewModel = viewModel,
                    navController = navController
                )
            }
        }
        }
    }
