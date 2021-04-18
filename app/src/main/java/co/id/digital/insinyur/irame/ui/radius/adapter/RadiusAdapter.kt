package co.id.digital.insinyur.irame.ui.radius.adapter
/*
 * Copyright (c) 2021. rogergcc
 */

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.id.digital.insinyur.irame.data.models.UsersResponse
import co.id.digital.insinyur.irame.databinding.ItemExpirationBinding
import java.text.SimpleDateFormat
import java.util.*


class RadiusAdapter(private val list: ArrayList<UsersResponse>, val mContext: Context) : RecyclerView.Adapter<RadiusAdapter.ViewHolder>() {

    private val mInflater: LayoutInflater = LayoutInflater.from(mContext)

    fun addList(l: List<UsersResponse>){
        list.clear()
        list.addAll(l)

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemPagerCardBinding: ItemExpirationBinding = ItemExpirationBinding.inflate(inflater, parent, false)
        return ViewHolder(itemPagerCardBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setBind(list[position])
        holder.binding.cardViewCourse.setOnClickListener {
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(var binding: ItemExpirationBinding) : RecyclerView.ViewHolder(binding.root) {

        fun setBind(item: UsersResponse) {
            if (item.expiration != null) {
                try {

                    val format = SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.ENGLISH)
                    val date = format.parse(item.expiration)
                    if (date != null){
                        val formatterDay = SimpleDateFormat("dd", Locale.ENGLISH)
                        val formatterMonth = SimpleDateFormat("MMM", Locale.ENGLISH)
                        val formatterHours = SimpleDateFormat("HH:mm:ss", Locale.ENGLISH)

                        binding.tvDate.text = formatterDay.format(date)
                        binding.tvToday.text = formatterMonth.format(date)
                        binding.tvHours.text = formatterHours.format(date)
                    }
                } catch (e: Exception){
                    binding.tvDate.text = "0"
                    binding.tvToday.text = "Null"
                    binding.tvHours.text = "00:00:00"
                }
            } else {
                binding.tvDate.text = "0"
                binding.tvToday.text = "Null"
                binding.tvHours.text = "00:00:00"
            }

            binding.tvUsername.text = item.username
            binding.tvPackage.text = item.packageName
            binding.tvProfile.text = item.profile
        }

    }
}