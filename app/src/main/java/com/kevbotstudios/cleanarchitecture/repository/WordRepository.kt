package com.kevbotstudios.cleanarchitecture.repository

import com.kevbotstudios.cleanarchitecture.dao.WordDao
import com.kevbotstudios.cleanarchitecture.entities.Word

/**
 * WordRepository - repository for accessing data sources
 * @param wordDao - the word DAO for Room. Better to pass this not the whole database
 * @author KevBotStudios (c) 2020
 */
class WordRepository(private val wordDao: WordDao) {
    val allWords = wordDao.getAlphabetizedWords()

    /**
     * insert - inserts a word into the database
     * @param word - the word to add to the database
     */
    suspend fun insert(word: Word) {
        wordDao.insert(word)
    }
}