package com.example.voicetotext.presentation.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.example.voicetotext.R

const val homePage = "HOMEPAGE"
const val voiceScreen = "VOICESCREEN"
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
) {
    val screens = listOf(homePage, voiceScreen)
    var currentScreen by remember { mutableStateOf(screens[0]) }

    when(currentScreen) {
        homePage -> HomePage(
            onVoiceIconClick = {
                currentScreen = voiceScreen
            }
        )
        voiceScreen -> VoiceScreen(

        )
    }
}

@Composable
fun HomePage(
    onVoiceIconClick: () -> Unit
) {
    Scaffold(

    ){innerPadding->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(top = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedCard(
                onClick = { },
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(0.9f)
                    .height(48.dp),
                shape = RoundedCornerShape(24.dp)
            ){
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 16.dp, end = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Search,
                        contentDescription = null,
                        modifier = Modifier
                            .size(20.dp)
                    )
                    Text(
                        text = "search something...",
                        modifier = Modifier
                            .weight(1f)
                    )
                    IconButton(
                        onClick = {
                            onVoiceIconClick()
                        }
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.mic_icon),
                            contentDescription = "voice search",
                            modifier = Modifier
                                .size(20.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun VoiceScreen() {

}