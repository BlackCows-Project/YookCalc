package com.example.yookcalc.presentation.feature.parental

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.collectLatest


@Composable
fun ParentalUi(viewModel: ParentalViewModel = viewModel()) {
    val state = viewModel.state.collectAsState()

    // Effect 처리
    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest { effect ->
            println("[ParentalUi] Effect received: $effect")
            when (effect) {
                is ParentalEffect.ShowToast -> println("Toast: ${effect.message}")
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("육아휴직 계산기 페이지")

        OutlinedTextField(
            value = state.value.startDate,
            onValueChange = { viewModel.onEvent(ParentalEvent.OnStartDateChanged(it)) },
            label = { Text("시작일") },
            modifier = Modifier.padding(top = 16.dp)
        )

        OutlinedTextField(
            value = state.value.endDate,
            onValueChange = { viewModel.onEvent(ParentalEvent.OnEndDateChanged(it)) },
            label = { Text("종료일") },
            modifier = Modifier.padding(top = 8.dp)
        )

        Button(
            onClick = { viewModel.onEvent(ParentalEvent.OnCalculateClicked) },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("계산하기")
        }

        Text(
            text = state.value.result,
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}