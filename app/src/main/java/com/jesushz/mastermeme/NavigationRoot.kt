package com.jesushz.mastermeme

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.jesushz.mastermeme.core.util.FileUtils
import com.jesushz.mastermeme.core.util.Routes
import com.jesushz.mastermeme.editor.presentation.EditorScreenRoot
import com.jesushz.mastermeme.home.presentation.HomeScreenRoot

@Composable
fun NavigationRoot(
    navController: NavHostController,
    startDestination: Routes = Routes.HomeGraph
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        homeGraph(navController)
    }
}

private fun NavGraphBuilder.homeGraph(navController: NavHostController) {
    navigation<Routes.HomeGraph>(
        startDestination = Routes.HomeScreen
    ) {
        composable<Routes.HomeScreen> {
            val context = LocalContext.current
            HomeScreenRoot(
                onNavigateToEditor = { template ->
                    navController.navigate(Routes.EditorScreen(template.image))
                },
                onShareMemes = { memes ->
                    val uris = memes.map { meme ->
                        FileUtils.pathToUri(context, meme.path)
                    }
                    Intent(Intent.ACTION_SEND_MULTIPLE).apply {
                        type = "image/*"
                        putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris.toCollection(ArrayList()))
                        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    }.also {
                        context.startActivity(
                            Intent.createChooser(it, "Share meme")
                        )
                    }
                }
            )
        }

        composable<Routes.EditorScreen> {
            EditorScreenRoot(
                onNavigateUp = {
                    navController.navigateUp()
                },
                onNavigateToHome = {
                    navController.navigate(Routes.HomeScreen) {
                        popUpTo(Routes.HomeGraph) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}
