package co.id.digital.insinyur.irame.data.models

import com.google.gson.annotations.SerializedName

data class ProfileResponse(

    @SerializedName("profile_name")
    var profileName: String? = null,

    @SerializedName("priority")
    var priority: Int? = null,

    @SerializedName("prefix_name")
    var prefixName: String? = null,

    @SerializedName("pool_name")
    var poolName: String? = null,

    @SerializedName("use_limit_session")
    var useLimitSession: Boolean? = null,

    @SerializedName("limit_session")
    var limitSession: Int? = null,

    @SerializedName("use_limit_speed")
    var useLimitSpeed: Boolean? = null,

    @SerializedName("limit_cir_upload")
    var limitCIRUpload: Int? = null,

    @SerializedName("limit_cir_upload_unit")
    var limitCIRUploadUnit: String? = null,

    @SerializedName("limit_mir_upload")
    var limitMIRUpload: Int? = null,

    @SerializedName("limit_mir_upload_unit")
    var limitMIRUploadUnit: String? = null,

    @SerializedName("limit_cir_download")
    var limitCIRDownload: Int? = null,

    @SerializedName("limit_cir_download_unit")
    var limitCIRDownloadUnit: String? = null,

    @SerializedName("limit_mir_download")
    var limitMIRDownload: Int? = null,

    @SerializedName("limit_mir_download_unit")
    var limitMIRDownloadUnit: String? = null
)