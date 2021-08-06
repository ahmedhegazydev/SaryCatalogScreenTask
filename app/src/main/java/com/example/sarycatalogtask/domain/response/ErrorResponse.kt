package com.example.sarycatalogtask.domain.response

enum class ErrorCode(val code: Int) {
    UNAUTHORIZED(401),
    NOT_FOUND(404),
    NO_NETWORK(0),
    BAD_RESPONSE(20),
    UNKNOWN(30)
}

data class ErrorResponse(
    val code: ErrorCode = ErrorCode.UNKNOWN,
    val message: String = "",
    val errors: String = ""
) {

    override fun toString(): String {
        return code.name + " - " + message + ": " + errors
    }

}
