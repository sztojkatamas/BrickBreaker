package com.bbsoftware.brickbreaker.core

data class Command(val recipient :String, val command: String, val payload: Any)
