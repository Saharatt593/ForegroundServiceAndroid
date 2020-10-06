package th.co.cdg.foregroundserviceandroid.data.remote.entity

import com.google.gson.annotations.SerializedName

class Message(
        @SerializedName("businessCode")
        var businessCode: String? = null,
        @SerializedName("message")
        var message: String? = null,
        @SerializedName("level")
        var level: String? = null
)
