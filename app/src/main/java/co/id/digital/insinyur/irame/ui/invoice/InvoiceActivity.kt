package co.id.digital.insinyur.irame.ui.invoice

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
import co.id.digital.insinyur.irame.data.models.InvoiceResponse
import co.id.digital.insinyur.irame.data.models.UsersResponse
import co.id.digital.insinyur.irame.ui.users.UFormFragment
import co.id.digital.insinyur.irame.ui.users.UListFragment
import co.id.digital.insinyur.irame.util.Constant
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class InvoiceActivity : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()

    private val factory: InvoiceViewModelFactory by instance()

    private lateinit var viewModel: InvoiceViewModel

    private lateinit var listFragment: INListFragment

    private lateinit var formFragment: INFormFragment

    private lateinit var pbLoading: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {

        viewModel = ViewModelProvider(this, factory).get(InvoiceViewModel::class.java)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invoice)

        listFragment = INListFragment()
        formFragment = INFormFragment()

        pbLoading = findViewById(R.id.pb_loading)

        viewModel.mode.observe(this, Observer {
            if (it != null && it.mode == Constant.OPERATION_MODE){
                if (it.result == null) { displayListFragment() }
                else { displayFormFragment() }
            }
        })

        viewModel.loading.observe(this, Observer { if (it != null) { pbLoading.visibility = it } })

        viewModel.message.observe(this, Observer { if (it != null) { showMessage(it) } })

        viewModel.setMode(Constant.OPERATION_MODE, null)
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
                viewModel.setMode(Constant.OPERATION_MODE, InvoiceResponse())
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