package com.example.thecr.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.thecr.view.screen.*

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TheCRNavHost(
    navHostController: NavHostController,
    modifier: Modifier,
    isLoggedIn: Boolean
) {
    NavHost(
        navController = navHostController,
        startDestination = if (isLoggedIn) TheCRScreen.CALENDAR_SCREEN.name else TheCRScreen.ONBOARDING_SCREEN.name
    ) {
        composable(TheCRScreen.ONBOARDING_SCREEN.name) {
            OnboardingScreen(modifier = modifier, navHostController)
        }
        composable(TheCRScreen.LOGIN_SCREEN.name) {
            LoginScreen(modifier = modifier, navHostController)
        }
        composable(TheCRScreen.CALENDAR_SCREEN.name) {
            CalendarScreen(modifier = modifier, navHostController)
        }
        composable(
            "${TheCRScreen.ATTENDANCE_SCREEN.name}/{timetablePeriodId}/{day}/{year_branch}",
            arguments = listOf(
                navArgument("timetablePeriodId") { type = NavType.StringType },
                navArgument("day") { type = NavType.IntType },
                navArgument("year_branch") { type = NavType.StringType }),
        ) {
            AttendanceScreen(modifier, it.arguments?.getString("timetablePeriodId"), it.arguments?.getInt("day"),it.arguments?.getString("year_branch")!!,navHostController)
        }
        composable(TheCRScreen.EDIT_SCREEN.name) {
            EditScreen(modifier, navHostController)
        }
        composable(TheCRScreen.COLLEGE_SELECTORS_SCREEN.name) {
            CollegeSelectorScreen(modifier, navHostController)
        }
        composable(TheCRScreen.EXPORT_CSV_SCREEN.name) {
            ExportScreen(modifier, navHostController)
        }
    }
}