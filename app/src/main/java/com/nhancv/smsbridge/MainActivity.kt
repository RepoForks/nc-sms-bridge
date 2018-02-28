package com.nhancv.smsbridge

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import io.socket.client.IO
import io.socket.client.Socket
import java.net.URISyntaxException


class MainActivity : AppCompatActivity() {
    private lateinit var socket: Socket

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        try {
            socket = IO.socket("http://192.168.0.101:3000")
        } catch (exception: URISyntaxException) {
        }

        socket
                .on(Socket.EVENT_CONNECT, {
                    socket.emit("foo", "hi")
                })
                .on("event", { args -> System.out.println(args[0]) })
                .on(Socket.EVENT_DISCONNECT, { })
        socket.connect()
    }

    override fun onDestroy() {
        super.onDestroy()
        socket.disconnect()
    }
}
