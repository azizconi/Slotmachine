package com.example.slotmachine.repository

interface Event {
    fun eventEnd(result: Int, count: Int)
}