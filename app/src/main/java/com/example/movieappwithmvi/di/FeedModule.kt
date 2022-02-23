package com.example.movieappwithmvi.di

import com.example.movieappwithmvi.data.locale.AppDatabase
import com.example.movieappwithmvi.data.remote.ApiService
import com.example.movieappwithmvi.domain.locale.DatabaseImpl
import com.example.movieappwithmvi.domain.locale.DatabaseRepository
import com.example.movieappwithmvi.domain.remote.ApiImpl
import com.example.movieappwithmvi.domain.remote.ApiRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.components.ViewComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.FragmentScoped
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.android.scopes.ViewScoped

@Module
@InstallIn(ViewModelComponent::class)
object FeedModule {

    @Provides
    @ViewModelScoped
    fun provideDatabaseRep(appDatabase: AppDatabase): DatabaseRepository {
        return DatabaseImpl(appDatabase)
    }

    @Provides
    @ViewModelScoped
    fun provideApiRep(apiService: ApiService): ApiRepository {
        return ApiImpl(apiService)
    }
}