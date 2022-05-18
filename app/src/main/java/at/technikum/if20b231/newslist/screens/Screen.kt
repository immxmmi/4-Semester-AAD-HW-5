package at.technikum.if20b231.newslist.screens

sealed class Screen(val route: String){
    object ListPage: Screen("list_of_pages_screen")
    object PageDetail: Screen("page_screen")
    object Settings: Screen("settings_screen")

    // ARG
    fun withArgs(vararg args: String) : String{
        return buildString {
            append(route)
            args.forEach { arg ->
             append("/$arg")
            }
        }
    }
}