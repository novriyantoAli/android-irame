package co.id.digital.insinyur.irame.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.id.digital.insinyur.irame.R
import co.id.digital.insinyur.irame.data.models.MenuResponse
import co.id.digital.insinyur.irame.data.models.RadusergroupResponse
import co.id.digital.insinyur.irame.ui.menu.MenuViewModel
import co.id.digital.insinyur.irame.ui.menu.adapter.AdapterListMenu
import co.id.digital.insinyur.irame.ui.profile.adapter.AdapterListProfile
import co.id.digital.insinyur.irame.util.ProfileOptionListener

/**
 * A simple [Fragment] subclass.
 * Use the [PRListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PRListFragment : Fragment(), ProfileOptionListener{

    private lateinit var viewModel: ProfileViewModel

    private lateinit var tvDescription: TextView

    private lateinit var btnAdd: Button

    private lateinit var rvContent: RecyclerView

    private lateinit var adapter: AdapterListProfile

    override fun onCreateView(inflater: LayoutInflater, group: ViewGroup?, state: Bundle?): View? {
        viewModel = ViewModelProvider(requireActivity()).get(ProfileViewModel::class.java)

        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_p_r_list, group, false)


        tvDescription = root.findViewById(R.id.tv_description)
        btnAdd = root.findViewById(R.id.btn_add)

        rvContent = root.findViewById(R.id.rv_content)
        rvContent.layoutManager = LinearLayoutManager(activity)
        rvContent.setHasFixedSize(true)

        val list = ArrayList<RadusergroupResponse>()
        adapter = AdapterListProfile(list, this)

        rvContent.adapter = adapter

        viewModel.resultProfile.observe(requireActivity(), Observer {
            if (it != null){

                adapter.addAll(it)

                if (it.isNotEmpty()) {
                    rvContent.visibility = View.VISIBLE
                    tvDescription.visibility = View.GONE
                    btnAdd.visibility = View.GONE
                } else {
                    rvContent.visibility = View.GONE
                    tvDescription.visibility = View.VISIBLE
                    btnAdd.visibility = View.VISIBLE
                }

            }
        })

        viewModel.addProfile.observe(requireActivity(), Observer {
            if (it != null){
                adapter.addOne(it)
            }
        })

        viewModel.deleteProfile.observe(requireActivity(), Observer {
            if (it != null){
                adapter.removeOne(it)
            }
        })

        viewModel.fetchProfile()

        btnAdd.setOnClickListener {
            val rug = RadusergroupResponse(username = "", groupname = "", priority = 8)
            viewModel.setSelected(rug)
        }

        return root
    }

    override fun onOptionItemClick(id: Int, profile: RadusergroupResponse) {
        when (id) {
            R.id.menu_create, R.id.menu_update -> {
                viewModel.setSelected(profile)
            }
            R.id.menu_delete -> {
                viewModel.deleteProfile(profile)
            }
        }
    }
}