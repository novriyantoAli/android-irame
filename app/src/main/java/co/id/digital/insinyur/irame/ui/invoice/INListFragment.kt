package co.id.digital.insinyur.irame.ui.invoice

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.id.digital.insinyur.irame.R
import co.id.digital.insinyur.irame.data.models.InvoiceResponse
import co.id.digital.insinyur.irame.data.models.UsersResponse
import co.id.digital.insinyur.irame.ui.invoice.adapter.AdapterListInvoice
import co.id.digital.insinyur.irame.ui.payment.PaymentActivity
import co.id.digital.insinyur.irame.ui.users.UsersViewModel
import co.id.digital.insinyur.irame.ui.users.adapter.AdapterListUsers
import co.id.digital.insinyur.irame.util.Constant
import co.id.digital.insinyur.irame.util.InvoiceOptionListener
import java.util.ArrayList

class INListFragment : Fragment(), InvoiceOptionListener {

    private lateinit var viewModel: InvoiceViewModel

    private lateinit var rvContent: RecyclerView

    private lateinit var adapter: AdapterListInvoice

    private var rowsArrayList = ArrayList<InvoiceResponse?>()

    var isLoading = false

    override fun onCreateView(inflater: LayoutInflater, group: ViewGroup?, state: Bundle?): View? {
        viewModel = ViewModelProvider(requireActivity()).get(InvoiceViewModel::class.java)
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_in_list, group, false)

        rvContent = root.findViewById(R.id.rv_content)
        rvContent.layoutManager = LinearLayoutManager(activity)
        rvContent.setHasFixedSize(true)

        adapter = AdapterListInvoice(rowsArrayList, this)
        rvContent.adapter = adapter

        rvContent.addOnScrollListener(object: RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?
                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() < (rowsArrayList.size - 1)) {
                        //bottom of list!
                        showLoading()
                        viewModel.fetch(rowsArrayList[rowsArrayList.size - 1])
                    }
                }
            }
        })

        viewModel.result.observe(requireActivity(), androidx.lifecycle.Observer {
            if (it != null){
                if (rowsArrayList.size > 0) hideLoading()

                if (rowsArrayList.size >= it.totalPage) return@Observer

                rowsArrayList.addAll(it.data)
                adapter.notifyDataSetChanged()

                isLoading = false
            }
        })

        viewModel.operationResult.observe(requireActivity(), Observer {
            if (it != null){
                when(it.mode){
                    Constant.OPERATION_SAVE -> {

                    }
                    Constant.OPERATION_UPDATE -> {

                    }
                    Constant.OPERATION_DELETE -> {
                        rowsArrayList.remove(it.result)
                        adapter.notifyDataSetChanged()
                    }
                }
            }
        })

        viewModel.fetch(null)
        return root
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

    override fun onOptionItemClick(id: Int, invoice: InvoiceResponse) {
        when(id){
            R.id.menu_update -> { viewModel.setMode(Constant.OPERATION_UPDATE, invoice) }
            R.id.menu_delete -> { viewModel.setMode(Constant.OPERATION_DELETE, invoice) }
            R.id.menu_payment -> {
                val i = Intent(requireContext(), PaymentActivity::class.java)
                i.putExtra("NO_INVOICE", invoice.noInvoice);
                startActivity(i)
            }
        }
    }
}