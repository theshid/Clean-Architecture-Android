package com.example.ecomkt.app.di

import android.content.Context
import com.example.ecomkt.app.util.Session
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SessionModule {

    @Provides
    @Singleton
    fun provideSessionManager(@ApplicationContext context: Context): Session {
        return Session(context)
    }
}