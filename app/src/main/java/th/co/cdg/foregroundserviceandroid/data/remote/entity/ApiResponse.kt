package th.co.cdg.foregroundserviceandroid.data.remote.entity

import com.google.gson.annotations.SerializedName

class ApiResponse<T>(
        @SerializedName("results")
        var results: List<T>? = null,
        @SerializedName("result")
        var result: T? = null,
        @SerializedName("messages")
        var messages: List<Message>? = emptyList(),
        @SerializedName("message")
        var message: String? = null,
        @SerializedName("status")
        var status: StatusBean? = null,
        @SerializedName("isSuccess")
        var isSuccess: Boolean = false
) {
    val messageString: String
        get() {
            val messageString = StringBuilder("")

            if (!message.isNullOrEmpty()) {
                messageString.append(message)
            }

            messages!!
                    .filter { !it.message.isNullOrEmpty() }
                    .forEach { messageString.append(it.message!!) }

            return messageString.toString()
        }
}
