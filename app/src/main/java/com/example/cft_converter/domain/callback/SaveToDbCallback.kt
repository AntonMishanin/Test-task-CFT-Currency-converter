package com.example.cft_converter.domain.callback

interface SaveToDbCallback {

    fun onSuccess()

    fun onError(message: String)
}