package com.wooyj.ordermenu.data.remote.source

import com.wooyj.ordermenu.data.remote.dto.BreedListDTO
import com.wooyj.ordermenu.data.remote.dto.DogImageListDTO
import com.wooyj.ordermenu.data.remote.service.DogApiService
import com.wooyj.ordermenu.source.DogDataSource
import retrofit2.Response
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
                .map {
                    it.map {
                        BreedListDTO(it.key, it.value)
                    }
                }.onFailure {
                    Timber.d("Failed to get dog list")
                }.fold(
                    onSuccess = { it },
                    onFailure = { emptyList() },
                )

        override suspend fun getDogImageList(breedName: String): Response<List<DogImageListDTO>> = service.getDogImageList(breedName)

        override suspend fun getDogImageList(
            breedName: String,
            subBreedName: String,
        ): Response<List<DogImageListDTO>> {
            val response = service.getDogImageList(breedName, subBreedName)

            if (response.isSuccessful) {
                return response.body() ?: emptyList()
            } else {
                throw Exception("Failed to get dog image list")
            }
        }
    }
