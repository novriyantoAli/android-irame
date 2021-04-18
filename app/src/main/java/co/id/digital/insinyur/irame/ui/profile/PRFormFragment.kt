package co.id.digital.insinyur.irame.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.SwitchCompat
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import co.id.digital.insinyur.irame.R
import co.id.digital.insinyur.irame.data.models.ProfileResponse

/**
 * A simple [Fragment] subclass.
 * Use the [PRFormFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PRFormFragment : Fragment() {

    private lateinit var viewModel: ProfileViewModel

    private lateinit var etProfileName: EditText

    private lateinit var etPriority: EditText

    private lateinit var switchLimitUsers: SwitchCompat

    private lateinit var etLimitUsers: EditText

    private lateinit var switchLimitSpeed: SwitchCompat

    private lateinit var clLimitSpeed: ConstraintLayout

    private lateinit var rgHotspotOrPPPoE: RadioGroup

    // upload
    private lateinit var etCirUpload: EditText

    private lateinit var rgCIRUpload: RadioGroup

    private lateinit var etMirUpload: EditText

    private lateinit var rgMIRUpload: RadioGroup

    // download
    private lateinit var etCirDownload: EditText

    private lateinit var rgCIRDownload: RadioGroup

    private lateinit var etMirDownload:EditText

    private lateinit var rgMIRDownload: RadioGroup

    private lateinit var etPool: EditText

    // save
    private lateinit var btnSave: Button

    override fun onCreateView(inflater: LayoutInflater, group: ViewGroup?, state: Bundle?): View? {
        viewModel = ViewModelProvider(requireActivity()).get(ProfileViewModel::class.java)
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_p_r_form, group, false)
        etProfileName = root.findViewById(R.id.et_profile)
        etPriority = root.findViewById(R.id.et_priority)
        etPool = root.findViewById(R.id.et_pool_name)

        rgHotspotOrPPPoE = root.findViewById(R.id.rg_hotspot_or_pppoe)

        switchLimitUsers = root.findViewById(R.id.sw_limit_user)
        etLimitUsers = root.findViewById(R.id.et_limit_users_count)

        switchLimitSpeed = root.findViewById(R.id.sw_limit_speed)
        clLimitSpeed = root.findViewById(R.id.cl_limit_speed)

        etCirUpload = root.findViewById(R.id.et_limit_speed_cir_upload)
        rgCIRUpload = root.findViewById(R.id.rg_limit_speed_cir_upload_unit)

        etMirUpload = root.findViewById(R.id.et_limit_speed_mir_upload)
        rgMIRUpload = root.findViewById(R.id.rg_limit_speed_mir_upload_unit)

        etCirDownload = root.findViewById(R.id.et_limit_speed_cir_download)
        rgCIRDownload = root.findViewById(R.id.rg_limit_speed_cir_download_unit)

        etMirDownload = root.findViewById(R.id.et_limit_speed_mir_download)
        rgMIRDownload = root.findViewById(R.id.rg_limit_speed_mir_download_unit)

        btnSave = root.findViewById(R.id.btn_save)

        rgHotspotOrPPPoE.setOnCheckedChangeListener { _, i ->
            if (i == R.id.radio_btn_hotspot){
                etPool.visibility = View.INVISIBLE
            } else {
                etPool.visibility = View.VISIBLE
            }
        }

        switchLimitUsers.setOnCheckedChangeListener { _, b ->
            if (b) etLimitUsers.visibility = View.VISIBLE
            else etLimitUsers.visibility = View.GONE
        }

        switchLimitSpeed.setOnCheckedChangeListener { _, b ->
            if (b) clLimitSpeed.visibility = View.VISIBLE
            else clLimitSpeed.visibility = View.GONE
        }

        viewModel.profile.observe(requireActivity(), Observer {
            if (it != null){

                etProfileName.setText(it.profileName)
                etPool.setText(it.poolName)

                if (it.prefixName == null) {
                    rgHotspotOrPPPoE.isEnabled = true
                } else {
                    if (it.prefixName == "pppoe")
                        rgHotspotOrPPPoE.check(R.id.radio_btn_pppoe)
                    else
                        rgHotspotOrPPPoE.check(R.id.radio_btn_hotspot)

                    etProfileName.isEnabled = false
                    root.findViewById<RadioButton>(R.id.radio_btn_pppoe).isEnabled = false
                    root.findViewById<RadioButton>(R.id.radio_btn_hotspot).isEnabled = false
                }


                if (it.useLimitSession == true){
                    switchLimitUsers.isChecked = true
                    etLimitUsers.setText(it.limitSession.toString())
                }

                if (it.useLimitSpeed == true){
                    switchLimitSpeed.isChecked = true
                    etCirUpload.setText(it.limitCIRUpload.toString())
                    etMirUpload.setText(it.limitMIRUpload.toString())

                    etCirDownload.setText(it.limitCIRDownload.toString())
                    etMirDownload.setText(it.limitMIRDownload.toString())
                }
            }
        })

        viewModel.selected.observe(requireActivity(), Observer {
            if (it != null){
                etProfileName.setText(it.username)
                etPriority.setText(it.priority.toString())

                if (it.username != ""){
                    viewModel.loadProfile(it.groupname)
                }
            }
        })

        btnSave.setOnClickListener {
            etProfileName.error = null
            if (etProfileName.text.toString().isEmpty()){
                etProfileName.error = "field cannot be empty"
                return@setOnClickListener
            }

            etPriority.error = null
            if (etPriority.text.toString().isEmpty()){
                etPriority.error = "field cannot be empty"
                return@setOnClickListener
            }

            if (etPool.visibility == View.VISIBLE) {
                etPool.error = null
                if (etPool.text.toString().isEmpty()){
                    etPool.error = "field cannot be empty"
                    return@setOnClickListener
                }
            }

            val profile = ProfileResponse()
            profile.profileName = etProfileName.text.toString()
            profile.priority = etPriority.text.toString().toInt()
            profile.poolName = etPool.text.toString()

            etLimitUsers.error = null
            if (switchLimitUsers.isChecked){
                if (etLimitUsers.text.toString().isEmpty()){
                    etLimitUsers.error = "field cannot be empty"
                    return@setOnClickListener
                }
                profile.useLimitSession = true
                profile.limitSession = etLimitUsers.text.toString().toInt()
            }

            etCirUpload.error = null
            etMirUpload.error = null

            etCirDownload.error = null
            etMirDownload.error = null
            if (switchLimitSpeed.isChecked){
                profile.useLimitSpeed = true

                if (etCirUpload.text.toString().isEmpty()){
                    etCirUpload.error = "field cannot be empty"
                    return@setOnClickListener
                }
                profile.limitCIRUpload = etCirUpload.text.toString().toInt()

                if (etMirUpload.text.toString().isEmpty()){
                    etMirUpload.error = "field cannot be empty"
                    return@setOnClickListener
                }
                profile.limitMIRUpload = etMirUpload.text.toString().toInt()

                if (etCirDownload.text.toString().isEmpty()){
                    etCirDownload.error = "field cannot be empty"
                    return@setOnClickListener
                }
                profile.limitCIRDownload = etCirDownload.text.toString().toInt()

                if (etMirDownload.text.toString().isEmpty()){
                    etMirDownload.error = "field cannot be empty"
                    return@setOnClickListener
                }
                profile.limitMIRDownload = etMirDownload.text.toString().toInt()
            }

            profile.prefixName = if (rgHotspotOrPPPoE.checkedRadioButtonId == R.id.radio_btn_hotspot) { "hotspot" } else { "pppoe" }

            // upload
            when(rgCIRUpload.checkedRadioButtonId){
                R.id.radio_b_cir_upload -> {
                    profile.limitCIRUploadUnit = "b"
                }
                R.id.radio_kb_cir_upload -> {
                    profile.limitCIRUploadUnit = "kb"
                }
                R.id.radio_mb_cir_upload -> {
                    profile.limitCIRUploadUnit = "mb"
                }
            }
            when(rgMIRUpload.checkedRadioButtonId){
                R.id.radio_b_mir_upload -> {
                    profile.limitMIRUploadUnit = "b"
                }
                R.id.radio_kb_mir_upload -> {
                    profile.limitMIRUploadUnit = "kb"
                }
                R.id.radio_mb_mir_upload -> {
                    profile.limitMIRUploadUnit = "mb"
                }
            }

            // download
            when(rgCIRDownload.checkedRadioButtonId){
                R.id.radio_b_cir_download -> {
                    profile.limitCIRDownloadUnit = "b"
                }
                R.id.radio_kb_cir_download -> {
                    profile.limitCIRDownloadUnit = "kb"
                }
                R.id.radio_mb_cir_download -> {
                    profile.limitCIRDownloadUnit = "mb"
                }
            }
            when(rgMIRDownload.checkedRadioButtonId){
                R.id.radio_b_mir_download -> {
                    profile.limitMIRDownloadUnit = "b"
                }
                R.id.radio_kb_mir_download -> {
                    profile.limitMIRDownloadUnit = "kb"
                }
                R.id.radio_mb_mir_download -> {
                    profile.limitMIRDownloadUnit = "mb"
                }
            }

            viewModel.saveProfile(profile)
        }

        return root
    }
}