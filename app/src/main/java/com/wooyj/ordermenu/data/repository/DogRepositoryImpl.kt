package com.wooyj.ordermenu.data.repository

import com.wooyj.ordermenu.data.remote.dto.BreedListDTO
import com.wooyj.ordermenu.data.source.DogDataSource
import com.wooyj.ordermenu.domain.repository.DogRepository
import javax.inject.Inject

class DogRepositoryImpl
    @Inject
    constructor(
        private val remoteDataSource: DogDataSource,
    ) : DogRepository {
        override suspend fun getBreedList(): List<BreedListDTO> = remoteDataSource.getDogList()
    }
