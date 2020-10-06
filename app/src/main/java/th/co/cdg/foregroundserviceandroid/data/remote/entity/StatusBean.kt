package th.co.cdg.foregroundserviceandroid.data.remote.entity

import com.google.gson.annotations.SerializedName

data class StatusBean(
        @SerializedName("statusCode")
        var statusCode: String? = null,
        @SerializedName("statusMessage")
        var statusMessage: String? = null
)
