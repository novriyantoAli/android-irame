package co.id.digital.insinyur.irame.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import co.id.digital.insinyur.irame.util.Constant

@Entity
data class UserState(@PrimaryKey(autoGenerate = false) val uid: Int = Constant.CURRENT_ID, val username: String, val token: String)