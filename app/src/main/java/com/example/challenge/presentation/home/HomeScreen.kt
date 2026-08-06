package com.example.challenge.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.challenge.data.model.Movie

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onMovieClick: (Int) -> Unit
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
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 100.dp)
            ) {
                // Feature 1: Hero Section (Featured Movie)
                item {
                    state.nowPlayingMovies.firstOrNull()?.let { movie ->
                        FeaturedMovieItem(movie = movie, onMovieClick = onMovieClick)
                    }
                }

                item {
                    Text(
                        text = "Keşfet",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.ExtraBold,
                        modifier = Modifier.padding(16.dp)
                    )
                }

                item {
                    MovieCategoryRow(
                        title = "Vizyondakiler",
                        movies = state.nowPlayingMovies,
                        onMovieClick = onMovieClick
                    )
                }

                item {
                    MovieCategoryRow(
                        title = "Popüler",
                        movies = state.popularMovies,
                        onMovieClick = onMovieClick
                    )
                }

                item {
                    MovieCategoryRow(
                        title = "En İyiler",
                        movies = state.topRatedMovies,
                        onMovieClick = onMovieClick
                    )
                }

                item {
                    MovieCategoryRow(
                        title = "Yakında",
                        movies = state.upcomingMovies,
                        onMovieClick = onMovieClick
                    )
                }
            }
        }
    }
}

@Composable
fun FeaturedMovieItem(movie: Movie, onMovieClick: (Int) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
            .clickable { onMovieClick(movie.id) }
    ) {
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
                        startY = 300f
                    )
                )
        )
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
        ) {
            Text(
                text = movie.title,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = { onMovieClick(movie.id) },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text("Şimdi İzle", color = Color.Black)
            }
        }
    }
}

@Composable
fun MovieCategoryRow(
    title: String,
    movies: List<Movie>,
    onMovieClick: (Int) -> Unit
) {
    if (movies.isNotEmpty()) {
        Column(modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp)) {
            Text(
                text = title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )

            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(movies) { movie ->
                    MovieItem(movie = movie, onMovieClick = onMovieClick)
                }
            }
        }
    }
}

@Composable
fun MovieItem(
    movie: Movie,
    onMovieClick: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .width(150.dp)
            .clickable { onMovieClick(movie.id) }
    ) {
        Box {
            AsyncImage(
                model = "https://image.tmdb.org/t/p/w500${movie.posterPath}",
                contentDescription = movie.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(150.dp)
                    .height(225.dp)
                    .clip(RoundedCornerShape(12.dp))
            )
            // Feature 2: Rating Badge on Image
            Surface(
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.TopEnd),
                color = Color.Black.copy(alpha = 0.7f),
                shape = RoundedCornerShape(8.dp)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Star,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = movie.voteAverage.toString(),
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = movie.title,
            fontSize = 14.sp,
            maxLines = 1,
            fontWeight = FontWeight.Medium,
            overflow = TextOverflow.Ellipsis
        )
    }
}
