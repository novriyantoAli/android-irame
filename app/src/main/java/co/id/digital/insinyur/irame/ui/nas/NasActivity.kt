package co.id.digital.insinyur.irame.ui.nas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import co.id.digital.insinyur.irame.R
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class NasActivity : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()

    private val factory: NasViewModelFactory by instance()

    private lateinit var viewModel: NasViewModel

    private lateinit var etHost: EditText

    private lateinit var etSecret: EditText

    private lateinit var btnSave: Button

    private var recentHost: String? = ""

    private var recentSecret: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {

        viewModel = ViewModelProvider(this, factory).get(NasViewModel::class.java)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nas)

        etHost = findViewById(R.id.et_host)
        etSecret = findViewById(R.id.et_secret)

        viewModel.result.observe(this, Observer {
            if (it != null){
                recentHost = it.nasname
                recentSecret = it.secret

                etHost.setText(it.nasname)
                etSecret.setText(it.secret)
            }
        })

        btnSave = findViewById(R.id.btn_save)
        btnSave.setOnClickListener {
            if (etHost.text.toString().isEmpty()){
                etHost.error = "field cannot be empty"
                return@setOnClickListener
            }
            etHost.error = null

            if (etSecret.text.toString().isEmpty()){
                etSecret.error = "field cannot be empty"
                return@setOnClickListener
            }
            etSecret.error = null

            if (etHost.text.toString().trim() == recentHost && etSecret.text.toString().trim() == recentSecret){
                return@setOnClickListener
            }

            viewModel.save(etHost.text.toString().trim(), etSecret.text.toString().trim())
        }

        viewModel.load()
    }
}