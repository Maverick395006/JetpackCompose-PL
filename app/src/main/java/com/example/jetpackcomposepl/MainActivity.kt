package com.example.jetpackcomposepl

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material.TextField
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App()
        }
    }

}

/**
 * Part 4: Creating an Image Card Composable
 */

@Composable
fun ImageCardComponent(
    painter: Painter,
    contentDescription: String,
    title: String,
    modifier: Modifier = Modifier.border(
        2.dp,
        Brush.linearGradient(
            listOf(
                Color.Blue,
                Color.Green
            )
        ),
        RoundedCornerShape(15.dp)
    ),
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.cardElevation(5.dp)
    ) {
        Box(modifier = Modifier.height(200.dp)) {
            Image(
                painter = painter,
                contentDescription = contentDescription,
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black
                            ), startY = 350f
                        )
                    )
            ) {

            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                Text(text = title, style = TextStyle(color = Color.White, fontSize = 16.sp))
            }
        }

    }
}

@Preview(name = "Image Card", showSystemUi = true, showBackground = true)
@Composable
fun ImageCardPreview() {
    val painter = painterResource(id = R.drawable.img_piyush_radadiya)
    val title = "Piyush Radadiya"
    val description = "$title is an Android Developer"
    Box(
        modifier = Modifier
            .fillMaxWidth(0.5f)
            .padding(16.dp)
    ) {
        ImageCardComponent(painter = painter, contentDescription = description, title = title)
    }
}

/**
 * Part 5: Styling Text
 */

@Composable
fun StylishTextComponent() {
    val fontFamily = FontFamily(
        Font(R.font.rintexy_bold, FontWeight.Bold),
        Font(R.font.rintexy_regular, FontWeight.Normal)
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = Color.Green, fontSize = 50.sp
                    )
                ) {
                    append("M")
                }
                append("averick")
                withStyle(
                    style = SpanStyle(
                        color = Color.Green, fontSize = 50.sp
                    )
                ) {
                    append("U")
                }
                append("niverse")
            },
            color = Color.White,
            fontSize = 30.sp,
            fontFamily = fontFamily,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(name = "Stylish Text", showSystemUi = true, showBackground = true)
@Composable
fun StylishTextPreview() {
    StylishTextComponent()
}

/**
 * Part 6: State
 */

@Composable
fun ColorBoxComponent(
    modifier: Modifier = Modifier,
    updateColor: (Color) -> Unit,
) {
    Box(
        modifier = modifier
            .background(Color.Yellow)
            .clickable {
                updateColor(
                    Color(
                        Random.nextFloat(),
                        Random.nextFloat(),
                        Random.nextFloat(),
                        1f
                    )
                )
            }
    )
}

@Preview(name = "Stylish Text", showSystemUi = true, showBackground = true)
@Composable
fun ColorBoxPreview() {
    Column(Modifier.fillMaxSize()) {
        val color = remember {
            mutableStateOf(Color.Green)
        }
        Box(
            modifier = Modifier
                .background(color = color.value)
                .fillMaxSize()
                .weight(1f)
        )
        ColorBoxComponent(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        ) {
            color.value = it
        }
    }
}

/**
 * Part 7: Textfields, Buttons & Showing Snackbars
 */

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TxtBtnScaffoldComponent() {

    val scaffoldState = rememberScaffoldState()
    var textFieldState by remember {
        mutableStateOf("")
    }
    val scope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 30.dp)
        ) {
            it
            TextField(
                value = textFieldState,
                label = {
                    Text(text = "Enter Your Name")
                },
                onValueChange = { str ->
                    textFieldState = str
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                keyboardController?.hide()
                scope.launch {
                    delay(100L)
                    scaffoldState.snackbarHostState.showSnackbar("Hello $textFieldState")
                }
            }
            ) {
                Text(text = "Please Greet Me")
            }
        }
    }


}

@Preview(name = "Stylish Text", showSystemUi = true, showBackground = true)
@Composable
fun TxtBtnScaffoldPreview() {
    TxtBtnScaffoldComponent()
}

/**
 * Part 8: Lists
 */

@Composable
fun ListComponent() {
    LazyColumn {
        itemsIndexed(
            listOf(
                "Java",
                "Python",
                "JavaScript",
                "Ruby",
                "PHP",
                "C",
                "C++",
                "Swift",
                "Dart",
                "Scala",
                "Rust",
                "Perl",
                "Kotlin",
                "SQL",
                "TypeScript",
                "Objective-C"
            )
        ) { index, string ->
            Text(
                text = "$string",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
            )
        }
    }
}

@Preview(name = "Stylish Text", showSystemUi = true, showBackground = true)
@Composable
fun ListPreview() {
    ListComponent()
}

/**
 * Part 9: ConstraintLayout
 */

@Composable
fun ConstraintLayoutComponent() {
    val constraints = ConstraintSet {
        val greenBox = createRefFor("greenbox")
        val redBox = createRefFor("redbox")
        val yellowBox = createRefFor("yellowbox")

        constrain(greenBox) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            width = Dimension.value(40.dp)
            height = Dimension.value(40.dp)
        }

        constrain(redBox) {
            top.linkTo(greenBox.bottom)
            start.linkTo(greenBox.end)
            width = Dimension.value(40.dp)
            height = Dimension.value(40.dp)
        }

        constrain(yellowBox) {
            top.linkTo(redBox.bottom)
            start.linkTo(redBox.end)
            end.linkTo(parent.end)
            width = Dimension.fillToConstraints
            height = Dimension.value(40.dp)
        }

    }

    ConstraintLayout(constraintSet = constraints, modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .background(Color.Green)
                .layoutId("greenbox")
        )
        Box(
            modifier = Modifier
                .background(Color.Red)
                .layoutId("redbox")
        )
        Box(
            modifier = Modifier
                .background(Color.Yellow)
                .layoutId("yellowbox")
        )
    }

}

@Preview(name = "Stylish Text", showSystemUi = true, showBackground = true)
@Composable
fun ConstraintLayoutPreview() {
    ConstraintLayoutComponent()
}

/**
 * Part 10: Side Effect handlers
 */

@Composable
fun SideEffectHandlersComponent() {
    //Launched Effect
    var text by remember {
        mutableStateOf("")
    }
    LaunchedEffect(key1 = text) {
        delay(1000L)
        print("The text is $text")
    }
}

@Preview(name = "Side-Effect Handler", showSystemUi = true, showBackground = true)
@Composable
fun SideEffectHandlersPreview() {
    SideEffectHandlersComponent()
}

// rememberUpdatedState

@Composable
fun App() {
    var counter = remember {
        mutableStateOf(0)
    }
    LaunchedEffect(key1 = Unit) {
        delay(2000L)
        counter.value = 10
    }
    Counter(counter.value)
}

@Composable
fun Counter(value: Int) {
    val state = rememberUpdatedState(newValue = value)
    LaunchedEffect(key1 = Unit) {
        delay(4000L)
        Log.d("funnie", state.value.toString())
    }
    Text(text = state.value.toString())
}