package com.leoarmelin.kingscorner.grpc

import android.net.Uri
import android.util.Log
import com.leoarmelin.kingscorner.BeginRequest
import com.leoarmelin.kingscorner.BeginResponse
import com.leoarmelin.kingscorner.CreateRequest
import com.leoarmelin.kingscorner.GameServiceGrpc
import com.leoarmelin.kingscorner.JoinRequest
import com.leoarmelin.kingscorner.JoinResponse
import com.leoarmelin.kingscorner.PlayRequest
import com.leoarmelin.kingscorner.PlayRequest.CardTurn
import com.leoarmelin.kingscorner.PlayRequest.MoveTurn
import com.leoarmelin.kingscorner.PlayerServiceGrpc
import io.grpc.ManagedChannelBuilder
import io.grpc.StatusRuntimeException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asExecutor
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.withContext
import java.io.Closeable

class ClientRCP(
    url: String
) : Closeable {
    private val uri: Uri = Uri.parse(url)

    var joinResponseCallback: (JoinResponse) -> Unit = {}

    private val channel = let {
        println("Connecting to ${uri.host}:${uri.port}")

        val builder = ManagedChannelBuilder.forAddress(uri.host, uri.port)
        if (uri.scheme == "https") {
            builder.useTransportSecurity()
        } else {
            builder.usePlaintext()
        }

        builder.executor(Dispatchers.IO.asExecutor()).build()
    }

    private val gameService = GameServiceGrpc.newBlockingStub(channel)
    private val playerService = PlayerServiceGrpc.newBlockingStub(channel)

    suspend fun createGame() = gameService.create(
        CreateRequest
            .newBuilder()
            .build()
    ).asFlow().collect { joinResponseCallback(it) }

    fun beginGame(id: String): BeginResponse = gameService.begin(
        BeginRequest
            .newBuilder()
            .setId(id)
            .build()
    )

    suspend fun joinGame(id: String) = playerService.join(
        JoinRequest
            .newBuilder()
            .setGameId(id)
            .build()
    ).asFlow().collect { joinResponseCallback(it) }

    suspend fun play(
        gameId: String,
        playerId: String,
        cardTurn: CardTurn,
    ) {
        withContext(Dispatchers.IO) {
            try {
                playerService.play(
                    PlayRequest
                        .newBuilder()
                        .setGameId(gameId)
                        .setPlayerId(playerId)
                        .setTurnMode(PlayRequest.Turn.CARD)
                        .setCardTurn(cardTurn)
                        .build()
                )
            } catch (e: StatusRuntimeException) {
                Log.e("proto", e.message ?: "Some error ocurred")
            }
        }
    }

    fun play(
        gameId: String,
        playerId: String,
        moveTurn: MoveTurn
    ) {
        try {
            playerService.play(
                PlayRequest
                    .newBuilder()
                    .setGameId(gameId)
                    .setPlayerId(playerId)
                    .setTurnMode(PlayRequest.Turn.MOVE)
                    .setMoveTurn(moveTurn)
                    .build()
            )
        } catch (e: StatusRuntimeException) {
            Log.e("proto", e.message ?: "Some error ocurred")
        }
    }

    suspend fun play(
        gameId: String,
        playerId: String,
    ) {
        withContext(Dispatchers.IO) {
            try {
                playerService.play(
                    PlayRequest
                        .newBuilder()
                        .setGameId(gameId)
                        .setPlayerId(playerId)
                        .setTurnMode(PlayRequest.Turn.PASS)
                        .build()
                )
            } catch (e: StatusRuntimeException) {
                Log.e("proto", e.message ?: "Some error ocurred")
            }
        }
    }

    override fun close() {
        channel.shutdownNow()
    }
}