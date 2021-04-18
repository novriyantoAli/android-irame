package co.id.digital.insinyur.irame.ui.profile

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import co.id.digital.insinyur.irame.R
import co.id.digital.insinyur.irame.data.models.RadusergroupResponse
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class ProfileActivity : AppCompatActivity(), KodeinAware{

    override val kodein by kodein()

    private val factory: ProfileViewModelFactory by instance()

    private lateinit var viewModel: ProfileViewModel

    private lateinit var listFragment: PRListFragment

    private lateinit var  formFragment: PRFormFragment

    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        viewModel = ViewModelProvider(this, factory).get(ProfileViewModel::class.java)

        viewModel.message.observe(this, Observer {
            if (it != null){
                showMessage(it)
            }
        })
        listFragment = PRListFragment()
        formFragment = PRFormFragment()

        progressBar = findViewById(R.id.pb_loading)

        viewModel.loading.observe(this, Observer {
            if (it != null){ progressBar.visibility = it }
        })

        viewModel.selected.observe(this, Observer {
            if (it == null) displayListFragment() else displayFormFragment()
        })

        displayListFragment()
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
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