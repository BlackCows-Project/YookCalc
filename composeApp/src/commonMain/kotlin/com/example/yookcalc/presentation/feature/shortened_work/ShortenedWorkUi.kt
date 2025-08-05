package com.example.yookcalc.presentation.feature.shortened_work

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ShortenedWorkUi(viewModel: ShortenedWorkViewModel = viewModel()) {
    val state = viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest { effect ->
            println("[ShortenedWorkUi] Effect received: $effect")
            when (effect) {
                is ShortenedWorkEffect.ShowToast -> println("Toast: ${effect.message}")
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("단축근로 계산기 페이지")
        OutlinedTextField(
            value = state.value.workHours,
            onValueChange = { viewModel.onEvent(ShortenedWorkEvent.OnWorkHoursChanged(it)) },
            label = { Text("근로 시간") },
            modifier = Modifier.padding(top = 16.dp)
        )
        Button(
            onClick = { viewModel.onEvent(ShortenedWorkEvent.OnCalculateClicked) },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("계산하기")
        }
        Text(state.value.result, modifier = Modifier.padding(top = 16.dp))
    }
}