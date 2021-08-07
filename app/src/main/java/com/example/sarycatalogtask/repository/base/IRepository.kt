package com.example.sarycatalogtask.repository.base

import android.preference.PreferenceActivity
import android.util.Log
import android.util.MalformedJsonException
import com.example.sarycatalogtask.domain.response.ApiResponse
import com.example.sarycatalogtask.domain.response.ErrorCode
import com.example.sarycatalogtask.domain.response.ErrorResponse
import com.example.sarycatalogtask.utils.logger.Logger
import com.example.sarycatalogtask.utils.network.NoConnectivityException
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Response
import java.net.ConnectException


private const val TAG = "IRepository"

interface IRepository {

    suspend fun <T> handleRequest(call: suspend () -> Response<T>): ApiResponse<T>? {
        return try {
            val apiResponse = call.invoke()
            if (apiResponse.isSuccessful) {
                Log.e(TAG, "handleRequest Successful: ${apiResponse.body()}")
                ApiResponse.Success(apiResponse.body())
            } else {
                when (apiResponse.code()) {
                    ErrorCode.UNAUTHORIZED.code -> {
//                        return null
                        handleFailureResponse(
                            apiResponse.headers(),
                            apiResponse.code(),
                            apiResponse.errorBody()
                        )
                    }
                    else -> {
                        Log.e(TAG, "handleRequest Not Successful: ${apiResponse.errorBody()}")
                        handleFailureResponse(
                            apiResponse.headers(),
                            apiResponse.code(),
                            apiResponse.errorBody()
                        )
//                            handleFailureResponse(apiResponse.code(), apiResponse.errorBody())
//                            handleFailureResponse(apiResponse)
                    }
                }

            }
        } catch (ex: Exception) {
            Logger.Error(TAG, ex.localizedMessage)
            ex.printStackTrace()
            Log.e(TAG, "handleRequest: Exception${ex.localizedMessage}")
            handleException(ex)
        }
    }

    fun <T> handleFailureResponse(
        headers: okhttp3.Headers,
        code: Int,
        errorBody: ResponseBody?
    ): ApiResponse<T> {
//    fun <T> handleFailureResponse(apiResponse: ApiResponse<T>): ApiResponse<T> {


        val headerLang = headers.filter {
            it.first == "Content-Language"
        }.let {
            it.first().second
        }
        Log.e(TAG, "handleFailureResponse: ${headerLang}")

        errorBody?.let {
            val jsonObject = JSONObject(errorBody.string())
            Log.e(TAG, "handleFailureResponse: $jsonObject")
            try {

                var message = ""
                var error = ""

                when(headerLang){
                    "ar" -> {
                        jsonObject.takeIf {
                            it.has("message_ar")
                        }?.let { it ->
                            message = it.getString("message_ar")
                        }
                    }
                    else -> {
                        jsonObject.takeIf {
                            it.has("message_en")
                        }?.let { it ->
                            message = it.getString("message")
                        }
                    }
                }
                Log.e(TAG, "handleFailureResponse: ${error}")

                val errorCode: ErrorCode = when (code) {
                    401 -> ErrorCode.UNAUTHORIZED
                    404 -> ErrorCode.NOT_FOUND
                    else -> ErrorCode.UNKNOWN
                }

                return ApiResponse.Failure(ErrorResponse(errorCode, message, error))

            } catch (ex: Exception) {
                val log = ex.message ?: "Unknown error while handling failure response"
                Logger.Debug(
                    "BaseRepository",
                    log
                )
                Log.e(TAG, "handleFailureResponse: $log")
            }
        }
        return ApiResponse.Failure(ErrorResponse(ErrorCode.UNKNOWN))
    }

    private fun <T> handleException(exception: Exception?): ApiResponse<T> {
        exception?.let {
            val errorResponse =
                if (exception is ConnectException ||
                    exception is NoConnectivityException
                ) {
                    ErrorResponse(ErrorCode.NO_NETWORK, message = "No internet connection")
                } else if (exception is MalformedJsonException) {
                    ErrorResponse(ErrorCode.BAD_RESPONSE, message = "MalformedJsonException")
                } else {
                    ErrorResponse(ErrorCode.UNKNOWN, message = "UNKNOWN")
                }
            return ApiResponse.Failure(errorResponse)
        }
        return ApiResponse.Failure(ErrorResponse(ErrorCode.UNKNOWN))
    }


}

