package com.wooyj.ordermenu.data.remote.source

import com.wooyj.datalayer.data.remote.api.DogApi
import com.wooyj.ordermenu.data.source.DogDataSource
import javax.inject.Inject

class DogRemoteDataSource
    @Inject
    constructor(
        private val dogApi: DogApi,
    ) : DogDataSource {
        override fun getDogList(): List<String> = dogApi.getBreedList()

        override fun getDogImageList(breed: String): List<String> {
            TODO("Not yet implemented")
        }

        override fun getDogImageList(
            breed: String,
            subBreed: String,
        ): List<String> {
            TODO("Not yet implemented")
        }

        override fun getDogImage(
            breed: String,
            subBreed: String,
            image: String,
        ): String {
            TODO("Not yet implemented")
        }
    }
