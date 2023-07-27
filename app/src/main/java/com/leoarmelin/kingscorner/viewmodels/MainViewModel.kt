package com.leoarmelin.kingscorner.viewmodels

import android.util.Log
import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leoarmelin.kingscorner.Board
import com.leoarmelin.kingscorner.Board.Field
import com.leoarmelin.kingscorner.Board.Player
import com.leoarmelin.kingscorner.Card
import com.leoarmelin.kingscorner.JoinResponse
import com.leoarmelin.kingscorner.PlayRequest.CardTurn
import com.leoarmelin.kingscorner.extensions.isInsideOfRotatedRectangle
import com.leoarmelin.kingscorner.grpc.ClientRCP
import com.leoarmelin.kingscorner.models.FieldPosition
import com.leoarmelin.kingscorner.utils.getRotationByIndex
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    grpcUrl: String
) : ViewModel() {
    private val clientRCP = ClientRCP(grpcUrl)

    private val _board = MutableStateFlow<Board?>(null)

    private val _fieldsList = MutableStateFlow<List<Field>>(emptyList())
    val fieldsList = _fieldsList.asStateFlow()

    private val _hand = MutableStateFlow<List<Card>>(emptyList())
    val hand = _hand.asStateFlow()

    private val _fieldsGlobalPositionList = MutableStateFlow<List<FieldPosition>>(emptyList())

    private val _playerId = MutableStateFlow<String?>(null)
    val playerId = _playerId.asStateFlow()

    private val _playerList = MutableStateFlow<List<Player>>(emptyList())
    val playerIds = _playerList.asStateFlow()

    private val _isStarted = MutableStateFlow(false)
    val isStarted = _isStarted.asStateFlow()

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
                    "board-players-ids: ${joinResponse.board.playersList}\n" +
                    "board-current-turn: ${joinResponse.board.currentTurn}\n" +
                    "board-is-started: ${joinResponse.board.isStarted}\n" +
                    "board-fields: ${fieldsString}\n" +
                    "hand: ${handString}\n" +
                    "=============END BOARD============="
        )


        _board.value = joinResponse.board
        _fieldsList.value = joinResponse.board.fieldsList
        _playerList.value = joinResponse.board.playersList
        _hand.value = joinResponse.handList
        _playerId.value = joinResponse.playerId
        _isStarted.value = joinResponse.board.isStarted
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

    fun addFieldPosition(fieldPosition: FieldPosition) {
        if (_fieldsGlobalPositionList.value.any { it.index == fieldPosition.index }) return

        val pivotList = _fieldsGlobalPositionList.value.toMutableList()
        pivotList.add(fieldPosition)

        _fieldsGlobalPositionList.value = pivotList
    }

    fun dropCardOnBoard(card: Card, offset: Offset) {
        val fieldIndex =
            _fieldsGlobalPositionList.value.indexOfFirst { offset.isInsideOfRotatedRectangle(it.offset, getRotationByIndex(it.index)) }
        if (fieldIndex < 0) return

        val board = _board.value ?: return
        val playerId = _playerId.value ?: return

        viewModelScope.launch(Dispatchers.IO) {
            clientRCP.play(
                gameId = board.id,
                playerId = playerId,
                cardTurn = CardTurn
                    .newBuilder()
                    .setCard(card)
                    .setFieldLevel(fieldIndex)
                    .build(),
            )
        }
    }

    fun pass() {
        val board = _board.value ?: return
        val playerId = _playerId.value ?: return

        viewModelScope.launch(Dispatchers.IO) {
            clientRCP.play(
                gameId = board.id,
                playerId = playerId
            )
        }
    }

    fun joinGame() {
        viewModelScope.launch(Dispatchers.IO) {
            clientRCP.joinGame("cishottdrb6p1l1jgs60")
        }
    }
}