package th.co.cdg.foregroundserviceandroid

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import th.co.cdg.foregroundserviceandroid.data.di.remoteModule
import th.co.cdg.foregroundserviceandroid.data.repositoryModule
import th.co.cdg.foregroundserviceandroid.presentation.di.presentationModules


class Application : Application(), Application.ActivityLifecycleCallbacks {
    companion object {
        var mApplicationContext: Context? = null
        var isOpen: Boolean = false
    }

    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleCallbacks(this)
        stratKoin()
    }

    private fun stratKoin(){
        startKoin {
            androidContext(this@Application)
            androidLogger()
            modules(listOf(repositoryModule, remoteModule, presentationModules) )
        }
    }

    override fun onActivityPaused(activity: Activity) {
        isOpen = false
    }

    override fun onActivityResumed(activity: Activity) {
        isOpen = true
        activity.let { mApplicationContext = it }
    }

    override fun onActivityStarted(activity: Activity) {
    }

    override fun onActivityDestroyed(activity: Activity) {
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
    }

    override fun onActivityStopped(activity: Activity) {
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        isOpen = true
        activity.let { mApplicationContext = it }
    }

}
