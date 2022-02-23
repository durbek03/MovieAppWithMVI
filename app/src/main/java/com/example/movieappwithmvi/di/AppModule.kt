package com.example.movieappwithmvi.di

import android.content.Context
import androidx.room.Room
import com.example.movieappwithmvi.constants.Constants
import com.example.movieappwithmvi.data.locale.AppDatabase
import com.example.movieappwithmvi.data.remote.ApiService
import com.example.movieappwithmvi.domain.remote.ApiImpl
import com.example.movieappwithmvi.domain.remote.ApiRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.FragmentScoped
import dagger.hilt.android.scopes.ViewScoped
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "moviedatabase")
            .fallbackToDestructiveMigration()
            .build()
    }
}