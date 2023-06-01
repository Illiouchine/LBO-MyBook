package com.illiouchine.mybook.di

import android.content.Context
import androidx.room.Room
import com.google.gson.GsonBuilder
import com.illiouchine.mybook.data.BookDataMapper
import com.illiouchine.mybook.data.datasource.BookLocalDataSource
import com.illiouchine.mybook.data.datasource.BookRemoteDataSource
import com.illiouchine.mybook.data.room.AppDatabase
import com.illiouchine.mybook.feature.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    fun provideGetBookByIdUseCase(
        bookDataMapper: BookDataMapper
    ): GetBookByIdUseCase {
        return GetBookByIdUseCaseImpl(bookDataMapper)
    }

    @Singleton
    @Provides
    fun provideMyLibUseCase(
        bookDataMapper: BookDataMapper
    ): GetMyLibUseCase {
        return GetMyLibUseCaseImpl(bookDataMapper)
    }

    @Singleton
    @Provides
    fun provideLikeUseCase(
        bookDataMapper: BookDataMapper
    ): AddBookToLikedUseCase {
        return AddBookToLikedUseCaseImpl(bookDataMapper)
    }

    @Singleton
    @Provides
    fun provideUnlikeUseCase(
        bookDataMapper: BookDataMapper
    ): RemoveBookToLikedUseCase {
        return RemoveBookToLikedUseCaseImpl(bookDataMapper)
    }

    @Singleton
    @Provides
    fun provideGetSearchUseCase(
        bookDataMapper: BookDataMapper
    ): GetSearchUseCase {
        return GetSearchUseCaseImpl(bookDataMapper)
    }

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
class BookDataModule {
    @Singleton
    @Provides
    fun provideBookDataMapper(
        bookRemoteDataSource: BookRemoteDataSource,
        bookLocalDataSource: BookLocalDataSource
    ): BookDataMapper {
        return BookDataMapper(
            bookRemoteDataSource, bookLocalDataSource
        )
    }

    @Singleton
    @Provides
    fun provideBookLocalDataSource(
        appDatabase: AppDatabase
    ): BookLocalDataSource = appDatabase.bookDao()
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

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room
            .databaseBuilder(
                appContext,
                AppDatabase::class.java,
                "appDatabase"
            )
            .build()
    }
}

