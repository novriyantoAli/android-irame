package co.id.digital.insinyur.irame.ui.payment

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import co.id.digital.insinyur.irame.R
import co.id.digital.insinyur.irame.data.models.InvoiceResponse
import co.id.digital.insinyur.irame.data.models.PaymentResponse
import co.id.digital.insinyur.irame.ui.invoice.INFormFragment
import co.id.digital.insinyur.irame.ui.invoice.INListFragment
import co.id.digital.insinyur.irame.util.Constant
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class PaymentActivity : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()

    private val factory: PaymentViewModelFactory by instance()

    private lateinit var viewModel: PaymentViewModel

    private lateinit var listFragment: PAListFragment

    private lateinit var formFragment: PAFormFragment

    private lateinit var pbLoading: ProgressBar

    private var noInvoice: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this, factory).get(PaymentViewModel::class.java)

        noInvoice = intent.getIntExtra("NO_INVOICE", 0)

        supportActionBar?.title = noInvoice.toString()

        setContentView(R.layout.activity_payment)

        listFragment = PAListFragment()
        formFragment = PAFormFragment()

        pbLoading = findViewById(R.id.pb_loading)

        viewModel.mode.observe(this, Observer {
            if (it !=  null){
                if (it == true){
                    displayListFragment()
                } else {
                    displayFormFragment()
                }
            }
        })

        viewModel.message.observe(this, Observer { if (it != null){ showMessage(it) } })

        viewModel.loading.observe(this, Observer { if (it != null){ pbLoading.visibility = it } })

        viewModel.setMode(true)

        viewModel.find(PaymentResponse(noInvoice = noInvoice.toString()))
    }

    private fun showMessage(s: String) {
        Toast.makeText(this, s,Toast.LENGTH_SHORT).show()
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
                viewModel.setMode(false)
                viewModel.setData(noInvoice)
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
                true
            }
        }
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