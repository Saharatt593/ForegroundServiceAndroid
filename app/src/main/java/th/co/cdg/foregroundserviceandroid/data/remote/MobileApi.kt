package th.co.cdg.foregroundserviceandroid.data.remote

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface MobileApi {
    @GET("test/add/{data}/{device}")
    fun add(@Path("data") data:String,@Path("device") device:String): Call<ResponseBody>

}