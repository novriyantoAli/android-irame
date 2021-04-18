package co.id.digital.insinyur.irame.data.models

import com.google.gson.annotations.SerializedName

data class PaymentResponse(
    @SerializedName("id")
    var id: Int? = null,

    @SerializedName("no_invoice")
    var noInvoice: String? = null,

    @SerializedName("nominal")
    var nominal: Int? = null,

    @SerializedName("created_at")
    var createdAt: String? = null
)