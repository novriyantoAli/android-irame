package co.id.digital.insinyur.irame.data.models

import com.google.gson.annotations.SerializedName

data class InvoiceResponse(
    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("no_invoice")
    val noInvoice: Int? = null,

    @SerializedName("type")
    var type: String? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("nominal")
    val nominal: Int? = null,

    @SerializedName("created_at")
    val createdAt: String? = null,

    @SerializedName("type_customer")
    var typeCustomer: String? = null
)