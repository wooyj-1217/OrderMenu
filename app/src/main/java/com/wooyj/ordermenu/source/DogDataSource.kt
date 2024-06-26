package com.wooyj.ordermenu.source

import com.wooyj.ordermenu.data.remote.dto.BreedListDTO
import com.wooyj.ordermenu.data.remote.dto.DogImageListDTO

interface DogDataSource {
    suspend fun getDogList(): List<BreedListDTO>

    suspend fun getDogImageList(breedName: String): List<DogImageListDTO>

    suspend fun getDogImageList(
        breedName: String,
        subBreedName: String,
    ): List<DogImageListDTO>
}
