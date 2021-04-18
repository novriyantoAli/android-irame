package co.id.digital.insinyur.irame.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import co.id.digital.insinyur.irame.data.db.entities.UserState
import co.id.digital.insinyur.irame.util.Constant.CURRENT_ID

@Dao
interface UserStateDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(userState: UserState): Long

    @Query("SELECT * FROM userstate LIMIT 1")
    fun getUsers(): LiveData<UserState?>

    @Query("SELECT * FROM userstate LIMIT 1")
    fun getUsersSync(): UserState

    @Query("DELETE FROM userstate WHERE uid = $CURRENT_ID")
    fun deleteUsers()
}