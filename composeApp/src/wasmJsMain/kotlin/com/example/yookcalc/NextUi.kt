package com.example.yookcalc

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun NextUi() {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("여기는 두 번째 화면이다.")
    }
}