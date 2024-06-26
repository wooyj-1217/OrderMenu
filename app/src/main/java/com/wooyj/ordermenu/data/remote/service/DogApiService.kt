package com.wooyj.ordermenu.data.remote.service

import com.wooyj.ordermenu.data.remote.dto.BreedListDTO
import com.wooyj.ordermenu.data.remote.dto.DogImageListDTO
import retrofit2.http.GET
import retrofit2.http.Path

/**
 *
 * Dog API 기반으로 연동
 * @see <a href="https://dog.ceo/dog-api/">Dog API</a>
 *
 */

interface DogApiService {
    @GET("/breeds/list/all")
    suspend fun getBreedList(): Result<List<BreedListDTO>>

    @GET("/breed/{breedName}/images")
    suspend fun getDogImageList(
        @Path("breedName") breedName: String,
    ): Result<List<DogImageListDTO>>

    @GET("/breed/{breedName}/{subBreedName}/images")
    suspend fun getDogImageList(
        @Path("breedName") breedName: String,
        @Path("subBreedName") subBreedName: String,
    ): Result<List<DogImageListDTO>>
}
