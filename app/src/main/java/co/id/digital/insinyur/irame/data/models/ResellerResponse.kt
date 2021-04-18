package co.id.digital.insinyur.irame.data.models

import com.google.gson.annotations.SerializedName

data class ResellerResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("telegram_id")
    val idTelegram: Int,
    @SerializedName("chat_id")
    val idChat: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("register_code")
    val registerCode: String,
    @SerializedName("active")
    val activated: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("status_transaction")
    val statusTransaction: String?,
    @SerializedName("date_transaction")
    val dateTransaction: String?
)