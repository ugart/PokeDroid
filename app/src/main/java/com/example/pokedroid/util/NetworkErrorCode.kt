package com.example.pokedroid.util

import android.support.annotation.StringRes
import com.example.pokedroid.R

enum class NetworkErrorCode(@StringRes val message: Int) {
    CLIENT(R.string.network_error_try_again),
    SERVER(R.string.network_error_server),
    BAD_REQUEST(R.string.network_error_bad_request),
    UNAUTHORIZED(R.string.network_error_unauthorized),
    FORBIDDEN(R.string.network_error_try_again),
    NOT_FOUND(R.string.network_error_not_found),
    TIMEOUT(R.string.network_error_try_again),
    UNKNOWN_HOST(R.string.network_error_connection),
    CONNECTION(R.string.network_error_connection),
    CANCELED(R.string.network_error_canceled),
    UNEXPECTED(R.string.network_error_try_again)
}