package com.wooyj.ordermenu.data.remote.api

class ApiUrl {
    companion object {
        const val API_BREED_LIST = "breeds/list/all"
        const val API_BREED_IMAGE_LIST = "breed/{breedName}/images"
        const val API_SUBBREED_IMAGE_LIST = "breed/{breedName}/{subBreedName}/images"
    }
}
