package co.id.digital.insinyur.irame.ui.packages

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
import co.id.digital.insinyur.irame.data.models.PackageResponse
import co.id.digital.insinyur.irame.ui.packages.adapter.AdapterListPackage
import co.id.digital.insinyur.irame.util.PackageOptionListener

class PListFragment : Fragment(), PackageOptionListener{

    private lateinit var viewModel: PackagesViewModel

    private lateinit var rvContent: RecyclerView

    private lateinit var adapter: AdapterListPackage

    private val list = ArrayList<PackageResponse>()

    override fun onCreateView(inflater: LayoutInflater, group: ViewGroup?, state: Bundle?): View? {
        viewModel = ViewModelProvider(requireActivity()).get(PackagesViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_p_list, group, false)

        rvContent = root.findViewById(R.id.rv_content)
        adapter =  AdapterListPackage(list, this)

        rvContent.layoutManager = LinearLayoutManager(requireActivity())
        rvContent.adapter = adapter

        viewModel.result.observe(requireActivity(), Observer {
            if (it != null){
                adapter.addAll(it)
            }
        })

        viewModel.upsert.observe(requireActivity(), Observer {
            if (it != null){
                when (it.mode) {
                    0 -> {
                        // delete mode
                        adapter.removeOne(it.pr)
                    }
                    1 -> {
                        // insert mode
                        adapter.replace(it.pr)
                    }
                    2 -> {
                        // update mode
                        adapter.replace(it.pr)
                    }
                }
            }
        })

        viewModel.fetch()

        return root
    }

    override fun onPackageItemClick(menu: Int, packageResponse: PackageResponse) {
        if (menu == 0)
            viewModel.delete(packageResponse)
        else if (menu == 1 || menu == 2){
            viewModel.select(packageResponse)
        }
    }
}