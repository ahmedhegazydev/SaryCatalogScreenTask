package com.example.sarycatalogtask.utils.logger

import android.util.Log
import com.example.sarycatalogtask.BuildConfig

/**
 * Helper class to print logs on console
 */
sealed class Logger {

    class Debug(tag : String?, log : String?) : Logger() {
        init {
            if(BuildConfig.DEBUG) {
                Log.d(tag, log ?: "")
            }
        }
    }

    class Info(tag : String?, info : String?) : Logger() {
        init {
            if(BuildConfig.DEBUG) {
                Log.i(tag, info ?: "")
            }
        }
    }

    class Error(tag : String?, error : String?) : Logger() {
        init {
            if(BuildConfig.DEBUG) {
                Log.e(tag, error ?: "")
            }
        }
    }
}