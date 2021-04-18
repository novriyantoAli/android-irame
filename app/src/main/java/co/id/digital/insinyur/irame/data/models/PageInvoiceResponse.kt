package co.id.digital.insinyur.irame.data.models

import com.google.gson.annotations.SerializedName

data class PageInvoiceResponse(
    @SerializedName("total_page")
    val totalPage: Int,

    @SerializedName("data")
    val data: List<InvoiceResponse>
)