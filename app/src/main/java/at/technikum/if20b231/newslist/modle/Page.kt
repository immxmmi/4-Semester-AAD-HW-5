package at.technikum.if20b231.newslist.modle

import java.util.*

data class Page(
        var id: String? = null,
        var title: String? = null,
        var author: String? = null,
        var description: String? = null,
        var pubDate: Date? = null,
        var imageURL: String? = null,
        var articleURL: String? = null,
        var keywords: Set<String>? = null
)
