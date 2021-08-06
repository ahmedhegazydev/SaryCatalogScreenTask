package com.example.sarycatalogtask.data

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
//    val errors: ErrorHolder<String>
//    val errors: ErrorHolder?
) {

    override fun toString(): String {
        return code.name + " - " + message + ": " + errors
    }

//    data class ErrorHolder<out T>(
////        val search_text: List<T> = listOf(),
//        val search_text: List<T> = listOf(),
//    )

    data class ErrorHolder(
        var search_text: List<String> = listOf(),
    )

}
