package co.id.digital.insinyur.irame.ui.configuration

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import co.id.digital.insinyur.irame.R
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import java.io.File

class ConfigurationActivity : AppCompatActivity(), KodeinAware{

    override val kodein by kodein()

    private val factory: ConfigurationViewModelFactory by instance()

    private lateinit var viewModel: ConfigurationViewModel

    private lateinit var etHost: EditText

    private lateinit var etPort: EditText

    private lateinit var btnSave: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this, factory).get(ConfigurationViewModel::class.java)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_configuration)

        etHost = findViewById(R.id.et_host)
        etPort = findViewById(R.id.et_port)

        viewModel.app.observe(this, Observer {
            if (it != null){
                etHost.setText(it.link)
                etPort.setText(it.port)
            }
        })

        viewModel.message.observe(this, Observer {
            if (it != null){ finishAffinity() }
        })

        btnSave = findViewById(R.id.btn_save)

        btnSave.setOnClickListener {
            if (etHost.text.isEmpty()){
                etHost.error = "field cannot be empty"
                return@setOnClickListener
            }
            etHost.error = null

            if (etPort.text.isEmpty()){
                etPort.error = "field cannot be empty"
                return@setOnClickListener
            }
            etPort.error = null

            viewModel.save(etHost.text.toString(), etPort.text.toString())
        }

        viewModel.load()
    }

}