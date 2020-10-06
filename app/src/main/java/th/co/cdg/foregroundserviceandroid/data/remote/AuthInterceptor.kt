package th.co.cdg.foregroundserviceandroid.data.remote



class AuthInterceptor() {
//
//    val remoteSource: MobileApi by inject()
//
//    override fun intercept(chain: Interceptor.Chain): Response {
//
//        val request = chain.request()
//
//        val token: String? = if (!UserPreferences.getAccessToken(Application.mApplicationContext!!)
//                .isNullOrBlank()
//        ) {
//            if (chain.request().url.toString()
//                    .contains("renewToken") || chain.request().url.toString()
//                    .contains("auth") || chain.request().url.toString()
//                    .contains("lov") || chain.request().url.toString()
//                    .contains("company/register") || chain.request().url.toString()
//                    .contains("employee/register")
//            ) {
//                null
//            } else {
//                "Bearer ${UserPreferences.getAccessToken(Application.mApplicationContext!!)}"
//            }
//        } else {
//            null
//        }
//
//
//        val originalRequest = if (
//            !token.isNullOrBlank()
//        ) {
//            request.newBuilder().header("Authorization", token).build()
//        } else {
//            request.newBuilder().build()
//        }
//
//        val response = chain.proceed(originalRequest)
//
//        if (response.code == Constants.HTTP_UNAUTHORIZED || response.code == Constants.HTTP_FORBIDDEN) {
//
//            if (chain.request().url.toString()
//                    .contains("renewToken") || chain.request().url.toString()
//                    .contains("auth")
//            ) { // refresh token are expired
//                return response
//            } else { // access token are expired
//                val oldRefreshToken =
//                    UserPreferences.getRefreshToken(Application.mApplicationContext!!)
//
//                val callAccessToken: retrofit2.Response<ApiResponse<ResponseAuth>> =
//                    remoteSource.renewToken(
//                    ApiRequest(request =Authentication(refreshToken =oldRefreshToken ))
//                    ).execute()
//                return if (callAccessToken.raw().code == Constants.HTTP_UNAUTHORIZED || callAccessToken.raw().code == Constants.HTTP_FORBIDDEN || callAccessToken.raw().code == Constants.HTTP_ERROR) {
//                    response
//                } else {
//
//                    UserPreferences.setNullToken(Application.mApplicationContext)
//
//
//                    val accessToken = callAccessToken.body()?.result?.authentication?.accessToken
//                    val refreshToken = callAccessToken.body()?.result?.authentication?.refreshToken
//                    UserPreferences.setAccessToken(Application.mApplicationContext!!, accessToken)
//                    UserPreferences.setRefreshToken(Application.mApplicationContext!!, refreshToken)
//                    val newRequest =
//                        request.newBuilder().header("Authorization", "Bearer $accessToken").build()
//                    return chain.proceed(newRequest)
//                }
//            }
//        } else {
//            return response
//        }
//    }
}
