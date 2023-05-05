package com.example.thecr.view.screen

import android.graphics.drawable.Icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.thecr.R
import com.example.thecr.navigation.TheCRScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnboardingScreen(
    modifier: Modifier,
    navController: NavController
) {
    Scaffold {
        Column(
            modifier = modifier
                .fillMaxSize().padding(it),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                shape = CircleShape, colors = CardDefaults.cardColors(
                    Color.White
                ), elevation = CardDefaults.elevatedCardElevation(20.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_cr_foreground),
                    "logo",
                    modifier = Modifier.size(200.dp)
                )
            }
            Button(
                {
                    navController.navigate(TheCRScreen.COLLEGE_SELECTORS_SCREEN.name)
                },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                modifier = Modifier.fillMaxWidth().padding(20.dp),
            ) {
                Text("Login", fontSize = 25.sp)
                Icon(Icons.Default.ArrowForward, "Forward", tint = Color.White)
            }
        }
    }
}
