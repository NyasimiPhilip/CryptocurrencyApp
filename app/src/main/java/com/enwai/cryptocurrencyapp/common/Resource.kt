package com.enwai.cryptocurrencyapp.common

// A sealed class representing a generic resource that can hold either data of type T,
// a message, or both. This class is designed to encapsulate the different states
// (success, error, loading) of a resource that is typically used in asynchronous operations.
sealed class Resource<T>(val data: T? = null, val message: String? = null) {

    // A subclass of Resource representing the success state.
    class Success<T>(data: T) : Resource<T>(data)

    // A subclass of Resource representing the error state.
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)

    // A subclass of Resource representing the loading state.
    class Loading<T>(data: T? = null) : Resource<T>(data)
}
