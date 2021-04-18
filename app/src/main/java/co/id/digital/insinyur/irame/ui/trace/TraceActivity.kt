package co.id.digital.insinyur.irame.ui.trace

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.id.digital.insinyur.irame.R
import co.id.digital.insinyur.irame.data.models.RadpostauthResponse
import co.id.digital.insinyur.irame.data.models.UsersResponse
import co.id.digital.insinyur.irame.ui.trace.adapter.AdapterListTrace
import co.id.digital.insinyur.irame.ui.users.adapter.AdapterListUsers
import co.id.digital.insinyur.irame.util.TraceOptionListener
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import java.util.ArrayList

class TraceActivity : AppCompatActivity(), KodeinAware, TraceOptionListener{

    override val kodein by kodein()

    private val factory: TraceViewModelFactory by instance()

    private lateinit var viewModel: TraceViewModel

    private lateinit var etSearch: EditText

    private lateinit var btnSearch: Button

    private lateinit var rvContent: RecyclerView

    private lateinit var pbLoading: ProgressBar

    private lateinit var adapter: AdapterListTrace

    private var rowsArrayList = ArrayList<RadpostauthResponse?>()

    var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this, factory).get(TraceViewModel::class.java)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trace)

        etSearch = findViewById(R.id.et_search)
        btnSearch = findViewById(R.id.btn_search)

        rvContent = findViewById(R.id.rv_content)
        pbLoading = findViewById(R.id.pb_loading)

        rvContent.layoutManager = LinearLayoutManager(this)
        rvContent.setHasFixedSize(true)

        adapter = AdapterListTrace(rowsArrayList, this)
        rvContent.adapter = adapter

        rvContent.addOnScrollListener(object: RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?
                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() < (rowsArrayList.size - 1)) {
                        //bottom of list!
                        showLoading()
                        rowsArrayList[rowsArrayList.size - 1]?.let { viewModel.get(it) }
                    }
                }
            }
        })

        viewModel.result.observe(this, androidx.lifecycle.Observer {
            if (it != null){
                if (rowsArrayList.size > 0) hideLoading()

                if (rowsArrayList.size >= it.page) return@Observer

                rowsArrayList.addAll(it.data)
                adapter.notifyDataSetChanged()

                isLoading = false
            }
        })

        viewModel.message.observe(this, androidx.lifecycle.Observer {
            if (it != null){
                showMessage(it)
            }
        })

        viewModel.loading.observe(this, Observer {
            if (it != null){
                pbLoading.visibility = it
            }
        })

        btnSearch.setOnClickListener {
            val keyword = etSearch.text.toString().trim()
            if (keyword.isEmpty()){
                etSearch.error = "field cannot be empty"
                return@setOnClickListener
            }
            etSearch.error = null

            val param = RadpostauthResponse(id = 0, username = keyword)
            viewModel.get(param)
        }
    }

    override fun onOptionItemClick(id: Int, rpa: RadpostauthResponse) {

    }

    private fun showMessage(message: String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(){
        rowsArrayList.add(null)
        adapter.notifyItemInserted(rowsArrayList.size - 1)
        isLoading = true
    }

    private fun hideLoading(){
        rowsArrayList.removeAt(rowsArrayList.size - 1)
        val scrollPosition = rowsArrayList.size
        adapter.notifyItemRemoved(scrollPosition)
    }

}