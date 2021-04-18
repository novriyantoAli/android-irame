package co.id.digital.insinyur.irame.ui.invoice

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import co.id.digital.insinyur.irame.R
import co.id.digital.insinyur.irame.data.models.InvoiceResponse
import co.id.digital.insinyur.irame.util.Constant

class INFormFragment : Fragment(), View.OnClickListener{

    private lateinit var viewModel: InvoiceViewModel

    private lateinit var rgPaid: RadioGroup

    private lateinit var rgKind: RadioGroup

    private lateinit var etName: EditText

    private lateinit var etNominal: EditText

    private lateinit var btnSave: Button

    private var selected: InvoiceResponse? = null

    private lateinit var mode: String

    override fun onCreateView(inflater: LayoutInflater, group: ViewGroup?, state: Bundle?): View? {
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(requireActivity()).get(InvoiceViewModel::class.java)

        val view = inflater.inflate(R.layout.fragment_in_form, group, false)

        viewModel.mode.observe(requireActivity(), Observer {
            if (it != null){

                mode = it.mode

                when(it.mode){
                    Constant.OPERATION_SAVE -> {
                        etName.text = null
                        etNominal.text = null
                    }
                    Constant.OPERATION_UPDATE -> {
                        etName.setText(it.result?.name)
                        etNominal.setText(it.result?.nominal.toString())

                        if (it.result?.type.equals("prepaid", true))
                            rgPaid.check(R.id.radio_prepaid)
                        else
                            rgPaid.check(R.id.radio_postpaid)

                        if (it.result?.typeCustomer.equals("customer", true))
                           rgKind.check(R.id.radio_customer)
                        else
                            rgKind.check(R.id.radio_reseller)

                        selected = it.result
                    }
                }
            }
        })
        rgPaid = view.findViewById(R.id.rg_paid)
        rgKind = view.findViewById(R.id.rg_kind)
        etName = view.findViewById(R.id.et_name)
        etNominal = view.findViewById(R.id.et_nominal)
        btnSave = view.findViewById(R.id.btn_save)

        btnSave.setOnClickListener(this)

        return view
    }

    override fun onClick(p0: View?) {
        if (p0?.id == R.id.btn_save){
            if (etName.text.toString().trim().isEmpty()){
                etName.error = "field cannot be empty"
                return
            }
            etName.error = null

            if (etNominal.text.toString().trim().isEmpty()){
                etNominal.error = "field cannot be empty"
                return
            }
            etNominal.error = null

            if (selected == null) {
                selected = InvoiceResponse(
                    name = etName.text.toString().trim(),
                    nominal = etNominal.text.toString().trim().toInt()
                )
            }

            when(rgPaid.checkedRadioButtonId){
                R.id.radio_postpaid -> { selected!!.type = "postpaid" }
                R.id.radio_prepaid -> { selected!!.type = "prepaid" }
            }
            when(rgKind.checkedRadioButtonId){
                R.id.radio_reseller -> { selected!!.typeCustomer = "reseller" }
                R.id.radio_customer -> { selected!!.typeCustomer = "customer" }
            }

            if (mode == Constant.OPERATION_SAVE)
                viewModel.save(selected!!)
            else if (mode == Constant.OPERATION_UPDATE)
                viewModel.update(selected!!)
        }
    }
}