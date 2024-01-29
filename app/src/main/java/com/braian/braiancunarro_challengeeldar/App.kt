package com.braian.braiancunarro_challengeeldar

import android.app.Application
import androidx.room.Room
import com.braian.braiancunarro_challengeeldar.data.local.AppDatabase
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        MyApp.database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "my-database"
        ).fallbackToDestructiveMigration().build()

    }
}
