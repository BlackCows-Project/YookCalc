package com.example.yookcalc.domain.entity

data class User(
    val uniqId: String, // 가입시간을 nano단위로 기록후 저장 - 일회성 유저를 위한 값
    val email: String?,
    val name: String?,
)
