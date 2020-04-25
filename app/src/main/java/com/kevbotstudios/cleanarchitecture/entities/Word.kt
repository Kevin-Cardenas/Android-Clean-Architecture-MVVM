package com.kevbotstudios.cleanarchitecture.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Word - class which describes an Entity (SQLite table)
 * @author KevBotStudios (c) 2020
 */
@Entity(tableName = "word_table")
data class Word(@PrimaryKey @ColumnInfo(name = "word") val word: String)