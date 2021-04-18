package co.id.digital.insinyur.irame.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import co.id.digital.insinyur.irame.data.db.entities.AppState
import co.id.digital.insinyur.irame.data.db.entities.UserState

@Database(entities = [UserState::class, AppState::class], version = 2, exportSchema = false)
abstract class DB: RoomDatabase() {

    abstract fun getUserState(): UserStateDAO

    abstract fun getAppState(): AppStateDAO

    companion object {

        private const val DB_NAME = "irame.db"

        @Volatile
        private var instance: DB? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext, DB::class.java, DB_NAME
            ).allowMainThreadQueries().fallbackToDestructiveMigration().build()
    }
}