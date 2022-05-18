package at.technikum.if20b231.newslist.db

import androidx.annotation.NonNull
import androidx.room.*


@Entity(tableName = "PageEntry", indices = [Index(value = ["id", "title", "author", "description", "pubDate", "imageURL", "articleURL", "keywords"], unique = true)])
data class PageEntry(
    @PrimaryKey @ColumnInfo var id: String?,
    @ColumnInfo @NonNull var title: String?,
    @ColumnInfo var author: String?,
    @ColumnInfo var description: String?,
    @ColumnInfo var pubDate: String,
    @ColumnInfo var imageURL: String?,
    @ColumnInfo var articleURL: String?,
    @ColumnInfo var keywords: String?,
)