package com.example.sarycatalogtask.utils.network

import java.io.IOException

class NoConnectivityException : IOException() {
    override val message: String
        get() = "No internet connection"
}