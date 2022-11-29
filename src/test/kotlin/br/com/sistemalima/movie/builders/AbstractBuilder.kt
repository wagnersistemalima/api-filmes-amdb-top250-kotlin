package br.com.sistemalima.movie.builders

import java.util.Random
import java.util.UUID


abstract class AbstractBuilder<T> {

    protected val random = Random()

    private val saltChars = "abcdefghijlmnopqrstuvwxyz1234567890"
    private val saltNumbers = "0123456789"

    abstract fun random(): T


    fun random(size: Int): List<T> {
        val filmes = ArrayList<T>()

        for(i in 1..size) {
            filmes.add(random())
        }
        return filmes
    }

    fun randomString(size: Int): String {
        val salt = StringBuilder()
        while (salt.length < size) {
            salt.append(saltChars[random.nextInt(saltChars.length)])
        }
        return salt.toString()
    }

    fun randomDigits(size: Int): String {
        val salt = StringBuilder()
        while (salt.length < size) {
            salt.append(saltNumbers[random.nextInt(saltNumbers.length)])
        }
        return salt.toString()
    }

    fun randomUuid(): String {
        return UUID.randomUUID().toString()
    }
}