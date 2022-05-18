package at.technikum.if20b231.newslist.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import at.technikum.if20b231.newslist.R
import at.technikum.if20b231.newslist.modle.Page
import at.technikum.if20b231.newslist.ui.theme.NewsListTheme
import at.technikum.if20b231.newslist.viewmodel.NewsListViewModel
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun PageItem(page: Page, navController: NavController) {
    var imageUrl by remember { mutableStateOf(page.imageURL) }


    val context = LocalContext.current
    Row(
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
            .border(0.02.dp, color = Color.Black)
            .padding(24.dp)
            .clickable(enabled = true) {
                if (page == null) {
                    Toast
                        .makeText(context, "No Details", Toast.LENGTH_SHORT)
                        .show()
                } else {

                    navController.navigate(
                        route = Screen.PageDetail.withArgs(
                            page.id.orEmpty(),
                            page.title.orEmpty(),
                            page.author.orEmpty(),
                            page.descriptor
                                .orEmpty()
                                .replace("/", "\\"),
                            page.pubDate.toString(),
                            page.imageURL
                                .orEmpty()
                                .replace("/", "\\"),
                            page.articleURL
                                .toString()
                                .replace("/", "\\")
                        )
                    )
                }
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),

        ) {
        // IMG
        GlideImage(
            imageModel = imageUrl,
            contentScale = ContentScale.Fit,
            circularReveal = CircularReveal(250),
            modifier = Modifier.size(80.dp),
            placeHolder = Icons.Filled.Image,
            error = Icons.Filled.Error
        )
        Column() {

            Text(
                text = "${page.title}",
                color = Color.DarkGray,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Text(
                text = "${page.author}",
                color = Color.Gray,
                fontWeight = FontWeight.Bold,
                fontSize = 8.sp
            )
            Text(
                text = "${page.pubDate}",
                color = Color.Gray,
                fontWeight = FontWeight.Bold,
                fontSize = 8.sp
            )
        }
    }
}

@Composable
fun PageItemNoImg(page: Page, navController: NavController) {
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
            .border(0.02.dp, color = Color.Black)
            .padding(24.dp)
            .clickable(enabled = true) {
                if (page == null) {
                    Toast
                        .makeText(context, "No Details", Toast.LENGTH_SHORT)
                        .show()
                } else {

                    navController.navigate(
                        route = Screen.PageDetail.withArgs(
                            page.id.orEmpty(),
                            page.title.orEmpty(),
                            page.author.orEmpty(),
                            page.descriptor
                                .orEmpty()
                                .replace("/", "\\"),
                            page.pubDate.toString(),
                            page.imageURL
                                .orEmpty()
                                .replace("/", "\\"),
                            page.articleURL
                                .toString()
                                .replace("/", "\\")
                        )
                    )
                }
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),

        ) {

        Column() {

            Text(
                text = "${page.title}",
                color = Color.DarkGray,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Text(
                text = "${page.author}",
                color = Color.Gray,
                fontWeight = FontWeight.Bold,
                fontSize = 8.sp
            )
            Text(
                text = "${page.pubDate}",
                color = Color.Gray,
                fontWeight = FontWeight.Bold,
                fontSize = 8.sp
            )
        }
    }
}

@Composable
fun PageFirstItem(page: Page, navController: NavController) {
    var imageUrl by remember { mutableStateOf(page.imageURL) }


    val context = LocalContext.current
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .clickable(enabled = true) {
                    if (page == null) {
                        Toast
                            .makeText(context, "No Details", Toast.LENGTH_SHORT)
                            .show()
                    } else {

                        navController.navigate(
                            route = Screen.PageDetail.withArgs(
                                page.id.orEmpty(),
                                page.title.orEmpty(),
                                page.author.orEmpty(),
                                page.descriptor
                                    .orEmpty()
                                    .replace("/", "\\"),
                                page.pubDate.toString(),
                                page.imageURL
                                    .orEmpty()
                                    .replace("/", "\\"),
                                page.articleURL
                                    .toString()
                                    .replace("/", "\\")
                            )
                        )
                    }
                }
                .fillMaxWidth()
                .background(Color.White)
                .border(0.02.dp, color = Color.Black)
                .padding(24.dp),
           // verticalAlignment = Alignment.CenterVertically,
           // horizontalArrangement = Arrangement.spacedBy(12.dp),
//

            ) {
            Box(
                contentAlignment = Alignment.BottomCenter
            ) {
                // IMG
                GlideImage(
                    imageModel = imageUrl,
                    contentScale = ContentScale.Fit,
                    circularReveal = CircularReveal(250),
                    modifier = Modifier.fillMaxSize(),
                    placeHolder = Icons.Filled.Image,
                    error = Icons.Filled.Error
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White.copy(alpha = 0.5f))
                        .padding(10.dp)
                    ,
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
        }
    }
}

@Composable
fun ShowListOfPages(navController: NavController, model: NewsListViewModel) {

    val pages by model.load.observeAsState()
    val imageShow by model.imageShow.observeAsState();
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }
    NewsListTheme {
        Surface(color = MaterialTheme.colors.background) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text(stringResource(R.string.app_title)) },
                        actions = {
                            IconButton(onClick = {
                                expanded = true
                            }) {
                                Icon(Icons.Filled.MoreVert, contentDescription = "menu")
                                DropdownMenu(expanded = expanded,
                                    onDismissRequest = { expanded = false }) {
                                    DropdownMenuItem(onClick = {
                                        navController.navigate(Screen.Settings.route)
                                        expanded = false
                                    }) {
                                        Text(stringResource(R.string.Menu_1))
                                    }
                                    DropdownMenuItem(onClick = {
                                        model.reload()
                                        expanded = false
                                    }) {
                                        Text(stringResource(R.string.Menu_2))
                                    }
                                }
                            }
                        }

                    )
                }, content = {
                    LazyColumn() {
                        itemsIndexed(pages ?: listOf()) { index, page ->
                            if (imageShow == false) {
                                PageItemNoImg(page = page, navController)
                            } else {
                                if (index == 0) {
                                    PageFirstItem(page = page, navController)
                                } else {
                                    PageItem(page = page, navController)
                                }
                            }
                        }
                    }
                }
            )
        }
    }
}



