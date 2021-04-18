package co.id.digital.insinyur.irame.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import co.id.digital.insinyur.irame.R
import co.id.digital.insinyur.irame.ui.authenticate.AuthenticateActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
//                window.decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        //region REGION alternative desing xml

        runBlocking {
            delay(1000L)
            val intent = Intent(this@SplashActivity, AuthenticateActivity::class.java)
            startActivity(intent)
            finish()
        }
        //endregion
    }
}