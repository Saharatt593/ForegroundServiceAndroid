package th.co.cdg.foregroundserviceandroid.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import th.co.cdg.foregroundserviceandroid.R
import th.co.cdg.foregroundserviceandroid.presentation.foreground.AwesomeForegroundService
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_click.setOnClickListener {
            val intent = Intent(this@MainActivity, AwesomeForegroundService::class.java)
            startService(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopService(Intent(this@MainActivity, AwesomeForegroundService::class.java))
    }
}