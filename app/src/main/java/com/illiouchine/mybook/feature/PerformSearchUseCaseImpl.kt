package com.illiouchine.mybook.feature

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PerformSearchUseCaseImpl @Inject constructor(

): PerformSearchUseCase {

    override fun invoke(author: String, title: String): Flow<PerformSearchUseCase.SearchResult> {
        return flow {
            emit(PerformSearchUseCase.SearchResult.Error)
        }
    }

}


