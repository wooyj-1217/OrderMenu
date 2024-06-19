package com.wooyj.ordermenu.data.remote.source

import com.wooyj.ordermenu.data.remote.dto.BreedListDTO
import com.wooyj.ordermenu.data.remote.dto.DogImageListDTO
import com.wooyj.ordermenu.data.remote.service.DogApiService
import com.wooyj.ordermenu.source.DogDataSource
import retrofit2.Response
import javax.inject.Inject

class DogRemoteDataSource
    @Inject
    constructor(
        private val service: DogApiService,
    ) : DogDataSource {
        override suspend fun getDogList(): Response<List<BreedListDTO>> = service.getBreedList()

        override suspend fun getDogImageList(breedName: String): Response<List<DogImageListDTO>> = service.getDogImageList(breedName)

        override suspend fun getDogImageList(
            breedName: String,
            subBreedName: String,
        ): Response<List<DogImageListDTO>> = service.getDogImageList(breedName, subBreedName)
    }
