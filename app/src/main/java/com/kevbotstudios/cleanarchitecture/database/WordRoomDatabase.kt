package com.kevbotstudios.cleanarchitecture.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.kevbotstudios.cleanarchitecture.dao.WordDao
import com.kevbotstudios.cleanarchitecture.entities.Word
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * WordRoomDatabase - the database class which provides access to each of the DAOs
 * @see RoomDatabase
 * @author KevBotStudios (c) 2020
 */
@Database(entities = arrayOf(Word::class), version = 1, exportSchema = false)
abstract class WordRoomDatabase: RoomDatabase() {

    abstract fun wordDao(): WordDao

    companion object {
        @Volatile
        private var instance: WordRoomDatabase? = null

        /**
         * getDatabase - allows access to the database
         * @param context - the context the application is running in
         * @param scope - the scope to launch the population of the database
         * @return the database instance
         */
        fun getDatabase(context: Context, scope: CoroutineScope): WordRoomDatabase {
            synchronized(this) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.applicationContext,
                        WordRoomDatabase::class.java, "word_database").addCallback(WordDatabaseCallback(scope)).build()
                }

                return instance!!
            }
        }
    }

    /**
     * WordDatabaseCallback - adds a callback to the database to override what happens on open. In
     *                      this case, we are deleting everything from the prior session
     * @param scope - the scope which the coroutine will be launched in
     * @see RoomDatabase.Callback
     */
    private class WordDatabaseCallback(private val scope: CoroutineScope): RoomDatabase.Callback() {
        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)

            instance?.let { database ->
                scope.launch {
                    populateDatabase(database.wordDao())
                }
            }
        }

        /**
         * populateDatabase - on open just add new session
         */
        suspend fun populateDatabase(wordDao: WordDao) {
            //wordDao.deleteAll() // If I want to start fresh every session uncomment
            wordDao.insert(Word("New Session"))
        }
    }
}