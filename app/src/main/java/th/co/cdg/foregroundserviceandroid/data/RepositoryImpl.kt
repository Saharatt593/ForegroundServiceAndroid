package th.co.cdg.foregroundserviceandroid.data

import org.koin.dsl.module
import th.co.cdg.foregroundserviceandroid.data.remote.MobileApi
import th.co.cdg.foregroundserviceandroid.data.remote.streaming.StreamMobileApi


val repositoryModule = module {
    single<Repository> {
        RepositoryImpl(
            get(),
            get()
        )
    }
}

class RepositoryImpl(
    private val remoteSource: MobileApi,
    private val remoteStream: StreamMobileApi
) : Repository {


}