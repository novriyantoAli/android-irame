package co.id.digital.insinyur.irame.ui.packages

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import co.id.digital.insinyur.irame.R
import co.id.digital.insinyur.irame.data.models.MenuResponse
import co.id.digital.insinyur.irame.data.models.PackageResponse
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class PackagesActivity : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()

    private val factory: PackagesViewModelFactory by instance()

    private lateinit var viewModel: PackagesViewModel

    private lateinit var pbLoading: ProgressBar

    private lateinit var listFragment: PListFragment

    private lateinit var formFragment: PFormFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_packages)

        viewModel = ViewModelProvider(this, factory).get(PackagesViewModel::class.java)
        pbLoading = findViewById(R.id.pb_loading)

        listFragment = PListFragment()
        formFragment = PFormFragment()

        viewModel.message.observe(this, Observer {
            if (it != null){
                showMessage(it)
            }
        })

        viewModel.loading.observe(this, Observer {
            if (it != null){
                showLoading(it)
            }
        })

        viewModel.selected.observe(this, Observer {
            if (it != null){
                displayFormFragment()
            } else {
                displayListFragment()
            }
        })

        displayListFragment()
    }

    private fun showLoading(b: Boolean?) {
        pbLoading.visibility = if (b == true){ View.VISIBLE } else { View.GONE }
    }

    private fun showMessage(s: String?) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
    }

    private fun displayListFragment() {
        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
        if (listFragment.isAdded) { // if the fragment is already in container
            ft.show(listFragment)
        } else { // fragment needs to be added to frame container
            ft.add(R.id.fl_context, listFragment, "listFragment")
        }
        // Hide fragment B
        if (formFragment.isAdded) {
            ft.hide(formFragment)
        }
        // Commit changes
        ft.commit()
    }

    private fun displayFormFragment() {
        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
        if (formFragment.isAdded) { // if the fragment is already in container
            ft.show(formFragment)
        } else { // fragment needs to be added to frame container
            ft.add(R.id.fl_context, formFragment, "formFragment")
        }
        // Hide fragment B
        if (listFragment.isAdded) {
            ft.hide(listFragment)
        }
        // Commit changes
        ft.commit()
    }
}