package com.leoarmelin.kingscorner.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leoarmelin.kingscorner.Board
import com.leoarmelin.kingscorner.JoinResponse
import com.leoarmelin.kingscorner.grpc.ClientRCP
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val clientRCP = ClientRCP()

    private var _board = MutableStateFlow<Board?>(null)
    val board = _board.asStateFlow()

    private var onReceiveJoinResponse: (JoinResponse) -> Unit = { joinResponse ->
        val fieldsString = joinResponse.board.fieldsList.map { field ->
            field.cardsList.map { card ->
                "${card.suit}-${card.rank}"
            }
        }

        val handString = joinResponse.handList.map { card ->
            "${card.suit}-${card.rank}"
        }

        Log.d(
            "proto",
            "=============BOARD=============\n" +
                    "board-id: ${joinResponse.board.id}\n" +
                    "board-players-ids: ${joinResponse.board.playerIdsList}\n" +
                    "board-current-turn: ${joinResponse.board.currentTurn}\n" +
                    "board-is-started: ${joinResponse.board.isStarted}\n" +
                    "board-fields: ${fieldsString}\n" +
                    "hand: ${handString}\n" +
                    "=============END BOARD============="
        )

        _board.value = joinResponse.board
    }

    init {
        clientRCP.joinResponseCallback = onReceiveJoinResponse
    }

    fun createGame() {
        if (_board.value != null) return
        viewModelScope.launch(Dispatchers.IO) {
            clientRCP.createGame()
        }
    }

    fun beginGame() {
        val gameId = _board.value?.id ?: return
        clientRCP.beginGame(gameId)
    }
}