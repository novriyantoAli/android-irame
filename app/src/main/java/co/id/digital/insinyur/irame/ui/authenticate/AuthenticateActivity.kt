package co.id.digital.insinyur.irame.ui.authenticate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import co.id.digital.insinyur.irame.R
import co.id.digital.insinyur.irame.ui.configuration.ConfigurationActivity
import co.id.digital.insinyur.irame.ui.main.MainActivity
import co.id.digital.insinyur.irame.util.Coroutines
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class AuthenticateActivity : AppCompatActivity(), KodeinAware, View.OnClickListener{

    override val kodein by kodein()

    private val factory: AuthenticateViewModelFactory by instance()

    private lateinit var viewModel: AuthenticateViewModel

    private lateinit var etUsername: EditText

    private lateinit var etPassword: EditText

    private lateinit var btnLogin: Button

    private lateinit var btnImgConfig: ImageButton

    private lateinit var pbLoading: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authenticate)

        supportActionBar?.hide()

        viewModel = ViewModelProvider(this, factory).get(AuthenticateViewModel::class.java)
        viewModel.message.observe(this, Observer {
            if(it != null) showMessage(it) else moveActivity()
        })

        etUsername = findViewById(R.id.et_username)
        etPassword = findViewById(R.id.et_password)

        btnLogin = findViewById(R.id.btn_login)
        btnLogin.setOnClickListener(this)

        btnImgConfig = findViewById(R.id.ib_connection)
        btnImgConfig.setOnClickListener(this)

        pbLoading = findViewById(R.id.pb_loading)

        checkUser()
    }

    private fun checkUser() = Coroutines.main {
        viewModel.user.await().observe(this, Observer {
            if (it != null) moveActivity()
        })
    }

    private fun moveActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)

        finish()
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onClick(p0: View?) {
        if (p0?.id == R.id.btn_login){
            etUsername.error = null
            etPassword.error = null

            if (etUsername.text.toString().isEmpty()){
                etUsername.error = "username not valid"
                return
            }
            if (etPassword.text.toString().isEmpty()){
                etPassword.error = "password not valid"
                return
            }

            viewModel.login(etUsername.text.toString(), etPassword.text.toString())
        } else if (p0?.id == R.id.ib_connection){
            val intent = Intent(this, ConfigurationActivity::class.java)
            startActivity(intent)
        }
    }
}