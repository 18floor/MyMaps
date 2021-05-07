package ru.netology.mymap.model

sealed class AppError(var code: String): RuntimeException()

object UnknownError: AppError("error_unknown")
