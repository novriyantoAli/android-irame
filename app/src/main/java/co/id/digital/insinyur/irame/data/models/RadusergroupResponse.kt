package co.id.digital.insinyur.irame.data.models

import com.google.gson.annotations.SerializedName

data class RadusergroupResponse(
    @SerializedName("username")
    val username: String,
    @SerializedName("groupname")
    val groupname: String,
    @SerializedName("priority")
    val priority: Int
){ override fun toString(): String { return username } }