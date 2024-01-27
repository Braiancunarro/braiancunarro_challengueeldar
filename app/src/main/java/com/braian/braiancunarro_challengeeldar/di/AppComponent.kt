package com.braian.braiancunarro_challengeeldar.di

import com.braian.braiancunarro_challengeeldar.App
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun inject(application: App)

    @Component.Builder
    interface Builder {
        fun build(): AppComponent
    }
}
