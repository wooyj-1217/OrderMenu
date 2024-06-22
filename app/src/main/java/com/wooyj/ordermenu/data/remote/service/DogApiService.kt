package com.wooyj.ordermenu.data.remote.service

import com.wooyj.ordermenu.data.remote.api.ApiUrl
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
    @GET(ApiUrl.API_BREED_LIST)
    suspend fun getBreedList(): Result<List<BreedListDTO>>

    @GET(ApiUrl.API_BREED_IMAGE_LIST)
    suspend fun getDogImageList(
        @Path("breedName") breedName: String,
    ): Result<List<DogImageListDTO>>

    @GET(ApiUrl.API_SUBBREED_IMAGE_LIST)
    suspend fun getDogImageList(
        @Path("breedName") breedName: String,
        @Path("subBreedName") subBreedName: String,
    ): Result<List<DogImageListDTO>>
}
