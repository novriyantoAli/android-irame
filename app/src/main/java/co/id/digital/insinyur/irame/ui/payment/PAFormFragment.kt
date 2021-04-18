package co.id.digital.insinyur.irame.ui.payment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import co.id.digital.insinyur.irame.R
import co.id.digital.insinyur.irame.data.models.PaymentResponse

class PAFormFragment : Fragment(), View.OnClickListener{

    private lateinit var viewModel: PaymentViewModel

    private lateinit var etNoInvoice: EditText

    private lateinit var etNominal: EditText

    private lateinit var btnSave: Button

    override fun onCreateView(inflater: LayoutInflater, group: ViewGroup?, state: Bundle?): View? {
        viewModel = ViewModelProvider(requireActivity()).get(PaymentViewModel::class.java)
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_pa_form, group, false)

        etNoInvoice = root.findViewById(R.id.et_no_invoice)
        etNominal = root.findViewById(R.id.et_nominal)
        btnSave = root.findViewById(R.id.btn_save)

        viewModel.data.observe(requireActivity(), Observer {
            if (it != null){ etNoInvoice.setText(it.toString()) }
        })

        btnSave.setOnClickListener(this)

        return root
    }

    override fun onClick(p0: View?) {
        if (p0?.id == R.id.btn_save){
            if (etNoInvoice.text.toString().trim().isEmpty()){
                viewModel.setMessage("no invoice cannot be empty")
                return
            }

            if (etNominal.text.toString().trim().isEmpty()){
                etNominal.error = "nominal cannot be empty"
                return
            }
            etNominal.error = null

            viewModel.save(
                noInvoice = etNoInvoice.text.toString().trim().toInt(),
                nominal = etNominal.text.toString().trim().toInt()
            )

            viewModel.setMode(true)
        }
    }
}