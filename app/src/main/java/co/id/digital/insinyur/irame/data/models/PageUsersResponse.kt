package co.id.digital.insinyur.irame.data.models

import com.google.gson.annotations.SerializedName

data class PageUsersResponse(
    @SerializedName("total_page")
    val totalPage: Int,

    @SerializedName("users")
    val users: List<UsersResponse>
)