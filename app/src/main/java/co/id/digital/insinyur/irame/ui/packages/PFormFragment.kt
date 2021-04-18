package co.id.digital.insinyur.irame.ui.packages

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
import co.id.digital.insinyur.irame.ui.menu.adapter.CustomSpinnerPackagesAdapter
import co.id.digital.insinyur.irame.ui.packages.adapter.CustomSpinnerAdapter
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class PFormFragment : Fragment(), AdapterView.OnItemSelectedListener, View.OnClickListener{

    private lateinit var viewModel: PackagesViewModel

    private lateinit var etName: EditText

    private lateinit var etValidityValue: EditText

    private lateinit var spnValidityUnit: Spinner

    private lateinit var etPrice: EditText

    private lateinit var etMargin: EditText

    private lateinit var btnSave: Button

//    private lateinit var adapter: ArrayAdapter<String>

    private lateinit var adapterSpinner: CustomSpinnerAdapter

    private var packageSelected: PackageResponse? = null

    private val validityUnit = arrayListOf("HOUR", "DAY", "MONTH", "YEAR")

    override fun onCreateView(inflater: LayoutInflater, group: ViewGroup?, state: Bundle?): View? {
        viewModel = ViewModelProvider(requireActivity()).get(PackagesViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_p_form, group, false)

        etName = root.findViewById(R.id.et_package_name)

        etValidityValue = root.findViewById(R.id.et_validity_value)

        spnValidityUnit = root.findViewById(R.id.spn_validity_unit)

        etPrice = root.findViewById(R.id.et_price)

        etMargin = root.findViewById(R.id.et_margin)

        btnSave = root.findViewById(R.id.btn_save)

        adapterSpinner = CustomSpinnerAdapter(requireContext(), validityUnit)
        spnValidityUnit.adapter = adapterSpinner
        spnValidityUnit.onItemSelectedListener = this
//        adapter = ArrayAdapter(
//                requireContext(), android.R.layout.simple_spinner_item, validityUnit
//        )
//
//        spnValidityUnit.adapter = adapter
//        spnValidityUnit.onItemSelectedListener = this

        viewModel.selected.observe(requireActivity(), Observer { pr ->
            if (pr != null){

                packageSelected = pr

                etName.setText(pr.name)
                etValidityValue.setText(pr.validityValue.toString())
                etPrice.setText(pr.price.toString())
                etMargin.setText(pr.margin.toString())

                val filterPackage = validityUnit.filter { it == pr.name }
                if (filterPackage.isNotEmpty()){
                    spnValidityUnit.setSelection(validityUnit.indexOf(filterPackage[0]))
                    return@Observer
                }
            }
        })

        btnSave.setOnClickListener(this)

        return root
    }

    override fun onNothingSelected(p0: AdapterView<*>?) { }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        if(p0?.id == R.id.spn_validity_unit){
            if (packageSelected != null){
                packageSelected!!.validityUnit = adapterSpinner.getItem(p2).toString()
            }
        }
    }

    override fun onClick(p0: View?) {
        if (p0?.id == R.id.btn_save){
            etName.error = null
            etValidityValue.error = null
            etPrice.error = null
            etMargin.error = null

            if (etName.text.toString().isEmpty()) {
                etName.error = "not valid"
                return
            }
            if (etValidityValue.text.toString().isEmpty()){
                etValidityValue.error = "not valid"
                return
            }
            if (etPrice.text.toString().isEmpty()){
                etPrice.error = "not valid"
                return
            }
            if (etMargin.text.toString().isEmpty()) {
                etMargin.error = "not valid"
                return
            }

            if (adapterSpinner.isEmpty){
                viewModel.setMessage("validity unit not valid")
                return
            }

            if (packageSelected != null){
                packageSelected!!.name = etName.text.toString()
                packageSelected!!.validityValue = etValidityValue.text.toString().toInt()
                packageSelected!!.price = etPrice.text.toString().toInt()
                packageSelected!!.margin = etMargin.text.toString().toInt()

                val tz = TimeZone.getTimeZone("GMT+8")
                val df: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
                df.timeZone = tz

                packageSelected!!.createdAt = df.format(Date())

                if (packageSelected!!.id == 0)
                    viewModel.insert(packageSelected!!)
                else
                    viewModel.update(packageSelected!!)
            }
        }
    }
}