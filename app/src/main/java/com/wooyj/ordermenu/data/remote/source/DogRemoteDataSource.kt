package com.wooyj.ordermenu.data.remote.source

import com.wooyj.ordermenu.data.remote.dto.BreedListDTO
import com.wooyj.ordermenu.data.remote.dto.DogImageListDTO
import com.wooyj.ordermenu.data.remote.service.DogApiService
import com.wooyj.ordermenu.source.DogDataSource
import timber.log.Timber
import javax.inject.Inject

class DogRemoteDataSource
    @Inject
    constructor(
        private val service: DogApiService,
    ) : DogDataSource {
        override suspend fun getDogList(): List<BreedListDTO> =
            service
                .getBreedList()
                .onFailure {
                    Timber.d("Failed to get dog list")
                }.fold(
                    onSuccess = { it },
                    onFailure = { emptyList() },
                )
        override suspend fun getDogImageList(breedName: String): List<DogImageListDTO> =
            service
                .getDogImageList(breedName)
                .onFailure {
                    Timber.d("Failed to get dog image list : $breedName")
                }.fold(
                    onSuccess = { it },
                    onFailure = { emptyList() },
                )

        override suspend fun getDogImageList(
            breedName: String,
            subBreedName: String,
        ): List<DogImageListDTO> =
            service
                .getDogImageList(breedName, subBreedName)
                .onFailure {
                    Timber.d("Failed to get dog image list : $breedName / $subBreedName")
                }.fold(
                    onSuccess = { it },
                    onFailure = { emptyList() },
                )
    }
