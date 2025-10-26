package com.example.yookcalc.presentation.feature.maternity

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.koin.compose.koinInject

@Composable
fun MaternityUI(viewModel: MaternityViewModel = koinInject()) {
    val state by viewModel.state.collectAsState()
    var isLargeCompany by remember { mutableStateOf<Boolean?>(null) }
    var monthlyWorkHours by remember { mutableStateOf("") }
    var first30DaysInput by remember { mutableStateOf("") }
    var second30DaysInput by remember { mutableStateOf("") }
    var third30DaysInput by remember { mutableStateOf("") }
    var normalWageInput by remember { mutableStateOf("") }
    var showResultDialog by remember { mutableStateOf(false) }

    // Effect 처리
    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is MaternityEffect.ShowToast -> println("Toast: ${effect.message}")
                is MaternityEffect.ShowError -> println("Error: ${effect.message}")
                is MaternityEffect.ShowCalculationResult -> {
                    println("Result: $effect")
                    showResultDialog = true
                }
            }
        }
    }

    // 화면 진입 시 기본 데이터 로드
    LaunchedEffect(Unit) {
        viewModel.onEvent(MaternityEvent.LoadDefaultData)
    }

    // State 업데이트 시 입력 필드 동기화
    LaunchedEffect(state.first30DaysPay) {
        if (state.first30DaysPay.isNotEmpty() && first30DaysInput != state.first30DaysPay) {
            first30DaysInput = state.first30DaysPay
        }
    }
    LaunchedEffect(state.second30DaysPay) {
        if (state.second30DaysPay.isNotEmpty() && second30DaysInput != state.second30DaysPay) {
            second30DaysInput = state.second30DaysPay
        }
    }
    LaunchedEffect(state.third30DaysPay) {
        if (state.third30DaysPay.isNotEmpty() && third30DaysInput != state.third30DaysPay) {
            third30DaysInput = state.third30DaysPay
        }
    }
    LaunchedEffect(state.compensation) {
        if (state.compensation.isNotEmpty() && normalWageInput != state.compensation) {
            normalWageInput = state.compensation
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        // 제목
        Text(
            "출산휴직",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        Divider(color = Color(0xFFE0E0E0), thickness = 1.dp)
        Spacer(Modifier.height(24.dp))

        // 출산(예정)일
        SectionTitle("출산(예정)일")
        DateInputField(
            value = state.dueDate,
            onValueChange = { viewModel.onEvent(MaternityEvent.ChangeDueDate(it)) }
        )

        Spacer(Modifier.height(24.dp))

        // 출산 전후 휴가 기간
        SectionTitle("출산 전후 휴가 기간(일)")
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            DateInputField(
                value = state.startDate,
                onValueChange = { viewModel.onEvent(MaternityEvent.ChangeStartDate(it)) },
                modifier = Modifier.weight(1f)
            )
            Text(
                "~",
                fontSize = 16.sp,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            DateInputField(
                value = state.endDate,
                onValueChange = { viewModel.onEvent(MaternityEvent.ChangeEndDate(it)) },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(Modifier.height(24.dp))

        // 대규모 기업 여부
        SectionTitle("대규모 기업 여부")
        YesNoSelector(
            selected = isLargeCompany,
            onSelect = { isLargeCompany = it }
        )

        Spacer(Modifier.height(24.dp))

        // 다태아 여부
        SectionTitle("다태아 여부 (쌍둥이 이상)")
        YesNoSelector(
            selected = state.hasMultipleBirth,
            onSelect = { viewModel.onEvent(MaternityEvent.SelectMultipleBirth(it)) }
        )

        Spacer(Modifier.height(24.dp))

        // 미숙아 여부
        SectionTitle("미숙아 여부 (출산 시 체중 미달)")
        YesNoSelector(
            selected = state.hasMiscarriageHistory,
            onSelect = { viewModel.onEvent(MaternityEvent.SelectMiscarriage(it)) }
        )

        Spacer(Modifier.height(32.dp))

        // 월 소정 근로시간
        SectionTitle("월 소정 근로시간")
        OutlinedTextField(
            value = monthlyWorkHours,
            onValueChange = {
                if (it.isEmpty() || it.all { char -> char.isDigit() }) {
                    monthlyWorkHours = it
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            placeholder = {
                Text("시간", fontSize = 14.sp, color = Color(0xFFBDBDBD))
            },
            trailingIcon = {
                Text("시간", fontSize = 14.sp, color = Color.Gray)
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color(0xFFE0E0E0),
                focusedBorderColor = Color(0xFF5B4FFF),
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White
            ),
            shape = RoundedCornerShape(8.dp),
            singleLine = true
        )

        Spacer(Modifier.height(32.dp))

        // 출산 전후 휴가 기간 중 급여 산정 내역
        SectionTitle("출산 전후 휴가 기간 중 급여 산정 내역")
        EditableDetailField(
            label = "첫 번째 30일",
            value = first30DaysInput,
            onValueChange = {
                if (it.isEmpty() || it.all { char -> char.isDigit() }) {
                    first30DaysInput = it
                }
            }
        )
        Spacer(Modifier.height(12.dp))
        EditableDetailField(
            label = "두 번째 30일",
            value = second30DaysInput,
            onValueChange = {
                if (it.isEmpty() || it.all { char -> char.isDigit() }) {
                    second30DaysInput = it
                }
            }
        )
        Spacer(Modifier.height(12.dp))
        EditableDetailField(
            label = "세 번째 30일",
            value = third30DaysInput,
            onValueChange = {
                if (it.isEmpty() || it.all { char -> char.isDigit() }) {
                    third30DaysInput = it
                }
            }
        )
        Spacer(Modifier.height(12.dp))
        EditableDetailField(
            label = "통상임금",
            value = normalWageInput,
            onValueChange = {
                if (it.isEmpty() || it.all { char -> char.isDigit() }) {
                    normalWageInput = it
                }
            }
        )

        Spacer(Modifier.height(32.dp))

        // 계산하기 버튼
        Button(
            onClick = { viewModel.onEvent(MaternityEvent.CalculateMaternityPay) },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF5B4FFF)
            ),
            shape = RoundedCornerShape(8.dp),
            enabled = !state.isLoading
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = Color.White
                )
                Spacer(Modifier.width(8.dp))
                Text("계산 중...", fontSize = 16.sp, color = Color.White)
            } else {
                Text(
                    "계산하기",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }

    // 계산 결과 다이얼로그
    if (showResultDialog) {
        CalculationResultDialog(
            state = state,
            onDismiss = { showResultDialog = false }
        )
    }
}

@Composable
private fun SectionTitle(text: String) {
    Text(
        text,
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium,
        color = Color.Black,
        modifier = Modifier.padding(bottom = 8.dp)
    )
}

@Composable
private fun DateInputField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        trailingIcon = {
//            Icon(
//                Icons.Default.CalendarToday,
//                contentDescription = "날짜 선택",
//                tint = Color.Gray,
//                modifier = Modifier.size(20.dp)
//            )
        },
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = Color(0xFFE0E0E0),
            focusedBorderColor = Color(0xFF5B4FFF),
            unfocusedContainerColor = Color.White,
            focusedContainerColor = Color.White
        ),
        shape = RoundedCornerShape(8.dp),
        singleLine = true
    )
}

@Composable
private fun YesNoSelector(
    selected: Boolean?,
    onSelect: (Boolean) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        SelectionButton(
            text = "예",
            isSelected = selected == true,
            onClick = { onSelect(true) }
        )
        SelectionButton(
            text = "아니오",
            isSelected = selected == false,
            onClick = { onSelect(false) }
        )
    }
}

@Composable
private fun SelectionButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .width(100.dp)
            .height(44.dp)
            .border(
                width = 1.dp,
                color = if (isSelected) Color(0xFF5B4FFF) else Color(0xFFE0E0E0),
                shape = RoundedCornerShape(8.dp)
            )
            .background(
                color = if (isSelected) Color(0xFFF3F2FF) else Color.White,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text,
            fontSize = 14.sp,
            color = if (isSelected) Color(0xFF5B4FFF) else Color.Gray,
            fontWeight = if (isSelected) FontWeight.Medium else FontWeight.Normal
        )
    }
}

@Composable
private fun EditableDetailField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            label,
            fontSize = 14.sp,
            color = Color.Black,
            modifier = Modifier.width(100.dp)
        )
        Spacer(Modifier.width(12.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .weight(1f)
                .height(56.dp),
            placeholder = {
                Text("금액을 입력", fontSize = 14.sp, color = Color(0xFFBDBDBD))
            },
            trailingIcon = {
                Text("원", fontSize = 14.sp, color = Color.Gray)
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color(0xFFE0E0E0),
                focusedBorderColor = Color(0xFF5B4FFF),
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White
            ),
            shape = RoundedCornerShape(8.dp),
            singleLine = true
        )
    }
}

@Composable
private fun CalculationResultDialog(
    state: MaternityUiState,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                "계산 결과",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                if (state.calculationDetails.isNotEmpty()) {
                    state.calculationDetails.forEach { detail ->
                        Text(
                            detail,
                            fontSize = 14.sp,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                    }
                } else {
                    // 기본 계산 결과 표시
                    Text(
                        "첫 번째 30일: ${state.first30DaysPay}원",
                        fontSize = 14.sp,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                    Text(
                        "두 번째 30일: ${state.second30DaysPay}원",
                        fontSize = 14.sp,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                    Text(
                        "세 번째 30일: ${state.third30DaysPay}원",
                        fontSize = 14.sp,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                    Spacer(Modifier.height(8.dp))
                    Divider()
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "총 급여: ${state.compensation}원",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF5B4FFF),
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = onDismiss,
                colors = ButtonDefaults.textButtonColors(
                    contentColor = Color(0xFF5B4FFF)
                )
            ) {
                Text("닫기", fontSize = 16.sp, fontWeight = FontWeight.Medium)
            }
        },
        shape = RoundedCornerShape(12.dp),
        containerColor = Color.White
    )
}