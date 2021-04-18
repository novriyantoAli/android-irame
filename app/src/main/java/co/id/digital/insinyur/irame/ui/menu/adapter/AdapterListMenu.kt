package co.id.digital.insinyur.irame.ui.menu.adapter


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
import co.id.digital.insinyur.irame.data.models.MenuResponse
import co.id.digital.insinyur.irame.util.MenuItemListener
import co.id.digital.insinyur.irame.util.MenuOptionListener


class AdapterListMenu(private val list: ArrayList<MenuResponse>, private val itemListener: MenuItemListener, private val optionalListener: MenuOptionListener): RecyclerView.Adapter<AdapterListMenu.ListViewHolder>(){

    private lateinit var context: Context

    fun addAll(list: List<MenuResponse>){
        this.list.clear()
        this.list.addAll(list)

        notifyDataSetChanged()
    }

    fun addOne(menu: MenuResponse){
        this.list.remove(menu)
        this.list.add(menu)

        notifyDataSetChanged()
    }

    fun removeOne(menu: MenuResponse){
        this.list.remove(menu)

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterListMenu.ListViewHolder {
        this.context = parent.context

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_message, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: AdapterListMenu.ListViewHolder, position: Int) {
        holder.layoutMain.setOnClickListener {
            itemListener.onItemClick(list[position])
        }

        holder.imgIcon.setOnClickListener {view ->
            val popupMenu = PopupMenu(context, view)
            popupMenu.setOnMenuItemClickListener{
                if (it.itemId == R.id.menu_delete){
                    optionalListener.onOptionItemClick(list[position].id)
                }
                true
            }
            popupMenu.inflate(R.menu.menu_menu_optional)
            popupMenu.show()
        }

        holder.tvIcon.text = list[position].name[0].toString()
        holder.tvTitle.text = list[position].profile
        holder.tvLongInformation.text = list[position].name
        holder.tvDescription.text = list[position].name
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