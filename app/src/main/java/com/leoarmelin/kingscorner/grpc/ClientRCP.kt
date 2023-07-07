package com.leoarmelin.kingscorner.grpc

import android.net.Uri
import com.leoarmelin.kingscorner.BeginRequest
import com.leoarmelin.kingscorner.CreateRequest
import com.leoarmelin.kingscorner.GameServiceGrpc
import com.leoarmelin.kingscorner.JoinRequest
import com.leoarmelin.kingscorner.JoinResponse
import com.leoarmelin.kingscorner.PlayRequest
import com.leoarmelin.kingscorner.PlayRequest.CardTurn
import com.leoarmelin.kingscorner.PlayRequest.MoveTurn
import com.leoarmelin.kingscorner.PlayerServiceGrpc
import io.grpc.ManagedChannelBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asExecutor
import kotlinx.coroutines.flow.asFlow
import java.io.Closeable

class ClientRCP : Closeable {
    private val uri: Uri = Uri.parse("http://10.0.2.2:50051")

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

    fun beginGame(id: String) = gameService.begin(
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

    fun play(
        gameId: String,
        playerId: String,
        turn: PlayRequest.Turn,
        cardTurn: CardTurn,
        moveTurn: MoveTurn
    ) = playerService.play(
        PlayRequest
            .newBuilder()
            .setGameId(gameId)
            .setPlayerId(playerId)
            .setTurn(turn)
            .setCardTurn(cardTurn)
            .setMoveTurn(moveTurn)
            .build()
    )

    override fun close() {
        channel.shutdownNow()
    }
}