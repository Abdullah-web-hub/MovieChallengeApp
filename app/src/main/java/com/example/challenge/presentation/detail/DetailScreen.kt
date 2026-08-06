package com.example.challenge.presentation.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage

@Composable
fun DetailScreen(
    viewModel: DetailViewModel = hiltViewModel(),
    onBackClick: () -> Unit = {}
) {
    val state = viewModel.state.value

    Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center), color = MaterialTheme.colorScheme.primary)
        } else if (state.error.isNotBlank()) {
            Text(
                text = state.error,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            state.movie?.let { movie ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    // Feature 3: Immersive Header
                    Box(modifier = Modifier.fillMaxWidth().height(500.dp)) {
                        AsyncImage(
                            model = "https://image.tmdb.org/t/p/original${movie.posterPath}",
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.verticalGradient(
                                        colors = listOf(Color.Transparent, MaterialTheme.colorScheme.background),
                                        startY = 400f
                                    )
                                )
                        )
                        
                        // Back Button
                        IconButton(
                            onClick = onBackClick,
                            modifier = Modifier
                                .padding(16.dp)
                                .align(Alignment.TopStart)
                                .background(Color.Black.copy(alpha = 0.5f), CircleShape)
                        ) {
                            Icon(Icons.Default.ArrowBack, contentDescription = null, tint = Color.White)
                        }
                    }

                    Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                        Text(
                            text = movie.title,
                            fontSize = 32.sp,
                            fontWeight = FontWeight.ExtraBold,
                            lineHeight = 38.sp
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Feature 4: Chips for Genres
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            movie.genres.take(3).forEach { genre ->
                                SuggestionChip(
                                    onClick = { },
                                    label = { Text(genre.name) },
                                    shape = RoundedCornerShape(16.dp),
                                    colors = SuggestionChipDefaults.suggestionChipColors(
                                        labelColor = MaterialTheme.colorScheme.primary
                                    )
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            InfoItem(icon = Icons.Default.Star, text = movie.voteAverage.toString(), color = MaterialTheme.colorScheme.primary)
                            InfoItem(icon = Icons.Default.CalendarMonth, text = movie.releaseDate ?: "")
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        Text(text = "Özet", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = movie.overview,
                            fontSize = 16.sp,
                            lineHeight = 24.sp,
                            color = Color.White.copy(alpha = 0.8f)
                        )

                        Spacer(modifier = Modifier.height(100.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun InfoItem(icon: androidx.compose.ui.graphics.vector.ImageVector, text: String, color: Color = Color.Gray) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = text, fontWeight = FontWeight.Bold)
    }
}
