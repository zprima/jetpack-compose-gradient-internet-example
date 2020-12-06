package com.example.internet_example

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.viewinterop.viewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.internet_example.ui.InternetexampleTheme
import com.squareup.moshi.Json
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers


//interface CatService {
//    @GET("facts/random")
//    suspend fun randomFact(): RandomFact
//}
//
//data class RandomFact(
//    @Json(name = "text") val text: String
//)
//
//val moshi = Moshi.Builder()
//    .addLast(KotlinJsonAdapterFactory())
//    .build()
//
//var retrofit = Retrofit.Builder()
//    .baseUrl("https://cat-fact.herokuapp.com/")
//    .addConverterFactory(MoshiConverterFactory.create(moshi))
//    .build()
//
//var service = retrofit.create(CatService::class.java)

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InternetexampleTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    App()
                }
            }
        }
    }
}

class AppViewModel : ViewModel(){
    val randomFact = mutableStateOf<String>("Some text here")

    fun getRandomFact(){
//        viewModelScope.launch(Dispatchers.IO) {
//            delay(2000)
//            randomFact.value = service.randomFact().text
//        }
    }
}

@Composable
fun App(){
    val vm: AppViewModel = viewModel()

    // Uncomment this for retrieving a random fact from api
//    val randomFact by remember { vm.randomFact }
//    LaunchedEffect(subject = randomFact, block = {
//        if(randomFact.isEmpty()) vm.getRandomFact()
//    })


    App(randomFact = vm.randomFact.value, fetchRandomFact = {
        vm.getRandomFact()
    })
}

// makeBrush is a function which receives Size and returns a Gradient Type
fun Modifier.sizedBrush(makeBrush: (Size) -> LinearGradient) = drawWithCache {
    // Pass the current size to the makeBrush function
    val brush = makeBrush(this.size)
    onDrawBehind() {
        drawRect(brush = brush)
    }
}

fun Modifier.verticalGradient(colors: List<Color>) =
    sizedBrush(
        // makeBrush is a function, it will accept size, and execute the generation of VerticalGradient
        // this will all happen in the sizedBrush modifier
        makeBrush = { brushSize ->
            VerticalGradient(colors = colors, startY = brushSize.height / 2, endY = brushSize.height)
        }
    )

@Composable
fun App(randomFact: String, fetchRandomFact: () -> Unit){
    val catPic = imageResource(id = R.drawable.cat1)

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize(1f).padding(20.dp)
    ) {
        Box(modifier = Modifier.preferredSize(350.dp)){
            Image(bitmap = catPic, modifier = Modifier.fillMaxSize(1f))
            Box(modifier = Modifier.fillMaxSize(1f).verticalGradient(colors = listOf(Color.Transparent, Color.White)))
        }

        Spacer(modifier = Modifier.preferredHeight(20.dp))

        if (randomFact.isEmpty()) {
            Text("Loading random fact...")
        } else {
            Text(randomFact)

            Spacer(modifier = Modifier.preferredHeight(20.dp))

            Button(onClick = { fetchRandomFact() }) {
                Text("Refresh")
            }
        }
    }
}



