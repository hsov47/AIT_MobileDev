package hu.ait.tictactoe.screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel

enum class Player {
    X, O
}
data class BoardCell(val row: Int, val col: Int)

class TicTacToeViewModel : ViewModel() {

    var board by mutableStateOf(Array(3) { Array(3) { null as Player? } })
    var currentPlayer by mutableStateOf(Player.X)
    var winner by mutableStateOf<Player?>(null)

    var playerXWins by mutableStateOf(0)
    var playerOWins by mutableStateOf(0)

    fun onCellClicked(cell: BoardCell) {
        if (board[cell.row][cell.col] == null) {
            val newBoard = board.copyOf()
            newBoard[cell.row][cell.col] = currentPlayer
            board = newBoard

            if (isWinner(currentPlayer)) {
                winner = currentPlayer

                if (currentPlayer == Player.X) {
                    playerXWins++
                } else {
                    playerOWins++
                }
            }
            else {
                currentPlayer = if (currentPlayer == Player.X) Player.O else Player.X
            }
        }
    }

    private fun isWinner(player: Player?): Boolean {
        // check for 3 in row
        for (i in 0 until 3) {
            if (board[i][0] == player && board[i][1] == player && board[i][2] == player) {
                return true
            }
            if (board[0][i] == player && board[1][i] == player && board[2][i] == player) {
                return true
            }
        }

        // Check diagonal from top-left to bottom-right
        if (board[0][0] == player && board[1][1] == player && board[2][2] == player) {
            return true
        }

        // Check diagonal from top-right to bottom-left
        if (board[0][2] == player && board[1][1] == player && board[2][0] == player) {
            return true
        }
        return false
    }


    fun resetGame() {
        board = Array(3) { Array(3) { null as Player? } }
        currentPlayer = Player.X
        winner = null
    }
}