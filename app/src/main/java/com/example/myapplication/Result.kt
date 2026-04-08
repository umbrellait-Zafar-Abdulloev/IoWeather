package com.example.myapplication

sealed class Result<T> {
    data class Data<T>(val data: T): Result<T>()
    data class Error<T>(val throwable: Throwable): Result<T>()
}