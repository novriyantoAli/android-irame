package co.id.digital.insinyur.irame.ui.users

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import co.id.digital.insinyur.irame.R
import co.id.digital.insinyur.irame.data.models.RadusergroupResponse
import co.id.digital.insinyur.irame.data.models.UsersResponse
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class UsersActivity : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()

    private val factory: UsersViewModelFactory by instance()

    private lateinit var viewModel: UsersViewModel

    private lateinit var listFragment: UListFragment

    private lateinit var formFragment: UFormFragment

    private lateinit var pbLoading: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users)

        viewModel = ViewModelProvider(this, factory).get(UsersViewModel::class.java)

        listFragment = UListFragment()
        formFragment = UFormFragment()

        pbLoading = findViewById(R.id.pb_loading)

        viewModel.message.observe(this, Observer {
            showMessage(it)
        })

        viewModel.loading.observe(this, Observer {
            pbLoading.visibility = it
        })

        viewModel.selected.observe(this, Observer{
            if (it == null) displayListFragment()
            else displayFormFragment()
        })

        viewModel.setSelected(null)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.item_add -> {
                viewModel.setSelected(UsersResponse(id = 0, username = "", packageName = ""))
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
                true
            }
        }
    }

    private fun showMessage(message: String){
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