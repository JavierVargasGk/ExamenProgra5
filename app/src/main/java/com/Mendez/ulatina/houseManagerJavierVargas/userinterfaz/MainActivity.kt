package com.Mendez.ulatina.houseManagerJavierVargas.userinterfaz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.Mendez.ulatina.houseManagerJavierVargas.ui.theme.JavierULatinaVargasMendezTheme
import com.Mendez.ulatina.houseManagerJavierVargas.userinterfaz.nav.Interface.Navigation


import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JavierULatinaVargasMendezTheme {
                Navigation();
            }
        }
    }
}
