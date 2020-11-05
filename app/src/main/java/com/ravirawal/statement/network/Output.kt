package com.ravirawal.statement.network

sealed class Output<RES> constructor(private val value: Any?, var isOutput: Boolean = false) {
    val isSuccess = value !is Error && value !is Unit
    val isFailure = value is Error

    data class Success<RES>(val data: RES, var message: String?, val code: Int?) : Output<RES>(data, true)
    data class Failure<RES>(val exception: Error, var message: String?, val code: Int?) : Output<RES>(exception, true)
//    object LOADING : Output<Nothing>(Unit, false)
    data class LOAD<RES>(val nothing: Nothing? = null) : Output<RES>(nothing, false)
}


/**
 * Base Class for handling errors/failures/exceptions.
 * Every feature specific error should extend [FeatureError] class.
 */
sealed class Error {
    /**
     * Case when
     * Network failure
     * NETWORK_FAILURE
     * */
    object NetworkConnection : Error()

    object ServerError : Error()

    object JobCancellation : Error()

    object SSLPeerUnverifiedException : Error()

    /** * Extend this class for feature specific failures.*/
    abstract class FeatureError : Error()

    object APIError : FeatureError()

}
