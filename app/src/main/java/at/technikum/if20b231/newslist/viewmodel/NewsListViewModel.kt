package at.technikum.if20b231.newslist.viewmodel

import androidx.compose.runtime.MutableState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.technikum.if20b231.newslist.handler.NewsDownloader
import at.technikum.if20b231.newslist.handler.XMLParser
import at.technikum.if20b231.newslist.modle.Page
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class NewsListViewModel : ViewModel() {

    val imageShow : MutableLiveData<Boolean> = MutableLiveData(true)
    val imageDownload : MutableLiveData<Boolean> = MutableLiveData(true)
    val url = MutableLiveData<String>("https://www.engadget.com/rss.xml")
   // val url = "https://www.engadget.com/rss.xml"


    fun updateImageShow(show: Boolean){
        imageShow.value = show;
    }

    fun updateUrl(newUrl: String){
        url.value = newUrl
    }

    private val pages : MutableLiveData<List<Page>> by lazy {
        MutableLiveData<List<Page>>().also {
            loadPages(it)
        }
    }

    val load:LiveData<List<Page>>
        get() = pages


    // LOAD
    private fun loadPages(liveData: MutableLiveData<List<Page>>) {
        viewModelScope.launch {
            liveData.value = loadWebResult()?.let { orderListByDate(it) }
        }
    }

    // SORTER
     private fun orderListByDate (pages:List<Page>): List<Page> {
        return pages.sortedBy { it.pubDate }
    }

     private fun orderListByAuthor (pages:List<Page>): List<Page> {
        return pages.sortedBy { it.author }
    }

     private fun orderListByTitle(pages:List<Page>): List<Page> {
        return pages.sortedBy { it.title }
    }

    //LOAD RESULT
    private suspend fun loadWebResult(): List<Page>? {
         return   loadXmlFromNetwork(url.value.toString())
    }

    //RELOAD RESULT
     fun reload(){
         loadPages(pages)
    }

}

@Throws(XmlPullParserException::class, IOException::class)
private suspend fun loadXmlFromNetwork(urlString: String): List<Page>? {
    return NewsDownloader().load(urlString)
}

