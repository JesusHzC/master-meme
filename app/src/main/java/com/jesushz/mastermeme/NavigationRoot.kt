package com.jesushz.mastermeme

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
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
            HomeScreenRoot(
                onNavigateToEditor = { template ->
                    navController.navigate(Routes.EditorScreen(template.image))
                }
            )
        }

        composable<Routes.EditorScreen> {
            EditorScreenRoot()
        }
    }
}
