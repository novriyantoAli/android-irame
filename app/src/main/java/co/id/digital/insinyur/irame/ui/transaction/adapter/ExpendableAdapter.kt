package co.id.digital.insinyur.irame.ui.transaction.adapter

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import co.id.digital.insinyur.irame.R
import co.id.digital.insinyur.irame.data.models.TransactionResponse
import co.id.digital.insinyur.irame.ui.transaction.ExpendableData
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class ExpendableAdapter(
    private val context: Context,
    private val expandableListTitle: ArrayList<String>,
    private val expandableListDetail: HashMap<String, List<TransactionResponse>>
) : BaseExpandableListAdapter() {

    fun addAll(data: ExpendableData){
//        expandableListTitle.clear()
//        expandableListDetail.clear()

        expandableListTitle.addAll(data.title)
        expandableListDetail.putAll(data.data)

        notifyDataSetChanged()
    }

    override fun getChild(listPosition: Int, expandedListPosition: Int): Any {
        return expandableListDetail[expandableListTitle[listPosition]]!![expandedListPosition]
    }

    override fun getChildId(listPosition: Int, expandedListPosition: Int): Long {
        return expandedListPosition.toLong()
    }

    override fun getChildView(listPosition: Int, expandedListPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup): View? {
        val response = getChild(listPosition, expandedListPosition) as TransactionResponse
        var localView = convertView
        if (localView == null) {
            val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            localView = layoutInflater.inflate(R.layout.list_item, null)
        }
        val transactionCode = localView?.findViewById<View>(R.id.tv_transaction_code_value) as TextView
        val status = localView.findViewById<View>(R.id.tv_status_value) as TextView
        val value = localView.findViewById<View>(R.id.tv_value_value) as TextView
        val information = localView.findViewById<View>(R.id.tv_information_value) as TextView
        val createdAt = localView.findViewById<View>(R.id.tv_created_at_value) as TextView

        try {
            val df1: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
            val result1: Date = df1.parse(response.createdAt)

            val df2: DateFormat = SimpleDateFormat("HH:mm:ss dd MMM yyyy")
            val result2 = df2.format(result1)

            createdAt.text = result2
        } catch (e: Exception){
            e.printStackTrace()
        }
        transactionCode.text = response.transactionCode
        status.text = response.status
        value.text = response.value.toString()
        information.text = response.information

        return localView
    }

    override fun getChildrenCount(listPosition: Int): Int {
        return expandableListDetail[expandableListTitle[listPosition]]!!.size
    }

    override fun getGroup(listPosition: Int): Any {
        return expandableListTitle[listPosition]
    }

    override fun getGroupCount(): Int {
        return expandableListTitle.size
    }

    override fun getGroupId(listPosition: Int): Long {
        return listPosition.toLong()
    }

    override fun getGroupView(listPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup): View? {
        val listTitle = getGroup(listPosition) as String
        var localView = convertView
        if (localView == null) {
            val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            localView = layoutInflater.inflate(R.layout.list_group, null)
        }
        val listTitleTextView = localView?.findViewById<View>(R.id.listTitle) as TextView
        listTitleTextView.setTypeface(null, Typeface.BOLD)
        listTitleTextView.text = listTitle
        return localView
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun isChildSelectable(listPosition: Int, expandedListPosition: Int): Boolean {
        return true
    }

}