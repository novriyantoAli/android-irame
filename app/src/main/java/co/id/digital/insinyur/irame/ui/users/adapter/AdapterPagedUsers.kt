package co.id.digital.insinyur.irame.ui.users.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import co.id.digital.insinyur.irame.R
import co.id.digital.insinyur.irame.data.models.UsersResponse
import co.id.digital.insinyur.irame.databinding.ItemMessageBinding
import co.id.digital.insinyur.irame.util.UsersOptionListener

class AdapterPagedUsers(private val optionalListener: UsersOptionListener): PagingDataAdapter<UsersResponse, AdapterPagedUsers.ViewHolder>(diffUtilCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMessageBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class ViewHolder(private val binding: ItemMessageBinding) : RecyclerView.ViewHolder(binding.root){

        private var user: UsersResponse? = null

        fun bind(user: UsersResponse){
            binding.tvIcon.text = user.username[0].toString()
            binding.tvTitle.text = user.username
            binding.tvDescription.text = user.packageName
            binding.tvLongInformation.text =  user.profile
            binding.tvDate.text = user.expiration
            binding.imgIcon.setOnClickListener { view ->
                val popupMenu = PopupMenu(binding.root.context, view)
                popupMenu.setOnMenuItemClickListener{
                    when (it.itemId) {
                        R.id.menu_delete -> {
                            optionalListener.onOptionItemClick(R.id.menu_delete, user)
                        }
                        R.id.menu_update -> {
                            optionalListener.onOptionItemClick(R.id.menu_update, user)
                        }
                        else -> {
                            val users = UsersResponse(id = 0, username = "", packageName = "")
                            optionalListener.onOptionItemClick(R.id.menu_create, users)
                        }
                    }
                    true
                }
                popupMenu.inflate(R.menu.menu_profile)
                popupMenu.show()
            }
            this.user = user
        }
    }

    companion object{

        private val diffUtilCallback = object : DiffUtil.ItemCallback<UsersResponse>(){
            override fun areItemsTheSame(oldItem: UsersResponse, newItem: UsersResponse): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: UsersResponse, newItem: UsersResponse): Boolean {
                return oldItem == newItem
            }
        }
    }
}