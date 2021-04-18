package co.id.digital.insinyur.irame.ui.users.adapter

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
import co.id.digital.insinyur.irame.data.models.UsersResponse
import co.id.digital.insinyur.irame.util.UsersOptionListener


class AdapterListUsers(private val list: List<UsersResponse?>, private val optionalListener: UsersOptionListener): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

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
//        val view = LayoutInflater.from(parent.context).inflate(
//            R.layout.item_message, parent, false
//        )
//
//        return ListViewHolder(view)
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
        viewHolder.imgIcon.setOnClickListener { view ->
            val popupMenu = PopupMenu(context, view)
            popupMenu.setOnMenuItemClickListener{
                if (it.itemId == R.id.menu_delete){
                    optionalListener.onOptionItemClick(R.id.menu_delete, list[position]!!)
                } else if (it.itemId == R.id.menu_update){
                    optionalListener.onOptionItemClick(R.id.menu_update, list[position]!!)
                }
                true
            }
            popupMenu.inflate(R.menu.menu_profile)
            popupMenu.show()
        }
        viewHolder.tvIcon.text = list[position]!!.username[0].toString()
        viewHolder.tvTitle.text = list[position]!!.username
        viewHolder.tvLongInformation.text = "Password => ${list[position]?.password}"
        viewHolder.tvDescription.text = "[ Profile => ${list[position]?.profile}, Package => ${list[position]?.packageName} ]"
        viewHolder.tvDate.text = list[position]?.expiration
        if (list[position]?.expiration == null){
            viewHolder.tvDate.text = "not activated"
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
}