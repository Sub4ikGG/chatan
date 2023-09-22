package ru.efremovkirill.ktorhandler

interface BaseResponse <T> {

    fun toModel(): T

}