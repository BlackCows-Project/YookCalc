package com.example.yookcalc.presentation.ui.maternity

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.yookcalc.domain.MaternityLeaveCalculator
import com.example.yookcalc.presentation.MaternityLeaveViewModel

/**
 * 출산휴가 계산기 화면 Composable 함수입니다. (Material 3 완벽 적용)
 * This is the Composable function for the Maternity Leave Calculator screen (Fully M3 Compliant).
 */
@Composable
fun MaternityLeaveScreen(viewModel: MaternityLeaveViewModel) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            // Use background color from the M3 theme
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // --- Screen Title ---
        Text(
            text = "출산 전후 휴가일수 계산기",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
            // Use primary text color from the M3 theme
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = "휴가 기간과 주요 날짜를 간편하게 계산해보세요.",
            style = MaterialTheme.typography.titleMedium,
            // Use secondary text color from the M3 theme
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 4.dp, bottom = 24.dp)
        )

        Card(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            shape = RoundedCornerShape(12.dp),
            // Use card container color from the M3 theme
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                // --- 1. Birth Type Selection ---
                SectionTitle("1. 출산 유형을 선택하세요")
                BirthTypeSelector(
                    selectedType = uiState.birthType,
                    onTypeSelected = { viewModel.updateBirthType(it) }
                )

                // --- 2. Date Input ---
                SectionTitle("2. 날짜를 입력하세요 (예: 2025-01-01)")
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    DateInput(
                        label = "휴가 시작일",
                        date = uiState.startDate,
                        onDateChange = { viewModel.updateStartDate(it) },
                        modifier = Modifier.weight(1f)
                    )
                    DateInput(
                        label = "출산(예정)일",
                        date = uiState.dueDate,
                        onDateChange = { viewModel.updateDueDate(it) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // --- 3. Calculation Result Display ---
        uiState.error?.let {
            ErrorDisplay(it)
        }

        uiState.result?.let { result ->
            ResultDisplay(result)
        }

        // --- Disclaimer Text ---
        Text(
            text = "※ 본 계산기는 참고용이며, 정확한 정보는 관할 고용센터에 문의하시기 바랍니다.",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 24.dp)
        )
    }
}

@Composable
private fun SectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
        color = MaterialTheme.colorScheme.onSurface,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
private fun BirthTypeSelector(
    selectedType: MaternityLeaveCalculator.BirthType,
    onTypeSelected: (MaternityLeaveCalculator.BirthType) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        MaternityLeaveCalculator.BirthType.values().forEach { birthType ->
            val isSelected = selectedType == birthType
            // Use M3 theme colors for selected/unselected states
            val backgroundColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondaryContainer
            val textColor = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSecondaryContainer
            val text = when (birthType) {
                MaternityLeaveCalculator.BirthType.SINGLE -> "단태아"
                MaternityLeaveCalculator.BirthType.MULTIPLE -> "다태아"
                MaternityLeaveCalculator.BirthType.PREMATURE -> "미숙아 등"
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(8.dp))
                    .background(backgroundColor)
                    .clickable { onTypeSelected(birthType) }
                    .padding(vertical = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(text, color = textColor, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun DateInput(
    label: String,
    date: String,
    onDateChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = date,
        onValueChange = onDateChange,
        label = { Text(label) },
        shape = RoundedCornerShape(8.dp),
        // Use M3 TextFieldDefaults and theme colors
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.surface,
            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        modifier = modifier,
        singleLine = true
    )
}

@Composable
private fun ResultDisplay(result: MaternityLeaveCalculator.LeaveCalculationResult) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                "계산 결과",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            ResultItem("🗓️ 예상 휴가 종료일", result.endDate.toString())
            ResultItem("🤰 산후 확보일수", "${result.postpartumDaysSecured}일 (필수 ${result.requiredDays}일)")
            ResultItem(
                "✅ 산후 필수일수 충족 여부",
                if (result.isPostpartumMet) "충족" else "부족 (시작일 조정 필요)",
                // Use M3 theme colors for success/error states
                contentColor = if (result.isPosteamMet) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
            )
            ResultItem("📅 산전휴가 최대 사용 시 시작 가능일", result.maxPrenatalStartDate.toString())
        }
    }
}

@Composable
private fun ResultItem(label: String, value: String, contentColor: Color = MaterialTheme.colorScheme.onSurface) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, modifier = Modifier.weight(1f), fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(value, fontWeight = FontWeight.Bold, color = contentColor, textAlign = TextAlign.End)
    }
}


@Composable
private fun ErrorDisplay(message: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .clip(RoundedCornerShape(12.dp))
            // Use M3 theme colors for error container
            .background(MaterialTheme.colorScheme.errorContainer)
            .border(1.dp, MaterialTheme.colorScheme.error, RoundedCornerShape(12.dp))
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "⚠️ $message",
            // Use M3 theme colors for text on error container
            color = MaterialTheme.colorScheme.onErrorContainer,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}