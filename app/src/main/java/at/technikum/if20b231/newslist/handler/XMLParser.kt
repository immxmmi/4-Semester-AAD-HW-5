package at.technikum.if20b231.newslist.handler

import android.util.Xml
import at.technikum.if20b231.newslist.modle.Page
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException
import java.io.InputStream
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class XMLParser() {
    private val ns: String? = null
    private val formatter: DateFormat? = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz")

    @Throws(XmlPullParserException::class, IOException::class)
    // Parser -> RETURN LIST OF PAGES
    fun parse(inputStream: InputStream): List<Page> {
        inputStream.use { inputStream ->
            val parser: XmlPullParser = Xml.newPullParser()
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
            parser.setInput(inputStream, null)
            parser.nextTag()
            return readRss(parser)
        }
    }

    // RSS
    @Throws(XmlPullParserException::class, IOException::class)
    private fun readRss(parser: XmlPullParser): List<Page> {
        var entries = mutableListOf<Page>()

        parser.require(XmlPullParser.START_TAG, ns, "rss")
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.eventType != XmlPullParser.START_TAG) {
                continue
            }
            // CHANNEL
            if (parser.name == "channel") {
                entries = readChannel(parser) as MutableList<Page>
            } else {
                skip(parser)
            }
        }
        return entries
    }

    // CHANNEL
    private fun readChannel(parser: XmlPullParser): List<Page> {
        val entries = mutableListOf<Page>()

        parser.require(XmlPullParser.START_TAG, ns, "channel")
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.eventType != XmlPullParser.START_TAG) {
                continue
            }
            // ITEM
            if (parser.name == "item") {
                entries.add(readItem(parser))
            } else {
                skip(parser)
            }
        }
        return entries
    }

    // READ - ITEM
    @Throws(XmlPullParserException::class, IOException::class)
    private fun readItem(parser: XmlPullParser): Page {
        parser.require(XmlPullParser.START_TAG, ns, "item")
        var id: String? = null
        var title: String? = null
        var creator: String? = null
        var description: String? = null
        var pubDate: Date? = null
        var articleURL: String? = null
        var imageURL: String? = null
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.eventType != XmlPullParser.START_TAG) {
                continue
            }
            when (parser.name) {
                "guid" -> id = readId(parser)
                "title" -> title = readTitle(parser)
                "dc:creator" -> creator = readCreator(parser)
                "link" -> articleURL = readLink(parser)
                "description" -> description = readDescription(parser)
                "pubDate" -> pubDate = readDate(parser)
                "media:content" -> imageURL = readMedia(parser)
                else -> skip(parser)
            }
        }

        return Page(id, title, creator, description,pubDate, imageURL,articleURL)
    }

    // ID TAG
    @Throws(IOException::class, XmlPullParserException::class)
    private fun readId(parser: XmlPullParser): String {
        parser.require(XmlPullParser.START_TAG, ns, "guid")
        val id = readText(parser)
        parser.require(XmlPullParser.END_TAG, ns, "guid")
        return id
    }

    // TITLE TAG
    @Throws(IOException::class, XmlPullParserException::class)
    private fun readTitle(parser: XmlPullParser): String {
        parser.require(XmlPullParser.START_TAG, ns, "title")
        val title = readText(parser)
        parser.require(XmlPullParser.END_TAG, ns, "title")
        return title
    }

    // LINK TAG
    @Throws(IOException::class, XmlPullParserException::class)
    private fun readLink(parser: XmlPullParser): String {
        parser.require(XmlPullParser.START_TAG, ns, "link")
        val title = readText(parser)
        parser.require(XmlPullParser.END_TAG, ns, "link")
        return title
    }

    // DATE TAG
    @Throws(IOException::class, XmlPullParserException::class)
    private fun readDate(parser: XmlPullParser): Date? {
        parser.require(XmlPullParser.START_TAG, ns, "pubDate")
        val date = formatter?.parse(readText(parser))
        parser.require(XmlPullParser.END_TAG, ns, "pubDate")
        return date
    }

    // Description TAG
    @Throws(IOException::class, XmlPullParserException::class)
    private fun readDescription(parser: XmlPullParser): String {
        parser.require(XmlPullParser.START_TAG, ns, "description")
        val description = readText(parser)
        parser.require(XmlPullParser.END_TAG, ns, "description")
        return description
    }

    // CREATOR TAG
    @Throws(IOException::class, XmlPullParserException::class)
    private fun readCreator(parser: XmlPullParser): String {
        parser.require(XmlPullParser.START_TAG, ns, "dc:creator")
        val creator = readText(parser)
        parser.require(XmlPullParser.END_TAG, ns, "dc:creator")
        return creator
    }

    // MEDIA TAG
    @Throws(IOException::class, XmlPullParserException::class)
    private fun readMedia(parser: XmlPullParser): String {
        var link = parser.getAttributeValue(null, "url").toString()
        parser.require(XmlPullParser.START_TAG, ns, "media:content")
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.eventType != XmlPullParser.START_TAG) {
                continue
            }
            if (parser.name == "url") {
                link = parser.getAttributeValue(null, "url").toString()
            } else {
                skip(parser)
            }
        }

        return link
    }

    // Text TAG
    @Throws(IOException::class, XmlPullParserException::class)
    private fun readText(parser: XmlPullParser): String {
        var result = ""
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.text
            parser.nextTag()
        }
        return result
    }

    // SKIP
    @Throws(XmlPullParserException::class, IOException::class)
    private fun skip(parser: XmlPullParser) {
        if (parser.eventType != XmlPullParser.START_TAG) {
            throw IllegalStateException()
        }
        var depth = 1
        while (depth != 0) {
            when (parser.next()) {
                XmlPullParser.END_TAG -> depth--
                XmlPullParser.START_TAG -> depth++
            }
        }
    }
}