package xyz.absolutez3ro.gamestash.data

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import xyz.absolutez3ro.gamestash.data.room.Game
import xyz.absolutez3ro.gamestash.data.room.GameRoomDatabase

class Repository private constructor(private val database: GameRoomDatabase) {
    companion object {
        @Volatile
        private var INSTANCE: Repository? = null

        fun getInstance(database: GameRoomDatabase): Repository {
            val tempInstance = INSTANCE
            if (tempInstance != null) return tempInstance
            synchronized(this) {
                val newInstance = Repository(database)
                INSTANCE = newInstance
                return newInstance
            }
        }
    }

    fun allGames(): LiveData<List<Game>>? = database.gameDao().getAllGames()

    suspend fun insert(game: Game) = withContext(Dispatchers.IO) {
        database.gameDao().insertGame(game)
    }

    suspend fun delete(game: Game) = withContext(Dispatchers.IO) {
        database.gameDao().deleteGame(game)
    }
}