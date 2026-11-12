package com.example.util

import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import java.util.concurrent.Executor

object BiometricHelper {
    fun authenticate(
        activity: FragmentActivity,
        onSuccess: () -> Unit,
        onError: (Int, CharSequence) -> Unit,
        onFailed: () -> Unit
    ) {
        val executor = ContextCompat.getMainExecutor(activity)
        val callback = object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                onSuccess()
            }
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                onError(errorCode, errString)
            }
            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                onFailed()
            }
        }
        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("ยืนยันตัวตนเพื่อเข้าใช้งาน")
            .setSubtitle("ใช้ลายนิ้วมือหรือใบหน้าของคุณ")
            .setNegativeButtonText("ใช้รหัสผ่านแทน") // Or "ยกเลิก"
            .build()
        
        val biometricPrompt = BiometricPrompt(activity, executor, callback)
        biometricPrompt.authenticate(promptInfo)
    }
}
