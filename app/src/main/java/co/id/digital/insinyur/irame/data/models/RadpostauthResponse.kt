package co.id.digital.insinyur.irame.data.models

import com.google.gson.annotations.SerializedName

data class RadpostauthResponse(
        @SerializedName("id")
        val id: Int? = null,

        @SerializedName("username")
        val username: String? = null,

        @SerializedName("pass")
        val password: String? = null,

        @SerializedName("reply")
        val reply: String? = null,

        @SerializedName("authdate")
        val authDate: String? = null
)