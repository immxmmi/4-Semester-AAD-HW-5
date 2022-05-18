package at.technikum.if20b231.newslist.db

import androidx.lifecycle.LiveData

class Repository(private val pageEntryDao: PageEntryDao) {
    val entries : LiveData<List<PageEntry>> = pageEntryDao.pageEntries

    suspend fun insert(pageEntry: PageEntry) {
        pageEntryDao.insert(pageEntry)
    }

    suspend fun update(pageEntry: PageEntry){
        pageEntryDao.update(pageEntry)
    }

    suspend fun delete() {
        pageEntryDao.delete()
    }
}