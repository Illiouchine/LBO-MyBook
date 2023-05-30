package com.illiouchine.mybook.di

import com.illiouchine.mybook.feature.PerformSearchUseCase
import com.illiouchine.mybook.feature.PerformSearchUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Singleton
    @Provides
    fun provideSearchUseCase(): PerformSearchUseCase {
        return PerformSearchUseCaseImpl()
    }
}