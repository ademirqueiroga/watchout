package com.admqueiroga.watchout

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.admqueiroga.movie.MovieDetailViewModel
import com.admqueiroga.movie.MovieDetails
import com.admqueiroga.movie.MovieGenreGrid
import com.admqueiroga.movie.Movies
import com.admqueiroga.people.People
import com.admqueiroga.tvshow.TvShowGenreGrid
import com.admqueiroga.tvshow.TvShows
import com.admqueiroga.discover.Discover
import com.admqueiroga.discover.SearchScreen

internal sealed class Screen(val route: String) {
    object Discover : Screen("discover")
    object Movies : Screen("movies")
    object TvShows : Screen("tvshows")
    object People : Screen("people")
}

private sealed class LeafScreen(private val route: String) {

    fun createRoute(root: Screen) = "${root.route}/${route}"

    object Search : LeafScreen("search")
    object Discover : LeafScreen("discover")
    object Movies : LeafScreen("movies")
    object TvShows : LeafScreen("tvshows")
    object People : LeafScreen("people")

    object MovieDetail : LeafScreen("movie/{movieId}") {
        fun createRoute(root: Screen, movieId: Long): String {
            return "${root.route}/movie/$movieId"
        }
    }

    object Genre : LeafScreen("genre/{genreId}") {
        fun createRoute(root: Screen, genreId: Long): String {
            return "${root.route}/genre/$genreId"
        }
    }
}

internal class Tab(
    val screen: Screen,
    @DrawableRes val iconRes: Int,
    @StringRes val labelRes: Int,
)

internal val HomeTabs = listOf(
    Tab(Screen.Discover, R.drawable.home_movies_icon, R.string.home_discover_tab_text),
    Tab(Screen.Movies, R.drawable.home_movies_icon, R.string.home_movies_tab_text),
    Tab(Screen.TvShows, R.drawable.home_tv_shows_icon, R.string.home_tv_shows_tab_text),
    Tab(Screen.People, R.drawable.home_people_icon, R.string.home_people_tab_text),
)

@Composable
internal fun AppNavigation(navController: NavHostController, context: Context, modifier: Modifier = Modifier) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Screen.Discover.route,
    ) {
        addDiscoverTopLevel(navController, context)
        addMoviesTopLevel(navController)
        addTvShowsTopLevel(navController)
        addPeopleTopLevel(navController)
    }
}

private fun NavGraphBuilder.addDiscoverTopLevel(navController: NavHostController, context: Context) {
    navigation(
        route = Screen.Discover.route,
        startDestination = LeafScreen.Discover.createRoute(Screen.Discover)
    ) {
        addDiscover(navController, Screen.Discover, context)
        addSearch(navController, Screen.Discover)
        addMovieDetail(Screen.Discover)
//        addMovieDetail(Screen.Search)
    }
}

private fun NavGraphBuilder.addSearch(navController: NavHostController, root: Screen) {
    composable(route = LeafScreen.Search.createRoute(root)) {
        SearchScreen(
            onMovieClick = { movieId ->
                navController.navigate(LeafScreen.MovieDetail.createRoute(root, movieId))
            },
            navigateBack = {
                navController.popBackStack()
            }
        )
    }
}

private fun NavGraphBuilder.addDiscover(navController: NavHostController, root: Screen, context: Context) {
    composable(route = LeafScreen.Discover.createRoute(root)) {
        Discover(
            onMovieClick = { movieId ->
                navController.navigate(LeafScreen.MovieDetail.createRoute(root, movieId))
            },
            onTvShowClick = { tvShowId ->

            },
            onSearchClick = {
                navController.navigate(LeafScreen.Search.createRoute(root))
            },
            onUserAuthRequested = { requestToken ->
                val intent = Intent().apply {
                    action = Intent.ACTION_VIEW
                    setData(Uri.parse("https://www.themoviedb.org/authenticate/$requestToken?redirect_to=watchout://watchout.com"))
                }
                context.startActivity(intent)
            }
        )
    }
}

private fun NavGraphBuilder.addMoviesTopLevel(navController: NavHostController) {
    navigation(
        route = Screen.Movies.route,
        startDestination = LeafScreen.Movies.createRoute(Screen.Movies),
    ) {
        addMovies(navController, Screen.Movies)
        addMovieDetail(Screen.Movies)
        addGenreMovies(navController, Screen.Movies)
    }
}

private fun NavGraphBuilder.addMovies(navController: NavHostController, root: Screen) {
    composable(route = LeafScreen.Movies.createRoute(root)) {
        Movies(
            onMovieClick = { movieId ->
                navController.navigate(LeafScreen.MovieDetail.createRoute(root, movieId))
            },
            onMoreClick = { genre ->
                navController.navigate(LeafScreen.Genre.createRoute(root, genre.id))
            },
        )
    }
}

private fun NavGraphBuilder.addMovieDetail(root: Screen) {
    composable(
        route = LeafScreen.MovieDetail.createRoute(root),
        arguments = listOf(
            navArgument("movieId") { type = NavType.LongType }
        ),
    ) {
        val movieId = it.arguments?.getLong("movieId") ?: 0L
        MovieDetails(
            movieId = movieId,
            viewModel = viewModel(initializer = { MovieDetailViewModel(movieId) })
        )
    }
}

private fun NavGraphBuilder.addGenreMovies(navController: NavHostController, root: Screen) {
    composable(
        route = LeafScreen.Genre.createRoute(root),
        arguments = listOf(
            navArgument("genreId") { type = NavType.LongType }
        ),
    ) {
        val genreId = it.arguments?.getLong("genreId") ?: 0L
        MovieGenreGrid(genreId = genreId, onMovieClick = { movie ->
            navController.navigate(LeafScreen.MovieDetail.createRoute(root, movie.id))
        })
    }
}

private fun NavGraphBuilder.addTvShowsTopLevel(navController: NavHostController) {
    navigation(
        route = Screen.TvShows.route,
        startDestination = LeafScreen.TvShows.createRoute(Screen.TvShows)
    ) {
        addTvShows(navController, Screen.TvShows)
        addGenreTvShows(navController, Screen.TvShows)
    }
}

private fun NavGraphBuilder.addTvShows(navController: NavHostController, root: Screen) {
    composable(
        route = LeafScreen.TvShows.createRoute(root)
    ) {
        TvShows()
    }
}

private fun NavGraphBuilder.addGenreTvShows(navController: NavHostController, root: Screen) {
    composable(
        route = LeafScreen.Genre.createRoute(root),
        arguments = listOf(navArgument("genreId") { type = NavType.LongType })
    ) {
        val genreId = it.arguments?.getLong("genreId") ?: 0L
        TvShowGenreGrid(
//            genreId
        )
    }
}

private fun NavGraphBuilder.addPeopleTopLevel(navController: NavHostController) {
    composable(
        route = Screen.People.route
    ) {
        People {}
    }
}