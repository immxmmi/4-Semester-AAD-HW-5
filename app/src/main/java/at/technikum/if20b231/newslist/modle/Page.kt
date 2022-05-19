package at.technikum.if20b231.newslist.modle

import java.util.*

data class Page(
        var id: String,
        var title: String,
        var author: String?,
        var description: String?,
        var pubDate: Date?,
        var imageURL: String?,
        var articleURL: String?,
        var keywords: Set<String>?
)
