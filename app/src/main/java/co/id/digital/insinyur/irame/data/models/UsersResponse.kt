package co.id.digital.insinyur.irame.data.models

import com.google.gson.annotations.SerializedName

data class UsersResponse(
    @SerializedName("id")
    val id: Int,

    @SerializedName("username")
    var username: String,

    @SerializedName("password")
    var password: String? = null,

    @SerializedName("expiration")
    val expiration: String? = null,

    @SerializedName("profile")
    var profile: String? = null,

    @SerializedName("package")
    var packages: Int? = null,

    @SerializedName("package_name")
    val packageName: String
)