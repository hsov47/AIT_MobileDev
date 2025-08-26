package hu.ait.minesweeper.ui.screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import com.wajahatkarim.flippable.rememberFlipController
import hu.ait.minesweeper.R

enum class GameMode {
    Flag, Try
}

data class BoardCell(
    val row: Int,
    val col: Int,
    var isMine: Boolean = false,
    var adjacentMines: Int = 0,
    var isRevealed: Boolean = false,
    var isFlagged: Boolean = false
)

class MinesweeperViewModel : ViewModel() {

    var board by mutableStateOf(createBoard(5, 5, 3))
    var numberOfMines by mutableStateOf(3)
    var currentMode by mutableStateOf<GameMode>(GameMode.Try)
    var gameOver by mutableStateOf<Boolean>(false)
    var won by mutableStateOf(false)


    fun onCellClicked(cell: BoardCell) {
        val newBoard = board.copyOf()
        newBoard[cell.row][cell.col].isRevealed = true
        board = newBoard

        if (currentMode == GameMode.Try && board[cell.row][cell.col].isMine ||
            currentMode == GameMode.Flag && !board[cell.row][cell.col].isMine) {
            gameOver = true;
            }
        else if (currentMode == GameMode.Flag && board[cell.row][cell.col].isMine) {
            board[cell.row][cell.col].isFlagged = true
            numberOfMines -= 1
            if (numberOfMines == 0) {gameOver = true; won = true}
        }

        }



    fun createBoard(rows: Int, cols: Int, numberOfMines: Int): Array<Array<BoardCell>> {
        // Create a board of BoardCells
        val board = Array(rows) { row ->
            Array(cols) { col ->
                BoardCell(row, col, false)
            }
        }

        // Place mines randomly
        var minesPlaced = 0
        while (minesPlaced < numberOfMines) {
            val row = (0 until rows).random()
            val col = (0 until cols).random()

            // Only place a mine if the cell isn't already a mine
            if (!board[row][col].isMine) {
                board[row][col].isMine = true
                minesPlaced++
            }
        }

        // Calculate adjacent mines for all cells
        for (row in board) {
            for (cell in row) {
                if (!cell.isMine) {
                    cell.adjacentMines = countAdjacentMines(board, cell.row, cell.col)
                }
            }
        }

        return board
    }

    // Function to count mines around a given cell
    private fun countAdjacentMines(board: Array<Array<BoardCell>>, row: Int, col: Int): Int {
        val directions = listOf(
            Pair(-1, -1), Pair(-1, 0), Pair(-1, 1),
            Pair(0, -1), Pair(0, 1),
            Pair(1, -1), Pair(1, 0), Pair(1, 1)
        )
        var mineCount = 0
        if (!board[row][col].isMine)
            for (direction in directions) {
            val newRow = row + direction.first
            val newCol = col + direction.second
            if (newRow in board.indices && newCol in board[0].indices && board[newRow][newCol].isMine) {
                mineCount++
            }
        }
        return mineCount
    }

    fun changeGameMode() {
        currentMode = if (currentMode == GameMode.Try) GameMode.Flag else GameMode.Try
    }

    fun resetGame() {
        board = createBoard(5, 5, 3)
        numberOfMines = 3
        currentMode = GameMode.Try
        gameOver = false
        won = false

    }
}