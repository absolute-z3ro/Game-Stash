package xyz.absolutez3ro.gamestash.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import xyz.absolutez3ro.gamestash.data.Repository
import xyz.absolutez3ro.gamestash.data.room.Game
import xyz.absolutez3ro.gamestash.data.room.GameRoomDatabase

class GameViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = Repository.getInstance(GameRoomDatabase.getDatabase(application))

    val games = repository.allGames()

    fun insert(game: Game) = viewModelScope.launch { repository.insert(game) }
    fun delete(game: Game) = viewModelScope.launch { repository.delete(game) }
}