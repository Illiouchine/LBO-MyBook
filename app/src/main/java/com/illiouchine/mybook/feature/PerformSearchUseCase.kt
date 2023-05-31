package com.illiouchine.mybook.feature

import com.illiouchine.mybook.feature.datagateway.entities.SearchResultEntity

interface PerformSearchUseCase{

    suspend operator fun invoke(author: String, title:String): SearchResultEntity


}
