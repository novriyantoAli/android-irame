package co.id.digital.insinyur.irame.ui.invoice.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import co.id.digital.insinyur.irame.R
import co.id.digital.insinyur.irame.data.models.InvoiceResponse
import co.id.digital.insinyur.irame.util.InvoiceOptionListener
import java.text.DateFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*


class AdapterListInvoice(private val list: List<InvoiceResponse?>, private val optionalListener: InvoiceOptionListener): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private lateinit var context: Context

    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_LOADING = 1

    override fun getItemViewType(position: Int): Int {
        return if (list[position] == null) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        this.context = parent.context

        return if (viewType == VIEW_TYPE_ITEM) {
            val view: View = LayoutInflater.from(parent.context).inflate(
                R.layout.item_message, parent, false
            )
            ListViewHolder(view)
        } else {
            val view: View = LayoutInflater.from(parent.context).inflate(
                R.layout.item_loading, parent, false
            )
            LoadingViewHolder(view)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ListViewHolder) { populateItemRows(holder, position) }
        else if (holder is LoadingViewHolder) { showLoadingView(holder, position) }
    }

    private fun showLoadingView(viewHolder: LoadingViewHolder, position: Int) {
        //ProgressBar would be displayed
    }

    private fun populateItemRows(viewHolder: ListViewHolder, position: Int) {
        viewHolder.layoutMain.setOnClickListener { view ->
            val popupMenu = PopupMenu(context, view)
            popupMenu.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.menu_delete -> {
                        optionalListener.onOptionItemClick(R.id.menu_delete, list[position]!!)
                    }
                    R.id.menu_update -> {
                        optionalListener.onOptionItemClick(R.id.menu_update, list[position]!!)
                    }
                    R.id.menu_payment -> {
                        optionalListener.onOptionItemClick(R.id.menu_payment, list[position]!!)
                    }
                }
                true
            }
            popupMenu.inflate(R.menu.menu_invoice)
            popupMenu.show()
        }

        val description = "Invoice No: [ "+ list[position]?.noInvoice?.toString() + " ], Type: [ " + list[position]?.type + " ]"

        viewHolder.tvIcon.text = list[position]?.name?.get(0).toString()
        viewHolder.tvTitle.text = list[position]?.name
        viewHolder.tvLongInformation.text = formatRupiah(list[position]?.nominal?.toDouble())
        viewHolder.tvDescription.text = description

        if (list[position]?.createdAt != null){
            val df1: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
            val result1: Date = df1.parse(list[position]!!.createdAt)

            val df2: DateFormat = SimpleDateFormat("HH:mm:ss dd MMM yyyy")
            val result2 = df2.format(result1)

            viewHolder.tvDate.text = result2
        } else {
            viewHolder.tvDate.text = list[position]?.createdAt
        }
    }

    inner class ListViewHolder(val view: View): RecyclerView.ViewHolder(view) {
        val layoutMain: RelativeLayout = view.findViewById(R.id.layout_main)
        val tvIcon: TextView = view.findViewById(R.id.tv_icon)
        val tvTitle: TextView = view.findViewById(R.id.tv_title)
        val tvLongInformation: TextView = view.findViewById(R.id.tv_long_information)
        val tvDescription: TextView = view.findViewById(R.id.tv_description)
        val tvDate: TextView = view.findViewById(R.id.tv_date)
        val imgIcon: ImageView = view.findViewById(R.id.img_icon)
    }

    private inner class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var progressBar: ProgressBar = itemView.findViewById(R.id.pb_loading)
    }

    private fun formatRupiah(number: Double?): String? {
        if (number == null)
            return null

        val localeID = Locale("in", "ID")
        val formatRupiah: NumberFormat = NumberFormat.getCurrencyInstance(localeID)
        return formatRupiah.format(number)
    }
}