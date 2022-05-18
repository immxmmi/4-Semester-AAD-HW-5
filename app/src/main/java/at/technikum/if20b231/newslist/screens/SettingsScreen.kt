package at.technikum.if20b231.newslist.screens

import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import at.technikum.if20b231.newslist.R
import at.technikum.if20b231.newslist.viewmodel.NewsListViewModel


@Composable
fun SettingsScreen(navController: NavController, model: NewsListViewModel) {

    val currentImageShow by model.imageShow.observeAsState()
    val currentImageDownload by model.imageDownload.observeAsState()
    val currentURL by model.url.observeAsState()
    var url by remember { mutableStateOf(currentURL) }
    val imageShow = remember { mutableStateOf(currentImageShow) }
    val imageDownload = remember { mutableStateOf(currentImageDownload) }

    BackHandler {
        model.updateImageShow(imageShow.value ?: true)
        model.updateUrl(url ?: "")
       // model.reload()
        navController.navigateUp()
    }

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
    ) {
        TopAppBar(
            title = { Text(stringResource(R.string.setings_menu_title)) })

        Row(
            modifier = Modifier
                .background(Color.White)
                .fillMaxWidth()
                .border(0.3.dp, color = Color.Black)
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Column(
                Modifier.padding(20.dp, 0.dp, 20.dp)
            ) {
                Text(
                    fontSize = 18.sp,
                    text = "News Feed URL "
                )
                TextField(
                    value = url ?: "",
                    onValueChange = {
                        url = it
                    },
                    label = { Text(text = currentURL.toString())}


                   //fontSize = 12.sp,
                   //color = Color.Gray,
                   //text = url.value.toString()
                )
            }
        }

        Row(
            modifier = Modifier
                .background(Color.White)
                .fillMaxWidth()
                .border(0.3.dp, color = Color.Black)
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Column(
                Modifier.padding(20.dp, 0.dp, 160.dp)
            ) {

                Text(
                    fontSize = 18.sp,
                    text = "Display Images "
                )
                Text(
                    fontSize = 12.sp,
                    color = Color.Gray,
                    text = "Images will be displayed."
                )
            }
            imageShow.value?.let {
                Checkbox(
                    checked = it,
                    onCheckedChange = { imageShow.value = it }
                )
            }
        }

        Row(
            modifier = Modifier
                .background(Color.White)
                .fillMaxWidth()
                .border(0.3.dp, color = Color.Black)
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Column(
                Modifier.padding(20.dp, 0.dp, 0.dp)
            ) {
                Text(
                    fontSize = 18.sp,
                    text = "Download Images in the Background."
                )
                Text(
                    fontSize = 12.sp,
                    color = Color.Gray,
                    text = "Images will be downloaded in the background."
                )
            }
            imageDownload.value?.let {
                Checkbox(
                    checked = it,
                    onCheckedChange = { imageDownload.value = it }
                )
            }
        }




    }
}


@Composable
fun BackHandler(enabled: Boolean = true, onBack: () -> Unit) {
    //https://dev.to/pawegio/handling-back-presses-in-jetpack-compose-50d5
    val currentOnBack by rememberUpdatedState(onBack)
    val backCallback = remember {
        object : OnBackPressedCallback(enabled) {
            override fun handleOnBackPressed() {
                currentOnBack()
            }
        }
    }
    SideEffect {
        backCallback.isEnabled = enabled
    }
    val backDispatcher = checkNotNull(LocalOnBackPressedDispatcherOwner.current) {
        "No OnBackPressedDispatcherOwner was provided via LocalOnBackPressedDispatcherOwner"
    }.onBackPressedDispatcher
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner, backDispatcher) {
        backDispatcher.addCallback(lifecycleOwner, backCallback)
        onDispose {
            backCallback.remove()
        }
    }
}
