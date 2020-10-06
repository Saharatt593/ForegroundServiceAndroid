package th.co.cdg.foregroundserviceandroid.data.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import th.co.cdg.foregroundserviceandroid.data.remote.AuthInterceptor
import th.co.cdg.foregroundserviceandroid.data.remote.MobileApi
import th.co.cdg.foregroundserviceandroid.data.remote.streaming.StreamMobileApi

import java.lang.reflect.Modifier
import java.util.concurrent.TimeUnit

val remoteModule = module {
    //remote
    single<MobileApi> { RemoteDataModule().provideMdApi(get{ parametersOf(false) }) }

    single<Gson> { RemoteDataModule().provideGsonBuilder() }
    single<Interceptor> { RemoteDataModule().provideLogging() }


    //Stream
    single<StreamMobileApi> { RemoteDataModule().provideStreamApi(get{ parametersOf(true) }) }

    factory<OkHttpClient> {(isStream: Boolean)-> RemoteDataModule().provideOkHttpClient(get(), false,isStream) }
    factory<Retrofit> { (isStream: Boolean)->RemoteDataModule().provideRetrofit(get(), get{ parametersOf(isStream) }, "http://10.100.150.155:8888/") }
}

class RemoteDataModule {


    fun provideMdApi(retrofit: Retrofit): MobileApi =
        retrofit.create(MobileApi::class.java)

    fun provideStreamApi(retrofit: Retrofit):StreamMobileApi =
        retrofit.create(StreamMobileApi::class.java)


    fun provideRetrofit(gson: Gson, client: OkHttpClient, baseUrl: String): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()


    fun provideGsonBuilder(): Gson = GsonBuilder()
        .excludeFieldsWithModifiers(Modifier.TRANSIENT,Modifier.STATIC)
        .create()



    fun provideOkHttpClient(
        logging: Interceptor,
        flagProduction: Boolean,
        isStream: Boolean
    ): OkHttpClient {
        val okHttpClient: OkHttpClient.Builder = OkHttpClient.Builder()

        if (!isStream) {
            okHttpClient.connectTimeout(30, TimeUnit.SECONDS)
            okHttpClient.readTimeout(60, TimeUnit.SECONDS)

            if (!flagProduction) {
                okHttpClient.addInterceptor(logging) //todo log okhttp
            }
        }
        return okHttpClient.build()
    }

    fun provideLogging(): Interceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return logging
    }

}
