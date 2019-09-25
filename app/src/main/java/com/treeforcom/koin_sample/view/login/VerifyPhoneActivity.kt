package com.treeforcom.koin_sample.view.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.treeforcom.koin_sample.R
import com.treeforcom.koin_sample.view.main.HomeActivity
import kotlinx.android.synthetic.main.activity_verify_phone.*
import java.util.concurrent.TimeUnit

class VerifyPhoneActivity : AppCompatActivity() {
    private lateinit var mVerificationID: String
    private lateinit var mResendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var credential: PhoneAuthCredential
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify_phone)
        initializeListener()
    }

    private fun initializeListener() {
        send.setOnClickListener {
            startPhoneNumberVerification("+84 " + phone.text.toString())
        }
        next.setOnClickListener {
            credential = PhoneAuthProvider.getCredential(mVerificationID, "451245")
            signInWithPhoneAuthCredential(credential)
        }
    }

    private fun startPhoneNumberVerification(phone: String) {
        mainProgressBar.visibility = View.VISIBLE
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            phone,
            60,
            TimeUnit.SECONDS,
            this,
            object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                    code.setText(p0.smsCode)
                    credential = PhoneAuthProvider.getCredential(mVerificationID, p0.smsCode?:"")
                    mainProgressBar.visibility = View.GONE
                }
                override fun onVerificationFailed(p0: FirebaseException) {
                    mainProgressBar.visibility = View.GONE
                    Toast.makeText(this@VerifyPhoneActivity, p0.message, Toast.LENGTH_SHORT).show()
                }
                override fun onCodeSent(
                    verificationId: String,
                    token: PhoneAuthProvider.ForceResendingToken
                ) {
                    super.onCodeSent(verificationId, token)
                    Toast.makeText(this@VerifyPhoneActivity, "sending....", Toast.LENGTH_SHORT).show()
                    mainProgressBar.visibility = View.GONE
                    mVerificationID = verificationId
                    mResendToken = token
                }
            })
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        mainProgressBar.visibility = View.VISIBLE
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                mainProgressBar.visibility = View.GONE
                when {
                    task.isSuccessful -> startActivity(Intent(this, HomeActivity::class.java))
                    else -> {
                        Toast.makeText(this,"Verify phone code failed", Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        FirebaseAuth.getInstance().signOut()
    }
}
