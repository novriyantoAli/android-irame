package co.id.digital.insinyur.irame.ui.menu.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import co.id.digital.insinyur.irame.R
import co.id.digital.insinyur.irame.data.models.PackageResponse

class CustomSpinnerPackagesAdapter(val context: Context, var list: ArrayList<PackageResponse>) : BaseAdapter() {


    private val mInflater: LayoutInflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val vh: ItemRowHolder
        if (convertView == null) {
            view = mInflater.inflate(R.layout.item_spinner, parent, false)
            vh = ItemRowHolder(view)
            view?.tag = vh
        } else {
            view = convertView
            vh = view.tag as ItemRowHolder
        }

        // setting adapter item height programatically.

        val params = view.layoutParams
        params.height = 90
        view.layoutParams = params

        vh.label.text = list[position].name
        return view
    }

    override fun getItem(position: Int): Any? {

        return list[position]

    }

    override fun getItemId(position: Int): Long {

        return 0

    }

    override fun getCount(): Int {
        return list.size
    }

    private class ItemRowHolder(row: View?) {

        val label: TextView = row?.findViewById(R.id.txtDropDownLabel) as TextView

    }
}