package co.id.digital.insinyur.irame.data.models

import com.google.gson.annotations.SerializedName

data class RadgroupResponse(
        @SerializedName("id")
        var id: Int? = null,

        @SerializedName("groupname")
        var groupname: String? = null,

        @SerializedName("attribute")
        var attribute: String? = null,

        @SerializedName("op")
        var op: String? = null,

        @SerializedName("value")
        var value: String?
)