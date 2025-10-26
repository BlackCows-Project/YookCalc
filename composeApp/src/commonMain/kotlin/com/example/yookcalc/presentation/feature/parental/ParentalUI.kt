package com.example.yookcalc.presentation.feature.parental

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.text.KeyboardOptions
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
fun ParentalUI(viewModel: ParentalViewModel = koinInject()) {
    val state by viewModel.state.collectAsState()
    var showResultDialog by remember { mutableStateOf(false) }

    // Effect 처리
    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is ParentalEffect.ShowToast -> println("Toast: ${effect.message}")
                is ParentalEffect.ShowError -> println("Error: ${effect.message}")
                is ParentalEffect.ShowCalculationResult -> {
                    println("Result: ${effect.result}")
                    showResultDialog = true
                }
            }
        }
    }

    // 화면 진입 시 기본 데이터 로드
    LaunchedEffect(Unit) {
        viewModel.onEvent(ParentalEvent.LoadDefaultData)
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
            "육아휴직",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        Divider(color = Color(0xFFE0E0E0), thickness = 1.dp)
        Spacer(Modifier.height(24.dp))

        // 출산일
        SectionTitle("출산일")
        DateInputField(
            value = state.birthDate,
            onValueChange = { viewModel.onEvent(ParentalEvent.ChangeBirthDate(it)) },
            placeholder = "날짜를 선택하세요"
        )

        Spacer(Modifier.height(24.dp))

        // 본인 육아휴직을 사용한 적이 있습니까?
        SectionTitle("본인 육아휴직을 사용한 적이 있습니까?")
        YesNoSelector(
            selected = state.useReducedWorkingHours,
            onSelect = { viewModel.onEvent(ParentalEvent.SelectReducedWorkingHours(it)) }
        )

        Spacer(Modifier.height(24.dp))

        // 육아휴직 기간
        SectionTitle("육아휴직 기간")
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            DateInputField(
                value = state.startDate,
                onValueChange = { viewModel.onEvent(ParentalEvent.ChangeStartDate(it)) },
                modifier = Modifier.weight(1f),
                placeholder = "2025. 06. 16일"
            )
            Text(
                "~",
                fontSize = 16.sp,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            DateInputField(
                value = state.endDate,
                onValueChange = { viewModel.onEvent(ParentalEvent.ChangeEndDate(it)) },
                modifier = Modifier.weight(1f),
                placeholder = "2025. 09. 1일"
            )
        }

        Spacer(Modifier.height(8.dp))

        // + 버튼 (기능 구현 필요시 추가)
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "＋",
                fontSize = 24.sp,
                color = Color.Gray,
                modifier = Modifier.clickable {
                    // 기간 추가 로직
                }
            )
        }

        Spacer(Modifier.height(24.dp))

        // 배우자가 육아휴직을 사용했거나 사용할 계획인가요?
        SectionTitle("배우자가 육아휴직을 사용했거나 사용할 계획인가요?")
        YesNoSelector(
            selected = state.useSpouseParentalLeave,
            onSelect = { viewModel.onEvent(ParentalEvent.SelectSpouseParentalLeave(it)) }
        )

        Spacer(Modifier.height(24.dp))

        // 통상임금
        SectionTitle("통상임금")
        OutlinedTextField(
            value = state.monthlyPay,
            onValueChange = {
                if (it.isEmpty() || it.all { char -> char.isDigit() }) {
                    viewModel.onEvent(ParentalEvent.ChangeMonthlyPay(it))
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            placeholder = {
                Text("금액 입력", fontSize = 14.sp, color = Color(0xFFBDBDBD))
            },
            trailingIcon = {
                Text("월", fontSize = 14.sp, color = Color.Gray)
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

        // 계산하기 버튼
        Button(
            onClick = { viewModel.onEvent(ParentalEvent.CalculateParentalPay) },
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
    modifier: Modifier = Modifier,
    placeholder: String = ""
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        placeholder = {
            if (placeholder.isNotEmpty()) {
                Text(placeholder, fontSize = 14.sp, color = Color(0xFFBDBDBD))
            }
        },
        trailingIcon = {
//            Icon(
//                // Calendar 아이콘 필요시 추가
//                // Icons.Default.CalendarToday,
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
private fun CalculationResultDialog(
    state: ParentalUiState,
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
                    Spacer(Modifier.height(8.dp))
                    Divider()
                    Spacer(Modifier.height(8.dp))
                }
                Text(
                    "총 급여: ${state.totalCompensation}원",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF5B4FFF),
                    modifier = Modifier.padding(vertical = 4.dp)
                )
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