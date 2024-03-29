package at.technikum.if20b231.newslist.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PageEntryDao {
    @get:Query("SELECT * FROM pageentry ORDER BY pubDate DESC")
    val pageEntries : LiveData<List<PageEntry>>

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(pageEntry: PageEntry)

    @Update
    suspend fun update(pageEntry: PageEntry)

    @Query("DELETE FROM pageentry")
    suspend fun delete()
}
