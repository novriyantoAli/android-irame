package co.id.digital.insinyur.irame.ui.payment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.id.digital.insinyur.irame.R
import co.id.digital.insinyur.irame.data.models.PaymentResponse
import co.id.digital.insinyur.irame.ui.payment.adapter.AdapterListPayment
import co.id.digital.insinyur.irame.util.Constant
import co.id.digital.insinyur.irame.util.PaymentOptionListener
import com.shrikanthravi.collapsiblecalendarview.data.Day
import com.shrikanthravi.collapsiblecalendarview.widget.CollapsibleCalendar
import com.shrikanthravi.collapsiblecalendarview.widget.CollapsibleCalendar.CalendarListener


class PAListFragment : Fragment(), PaymentOptionListener, CalendarListener{

    private lateinit var viewModel: PaymentViewModel

    private lateinit var rvContent: RecyclerView

    private lateinit var adapter: AdapterListPayment

    override fun onCreateView(inflater: LayoutInflater, group: ViewGroup?, state: Bundle?): View? {
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(requireActivity()).get(PaymentViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_pa_list, group, false)

        rvContent = root.findViewById(R.id.rv_content)
        rvContent.layoutManager = LinearLayoutManager(requireContext())
        rvContent.setHasFixedSize(true)

        val list = ArrayList<PaymentResponse>()
        adapter = AdapterListPayment(list, this)

        rvContent.adapter = adapter

        viewModel.result.observe(requireActivity(), Observer {
            if (it != null){ adapter.addAll(it) }
        })
        viewModel.operation.observe(requireActivity(), Observer {
            if (it != null){
                when(it.mode){
                    Constant.OPERATION_SAVE -> { adapter.addOne(it.data) }
                    Constant.OPERATION_DELETE -> { adapter.removeOne(it.data) }
                }
            }
        })

        val collapsibleCalendar: CollapsibleCalendar = root.findViewById(R.id.date_picker_timeline)
        collapsibleCalendar.setCalendarListener(this)

        return root
    }

    override fun onOptionItemClick(id: Int, payment: PaymentResponse) {
        if (id == R.id.menu_delete){ viewModel.delete(payment) }
    }

    override fun onClickListener() { }

    override fun onDataUpdate() { }

    override fun onDayChanged() { }

    override fun onDaySelect() {
        //                val day: Day = viewCalendar.getSelectedDay()
//                Log.i(
//                    javaClass.name, ("Selected Day: "
//                            + day.getYear()) + "/" + (day.getMonth() + 1).toString() + "/" + day.getDay()
//                )
    }

    override fun onItemClick(v: View) { }

    override fun onMonthChange() { }

    override fun onWeekChange(position: Int) { }

}