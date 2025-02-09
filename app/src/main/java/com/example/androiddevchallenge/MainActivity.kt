/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.Crossfade
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.androiddevchallenge.data.Pet
import com.example.androiddevchallenge.data.PetsData
import com.example.androiddevchallenge.ui.detail.PetDetailScreen
import com.example.androiddevchallenge.ui.overview.PetsOverviewScreen
import com.example.androiddevchallenge.ui.theme.MyTheme

class MainActivity : AppCompatActivity() {
    private val navigationViewModel by viewModels<NavigationViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTheme() {
                MyApp(navigationViewModel)
            }
        }
    }

    override fun onBackPressed() {
        if (!navigationViewModel.onBack()) {
            super.onBackPressed()
        }
    }
}

// Start building your app here!
@Composable
fun MyApp(navigationViewModel: NavigationViewModel) {
    Crossfade(targetState = navigationViewModel.currentScreen) { screen ->
        Surface(color = MaterialTheme.colors.background) {
            when (screen) {
                is Screen.Detail -> PetDetailScreen(screen.petId)
                is Screen.Overview -> PetsOverviewScreen(PetsData.pets) {
                    navigationViewModel.navigateTo(it)
                }
            }
        }
    }
}

@Preview(showBackground = false, name = "pet card")
@Composable
fun PetPreview(pets: List<Pet> = PetsData.pets) {
    PetsOverviewScreen(pets = pets, {})
}
