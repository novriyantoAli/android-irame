package co.id.digital.insinyur.irame.ui.menu

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import co.id.digital.insinyur.irame.R
import co.id.digital.insinyur.irame.data.models.MenuResponse
import co.id.digital.insinyur.irame.data.models.PackageResponse
import co.id.digital.insinyur.irame.data.models.RadusergroupResponse
import co.id.digital.insinyur.irame.ui.menu.adapter.CustomSpinnerPackagesAdapter
import co.id.digital.insinyur.irame.ui.menu.adapter.CustomSpinnerProfileAdapter
import java.util.*


class MFormFragment : Fragment(), AdapterView.OnItemSelectedListener, View.OnClickListener{

    private lateinit var viewModel: MenuViewModel

    private lateinit var spnPackage: Spinner

    private lateinit var edtValidityValue: EditText

    private lateinit var edtValidityUnit: EditText

    private lateinit var edtPrice: EditText

    private lateinit var edtMargin: EditText

    private lateinit var spnProfile: Spinner

    private lateinit var edtGroup: EditText

    private lateinit var edtPriority: EditText

    private lateinit var edtName: EditText

    private lateinit var btnSave: Button

    private lateinit var packageAdapter: CustomSpinnerPackagesAdapter

    private lateinit var profileAdapter: CustomSpinnerProfileAdapter

    private var menuSelected: MenuResponse? = null

    override fun onCreateView(inflater: LayoutInflater, group: ViewGroup?, state: Bundle?): View? {
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(requireActivity()).get(MenuViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_m_form, group, false)

        val packages = ArrayList<PackageResponse>()
        spnPackage = root.findViewById(R.id.spn_package)
        packageAdapter = CustomSpinnerPackagesAdapter(requireContext(), packages)
        spnPackage.adapter = packageAdapter
        spnPackage.onItemSelectedListener = this

        edtValidityValue = root.findViewById(R.id.edt_validity_value)
        edtValidityUnit = root.findViewById(R.id.edt_validity_unit)
        edtPrice = root.findViewById(R.id.edt_price)
        edtMargin = root.findViewById(R.id.edt_margin)

        val profile = ArrayList<RadusergroupResponse>()
        spnProfile = root.findViewById(R.id.spn_profile)
        profileAdapter = CustomSpinnerProfileAdapter(requireContext(), profile)

        spnProfile.adapter = profileAdapter
        spnProfile.onItemSelectedListener = this

        edtGroup = root.findViewById(R.id.edt_group)
        edtPriority = root.findViewById(R.id.edt_priority)

        edtName = root.findViewById(R.id.edt_name)
        btnSave = root.findViewById(R.id.btn_save)

        viewModel.selectedMenu.observe(requireActivity(), Observer {
            if (it != null){
                edtName.setText(it.name)
            }
            menuSelected = it

            viewModel.showLoading(true)

            viewModel.fetchPackage()
            viewModel.fetchProfile()
        })

        viewModel.packages.observe(requireActivity(), Observer { list ->
            if (list != null){
                packageAdapter.list.clear()
                packageAdapter.list.addAll(list)
                packageAdapter.notifyDataSetChanged()

                if (menuSelected != null){
                    val filterPackage = list.filter { it.id == menuSelected!!.idPackage }
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
                profileAdapter.list.clear()
                profileAdapter.list.addAll(list)
                profileAdapter.notifyDataSetChanged()

                if (menuSelected != null){
                    val filterPackage = list.filter { it.username == menuSelected!!.profile }
                    if (filterPackage.isNotEmpty()){
                        spnProfile.setSelection(list.indexOf(filterPackage[0]))
                        return@Observer
                    }
                    spnProfile.setSelection(0)
                }
            }
        })

        viewModel.resultMenu.observe(requireActivity(), Observer {
            if (it != null){
                viewModel.selectedMenu(null)
            }
        })

        btnSave.setOnClickListener(this)

        return root
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {}

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        when(p0?.id){
            R.id.spn_package -> {
                val x = packageAdapter.getItem(p2) as PackageResponse
                edtValidityValue.setText(x.validityValue.toString())
                edtValidityUnit.setText(x.validityUnit)
                edtPrice.setText(x.price.toString())
                edtMargin.setText(x.margin.toString())

                if (menuSelected != null){
                    menuSelected!!.idPackage = x.id
                }
            }
            R.id.spn_profile -> {
                val x = profileAdapter.getItem(p2) as RadusergroupResponse
                edtGroup.setText(x.groupname)
                edtPriority.setText(x.priority.toString())

                if (menuSelected != null){
                    menuSelected!!.profile = x.username
                }
            }
        }
    }

    override fun onClick(p0: View?) {
        if (p0?.id == R.id.btn_save){
            // validate data
            edtName.error = null
            if (edtName.text.toString().isEmpty()){
                edtName.error = "not valid..."
                return
            }
            if (packageAdapter.isEmpty){
                viewModel.showMessage("package is empty...")
                return
            }
            if (profileAdapter.isEmpty){
                viewModel.showMessage("profile is empty...")
                return
            }

            if (menuSelected != null){
                menuSelected!!.name = edtName.text.toString()
                if (menuSelected!!.id == 0){
                    // ready to store
                    viewModel.store(menuSelected!!)
                } else {
                    viewModel.update(menuSelected!!, menuSelected!!.id)
                }
            }
        }
    }
}