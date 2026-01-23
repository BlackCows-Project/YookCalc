package com.example.yookcalc.domain.service

import com.example.yookcalc.data.settings.ParentalLeavePaySetting
import com.example.yookcalc.domain.entity.DateRange
import com.example.yookcalc.domain.entity.ParentalLeave
import com.example.yookcalc.domain.entity.ParentalLeaveList

/**
 * 육아 휴직 계산을 처리하는 서비스 클래스입니다.
 * 휴가 회차 관리, 유효성 검사, 소급 적용 여부 등을 판단합니다.
 */
class ParentalLeaveCalculator {

    /**
     * 급여 설정(ParentalLeavePaySetting)이 초기화되었는지 확인합니다.
     * lazy를 사용하여 실제 필요할 때 확인합니다.
     */
    val checkInitialized by lazy {
        ParentalLeavePaySetting.isInitialized
    }

    /**
     * 6+6 부모육아휴직제 적용 시작 지점을 확인합니다.
     * 배우자의 육아 휴직 시작일과 현재 본인의 휴가 기간을 비교하여 
     * 소급 적용 대상을 찾습니다.
     * 
     * @return (휴가 회차 인덱스, 해당 회차 내의 라운드 인덱스) 쌍
     */
    private fun checkSixApplyStartRound(parentalLeaveList: ParentalLeaveList): Pair<Int, Int>? = with(parentalLeaveList) {
        if (!checkInitialized) return null

        // 본인의 모든 휴가 회차를 순회함
        leaveList.forEachIndexed { idx1, parentalLeave ->
            // 각 회차 내의 1개월 단위 라운드를 순회함
            parentalLeave.rangeList.forEachIndexed { idx2, range ->
                // 배우자의 초기 휴직일이 현재 라운드 기간에 포함되는지 확인
                if (range.contains(spouseInitialDate)) {
                    return Pair(idx1, idx2)
                }
            }
        }

        return null
    }

    /**
     * 새로운 육아 휴직(분할 사용)을 리스트에 추가합니다.
     * 유효성 검사(최대 4회, 기간 중복 안됨, 최대 12개월)를 통과해야 합니다.
     */
    private fun checkAndAddParentalLeave(parentalLeave: ParentalLeave, parentalLeaveList: ParentalLeaveList): ParentalLeaveList? {
        if (!checkInitialized) return null

        // [비즈니스 규칙] 육아 휴직은 최대 4회까지 분할 사용 가능으로 설정됨 (프로젝트 기준)
        if (parentalLeaveList.leaveList.size >= 4) return null

        // [비즈니스 규칙] 기존에 등록된 휴가 기간과 겹치면 안 됨
        parentalLeaveList.leaveList.forEach {
            if (it.range.isDuplicated(parentalLeave.range)) {
                return null
            }
        }

        // [비즈니스 규칙] 육아 휴직 총 합계는 12개월(라운드)을 초과할 수 없음
        if (parentalLeave.endRound > 12) return null

        // 모든 조건을 통과하면 리스트에 추가하여 반환
        return parentalLeaveList.copy(leaveList = parentalLeaveList.leaveList + parentalLeave)
    }


    /**
     * 새로운 휴가 회차를 생성하기 전 초기 상태를 설정합니다.
     * 이전 회차의 종료 정보를 바탕으로 연속성을 유지합니다.
     */
    private fun initializeParentalLeave(parentalLeaveList: ParentalLeaveList, dateRange: DateRange): ParentalLeave? {
        // 이미 4회 넘게 신청했다면 생성 불가
        if (parentalLeaveList.leaveList.size >= 4) return null

        // 이전 회차가 있다면 마지막 회차 정보를 참고함
        parentalLeaveList.leaveList.lastOrNull()?.let {
            return ParentalLeave(
                dateRange,
                it.endRestDate, // 이전 회차에서 남은 짜투리 일수를 이번 회차의 시작RestDays로 사용
                if (it.endRestDate == 0) it.endRound + 1 else it.endRound, // 라운드 번호 결정
            )
        }

        // 첫 번째 휴직 신청인 경우 초기값 설정
        return ParentalLeave(
            dateRange,
            0,
            1,
        )
    }
}
