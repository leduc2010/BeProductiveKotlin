package com.duc.beproductivekotlin

import android.media.MediaPlayer
import android.media.MediaPlayer.OnCompletionListener

class MediaManager private constructor() {
    private var playerBG: MediaPlayer? = null
    private var playerGame: MediaPlayer? = null
    private var isPauseBG = false
    private var isPauseGame = false

    fun playBG(song: Int) {
        if (playerBG != null) {
            playerBG!!.reset()
        }
        playerBG = MediaPlayer.create(App.instance, song)
        playerBG!!.setLooping(true)
        playerBG!!.start()
    }

    fun playBGNoLooping(song: Int) {
        if (playerBG != null) {
            playerBG!!.reset()
        }
        playerBG = MediaPlayer.create(App.instance, song)
        playerBG!!.setLooping(false)
        playerBG!!.start()
    }

    fun playGame(song: Int, event: OnCompletionListener?) {
        if (playerGame != null) {
            playerGame!!.reset()
        }
        playerGame = MediaPlayer.create(App.instance, song)
        playerGame!!.setOnCompletionListener(event)
        playerGame!!.start()
    }

    fun playSong() {
        if (playerBG != null && isPauseBG) {
            isPauseBG = false
            playerBG!!.start()
        }

        if (playerGame != null && isPauseGame) {
            isPauseGame = false
            playerGame!!.start()
        }
    }

    fun pauseSong() {
        if (playerBG != null && playerBG!!.isPlaying()) {
            playerBG!!.pause()
            isPauseBG = true
        }
        if (playerGame != null && playerGame!!.isPlaying()) {
            playerGame!!.pause()
            isPauseGame = true
        }
    }

    fun stopBG() {
        if (playerBG != null) {
            playerBG!!.reset()
        }
    }

    fun stopPlayGame() {
        if (playerGame != null) {
            playerGame!!.reset()
        }
    }

    fun setVolume(v: Float) {
        if (playerBG != null) {
            playerBG!!.setVolume(v, v)
        }
    }

    companion object {
        var instance: MediaManager? = null
            get() {
                if (field == null) {
                    field = MediaManager()
                }
                return field
            }
            private set
    }
}
