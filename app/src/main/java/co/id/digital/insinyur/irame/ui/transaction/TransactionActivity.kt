package co.id.digital.insinyur.irame.ui.transaction

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import android.widget.ExpandableListView.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import co.id.digital.insinyur.irame.R
import co.id.digital.insinyur.irame.data.models.TransactionResponse
import co.id.digital.insinyur.irame.ui.transaction.adapter.ExpendableAdapter
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class TransactionActivity : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()

    private val factory: TransactionViewModelFactory by instance()

    private lateinit var viewModel: TransactionViewModel

    private lateinit var edtStart: EditText

    private lateinit var edtEnd: EditText

    private lateinit var btnSearch: Button

    private lateinit var tvValueIn: TextView

    private lateinit var tvValueOut: TextView

    private lateinit var elvContent: ExpandableListView

    private lateinit var pbLoading: ProgressBar

    private lateinit var myCalendar: Calendar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction)

        viewModel = ViewModelProvider(this, factory).get(TransactionViewModel::class.java)

        edtStart = findViewById(R.id.et_start)
        edtEnd = findViewById(R.id.et_end)

        tvValueIn = findViewById(R.id.tv_value_in)
        tvValueOut = findViewById(R.id.tv_value_out)

        btnSearch = findViewById(R.id.btn_search)

        elvContent = findViewById(R.id.elv_content)

        pbLoading = findViewById(R.id.pb_loading)

        viewModel.loading.observe(this, Observer {
            if (it != null){
                pbLoading.visibility = if (it) View.VISIBLE else View.GONE
            }
        })

        viewModel.balance.observe(this, Observer {
            if (it != null){
                tvValueIn.text = it.balanceIN.toString()
                tvValueOut.text = it.balanceOUT.toString()
            }
        })

        viewModel.expendableData.observe(this, Observer {
            if (it != null){
                val list = ArrayList<String>(it.title)
                val adapter = ExpendableAdapter(this, list, it.data)
                elvContent.setAdapter(adapter)
            }
        })

        myCalendar = Calendar.getInstance()

        val dateStart = OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, monthOfYear)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateDateStart()
        }

        val dateEnd = OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, monthOfYear)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateDateEnd()
        }

        edtStart.setOnClickListener {
            DatePickerDialog(
                this@TransactionActivity,
                dateStart,
                myCalendar[Calendar.YEAR],
                myCalendar[Calendar.MONTH],
                myCalendar[Calendar.DAY_OF_MONTH]
            ).show()
        }

        edtEnd.setOnClickListener {
            DatePickerDialog(
                this@TransactionActivity,
                dateEnd,
                myCalendar[Calendar.YEAR],
                myCalendar[Calendar.MONTH],
                myCalendar[Calendar.DAY_OF_MONTH]
            ).show()
        }

        elvContent.setOnGroupExpandListener(OnGroupExpandListener { groupPosition ->

        })

        elvContent.setOnGroupCollapseListener(OnGroupCollapseListener { groupPosition ->
//            Toast.makeText(
//                applicationContext,
//                expandableListTitle.get(groupPosition).toString() + " List Collapsed.",
//                Toast.LENGTH_SHORT
//            ).show()
        })

        elvContent.setOnChildClickListener(OnChildClickListener { parent, v, groupPosition, childPosition, id ->
//            Toast.makeText(
//                applicationContext,
//                expandableListTitle.get(groupPosition)
//                    .toString() + " -> "
//                        + expandableListDetail.get(
//                    expandableListTitle.get(groupPosition)
//                ).get(
//                    childPosition
//                ), Toast.LENGTH_SHORT
//            ).show()
            false
        })

        btnSearch.setOnClickListener {
            edtStart.error = null
            edtEnd.error = null
            if (edtStart.text.toString().isEmpty()){
                edtStart.error = "not valid..."
                return@setOnClickListener
            }
            if (edtEnd.text.toString().isEmpty()){
                edtEnd.error = "not valid..."
                return@setOnClickListener
            }
            viewModel.fetchReport(
                dateStart = edtStart.text.toString(), dateEnd = edtEnd.text.toString()
            )
        }
    }

    private fun updateDateEnd() {
        val myFormat = "yyyy-MM-dd" //In which you need put here
        val sdf = SimpleDateFormat(myFormat)
        edtEnd.setText(sdf.format(myCalendar.time))
    }

    private fun updateDateStart() {
        val myFormat = "yyyy-MM-dd" //In which you need put here
        val sdf = SimpleDateFormat(myFormat)
        edtStart.setText(sdf.format(myCalendar.time))
    }
}