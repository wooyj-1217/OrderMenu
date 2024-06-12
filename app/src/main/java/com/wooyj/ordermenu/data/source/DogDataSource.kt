package com.wooyj.ordermenu.data.source

interface DogDataSource {
    fun getDogList(): List<String>

    fun getDogImageList(breed: String): List<String>

    fun getDogImageList(
        breed: String,
        subBreed: String,
    ): List<String>

    fun getDogImage(
        breed: String,
        subBreed: String,
        image: String,
    ): String
}
