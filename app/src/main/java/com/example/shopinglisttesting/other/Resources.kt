package com.example.shopinglisttesting.other

data class Resources<out T>(val status: Status, val data: T?, val message: String?) {

    companion object{
        fun <T> successes(data: T?) = Resources(Status.SUCCESSES, data , null)

        fun <T> error(message: String, data: T?) = Resources(Status.ERROR, data, message)

        fun <T> landing(data: T?) = Resources(Status.LOADING, data, null)
    }

    enum class Status{
        ERROR,
        SUCCESSES,
        LOADING
    }
}