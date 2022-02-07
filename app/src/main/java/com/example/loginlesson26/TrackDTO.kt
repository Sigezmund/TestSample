package com.example.loginlesson26
import com.google.gson.annotations.SerializedName

data class TrackDTO(
    @SerializedName("tracks")
    val tracks: Tracks? = null
)

data class Tracks(
//    @SerializedName("@attr")
//    val attr: Attr? = null,
    @SerializedName("track")
    val track: List<Track>? = null
)

//data class Attr(
//    @SerializedName("page")
//    val page: String? = null,
//    @SerializedName("perPage")
//    val perPage: String? = null,
//    @SerializedName("total")
//    val total: String? = null,
//    @SerializedName("totalPages")
//    val totalPages: String? = null
//)

data class Track(
    @SerializedName("artist")
    val artist: Artist? = null,
//    @SerializedName("duration")
//    val duration: String? = null,
    @SerializedName("image")
    val image: List<Image>? = null,
//    @SerializedName("listeners")
//    val listeners: String? = null,
//    @SerializedName("mbid")
//    val mbid: String? = null,
    @SerializedName("name")
    val name: String? = null
//    @SerializedName("playcount")
//    val playcount: String? = null,
//    @SerializedName("streamable")
//    val streamable: Streamable? = null,
//    @SerializedName("url")
//    val url: String? = null
)

data class Artist(
//    @SerializedName("mbid")
//    val mbid: String? = null,
    @SerializedName("name")
    val name: String? = null,
//    @SerializedName("url")
//    val url: String? = null
)

data class Image(
    @SerializedName("size")
    val size: String? = null,
    @SerializedName("#text")
    val text: String? = null
)

//data class Streamable(
//    @SerializedName("fulltrack")
//    val fulltrack: String? = null,
//    @SerializedName("#text")
//    val text: String? = null
//)