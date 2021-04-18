package co.id.digital.insinyur.irame.ui.reseller.adapter

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
import co.id.digital.insinyur.irame.data.models.ResellerResponse
import co.id.digital.insinyur.irame.util.Constant
import co.id.digital.insinyur.irame.util.ResellerOptionListener
import org.ocpsoft.prettytime.PrettyTime
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class AdapterListReseller(private val list: ArrayList<ResellerResponse>, private val optionalListener: ResellerOptionListener): RecyclerView.Adapter<AdapterListReseller.ListViewHolder>(){

    private lateinit var context: Context

    fun addAll(list: List<ResellerResponse>){
        this.list.clear()
        this.list.addAll(list)

        notifyDataSetChanged()
    }

    fun replace(index: Int, reseller: ResellerResponse){
        this.list.removeAt(index)
        this.list.add(reseller)

        notifyDataSetChanged()
    }

    fun removeOne(menu: ResellerResponse){
        this.list.remove(menu)

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterListReseller.ListViewHolder {
        this.context = parent.context

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_message, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: AdapterListReseller.ListViewHolder, position: Int) {
        holder.imgIcon.setOnClickListener {view ->
            val popupMenu = PopupMenu(context, view)
            popupMenu.setOnMenuItemClickListener{
                when (it.itemId){
                    R.id.menu_delete -> {
                        optionalListener.onOptionItemClick(
                            list[position].id, Constant.RESELLER_DELETE, position
                        )
                    }
                    R.id.menu_activated -> {
                        optionalListener.onOptionItemClick(
                            list[position].id, Constant.RESELLER_ACTIVATED, position
                        )
                    }
                    R.id.menu_balance -> {
                        optionalListener.onOptionItemClick(
                            list[position].id, Constant.RESELLER_BALANCE, position
                        )
                    }
                    R.id.menu_refill -> {
                        optionalListener.onOptionItemClick(
                            list[position].id, Constant.RESELLER_BALANCE_REFILL, position
                        )
                    }
                }

                true
            }
            popupMenu.inflate(R.menu.menu_reseller_optional)
            popupMenu.show()
        }

        if (list[position].dateTransaction != null){
            val df1: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
            val result1: Date = df1.parse(list[position].dateTransaction)

            val prettyTime = PrettyTime()
            val ago: String = prettyTime.format(result1)
            holder.tvDescription.text = "transaksi terakhir: [" + list[position].statusTransaction + "] " + ago
        } else {
            holder.tvDescription.text = "transaksi terakhir: [" + list[position].statusTransaction + "] " + list[position].dateTransaction
        }

        val activated = if (list[position].activated == "yes"){ "[ activated ]" } else { "[ not activated ]" }
        holder.tvIcon.text = list[position].name[0].toString()
        holder.tvTitle.text = list[position].name
        holder.tvLongInformation.text = activated + " " +list[position].registerCode

        val df1: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
        val result1: Date = df1.parse(list[position].createdAt)

        val df2: DateFormat = SimpleDateFormat("HH:mm:ss dd MMM yyyy")
        val result2 = df2.format(result1)

        holder.tvDate.text = result2
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