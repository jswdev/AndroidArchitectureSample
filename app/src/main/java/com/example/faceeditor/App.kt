package com.example.faceeditor

import android.app.Application
import com.example.faceeditor.extension.RemoteDataSource
import com.example.faceeditor.models.RealmDB
import com.example.faceeditor.models.RemoteManager
import com.example.faceeditor.models.RemoteService
import com.google.gson.GsonBuilder
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
class App: Application() {

    private var app: App? = null

    override fun onCreate() {
        super.onCreate()

        app = this

        dbManger = RealmDB(this)

        retrofit = Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        remoteManager = RemoteManager(retrofit)
    }

    companion object {
        @JvmStatic
        lateinit var app: App
            private set

        @JvmStatic
        lateinit var dbManger: RealmDB
            private set

        @JvmStatic
        lateinit var retrofit: Retrofit
            private set

        @JvmStatic
        lateinit var remoteManager: RemoteManager
            private set

    }
}