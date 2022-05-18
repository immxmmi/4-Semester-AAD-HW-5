package at.technikum.if20b231.newslist.handler

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.widget.ImageView
import androidx.core.content.PackageManagerCompat.LOG_TAG
import com.bumptech.glide.Glide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

class DownloadImage {
    companion object {
        private val LOG_TAG: String = DownloadImage::class.java.simpleName
    }

    private var imageView : ImageView? = null

    private fun test( urlString: String){

    }

    private suspend fun getImage() {
        val image = withContext(Dispatchers.IO) {
            downloadImage("https://source.android.com/setup/images/Android_symbol_green_RGB.png")
        }
        imageView?.setImageBitmap(image)
    }
    // Download code
    private fun downloadImage(urlString: String): Bitmap? {
        val url = URL(urlString)
        var urlConnection: HttpURLConnection? = null
        return try {
            urlConnection = url.openConnection() as HttpURLConnection
            urlConnection.connectTimeout = 5000
            val statusCode: Int = urlConnection.responseCode
            if (statusCode != 200) {
                Log.e(LOG_TAG, "Error downloading image from $url. Response code: $statusCode")
                return null
            }
            val inputStream = urlConnection.getInputStream()
            if (inputStream == null) {
                Log.e(LOG_TAG, "Error downloading image from $url")
                return null
            }
            return BitmapFactory.decodeStream(inputStream)
        } catch (ex: MalformedURLException) {
            Log.e(LOG_TAG, "Malformed url", ex)
            return null
        } catch (ex: IOException) {
            Log.e(LOG_TAG, "Error downloading image from $url", ex)
            null
        } finally {
            urlConnection?.disconnect()
        }!!
    }
}