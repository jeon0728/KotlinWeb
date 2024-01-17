package com.example.KotlinWeb

import java.time.LocalDateTime
import java.time.format.DateTimeFormatterBuilder
import java.time.temporal.ChronoField
import java.util.*

//kotlin에서는 Java에서 쓰던 util class 대신 Extension을 만들어서 사용한다고 함.

// LocalDateTime이라는 외부 라이브러리의 클래스에서
// format이라는 기존함수를 확장하여 사용하는 코드
fun LocalDateTime.format() {
    this.format(koreanDateFormat)
}

// 코틀린의 또다른 함수 정의 방법
// fun LocalDateTime.format() = this.format(koreanDateFormat)

// .. > 범위 연산자 : 좌항 부터 우항까지 포함
// associate 함수는 List를 Map으로 만들어준다.
// associate 함수 내부에 있는 'it'는 List의 value라고 생각하면 된다. 여기서는 1 부터 31
// to 는 '=' 으로 변환되는데, it.toLong() to getOrdinary(it) 의 결과는 1=1st, 2=2nd, 3=3rd, 4=4th, 5=5th ..} 이다.
private val daysLookup = (1..31).associate{ it.toLong() to getOrdinary(it)}

private val koreanDateFormat = DateTimeFormatterBuilder()
    .appendPattern("yyyy-MM-dd")
    .appendLiteral(" ")
    .appendText(ChronoField.DAY_OF_MONTH, daysLookup)
    .appendLiteral(" ")
    .appendPattern("yyyy")
    .toFormatter(Locale.KOREAN)

private fun getOrdinary(n:Int) = when {
    n in 11..13 -> "${n}th"
    n % 10 == 1 -> "${n}st"
    n % 10 == 2 -> "${n}nd"
    n % 10 == 3 -> "${n}rd"
    else -> "${n}th"
}

// String 이라는 외부 라이브러리의 클래스에서
// toSlug 라는 새로운 함수를 확장하여 사용하는 코드
fun String.toSlug() = lowercase()
    .replace("\n", " ")
    .replace("[^a-z\\d\\s]".toRegex(), "")
    .split(" ")
    .joinToString("-")
    .replace("-+".toRegex(), "-")