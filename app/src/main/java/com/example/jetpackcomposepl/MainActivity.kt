package com.example.jetpackcomposepl

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
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
import kotlin.random.Random

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ColorBoxPreview()
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
    updateColor: (Color) -> Unit
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