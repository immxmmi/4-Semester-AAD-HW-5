package at.technikum.if20b231.newslist.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import at.technikum.if20b231.newslist.db.PageEntry
import at.technikum.if20b231.newslist.modle.Page
import at.technikum.if20b231.newslist.viewmodel.NewsListViewModel
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

private var formatter: DateFormat? = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z",Locale.US)

@Composable
fun SetupNavGraph(navController: NavHostController, viewModel: NewsListViewModel) {
    NavHost(
        navController = navController,
        startDestination = Screen.ListPage.route
    ) {

        // List of Pages
        composable(route = Screen.ListPage.route) {
            ShowListOfPages(navController = navController,model = viewModel)
        }

        composable(route = Screen.Settings.route){
            SettingsScreen(navController = navController, model = viewModel)
        }

        // Single Page
        composable(
            route = Screen.PageDetail.route + "/{id}/{title}/{author}/{description}/{pubDate}/{imageURL}/{url}",
            arguments = mutableListOf(
                navArgument("id") {
                    type = NavType.StringType
                    defaultValue = "empty"
                    nullable = true
                },
                navArgument("title") {
                    type = androidx.navigation.NavType.StringType
                    defaultValue = "empty"
                    nullable = true
                },
                navArgument("author") {
                    type = NavType.StringType
                    defaultValue = "empty"
                    nullable = true
                },
                navArgument("description") {
                    type = androidx.navigation.NavType.StringType
                    defaultValue = "empty"
                    nullable = true
                },
                navArgument("pubDate") {
                    type = NavType.StringType
                    defaultValue = "empty"
                    nullable = true
                },
                navArgument("imageURL") {
                    type = NavType.StringType
                    defaultValue = "empty"
                    nullable = true
                },
                navArgument("url") {
                    type = NavType.StringType
                    defaultValue = "empty"
                    nullable = true
                },
            )
        ) { entry ->

            var date: Date? = formatter?.parse(entry.arguments?.getString("pubDate"))
            var pageEntry = PageEntry(
                entry.arguments?.getString("id")!!,
                entry.arguments?.getString("title")!!,
                entry.arguments?.getString("author"),
                entry.arguments?.getString("description"),
                entry.arguments?.getString("pubDate")!!,
                entry.arguments?.getString("imageURL"),
                entry.arguments?.getString("url"),
                entry.arguments?.keySet()?.joinToString(",")
            )

            var page = Page(
                entry.arguments?.getString("id")!!,
                entry.arguments?.getString("title")!!,
                entry.arguments?.getString("author"),
                entry.arguments?.getString("description"),
                date,
                //formatter?.parse(date),
                entry.arguments?.getString("imageURL"),
                entry.arguments?.getString("url"),
                entry.arguments?.keySet()
            )
            ShowSinglePage(navController = navController, pageEntry)
        }
    }
}