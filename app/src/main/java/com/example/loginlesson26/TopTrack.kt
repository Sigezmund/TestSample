package com.example.loginlesson26

data class TopTrack(
    val artist: Artist? = null,
    val duration: String? = null,
    val image: List<Image>? = null,
    val listeners: String? = null,
    val mbid: String? = null,
    val name: String? = null,
    val playcount: String? = null,
    val streamable: Streamable? = null,
    val url: String? = null
)