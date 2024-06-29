package com.admqueiroga.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "tmdb_session")


object SessionManager {
    private val TOKEN_KEY = stringPreferencesKey("tmdb_session_token")

    context(Context)
    suspend fun readToken(): String? {
        val preferences = dataStore.data.firstOrNull()
        return preferences?.get(TOKEN_KEY)
    }

    context(Context)
    suspend fun writeToken(token: String) {
        dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = token
        }
    }

    context(Context)
    fun observeTokenChanges(): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[TOKEN_KEY]
        }.flowOn(Dispatchers.IO)
    }

}