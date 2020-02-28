package xyz.absolutez3ro.gamestash.data

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import xyz.absolutez3ro.gamestash.data.room.Game
import xyz.absolutez3ro.gamestash.data.room.GameDao
import xyz.absolutez3ro.gamestash.data.room.GameRoomDatabase

class Repository private constructor(private val database: GameRoomDatabase) {
    private var dao: GameDao = database.gameDao()

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

    fun allGames(): LiveData<List<Game>>? = dao.getAllGames()

    suspend fun insert(game: Game) = withContext(Dispatchers.IO) {
        if (dao.getGame(game.name).size == 1)
            dao.updateGame(game)
        else
            database.gameDao().insertGame(game)
    }

    suspend fun delete(game: Game) = withContext(Dispatchers.IO) {
        dao.deleteGame(game)
    }
}