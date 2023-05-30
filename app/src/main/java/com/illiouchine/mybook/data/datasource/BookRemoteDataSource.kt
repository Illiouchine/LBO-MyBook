package com.illiouchine.mybook.data.datasource

import retrofit2.http.GET
import retrofit2.http.Query

interface BookRemoteDataSource {

    //https://www.googleapis.com/books/v1/volumes?q=Dune+inauthor:Herbert
    @GET("books/v1/volumes")
    suspend fun searchBook(@Query("inauthor") author: String, @Query("q") title: String): BookResult
    data class BookResult(
        val totalItems: Int,
        val items: List<Book>
    )
    data class Book(
        val volumeInfo: VolumeInfo
    )
    data class VolumeInfo(
        val title: String,
        val authors: List<String>,
        val publishedDate: String,
        val description: String?,
        val imageLinks: ImageLink?,
    )
    data class ImageLink(val thumbnail: String?)
}

/*
{
  "kind": "books#volumes",
  "totalItems": 327,
  "items": [
    {
      "kind": "books#volume",
      "id": "6FMHzgEACAAJ",
      "etag": "JaMorT24Nsg",
      "selfLink": "https://www.googleapis.com/books/v1/volumes/6FMHzgEACAAJ",
      "volumeInfo": {
        "title": "Dune #01 Éd. Collector",
        "authors": [
          "Frank Herbert"
        ],
        "publishedDate": "2020",
        "description": "Dans des mondes futurs, Atréides et Harkonnens se disputent l'exploitation de l'épice sur Dune, planète hostile peuplée de Fremens et d'immenses vers attirés par les impulsions des moissonneuses. Alors que les cruels Harkonnens complotent avec l'empereur, Paul Atréides, le fils du duc Leto, semble être le messie attendu par les Fremens.",
        "pageCount": 689,
        "printType": "BOOK",
        "maturityRating": "NOT_MATURE",
        "allowAnonLogging": false,
        "contentVersion": "preview-1.0.0",
        "panelizationSummary": {
          "containsEpubBubbles": false,
          "containsImageBubbles": false
        },
        "imageLinks": {
          "smallThumbnail": "http://books.google.com/books/content?id=6FMHzgEACAAJ&printsec=frontcover&img=1&zoom=5&source=gbs_api",
          "thumbnail": "http://books.google.com/books/content?id=6FMHzgEACAAJ&printsec=frontcover&img=1&zoom=1&source=gbs_api"
        },
        "language": "fr",
        "previewLink": "http://books.google.fr/books?id=6FMHzgEACAAJ&dq=Dune+inauthor:Herbert&hl=&cd=1&source=gbs_api",
        "infoLink": "http://books.google.fr/books?id=6FMHzgEACAAJ&dq=Dune+inauthor:Herbert&hl=&source=gbs_api",
        "canonicalVolumeLink": "https://books.google.com/books/about/Dune_01_%C3%89d_Collector.html?hl=&id=6FMHzgEACAAJ"
      },
      "saleInfo": {
        "country": "FR",
        "saleability": "NOT_FOR_SALE",
        "isEbook": false
      },
      "accessInfo": {
        "country": "FR",
        "viewability": "NO_PAGES",
        "embeddable": false,
        "publicDomain": false,
 */