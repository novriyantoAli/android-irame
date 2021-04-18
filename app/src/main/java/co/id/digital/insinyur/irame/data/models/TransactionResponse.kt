package co.id.digital.insinyur.irame.data.models

import com.google.gson.annotations.SerializedName

data class TransactionResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("id_reseller")
    val idReseller: Int,
    @SerializedName("name_reseller")
    val nameReseller: String,
    @SerializedName("transaction_code")
    val transactionCode: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("value")
    val value: Int,
    @SerializedName("information")
    val information: String,
    @SerializedName("created_at")
    val createdAt: String
)