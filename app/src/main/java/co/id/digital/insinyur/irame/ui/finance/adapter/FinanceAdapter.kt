package co.id.digital.insinyur.irame.ui.finance.adapter

/*
 * Copyright (c) 2021. rogergcc
 */

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.id.digital.insinyur.irame.R
import co.id.digital.insinyur.irame.data.models.FinanceReportResponse
import co.id.digital.insinyur.irame.databinding.ItemPagerCardBinding


class FinanceAdapter(private val list: ArrayList<FinanceReportResponse>, val mContext: Context) : RecyclerView.Adapter<FinanceAdapter.ViewHolder>() {

    private val mInflater: LayoutInflater = LayoutInflater.from(mContext)

    fun addList(l: List<FinanceReportResponse>){
        list.clear()
        list.addAll(l)

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        View view = mInflater.inflate(R.layout.item_pager_card, parent, false);
//        return new ViewHolder(view);
        val inflater = LayoutInflater.from(parent.context)
        //        View v = inflater.inflate(R.layout.item_shop_card, parent, false);
//        return new ViewHolder(v);
        val itemPagerCardBinding: ItemPagerCardBinding = ItemPagerCardBinding.inflate(inflater, parent, false)
        return ViewHolder(itemPagerCardBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setBind(list[position])
        holder.binding.cardViewCourse.setOnClickListener(View.OnClickListener {
//            matchCourseClickListener.onScrollPagerItemClick(
//                mCoursesList[position],
//                holder.binding.image
//            )
        })
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(var binding: ItemPagerCardBinding) : RecyclerView.ViewHolder(binding.root) {

        fun setBind(item: FinanceReportResponse) {
            binding.tvTitle.text = item.name
            binding.tvValue.text = item.value.toString()
            when(item.name){
                "Invoice" -> { binding.tvValue.setTextColor(mContext.resources.getColor(R.color.color4)) }
                "Payment" -> { binding.tvValue.setTextColor(mContext.resources.getColor(R.color.color2)) }
                "Credit" -> { binding.tvValue.setTextColor(mContext.resources.getColor(R.color.color3))}
            }
        }

    }
}