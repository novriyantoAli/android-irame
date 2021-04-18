package co.id.digital.insinyur.irame.ui.radius

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import co.id.digital.insinyur.irame.R
import co.id.digital.insinyur.irame.data.models.UsersResponse
import co.id.digital.insinyur.irame.databinding.FragmentRadiusBinding
import co.id.digital.insinyur.irame.ui.main.MainViewModel
import co.id.digital.insinyur.irame.ui.nas.NasActivity
import co.id.digital.insinyur.irame.ui.profile.ProfileActivity
import co.id.digital.insinyur.irame.ui.radius.adapter.RadiusAdapter
import co.id.digital.insinyur.irame.ui.trace.TraceActivity
import co.id.digital.insinyur.irame.ui.users.UsersActivity


class RadiusFragment : Fragment(), View.OnClickListener{

    private lateinit var viewModel: MainViewModel

    private lateinit var binding: FragmentRadiusBinding

    private lateinit var adapter: RadiusAdapter

    private lateinit var llUsers: LinearLayout

    private lateinit var llProfile: LinearLayout

    private lateinit var llTrace: LinearLayout

    private lateinit var llNAS: LinearLayout

    override fun onCreateView(inflater: LayoutInflater, group: ViewGroup?, state: Bundle?): View? {

        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        binding = FragmentRadiusBinding.inflate(layoutInflater)
        val view: View = binding.root

        val list = ArrayList<UsersResponse>()
        adapter = RadiusAdapter(list, requireContext())

        llUsers = view.findViewById(R.id.ll_users)
        llUsers.setOnClickListener(this)

        llProfile = view.findViewById(R.id.ll_profile)
        llProfile.setOnClickListener(this)

        llTrace = view.findViewById(R.id.ll_trace)
        llTrace.setOnClickListener(this)

        llNAS = view.findViewById(R.id.ll_nas)
        llNAS.setOnClickListener(this)

        viewModel.username.observe(requireActivity(), Observer {
            if (it != null) { binding.tvUsername.text = it }
        })


        val layoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.HORIZONTAL, false
        )

        binding.rvExpiredToday.layoutManager = layoutManager
        binding.rvExpiredToday.adapter = adapter
        binding.rvExpiredToday.setHasFixedSize(true)

        viewModel.expiration.observe(requireActivity(), Observer {
            if (it != null){ adapter.addList(it) }
        })

        viewModel.requestUsername()

        viewModel.fetchExpiredToday()

        return view
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.ll_users -> {
                val intent = Intent(requireContext(), UsersActivity::class.java)
                startActivity(intent)
            }
            R.id.ll_profile -> {
                val intent = Intent(requireContext(), ProfileActivity::class.java)
                startActivity(intent)
            }
            R.id.ll_trace -> {
                val intent = Intent(requireContext(), TraceActivity::class.java)
                startActivity(intent)
            }
            R.id.ll_nas -> {
                val intent = Intent(requireContext(), NasActivity::class.java)
                startActivity(intent)
            }
        }
    }
}