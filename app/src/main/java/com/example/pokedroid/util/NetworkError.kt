package com.example.pokedroid.util

import com.google.gson.Gson
import retrofit2.HttpException
import java.net.*

class NetworkError(private val exception: Throwable, private val avoidLogout: Boolean = false) {

    private var errorBody: ErrorResponse? = null

    var code: NetworkErrorCode
    var message: StringWrapper

    init {

        code = when (exception) {
            is HttpException -> getHttpErrorCode(exception)
            is SocketTimeoutException -> NetworkErrorCode.TIMEOUT
            is UnknownHostException -> NetworkErrorCode.UNKNOWN_HOST
            is ConnectException -> NetworkErrorCode.CONNECTION
            is SocketException -> NetworkErrorCode.CANCELED
            else -> NetworkErrorCode.UNEXPECTED
        }

        if (code != NetworkErrorCode.SERVER) {
            tryConvertErrorBody()
        }

        message = errorBody?.message?.let { serverMessage ->
            StringWrapper(serverMessage)
        } ?: kotlin.run { StringWrapper(code.message) }

        handleCode()

    }

    private fun getHttpErrorCode(httpException: HttpException): NetworkErrorCode {
        return when (httpException.code()) {
            HttpURLConnection.HTTP_BAD_REQUEST -> NetworkErrorCode.BAD_REQUEST
            HttpURLConnection.HTTP_UNAUTHORIZED -> NetworkErrorCode.UNAUTHORIZED
            HttpURLConnection.HTTP_FORBIDDEN -> NetworkErrorCode.FORBIDDEN
            HttpURLConnection.HTTP_NOT_FOUND -> NetworkErrorCode.NOT_FOUND
            in 400..499 -> NetworkErrorCode.CLIENT
            in 500..599 -> NetworkErrorCode.SERVER
            else -> NetworkErrorCode.UNEXPECTED
        }
    }

    private fun tryConvertErrorBody() {
        if (exception is HttpException) {
            try {
                errorBody = Gson().fromJson(exception.response().errorBody()?.string(), ErrorResponse::class.java)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun handleCode() {
        if (code == NetworkErrorCode.UNAUTHORIZED && !avoidLogout) {
            //TeenTXApplication().clearAppData()
        }
    }

}