package at.technikum.if20b231.newslist.screens

import android.content.Intent
import android.net.Uri
import android.widget.TextView
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Image
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.HtmlCompat
import androidx.navigation.NavController
import at.technikum.if20b231.newslist.R
import at.technikum.if20b231.newslist.handler.HtmlText
import at.technikum.if20b231.newslist.modle.Page
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.glide.GlideImage


@Composable
fun SinglePageScreen(page: Page) {
    page.imageURL = page.imageURL.toString().replace("\\", "/")
    //var imageUrl by remember { mutableStateOf(page.imageURL) }
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
    ) {
        TopAppBar(title = { Text(stringResource(R.string.pageTitle)) })


        Column(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize(),
        ) {
            Box(
                contentAlignment = Alignment.BottomCenter
            ) {
                // IMG
                GlideImage(
                    imageModel = page.imageURL,
                    contentScale = ContentScale.Fit,
                    circularReveal = CircularReveal(250),
                    modifier = Modifier.fillMaxSize(),
                    placeHolder = Icons.Filled.Image,
                    error = Icons.Filled.Error
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White.copy(alpha = 0.8f))
                        .padding(10.dp),
                    Alignment.BottomCenter
                ) {
                    Column() {

                        Text(
                            text = "${page.title}",
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                        Text(
                            text = "${page.author}",
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontSize = 8.sp
                        )
                        Text(
                            text = "${page.pubDate}",
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontSize = 8.sp
                        )
                    }
                }
            }


            // Box
            Column(
                Modifier.padding(0.dp, 0.dp, 0.dp, 0.dp)
            ) {


                // HTML
                page.descriptor?.let {
                    HtmlText(text = "$it")
                }

                //Full Story
                Button(
                    modifier = Modifier
                        .fillMaxWidth(),
                    onClick = {
                        context.startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse(page.articleURL ?: "")
                            )
                        )
                    }) {
                    Text(text = "Full Story")
                }

            }
        }
    }

}

@Composable
fun ShowSinglePage(navController: NavController, page: Page) {
    SinglePageScreen(page)
}


