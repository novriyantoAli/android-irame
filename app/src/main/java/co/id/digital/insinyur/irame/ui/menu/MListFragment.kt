package co.id.digital.insinyur.irame.ui.menu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.id.digital.insinyur.irame.R
import co.id.digital.insinyur.irame.data.models.MenuResponse
import co.id.digital.insinyur.irame.ui.menu.adapter.AdapterListMenu
import co.id.digital.insinyur.irame.util.MenuItemListener
import co.id.digital.insinyur.irame.util.MenuOptionListener

class MListFragment : Fragment(), MenuItemListener, MenuOptionListener{

    private lateinit var viewModel: MenuViewModel

    private lateinit var rvContent: RecyclerView

    private lateinit var adapter: AdapterListMenu

    override fun onCreateView(inflater: LayoutInflater, group: ViewGroup?, state: Bundle?): View? {
        viewModel = ViewModelProvider(requireActivity()).get(MenuViewModel::class.java)
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_m_list, group, false)

        rvContent = root.findViewById(R.id.rv_content)
        rvContent.layoutManager = LinearLayoutManager(activity)
        rvContent.setHasFixedSize(true)

        val list = ArrayList<MenuResponse>()
        adapter = AdapterListMenu(list, this, this)

        rvContent.adapter = adapter

        viewModel.menu.observe(requireActivity(), Observer {
            if (it != null){
                adapter.addAll(it)
            }
            viewModel.showLoading(false)
        })

        viewModel.resultMenu.observe(requireActivity(), Observer {
            if (it != null){
                adapter.addOne(it)
            }
        })

        viewModel.deleteMenu.observe(requireActivity(), Observer {
            if (it != null){
                adapter.removeOne(it)
            }
        })

        viewModel.fetchMenu()

        return  root
    }

    override fun onItemClick(item: MenuResponse) {
        viewModel.selectedMenu(item)
    }

    override fun onOptionItemClick(id: Int) {
        viewModel.delete(id)
    }
}