package at.technikum.if20b231.newslist.viewmodel

import android.app.Application
import android.database.sqlite.SQLiteConstraintException
import androidx.lifecycle.*
import at.technikum.if20b231.newslist.R
import at.technikum.if20b231.newslist.db.ApplicationDatabase
import at.technikum.if20b231.newslist.db.PageEntry
import at.technikum.if20b231.newslist.db.PageEntryDao
import at.technikum.if20b231.newslist.db.Repository
import at.technikum.if20b231.newslist.handler.NewsDownloader
import at.technikum.if20b231.newslist.modle.Page
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException
import java.lang.Exception

class NewsListViewModel(application: Application) : AndroidViewModel(application) {

    // URL
    val url = MutableLiveData<String>("https://www.engadget.com/rss.xml")

    //IMG - SETTINGS
    val imageShow: MutableLiveData<Boolean> = MutableLiveData(true)
    val imageDownload: MutableLiveData<Boolean> = MutableLiveData(true)

    //DATABASE
    private val repository: Repository
    lateinit var pageEnteries: LiveData<List<PageEntry>>
    val pageEntryDao: PageEntryDao
    private val pageItemList by lazy { MutableLiveData<List<Page>>(listOf()) }

    //DATABASE ERROR
    val dbError by lazy { MutableLiveData<String?>(null) }


    //INIT
    init {
        pageEntryDao = ApplicationDatabase.getDatabase(application).entryDao()
        repository = Repository(pageEntryDao)

        reload()

    }


    // SETTINGS - IMG / URL
    fun updateImageShow(show: Boolean) {
        imageShow.value = show;
    }
    fun updateUrl(newUrl: String) {
        url.value = newUrl
    }







    private val pages: MutableLiveData<List<Page>> by lazy {
        MutableLiveData<List<Page>>().also {
            loadPages(it)
        }
    }
    val load: LiveData<List<Page>>
        get() = pages




    // SORTER
    private fun orderListByDate(pages: List<Page>): List<Page> {
        return pages.sortedBy { it.pubDate }
    }
    private fun orderListByAuthor(pages: List<Page>): List<Page> {
        return pages.sortedBy { it.author }
    }
    private fun orderListByTitle(pages: List<Page>): List<Page> {
        return pages.sortedBy { it.title }
    }


    // DATABASE
    fun insert() = viewModelScope.launch {
        dbError.value = null
        if (pageItemList.value == null) {
            dbError.value =
                getApplication<Application>().applicationContext.getString(R.string.no_data)
        } else {

            try {
                for (page in pageItemList.value!!) {
                    val currentPage = PageEntry(
                        page.id,
                        page.title,
                        page.author,
                        page.description,
                        page.pubDate.toString(),
                        page.imageURL,
                        page.articleURL,
                        page.keywords!!.joinToString(",")
                    )
                    withContext(Dispatchers.IO) {
                        repository.insert(currentPage)
                    }
                }


            } catch (ex: SQLiteConstraintException) {
                dbError.value =
                    getApplication<Application>().applicationContext.getString(R.string.exists_already)
            } catch (ex: Exception) {
                dbError.value =
                    getApplication<Application>().applicationContext.getString(R.string.general_error)
            }


        }

    }

    // DB - DELETE
    fun delete() = viewModelScope.launch {
        dbError.value = null

        try {
            withContext(Dispatchers.IO) {
                repository.delete()
            }
        } catch (ex: Exception) {
            dbError.value =
                getApplication<Application>().applicationContext.getString(R.string.general_error)
        }
    }



    // DOWNLOAD - NEWS
    private fun downloadNews(url : String){
        pageItemList.value = listOf()
        viewModelScope.launch {
             val value = loadXmlFromNetwork(url)
            if(value == null){

            }else{
                pageItemList.value = value
                insert()
            }

        }
    }


    //RELOAD RESULT
    fun reload() {
        downloadNews(url.value.toString())
        pageEnteries = repository.entries
        //loadPages(pages)
    }

    // LOAD
    private fun loadPages(liveData: MutableLiveData<List<Page>>) {
        viewModelScope.launch {
            liveData.value = loadWebResult()?.let { orderListByDate(it) }
        }
    }
    //LOAD RESULT
    private suspend fun loadWebResult(): List<Page>? {
        return loadXmlFromNetwork(url.value.toString())
    }




}

@Throws(XmlPullParserException::class, IOException::class)
private suspend fun loadXmlFromNetwork(urlString: String): List<Page>? {
    return NewsDownloader().load(urlString)
}

