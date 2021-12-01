package com.sinapsis.vinilos.models

data class Album(

    val albumId: Int?=0,
    val name: String="",
    val cover: String="",
    val releaseDate: String="",
    val description: String="",
    val genre: String="",
    val recordLabel: String=""
) {
   constructor() : this(0,"","","","","")
}

