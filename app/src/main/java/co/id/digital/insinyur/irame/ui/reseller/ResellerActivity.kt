package co.id.digital.insinyur.irame.ui.reseller

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.id.digital.insinyur.irame.R
import co.id.digital.insinyur.irame.data.models.ResellerResponse
import co.id.digital.insinyur.irame.ui.reseller.adapter.AdapterListReseller
import co.id.digital.insinyur.irame.util.Constant
import co.id.digital.insinyur.irame.util.ResellerOptionListener
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance


class ResellerActivity : AppCompatActivity(), KodeinAware, ResellerOptionListener{

    override val kodein by kodein()

    private val factory: ResellerViewModelFactory by instance()

    private lateinit var viewModel: ResellerViewModel

    private lateinit var rvContent: RecyclerView

    private lateinit var pbLoading: ProgressBar

    private lateinit var adapter: AdapterListReseller

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reseller)

        viewModel = ViewModelProvider(this, factory).get(ResellerViewModel::class.java)
        rvContent = findViewById(R.id.rv_content)
        rvContent.layoutManager = LinearLayoutManager(this)
        rvContent.setHasFixedSize(true)

        val list = ArrayList<ResellerResponse>()
        adapter = AdapterListReseller(list, this)

        rvContent.adapter = adapter

        pbLoading = findViewById(R.id.pb_loading)
        // for show message
        viewModel.message.observe(this, Observer {
            if (it != null){
                showMessage(it)
            }
        })

        // for show loading
        viewModel.loading.observe(this, Observer {
            if (it != null){
                pbLoading.visibility = if (it == true){ View.VISIBLE } else { View.GONE }
            }
        })

        // for result reseller
        viewModel.resultReseller.observe(this, Observer {
            if (it != null){
                adapter.addAll(it)
            }
        })

        // for result update
        viewModel.result.observe(this, Observer {
            if (it != null){ for ((k, v) in it) { adapter.replace(k, v) } }
        })

        // for result delete
        viewModel.resultDelete.observe(this, Observer {
            if (it != null){
                adapter.removeOne(it)
            }
        })

        // for result balance
        viewModel.resultBalance.observe(this, Observer {
            if (it != null){
                showBalance(it)
            }
        })

        viewModel.fetch()
    }

    private fun showMessage(s: String) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
    }

    private fun showBalance(balance: Int){
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.title_balance)
        builder.setMessage("balance reseller: $balance")
        builder.setCancelable(true)
        // Create the AlertDialog object and return it
        builder.create()
        builder.show()
    }

    private fun showForm(id: Int){
        val builder = AlertDialog.Builder(this@ResellerActivity)

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        val input = EditText(this@ResellerActivity)
        val lp = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT
        )
        input.layoutParams = lp
        builder.setTitle(R.string.title_balance_refill)
        builder.setView(input)
            // Add action buttons
            .setPositiveButton(R.string.title_refill) { _, _ ->
                val digits = input.text.toString().toIntOrNull()
                if (digits != null){
                    viewModel.balanceRefill(id, digits)
                }
            }
            .setNegativeButton(R.string.title_cancel) { dialog, _ -> dialog.cancel() }
        builder.create()

        builder.show()
    }

    override fun onOptionItemClick(id: Int, resellerMenu: Int, index: Int) {
        when(resellerMenu){
            Constant.RESELLER_ACTIVATED -> {
                viewModel.activated(id, index)
            }
            Constant.RESELLER_DELETE -> {
                viewModel.delete(id)
            }
            Constant.RESELLER_BALANCE -> {
                viewModel.balance(id)
            }
            Constant.RESELLER_BALANCE_REFILL -> {
                // show form
                showForm(id)
            }
        }
    }
}