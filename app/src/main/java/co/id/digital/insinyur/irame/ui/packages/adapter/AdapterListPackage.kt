package co.id.digital.insinyur.irame.ui.packages.adapter

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
import co.id.digital.insinyur.irame.data.models.PackageResponse
import co.id.digital.insinyur.irame.util.PackageOptionListener
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class AdapterListPackage(private val list: ArrayList<PackageResponse>, private val itemListener: PackageOptionListener): RecyclerView.Adapter<AdapterListPackage.ListViewHolder>() {
    private lateinit var context: Context

    fun addAll(list: List<PackageResponse>){
        this.list.clear()
        this.list.addAll(list)

        notifyDataSetChanged()
    }

    fun addOne(menu: PackageResponse){
        this.list.add(menu)

        notifyDataSetChanged()
    }

    fun replace(menu: PackageResponse){
        this.list.remove(menu)
        this.list.add(menu)

        notifyDataSetChanged()
    }

    fun removeOne(menu: PackageResponse){
        this.list.remove(menu)

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        this.context = parent.context

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_message, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {

        holder.layoutMain.setOnClickListener {view ->
            val popupMenu = PopupMenu(context, view)
            popupMenu.setOnMenuItemClickListener{
                when (it.itemId) {
                    R.id.menu_delete -> {
                        itemListener.onPackageItemClick(0, list[position])
                    }
                    R.id.menu_update -> {
                        itemListener.onPackageItemClick(1, list[position])
                    }
                    else -> {
                        val packagesResponse = PackageResponse(
                            id = 0,
                            name = "",
                            validityValue = 1,
                            validityUnit = "HOUR",
                            price = 0,
                            margin = 0,
                            createdAt = ""
                        )
                        itemListener.onPackageItemClick(2, packagesResponse)
                    }
                }
                true
            }
            popupMenu.inflate(R.menu.menu_package)
            popupMenu.show()
        }

        holder.tvIcon.text = list[position].name[0].toString()
        holder.tvTitle.text = "${list[position].validityValue} ${list[position].validityUnit}"
        holder.tvLongInformation.text = list[position].name
        holder.tvDescription.text = "Margin: [${list[position].margin}] ~ Price: [${list[position].price}]"

        try {
            val df1: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
            val result1: Date = df1.parse(list[position].createdAt)

            val df2: DateFormat = SimpleDateFormat("HH:mm:ss dd MMM yyyy")
            val result2 = df2.format(result1)

            holder.tvDate.text = result2
        } catch (e: Exception){
            e.printStackTrace()
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
}