package com.example.yookcalc.presentation.feature.maternity

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
fun MaternityUi(viewModel: MaternityViewModel = viewModel()) {
    val state = viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest { effect ->
            println("[MaternityUi] Effect received: $effect")
            when (effect) {
                is MaternityEffect.ShowToast -> println("Toast: ${effect.message}")
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("출산휴가 계산기 페이지")

        OutlinedTextField(
            value = state.value.startDate,
            onValueChange = { viewModel.onEvent(MaternityEvent.OnStartDateChanged(it)) },
            label = { Text("시작일") },
            modifier = Modifier.padding(top = 16.dp)
        )

        OutlinedTextField(
            value = state.value.endDate,
            onValueChange = { viewModel.onEvent(MaternityEvent.OnEndDateChanged(it)) },
            label = { Text("종료일") },
            modifier = Modifier.padding(top = 8.dp)
        )

        Button(
            onClick = { viewModel.onEvent(MaternityEvent.OnCalculateClicked) },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("계산하기")
        }

        Text(state.value.result, modifier = Modifier.padding(top = 16.dp))
    }
}