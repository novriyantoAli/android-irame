package co.id.digital.insinyur.irame.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import co.id.digital.insinyur.irame.data.models.NASResponse
import co.id.digital.insinyur.irame.util.Constant

@Entity
data class AppState(
    @PrimaryKey(autoGenerate = false)
    val uid: Int = Constant.CURRENT_ID,

    var link: String = "172.0.0.1",

    var port: String = "3031"

)