package co.id.digital.insinyur.irame.data.models

import com.google.gson.annotations.SerializedName

data class MenuResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("id_package")
    var idPackage: Int,
    @SerializedName("profile")
    var profile: String,
    @SerializedName("name")
    var name: String,
    @SerializedName("created_at")
    val createdAt: String
)