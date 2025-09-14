package com.example.yookcalc.data.settings

object ParentalLeavePaySetting {

    var isInitialized = false

    private val normalWageMap: MutableMap<Int, MutableList<Int>> = mutableMapOf()
    private val postPaymentMap: MutableMap<Int, MutableList<Int>> = mutableMapOf()
    private val upperLimit: MutableMap<Int, MutableList<Int>> = mutableMapOf()
    private val lowerLimit: MutableMap<Int, MutableList<Int>> = mutableMapOf()

    private val normalWageMapSix: MutableMap<Int, MutableList<Int>> = mutableMapOf()
    private val postPaymentMapSix: MutableMap<Int, MutableList<Int>> = mutableMapOf()
    private val upperLimitSix: MutableMap<Int, MutableList<Int>> = mutableMapOf()
    private val lowerLimitSix: MutableMap<Int, MutableList<Int>> = mutableMapOf()

    // "타입, 년, 월, 값"으로 서버값 받아서 변환 후 세팅
    // TODO("추후 Response객체 만들어지면 수정하기")
    fun setParentalLeavePaySetting(settingList: MutableList<String>) {
        isInitialized = true

        settingList.forEach { it ->
            val valueList = it.split(" ,")

            val type = runCatching { ParentalPaySettingType.valueOf(valueList[0]) }.getOrNull()
            val year = valueList[1].toInt()
            val month = valueList[2].toInt()
            val value = valueList[3].toInt()

            when (type) {
                ParentalPaySettingType.NORMAL_WAGE -> normalWageMap
                ParentalPaySettingType.NORMAL_WAGE_SIX -> normalWageMapSix
                ParentalPaySettingType.POST_PAYMENT -> postPaymentMap
                ParentalPaySettingType.POST_PAYMENT_SIX -> postPaymentMapSix
                ParentalPaySettingType.UPPER_LIMIT -> upperLimit
                ParentalPaySettingType.UPPER_LIMIT_SIX -> upperLimitSix
                ParentalPaySettingType.LOWER_LIMIT -> lowerLimit
                ParentalPaySettingType.LOWER_LIMIT_SIX -> lowerLimitSix
                else -> mutableMapOf()
            }.getOrPut(year) {
                MutableList(12) { value }
            }[month] = value
        }
    }

    fun setDefaultParentalLeavePaySetting() {
        isInitialized = true

        normalWageMap[2023] = MutableList(12) { 80 }
        normalWageMap[2024] = MutableList(12) { 80 }
        normalWageMap[2025] = MutableList(12) { if (it < 6) 100 else 80 }

        postPaymentMap[2023] = MutableList(12) { 75 }
        postPaymentMap[2024] = MutableList(12) { 75 }
        postPaymentMap[2025] = MutableList(12) { 100 }

        upperLimit[2023] = MutableList(12) { 1500000 }
        upperLimit[2024] = MutableList(12) { 1500000 }
        upperLimit[2025] = MutableList(12) { 1500000 }

        lowerLimit[2023] = MutableList(12) { 700000 }
        lowerLimit[2024] = MutableList(12) { 700000 }
        lowerLimit[2025] = MutableList(12) { 700000 }

        normalWageMapSix[2023] = MutableList(12) { if (it < 3) 100 else 80 }
        normalWageMapSix[2024] = MutableList(12) { if (it < 6) 100 else 80 }
        normalWageMapSix[2025] = MutableList(12) { if (it < 6) 100 else 80 }

        postPaymentMapSix[2023] = MutableList(12) { if (it < 3) 100 else 75 }
        postPaymentMapSix[2024] = MutableList(12) { if (it < 6) 100 else 75 }
        postPaymentMapSix[2025] = MutableList(12) { 100 }

        upperLimitSix[2023] = MutableList(12) {
            when (it) {
                0 -> 2000000
                1 -> 2500000
                2 -> 3000000
                else -> 1500000
            }
        }
        upperLimitSix[2024] = MutableList(12) {
            when (it) {
                in 0..5 -> 2000000 + it*500000
                else -> 1500000
            }
        }
        upperLimitSix[2025] = MutableList(12) {
            when (it) {
                0, 1 -> 2500000
                in 2..5 -> 3000000 + it*500000
                else -> 1600000
            }
        }

        lowerLimitSix[2023] = MutableList(12) { 700000 }
        lowerLimitSix[2024] = MutableList(12) { 700000 }
        lowerLimitSix[2025] = MutableList(12) { 700000 }
    }

    // 초기화가 안 됐거나 존재하지 않는 값이면 -1 반환
    fun value(type: ParentalPaySettingType, year: Int, month: Int): Int {
        if (!isInitialized) return -1

        return when (type) {
            ParentalPaySettingType.NORMAL_WAGE -> normalWageMap
            ParentalPaySettingType.NORMAL_WAGE_SIX -> normalWageMapSix
            ParentalPaySettingType.POST_PAYMENT -> postPaymentMap
            ParentalPaySettingType.POST_PAYMENT_SIX -> postPaymentMapSix
            ParentalPaySettingType.UPPER_LIMIT -> upperLimit
            ParentalPaySettingType.UPPER_LIMIT_SIX -> upperLimitSix
            ParentalPaySettingType.LOWER_LIMIT -> lowerLimit
            ParentalPaySettingType.LOWER_LIMIT_SIX -> lowerLimitSix
        }.getOrElse(year) { mutableListOf() }.getOrElse(month - 1) { -1 }
    }

    enum class ParentalPaySettingType {
        NORMAL_WAGE,
        NORMAL_WAGE_SIX,
        POST_PAYMENT,
        POST_PAYMENT_SIX,
        UPPER_LIMIT,
        UPPER_LIMIT_SIX,
        LOWER_LIMIT,
        LOWER_LIMIT_SIX;
    }
}