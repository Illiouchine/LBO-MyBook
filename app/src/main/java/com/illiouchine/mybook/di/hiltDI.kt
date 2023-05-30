package com.illiouchine.mybook.di

import com.google.gson.GsonBuilder
import com.illiouchine.mybook.data.BookDataMapper
import com.illiouchine.mybook.data.datasource.BookRemoteDataSource
import com.illiouchine.mybook.feature.PerformSearchUseCase
import com.illiouchine.mybook.feature.PerformSearchUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Singleton
    @Provides
    fun provideSearchUseCase(
        bookDataMapper: BookDataMapper
    ): PerformSearchUseCase {
        return PerformSearchUseCaseImpl(bookDataMapper)
    }
}

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideRetrofit(): BookRemoteDataSource =
        Retrofit.Builder()
            .baseUrl("https://www.googleapis.com/")
            .client(
                OkHttpClient
                    .Builder()
                    .build()
            )
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder().setLenient().create()
                )
            )
            .build()
            .create(BookRemoteDataSource::class.java)
}
