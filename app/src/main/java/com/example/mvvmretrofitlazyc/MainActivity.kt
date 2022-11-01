package com.example.mvvmretrofitlazyc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Scale
import coil.transform.CircleCropTransformation
import com.example.mvvmretrofitlazyc.ui.theme.MVVMRetrofitLazyCTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MVVMRetrofitLazyCTheme {
                Surface(color = MaterialTheme.colors.background) {


                    MovieList(movieList = mainViewModel.moviesList)
                    mainViewModel.getMovieList()


                }
            }
        }
    }
}

@Composable
fun MovieList(movieList: List<MoviesModel>) {
    var selectedIndex by remember { mutableStateOf(-1) }

    LazyColumn {
        itemsIndexed(items = movieList) { index, item ->
            MovieCard(
                movie = item,
                index = index,
                selectedIndex = selectedIndex
            ) {
                selectedIndex = it
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MovieItem() {
    val movie = MoviesModel(
        name = "Coco",
        category = "Latest",
        imageUrl = "",
        desc = "Coco is a 2017 American 3D computer-animated musical fantasy adventure film produced by Pixar"
    )

    MovieCard(movie, 0, 0) { }

}


@Composable
fun MovieCard(movie: MoviesModel, index: Int, selectedIndex: Int, onClick: (Int) -> Unit) {

    val backgroundColor =
        if (index == selectedIndex) Color.Gray else MaterialTheme.colors.background

    Card(
        modifier = Modifier
            .padding(8.dp, 4.dp)
            .fillMaxWidth()
            .clickable { onClick(index) }
            .height(110.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp
    ) {
        Surface(color = backgroundColor) {

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(4.dp)
            ) {

                Image(
                    painter = rememberAsyncImagePainter(
                        ImageRequest.Builder(LocalContext.current).data(data = movie.imageUrl)
                            .apply(block = fun ImageRequest.Builder.() {
                                scale(Scale.FILL)
                                placeholder(R.drawable.placeholder)
                                transformations(CircleCropTransformation())
                            }).build()
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(0.2f)
                )


                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(4.dp)
                        .weight(0.8f)
                ) {
                    Text(
                        text = movie.name,
                        style = MaterialTheme.typography.subtitle1,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = movie.category,
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier
                            .padding(4.dp)
                            .background(Color.LightGray)
                    )
                    Text(
                        text = movie.desc,
                        style = MaterialTheme.typography.body1,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )


                }

            }


        }

    }
}
































