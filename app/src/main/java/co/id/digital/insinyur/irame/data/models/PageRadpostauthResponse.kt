package co.id.digital.insinyur.irame.data.models

import com.google.gson.annotations.SerializedName

data class PageRadpostauthResponse(
        @SerializedName("page")
        val page: Int,

        @SerializedName("data")
        val data: List<RadpostauthResponse>
)