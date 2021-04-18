package co.id.digital.insinyur.irame.ui.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import co.id.digital.insinyur.irame.R
import co.id.digital.insinyur.irame.databinding.FragmentFinanceBinding
import co.id.digital.insinyur.irame.databinding.FragmentSettingBinding
import co.id.digital.insinyur.irame.ui.main.MainViewModel

class SettingFragment : Fragment(), View.OnClickListener{

    private lateinit var viewModel: MainViewModel

    private lateinit var binding: FragmentSettingBinding

    private lateinit var llMenu: LinearLayout

    private lateinit var llLogout: LinearLayout

    override fun onCreateView(inflater: LayoutInflater, group: ViewGroup?, state: Bundle?): View? {
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        binding = FragmentSettingBinding.inflate(layoutInflater)
        val view: View = binding.root

        llMenu = view.findViewById(R.id.ll_menu)
        llMenu.setOnClickListener(this)

        llLogout = view.findViewById(R.id.ll_logout)
        llLogout.setOnClickListener(this)

        return view
    }

    override fun onClick(p0: View?) {
        if (p0?.id == R.id.ll_menu){
            viewModel.setMenu(true)
        } else if (p0?.id == R.id.ll_logout){
            viewModel.logout()
        }
    }
}