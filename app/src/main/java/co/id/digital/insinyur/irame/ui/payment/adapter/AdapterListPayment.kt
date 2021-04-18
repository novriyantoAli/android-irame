package co.id.digital.insinyur.irame.ui.payment.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import co.id.digital.insinyur.irame.R
import co.id.digital.insinyur.irame.data.models.PaymentResponse
import co.id.digital.insinyur.irame.util.PaymentOptionListener


class AdapterListPayment(private val list: ArrayList<PaymentResponse>, private val optionalListener: PaymentOptionListener): RecyclerView.Adapter<AdapterListPayment.ListViewHolder>(){

    private lateinit var context: Context

    fun addAll(list: List<PaymentResponse>){
        this.list.clear()
        this.list.addAll(list)

        notifyDataSetChanged()
    }

    fun addOne(payment: PaymentResponse){
        this.list.add(payment)

        notifyDataSetChanged()
    }

    fun removeOne(payment: PaymentResponse){
        this.list.remove(payment)

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterListPayment.ListViewHolder {
        this.context = parent.context

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_message, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: AdapterListPayment.ListViewHolder, position: Int) {
        holder.imgIcon.setOnClickListener {view ->
            val popupMenu = PopupMenu(context, view)
            popupMenu.setOnMenuItemClickListener{
                when (it.itemId){
                    R.id.menu_delete -> {
                        optionalListener.onOptionItemClick(R.id.menu_delete, list[position])
                    }
                }

                true
            }
            popupMenu.inflate(R.menu.menu_payment)
            popupMenu.show()
        }

        holder.tvIcon.text = list[position].noInvoice.toString()[0].toString()
        holder.tvTitle.text = list[position].nominal.toString()
        holder.tvLongInformation.text = list[position].noInvoice.toString()
        holder.tvDescription.text = list[position].noInvoice.toString()
        holder.tvDate.text = list[position].createdAt
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
}