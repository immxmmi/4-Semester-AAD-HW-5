package at.technikum.if20b231.newslist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import at.technikum.if20b231.newslist.screens.SetupNavGraph
import at.technikum.if20b231.newslist.ui.theme.NewsListTheme
import at.technikum.if20b231.newslist.viewmodel.NewsListViewModel

class MainActivity : ComponentActivity() {


    // NavHost
    private lateinit var navController: NavHostController

    //VieModel
    private val viewModel by viewModels<NewsListViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
             NewsListTheme {
                 navController = rememberNavController()
                SetupNavGraph(navController = navController,viewModel)
             }
        }

    }

}



