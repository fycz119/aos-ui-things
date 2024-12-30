package com.example.aosuithings

import android.graphics.Bitmap
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Matrix
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.aosuithings.ui.theme.AosUiThingsTheme


class MainActivity : ComponentActivity() {
    private val mPaint: Paint = Paint()
    private val mLinePaint: Paint = Paint() //坐标系
    private val bitmap: Bitmap? = null
    private val matrix: Matrix = Matrix()
    private val pos = FloatArray(2)
    private val tan = FloatArray(2)
    private var mFloat = mutableFloatStateOf(0f)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()



        setContent {
            AosUiThingsTheme {
                Row {
                    Canvas(modifier = Modifier.size(100.dp)) {
                        translate(size.width / 2, size.height / 2) {
                            drawLine(
                                color = Color.Blue,
                                strokeWidth = 2f,
                                start = Offset(0f, size.height / 2),
                                end = Offset(size.width, size.height / 2),
                            )
                            drawLine(
                                color = Color.Blue,
                                strokeWidth = 2f,
                                start = Offset(size.width / 2, 0f),
                                end = Offset(size.width / 2, size.height),
                            )
                            val path: Path = Path()
                            mFloat.floatValue += 0.01f
                            if (mFloat.floatValue >= 1) {
                                mFloat.floatValue = 0f
                            }

//                            drawPath(
//                                path = CircleShape,
//
//
//                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AosUiThingsTheme {
        Greeting("Android")
    }
}