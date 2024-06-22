package com.wooyj.ordermenu.source

import com.wooyj.ordermenu.data.remote.dto.BreedListDTO
import com.wooyj.ordermenu.data.remote.dto.DogImageListDTO
import retrofit2.Response

interface DogDataSource {
    suspend fun getDogList(): List<BreedListDTO>

    suspend fun getDogImageList(breedName: String): Response<List<DogImageListDTO>>

    suspend fun getDogImageList(
        breedName: String,
        subBreedName: String,
    ): Response<List<DogImageListDTO>>
}
