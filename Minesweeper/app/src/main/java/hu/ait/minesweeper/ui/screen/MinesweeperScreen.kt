package hu.ait.minesweeper.ui.screen

import android.media.MediaPlayer
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.draw
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.wajahatkarim.flippable.Flippable
import hu.ait.minesweeper.R
import hu.ait.minesweeper.ui.theme.displayFontFamily
import hu.ait.minesweeper.ui.theme.inversePrimaryDarkHighContrast

@Composable
fun MinesweeperScreen(modifier: Modifier = Modifier,
                    viewModel: MinesweeperViewModel = viewModel()
) {
    val context = LocalContext.current
    val gameMode = viewModel.currentMode
    val gameOver = viewModel.gameOver
    val won = viewModel.won

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    )
    {
        Text(
            context.getString(R.string.app_name),
            style = TextStyle(
                fontSize = 36.sp,
                fontFamily = displayFontFamily,
                color = colorScheme.primary,
                fontWeight = FontWeight.Bold,
            ),
            modifier = Modifier.padding(20.dp)
        )

        MinesweeperBoard(
            viewModel.board,
            onBoardCellClicked = { cell ->
                viewModel.onCellClicked(cell)
            },
            colorScheme = colorScheme
        )

        Spacer(modifier = Modifier.size(10.dp))

        Switch(
            checked = (gameMode == GameMode.Try),
            onCheckedChange = { viewModel.changeGameMode() }
        )

        Text("$gameMode Mode",
                style = TextStyle (
                    color = colorScheme.primary,
                    //fontFamily = displayFontFamily
                )
        )

        GameOverAlert(gameOver, viewModel)

    }
}

@Composable
fun MinesweeperBoard(
    board: Array<Array<BoardCell>>,
    viewModel: MinesweeperViewModel = viewModel(),
    colorScheme: ColorScheme,
    onBoardCellClicked: (BoardCell) -> Unit
) {

    val gameMode = viewModel.currentMode

    val flagPainter = rememberVectorPainter(ImageVector.vectorResource(id = R.drawable.flag))
    val bombPainter = rememberVectorPainter(ImageVector.vectorResource(id = R.drawable.bomb))

    val textMeasurer = rememberTextMeasurer()


    Canvas(modifier = Modifier
        .fillMaxWidth(0.8f)
        .aspectRatio(1f)
        .pointerInput(key1 = Unit) {

            detectTapGestures { offset ->
                Log.d("TAG", "MinesweeperScreen: ${offset.x}, ${offset.y} ")

                val row = (offset.y / (size.height / 5)).toInt()
                val col = (offset.x / (size.width / 5)).toInt()

                // Ensure row and col are within bounds
                if (row in 0 until 5 && col in 0 until 5) {
                    onBoardCellClicked(BoardCell(row, col))
                }
            }
        }
    ) {

        // Draw the board grid
        val gridSize = size.minDimension
        val fifthSize = gridSize / 5

        drawRoundRect(
            color = colorScheme.primary, // Set board color (change as needed)
            style = Stroke(10f),
            size = Size(gridSize, gridSize),
            topLeft = Offset(0f, 0f), // Position of the rectangle
            cornerRadius = CornerRadius(8f) // The standard rectangle with no rounding
        )
        for (i in 1..4) {
            drawLine(
                color = colorScheme.primary,
                strokeWidth = 8f,
                pathEffect = PathEffect.cornerPathEffect(100f),
                start = Offset(fifthSize * i, 0f),
                end = Offset(fifthSize * i, gridSize)
            )
            drawLine(
                color = colorScheme.primary,
                strokeWidth = 8f,
                pathEffect = PathEffect.cornerPathEffect(100f),
                start = Offset(0f, fifthSize * i),
                end = Offset(gridSize, fifthSize * i),
            )

            //Draw flags, bombs and numbers
            for (row in 0..4) {
                for (col in 0..4) {
                    val cell = board[row][col]
                    if (cell.isRevealed) {

                        val iconSize = fifthSize * 0.8f
                        val placeX = (col * fifthSize) + (fifthSize - iconSize)/2
                        val placeY = (row * fifthSize) + (fifthSize - iconSize)/2

                        if (cell.isFlagged) {
                            translate(placeX , placeY) {
                                with(flagPainter) {
                                    draw (
                                        size = Size(iconSize, iconSize),
                                        colorFilter = ColorFilter.tint(colorScheme.tertiary)
                                    )
                                }
                            }
                        } else if (cell.isMine) {
                            translate(placeX, placeY) {
                                with(bombPainter) {
                                    draw(
                                        size = Size(iconSize, iconSize),
                                        colorFilter = ColorFilter.tint(colorScheme.onErrorContainer)
                                    )
                                }
                            }
                        } else { // draw dist from bomb
                            val textLayoutResult: TextLayoutResult =
                                textMeasurer.measure(
                                text = "${cell.adjacentMines}",
                                style = TextStyle(
                                    fontSize = (fifthSize * 0.8f).toSp(),
                                    //fontWeight = FontWeight.Bold,
                                    fontFamily = displayFontFamily
                                    )
                                )
                                val textSize = textLayoutResult.size

                                drawText(
                                    textLayoutResult = textLayoutResult,
                                    topLeft = Offset(
                                        x  = (fifthSize/2 + col*fifthSize) - textSize.width/2,
                                        y = (fifthSize/2 + row*fifthSize) - textSize.height/2
                                    ),
                                    color = colorScheme.primary
                                )
                            }
                        }
                    }
                }

            }
        }

    }

@Composable
fun GameOverAlert(gameOver : Boolean,
             viewModel: MinesweeperViewModel = viewModel(),
             ) {
    val context = LocalContext.current
    val won = viewModel.won

    // MediaPlayer instances for sound effects
    val winSound = remember { MediaPlayer.create(context, R.raw.win) }
    val loseSound = remember { MediaPlayer.create(context, R.raw.explosion) }

    if (gameOver) {
        AlertDialog(
            onDismissRequest = {},
            title = {
                Text(context.getString(R.string.pop_over), color = colorScheme.primary)
            },
            text = {
                if (won) {
                    Text(context.getString(R.string.pop_won), color = colorScheme.primary)
                    winSound.setVolume(1.0f, 1.0f)
                    winSound.start()
                }
                else {
                    Text(context.getString(R.string.pop_lost), color = colorScheme.primary)
                    loseSound.setVolume(1.0f, 1.0f)
                    loseSound.start()}

            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.resetGame()
                    }
                ) {
                    Text(context.getString(R.string.btn_reset), color = colorScheme.onPrimary)
                }
            }

        )
    }
}








