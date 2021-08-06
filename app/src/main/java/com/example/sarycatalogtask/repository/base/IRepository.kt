package com.example.sarycatalogtask.repository.base

import android.util.Log
import com.example.sarycatalogtask.domain.response.ApiResponse
import com.example.sarycatalogtask.domain.response.ErrorCode
import com.example.sarycatalogtask.domain.response.ErrorResponse
import com.example.sarycatalogtask.utils.network.NoConnectivityException
import com.example.sarycatalogtask.utils.logger.Logger
import com.google.gson.stream.MalformedJsonException
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
                            return null
                        }
                        else -> {
                            Log.e(TAG, "handleRequest Not Successful: ${apiResponse.errorBody()}")
                            handleFailureResponse(apiResponse.code(), apiResponse.errorBody())
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

    fun <T> handleFailureResponse(code: Int, errorBody: ResponseBody?): ApiResponse<T> {
        errorBody?.let {
            val jsonObject = JSONObject(errorBody.string())
            Log.e(TAG, "handleFailureResponse: $jsonObject")
            try {

                var message = ""
                var error = ""

                if (jsonObject.has("errors")) {
//                    val errors = jsonObject.getJSONArray("errors")
                    val errorsObj = jsonObject.getJSONObject("errors")
                    errorsObj.takeIf {
                        it.has("trans_type")
                    }?.let { it ->
                        error = it.getJSONArray("trans_type").takeIf {
                            it.length() != 0
                        }?.let { errors ->
                            errors[0]
                        }.toString()
                    }

                    var isErrorDeptForm = false
                    errorsObj.takeIf {
                        it.has("departure_from")
                    }?.let { it ->
                        error = it.getJSONArray("departure_from").takeIf {
                            it.length() != 0
                        }?.let { errors ->
                            isErrorDeptForm = true
                            errors[0]
                        }.toString()
                    }
                    errorsObj.takeIf {
                        it.has("departure_to") && !isErrorDeptForm
                    }?.let { it ->
                        error = it.getJSONArray("departure_to").takeIf {
                            it.length() != 0
                        }?.let { errors ->
                            errors[0]
                        }.toString()
                    }

                    var isErrorEmail = false
                    errorsObj.takeIf {
                        it.has("email")
                    }?.let { it ->
                        error = it.getJSONArray("email").takeIf {
                            it.length() != 0
                        }?.let { errors ->
                            isErrorEmail = true
                            errors[0]
                        }.toString()
                    }
                    errorsObj.takeIf {
                        it.has("password")
                    }?.let { it ->
                        error = it.getJSONArray("password").takeIf {
                            it.length() != 0
                        }?.let { errors ->
                            errors[0]
                        }.toString()
                    }
                }

                Log.e(TAG, "handleFailureResponse: ${error}")


                if (jsonObject.has("message")) {
                    message = jsonObject.getString("message")
                }

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

