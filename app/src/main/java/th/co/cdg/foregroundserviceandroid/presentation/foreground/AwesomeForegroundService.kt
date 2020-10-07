package th.co.cdg.foregroundserviceandroid.presentation.foreground

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import org.koin.android.ext.android.inject
import kotlin.concurrent.schedule
import th.co.cdg.foregroundserviceandroid.R
import th.co.cdg.foregroundserviceandroid.data.Repository
import java.util.*
import kotlin.concurrent.fixedRateTimer

class AwesomeForegroundService() : Service() {
    companion object {
        val NOTIFICATION_ID = 123
    }
    val repo : Repository by inject()

    val testLoop =
        fixedRateTimer(name="testLoop",daemon = false,period= 10000){
            repo.testApi()
        }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        start()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        testLoop
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        testLoop.cancel()
    }

    fun start() {
        val channelId =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                createNotificationChannel("send_data", "My Foreground Service")
            } else {
                "send_data"
            }

        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle("กำลังทำงานอยู่เบื้องหลัง")
            .setContentText("แอบส่งข้อมูลอยู่นะจ๊ะ")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setTicker("AwesomeForegroundService")
            .build()
        startForeground(NOTIFICATION_ID, notification)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(channelId: String, channelName: String): String{
        val chan = NotificationChannel(channelId,
            channelName, NotificationManager.IMPORTANCE_NONE)
        chan.lightColor = Color.BLUE
        chan.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        val service = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        service.createNotificationChannel(chan)
        return channelId
    }
}