package com.kevbotstudios.cleanarchitecture.viewmodels


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.kevbotstudios.cleanarchitecture.database.WordRoomDatabase
import com.kevbotstudios.cleanarchitecture.entities.Word
import com.kevbotstudios.cleanarchitecture.repository.WordRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * WordViewModel - the ViewModel for the words
 * @param application - reference to the application to allow for grabbing the context
 * @see AndroidViewModel
 * @author KevBotStudios (c) 2020
 */
class WordViewModel(application: Application): AndroidViewModel(application) {

    private val repository: WordRepository
    val allWords: LiveData<List<Word>>

    init {
        val wordsDao = WordRoomDatabase.getDatabase(application, viewModelScope).wordDao()

        repository = WordRepository(wordsDao)
        allWords = repository.allWords
    }

    /**
     * insert - launches the coroutine for inserting into the database
     */
    fun insert(word: Word) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(word)
    }
}