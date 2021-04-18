package co.id.digital.insinyur.irame.data.models

import com.google.gson.annotations.SerializedName

data class NASResponse(
    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("nasname")
    val nasname: String? = null,

    @SerializedName("shortname")
    val shortname: String? = null,

    @SerializedName("type")
    val type: String? = null,

    @SerializedName("ports")
    val ports: String? = null,

    @SerializedName("secret")
    val secret: String? = null,

    @SerializedName("server")
    val server: String? = null,

    @SerializedName("community")
    val community: String? = null,

    @SerializedName("description")
    val description: String? = null
)