package co.id.digital.insinyur.irame.ui.users

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import co.id.digital.insinyur.irame.R
import co.id.digital.insinyur.irame.data.models.PackageResponse
import co.id.digital.insinyur.irame.data.models.RadusergroupResponse
import co.id.digital.insinyur.irame.data.models.UsersResponse
import java.util.ArrayList

class UFormFragment : Fragment(), AdapterView.OnItemSelectedListener, View.OnClickListener{

    private lateinit var viewModel: UsersViewModel

    private lateinit var etUsername: EditText

    private lateinit var etPassword: EditText

    private lateinit var spnProfile: Spinner

    private lateinit var spnPackage: Spinner

    private lateinit var btnSave: Button

    private lateinit var packageAdapter: ArrayAdapter<PackageResponse>

    private lateinit var profileAdapter: ArrayAdapter<RadusergroupResponse>

    private var usersSelected: UsersResponse? = null

    override fun onCreateView(inflater: LayoutInflater, group: ViewGroup?, state: Bundle?): View? {
        viewModel = ViewModelProvider(requireActivity()).get(UsersViewModel::class.java)

        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_u_form, group, false)
        // Initialize
        etUsername = view.findViewById(R.id.et_username)
        etPassword = view.findViewById(R.id.et_password)

        val packages = ArrayList<PackageResponse>()
        spnPackage = view.findViewById(R.id.spn_package)
        packageAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, packages)
        packageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spnPackage.adapter = packageAdapter
        spnPackage.onItemSelectedListener = this

        val profile = ArrayList<RadusergroupResponse>()
        spnProfile = view.findViewById(R.id.spn_profile)
        profileAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, profile)
        profileAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spnProfile.adapter = profileAdapter
        spnProfile.onItemSelectedListener = this

        viewModel.packages.observe(requireActivity(), Observer { list ->
            if (list != null){
                packageAdapter.clear()
                packageAdapter.addAll(list)
                packageAdapter.notifyDataSetChanged()

                if (usersSelected != null){
                    val filterPackage = list.filter { it.id == usersSelected!!.packages }
                    if (filterPackage.isNotEmpty()){
                        spnPackage.setSelection(list.indexOf(filterPackage[0]))
                        return@Observer
                    }
                    spnPackage.setSelection(0)
                }
            }
        })

        viewModel.profile.observe(requireActivity(), Observer { list ->
            if (list != null){
                profileAdapter.clear()
                profileAdapter.addAll(list)
                profileAdapter.notifyDataSetChanged()

                if (usersSelected != null){
                    val filterPackage = list.filter { it.username == usersSelected!!.profile }
                    if (filterPackage.isNotEmpty()){
                        spnProfile.setSelection(list.indexOf(filterPackage[0]))
                        return@Observer
                    }
                    spnProfile.setSelection(0)
                }
            }
        })

        btnSave = view.findViewById(R.id.btn_save)
        btnSave.setOnClickListener(this)

        viewModel.selected.observe(requireActivity(), Observer {
            if (it != null){
                usersSelected = it

                etUsername.setText(it.username)
                etPassword.setText(it.password)

                if (it.id != 0){
                    etUsername.isEnabled = false
                }

                viewModel.fetchPackage()
                viewModel.fetchProfile()
            }
        })
        return view
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {}

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        when(p0?.id){
            R.id.spn_package -> {
                val x = packageAdapter.getItem(p2)
                if (usersSelected != null)
                    usersSelected!!.packages = x!!.id
            }
            R.id.spn_profile -> {
                val x = profileAdapter.getItem(p2)
                if (usersSelected != null)
                    usersSelected!!.profile = x!!.username
            }
        }
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.btn_save -> {
                // validate save
                if (etUsername.text.toString().trim().isEmpty()){
                    etUsername.error = "field cannot empty"
                    return
                }
                etUsername.error = null

                if (etPassword.text.toString().trim().isEmpty()){
                    etPassword.error = "field cannot empty"
                    return
                }
                etPassword.error = null

                if (packageAdapter.isEmpty){
                    viewModel.setMessage("package is empty...")
                    return
                }
                if (profileAdapter.isEmpty){
                    viewModel.setMessage("profile is empty...")
                    return
                }

                usersSelected?.username = etUsername.text.toString().trim()
                usersSelected?.password = etPassword.text.toString().trim()

                viewModel.save(usersSelected!!)
            }
        }
    }
}