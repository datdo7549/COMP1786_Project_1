package com.example.comp1786project.util

import android.security.keystore.KeyPermanentlyInvalidatedException
import com.example.comp1786project.util.extensions.defaultEmpty
import javax.crypto.Cipher
import javax.crypto.spec.GCMParameterSpec

class BiometricSecurityUtils : BaseSecurityUtils() {
    private val biometricSecurityKeyAlias = "biometric-key"

    fun getInitializedCipherForEncryption(): Cipher? {
        return try {
            val secretKey = generateSecretKey(biometricSecurityKeyAlias, true)
            cipher.init(Cipher.ENCRYPT_MODE, secretKey)
            cipher
        } catch (e: KeyPermanentlyInvalidatedException) {
            /***
             * It will return this exception if the key can no longer be used because it has been permanently invalidated.
             * More information please read this: https://developer.android.com/reference/android/security/keystore/KeyPermanentlyInvalidatedException
             */
            null
        }
    }

    fun getInitializedCipherForDecryption(initializationVector: ByteArray? = null): Cipher? {
        return try {
            val secretKey = generateSecretKey(biometricSecurityKeyAlias, false)
            cipher.init(
                Cipher.DECRYPT_MODE,
                secretKey,
                GCMParameterSpec(128, initializationVector)
            )
            cipher
        } catch (e: KeyPermanentlyInvalidatedException) {
            null
        }

    }

    fun encryptData(cipher: Cipher, text: String): android.util.Pair<ByteArray, ByteArray> {
        return android.util.Pair(cipher.doFinal(text.toByteArray(charset)), cipher.iv)
    }

    fun decryptData(cipher: Cipher, encryptedData: ByteArray): String {
        return cipher.doFinal(encryptedData).toString(charset).defaultEmpty()
    }
}