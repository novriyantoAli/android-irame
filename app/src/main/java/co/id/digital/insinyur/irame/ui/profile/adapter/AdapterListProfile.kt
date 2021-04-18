package co.id.digital.insinyur.irame.ui.profile.adapter

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
import co.id.digital.insinyur.irame.data.models.RadusergroupResponse
import co.id.digital.insinyur.irame.util.ProfileOptionListener


class AdapterListProfile(private val list: ArrayList<RadusergroupResponse>, private val optionalListener: ProfileOptionListener): RecyclerView.Adapter<AdapterListProfile.ListViewHolder>(){

    private lateinit var context: Context

    fun addAll(list: List<RadusergroupResponse>){
        this.list.clear()
        this.list.addAll(list)

        notifyDataSetChanged()
    }

    fun addOne(menu: RadusergroupResponse){
        this.list.remove(menu)
        this.list.add(menu)

        notifyDataSetChanged()
    }

    fun removeOne(menu: RadusergroupResponse){
        this.list.remove(menu)

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterListProfile.ListViewHolder {
        this.context = parent.context

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_message, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: AdapterListProfile.ListViewHolder, position: Int) {

        holder.imgIcon.setOnClickListener { view ->
            val popupMenu = PopupMenu(context, view)
            popupMenu.setOnMenuItemClickListener{
                when (it.itemId) {
                    R.id.menu_delete -> {
                        optionalListener.onOptionItemClick(R.id.menu_delete, list[position])
                    }
                    R.id.menu_update -> {
                        optionalListener.onOptionItemClick(R.id.menu_update, list[position])
                    }
                    else -> {
                        val rug = RadusergroupResponse(username = "", groupname = "", priority = 8)
                        optionalListener.onOptionItemClick(R.id.menu_create, rug)
                    }
                }
                true
            }
            popupMenu.inflate(R.menu.menu_profile)
            popupMenu.show()
        }

        holder.tvIcon.text = list[position].username[0].toString()
        holder.tvTitle.text = list[position].username
        holder.tvLongInformation.text = "Group -> ${list[position].groupname}"
        holder.tvDescription.text = "Priority -> ${list[position].priority}"
        holder.tvDate.text = "{=}"
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