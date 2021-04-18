package co.id.digital.insinyur.irame.data.models

import com.google.gson.annotations.SerializedName

data class PackageResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    var name: String,
    @SerializedName("validity_value")
    var validityValue: Int,
    @SerializedName("validity_unit")
    var validityUnit: String,
    @SerializedName("price")
    var price: Int,
    @SerializedName("margin")
    var margin: Int,
    @SerializedName("created_at")
    var createdAt: String
) { override fun toString(): String { return name } }