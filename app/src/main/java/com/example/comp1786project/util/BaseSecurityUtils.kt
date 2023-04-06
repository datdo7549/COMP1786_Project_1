package com.example.comp1786project.util

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

abstract class BaseSecurityUtils {
    private val provider = "AndroidKeyStore"
    val cipher: Cipher by lazy {
        Cipher.getInstance("AES/GCM/NoPadding")
    }
    val charset by lazy {
        charset("UTF-8")
    }
    private val keyStore by lazy {
        KeyStore.getInstance(provider).apply {
            load(null)
        }
    }
    private val keyGenerator by lazy {
        KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, provider)
    }

    fun generateSecretKey(
        keyAlias: String,
        userAuthenticationRequired: Boolean = false
    ): SecretKey {
        getSecretKey(keyAlias)?.let {
            return it
        }
        return keyGenerator.apply {
            init(
                KeyGenParameterSpec
                    .Builder(keyAlias, KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                    /**
                     * setUserAuthenticationRequired(true) ensures that the user is only authorized
                     * to use the key if they authenticated themselves using the password/PIN/pattern
                     * or biometric. The key can only be generated if secure lock screen is set up
                     */
                    .setUserAuthenticationRequired(userAuthenticationRequired)
                    .build()
            )
        }.generateKey()
    }

    fun getSecretKey(keyAlias: String): SecretKey? =
        (keyStore.getEntry(keyAlias, null) as? KeyStore.SecretKeyEntry)?.secretKey
}