package com.example.yookcalc.presentation.screen.maternity

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MaternityUI(viewModel: MaternityViewModel = MaternityViewModel()) {
    println("[UI] MaternityUI Composable 실행됨")
    val state by viewModel.uiState.collectAsState()

    // Effect 처리
    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            println("[UI] Effect 수신 → $effect")
            when (effect) {
                is MaternityEffect.ShowToast -> println("Toast: ${effect.message}")
            }
        }
    }

    // 화면 진입 시 기본 데이터 로드
    LaunchedEffect(Unit) {
        println("[UI] 초기 데이터 로드 이벤트 전송")
        viewModel.onEvent(MaternityEvent.LoadDefaultData)
    }

    // UI 구성
    Column(modifier = Modifier.padding(16.dp)) {
        Text("출산휴직", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(8.dp))

        // 예정일 입력
        Text("출산 예정일")
        BasicTextField(
            value = state.dueDate,
            onValueChange = { viewModel.onEvent(MaternityEvent.ChangeDueDate(it)) },
            modifier = Modifier.fillMaxWidth().padding(4.dp)
        )

        Spacer(Modifier.height(8.dp))
        // 휴가 기간
        Text("출산 전후 휴가 기간")
        Row {
            BasicTextField(
                value = state.startDate,
                onValueChange = { viewModel.onEvent(MaternityEvent.ChangeStartDate(it)) },
                modifier = Modifier.weight(1f).padding(4.dp)
            )
            Spacer(Modifier.width(8.dp))
            BasicTextField(
                value = state.endDate,
                onValueChange = { viewModel.onEvent(MaternityEvent.ChangeEndDate(it)) },
                modifier = Modifier.weight(1f).padding(4.dp)
            )
        }

        Spacer(Modifier.height(8.dp))
        // 다태아 여부
        Text("다태아 여부")
        Row {
            Button(onClick = { viewModel.onEvent(MaternityEvent.SelectMultipleBirth(true)) }) {
                Text("예")
            }
            Spacer(Modifier.width(8.dp))
            Button(onClick = { viewModel.onEvent(MaternityEvent.SelectMultipleBirth(false)) }) {
                Text("아니오")
            }
        }

        Spacer(Modifier.height(8.dp))
        // 미산아 여부
        Text("미산아 여부")
        Row {
            Button(onClick = { viewModel.onEvent(MaternityEvent.SelectMiscarriage(true)) }) {
                Text("예")
            }
            Spacer(Modifier.width(8.dp))
            Button(onClick = { viewModel.onEvent(MaternityEvent.SelectMiscarriage(false)) }) {
                Text("아니오")
            }
        }

        Spacer(Modifier.height(16.dp))
        Button(onClick = { println("계산하기 버튼 클릭") }, modifier = Modifier.fillMaxWidth()) {
            Text("계산하기")
        }
    }
}