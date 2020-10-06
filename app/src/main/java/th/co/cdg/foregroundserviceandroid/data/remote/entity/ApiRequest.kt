package th.co.cdg.foregroundserviceandroid.data.remote.entity

import com.google.gson.annotations.SerializedName

class ApiRequest<T>(
        @SerializedName("requests")
        var requests: List<T>? = null,
        @SerializedName("request")
        var request: T? = null
)
