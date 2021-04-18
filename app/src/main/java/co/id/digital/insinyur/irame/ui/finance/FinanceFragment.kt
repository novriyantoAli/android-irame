package co.id.digital.insinyur.irame.ui.finance

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import co.id.digital.insinyur.irame.R
import co.id.digital.insinyur.irame.data.models.FinanceReportResponse
import co.id.digital.insinyur.irame.databinding.FragmentFinanceBinding
import co.id.digital.insinyur.irame.ui.finance.adapter.FinanceAdapter
import co.id.digital.insinyur.irame.ui.invoice.InvoiceActivity
import co.id.digital.insinyur.irame.ui.main.MainViewModel
import co.id.digital.insinyur.irame.ui.packages.PackagesActivity
import co.id.digital.insinyur.irame.ui.reseller.ResellerActivity
import co.id.digital.insinyur.irame.ui.transaction.TransactionActivity
import co.id.digital.insinyur.irame.util.HorizontalMarginItemDecoration
import java.lang.Math.abs


class FinanceFragment : Fragment(), View.OnClickListener{

    private lateinit var binding: FragmentFinanceBinding

    private lateinit var adapter: FinanceAdapter

    private lateinit var viewModel: MainViewModel

    private lateinit var llReseller: LinearLayout

    private lateinit var llTransaction: LinearLayout

    private lateinit var llPackages: LinearLayout

    private lateinit var llInvoice: LinearLayout

    override fun onCreateView(inflater: LayoutInflater, group: ViewGroup?, state: Bundle?): View? {
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        binding = FragmentFinanceBinding.inflate(layoutInflater)
        val view: View = binding.root

        llReseller = view.findViewById(R.id.ll_reseller)
        llReseller.setOnClickListener(this)

        llTransaction = view.findViewById(R.id.ll_transaction)
        llTransaction.setOnClickListener(this)

        llPackages = view.findViewById(R.id.ll_package)
        llPackages.setOnClickListener(this)

        llInvoice = view.findViewById(R.id.ll_invoice)
        llInvoice.setOnClickListener(this)

        val list = ArrayList<FinanceReportResponse>()
        setupViewpager(list)

        viewModel.month.observe(requireActivity(), Observer {
            if (it != null){ adapter.addList(it) }
        })

        viewModel.currentMonthReport()

        return view
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.ll_reseller -> {
                val reseller = ResellerActivity::class.java
                viewModel.moveActivity(reseller)
            }

            R.id.ll_transaction -> {
                val transaction = TransactionActivity::class.java
                viewModel.moveActivity(transaction)
            }
            R.id.ll_package -> {
                val packages = PackagesActivity::class.java
                viewModel.moveActivity(packages)
            }
            R.id.ll_invoice -> {
                val invoice = InvoiceActivity::class.java
                viewModel.moveActivity(invoice)
            }
        }
    }

    private fun setupViewpager(list: ArrayList<FinanceReportResponse>) {
        adapter = FinanceAdapter(list, requireContext())
        binding.viewPager.adapter = adapter
        // set selected item
        binding.viewPager.currentItem = 1
        // You need to retain one page on each side so that the next and previous items are visible
        binding.viewPager.offscreenPageLimit = 1
        // Add a PageTransformer that translates the next and previous items horizontally
        // towards the center of the screen, which makes them visible
        val nextItemVisiblePx =
            resources.getDimension(R.dimen.viewpager_next_item_visible).toInt()
        val currentItemHorizontalMarginPx =
            resources.getDimension(R.dimen.viewpager_current_item_horizontal_margin).toInt()
        val pageTranslationX = nextItemVisiblePx + currentItemHorizontalMarginPx
        val pageTransformer =
            ViewPager2.PageTransformer { page: View, position: Float ->
                page.translationX = -pageTranslationX * position
                // Next line scales the item's height. You can remove it if you don't want this effect
                page.scaleY = 1 - 0.15f * kotlin.math.abs(position)
            }
        binding.viewPager.setPageTransformer(pageTransformer)
        binding.viewPager.addItemDecoration(
            HorizontalMarginItemDecoration(
                requireActivity(), R.dimen.viewpager_current_item_horizontal_margin_testing,
                R.dimen.viewpager_next_item_visible_testing
            )
        )
        binding.viewPager.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                //                countTxtView.setText(String.format(Locale.ENGLISH,"%d/%d", position+1, matchCourseList.size()));
//                MyUtilsApp.showLog(
//                    TAG,
//                    String.format(
//                        Locale.ENGLISH,
//                        "%d/%d",
//                        position + 1,
//                        matchCourseList.size
//                    )
//                )
            }
        })
    }
}