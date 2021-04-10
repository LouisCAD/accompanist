/*
 * Copyright 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.accompanist.sample.glide

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.glide.rememberGlideImageState
import com.google.accompanist.glide.rememberGlidePainter
import com.google.accompanist.imageloading.ImageLoadState
import com.google.accompanist.imageloading.rememberLoadPainter
import com.google.accompanist.sample.AccompanistSampleTheme
import com.google.accompanist.sample.R
import com.google.accompanist.sample.rememberRandomSampleImageUrl

class GlideBasicSample : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AccompanistSampleTheme {
                Sample()
            }
        }
    }
}

@Composable
private fun Sample() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.glide_title_basic)) }
            )
        }
    ) {
        LazyColumn(Modifier.padding(16.dp)) {
            item {
                // Data parameter
                Image(
                    painter = rememberGlidePainter(
                        rememberRandomSampleImageUrl(),
                        previewPlaceholder = R.drawable.placeholder,
                    ),
                    contentDescription = null,
                    modifier = Modifier.size(128.dp),
                )
            }

            item {
                // Load GIF
                Image(
                    painter = rememberGlidePainter("https://cataas.com/cat/gif"),
                    contentDescription = "Cat animation",
                    modifier = Modifier.size(128.dp),
                )
            }

            item {
                // Loading content
                Box {
                    val coilState = rememberGlideImageState(rememberRandomSampleImageUrl())

                    Image(
                        painter = rememberLoadPainter(state = coilState),
                        contentDescription = null,
                        modifier = Modifier.size(128.dp),
                    )

                    Crossfade(
                        targetState = coilState.loadState,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(16.dp)
                    ) { state ->
                        if (state == ImageLoadState.Loading) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }

            item {
                // Fade in
                Image(
                    painter = rememberGlidePainter(
                        data = rememberRandomSampleImageUrl(),
                        fadeIn = true,
                    ),
                    contentDescription = null,
                    modifier = Modifier.size(128.dp),
                )
            }

            item {
                // Fade in and loading content
                Box {
                    val coilState = rememberGlideImageState(rememberRandomSampleImageUrl())

                    Image(
                        painter = rememberLoadPainter(state = coilState, fadeIn = true),
                        contentDescription = null,
                        modifier = Modifier.size(128.dp),
                    )

                    Crossfade(coilState.loadState) { state ->
                        if (state == ImageLoadState.Loading) {
                            CircularProgressIndicator(Modifier.align(Alignment.Center))
                        }
                    }
                }
            }

            item {
                // Implicit size
                Box {
                    val glideState = rememberGlideImageState(rememberRandomSampleImageUrl())

                    Image(
                        painter = rememberLoadPainter(state = glideState),
                        contentDescription = null,
                    )

                    Crossfade(glideState.loadState) { state ->
                        if (state == ImageLoadState.Loading) {
                            CircularProgressIndicator(Modifier.align(Alignment.Center))
                        }
                    }
                }
            }

            item {
                // Aspect ratio and crop
                Image(
                    painter = rememberGlidePainter(rememberRandomSampleImageUrl()),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(256.dp)
                        .aspectRatio(16 / 9f),
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewSample() {
    AccompanistSampleTheme {
        Sample()
    }
}
