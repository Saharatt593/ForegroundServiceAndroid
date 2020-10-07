package th.co.cdg.foregroundserviceandroid.data

import android.util.Log
import org.joda.time.DateTime
import org.joda.time.chrono.BuddhistChronology
import org.joda.time.format.DateTimeFormat
import org.koin.dsl.module
import th.co.cdg.foregroundserviceandroid.data.remote.MobileApi
import th.co.cdg.foregroundserviceandroid.data.remote.ServiceManager
import th.co.cdg.foregroundserviceandroid.data.remote.streaming.StreamMobileApi
import java.util.*


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
    override fun testApi(){
        val fmt = DateTimeFormat.forPattern("HH:mm:ss.SSS")
        val dateTime = DateTime()
        val dtBuddhist = dateTime.withChronology(BuddhistChronology.getInstance())

        ServiceManager.service(
            true,
            true,
            true, true,
            remoteSource.add(fmt.print(dtBuddhist),"pix4")
        ){

        }

    }


}