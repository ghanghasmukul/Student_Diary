package com.example.studentdiary

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.studentdiary.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.sign_up_dialog.*
import kotlinx.android.synthetic.main.update_records_dialog.*


class MainActivity : AppCompatActivity() {
    lateinit var mGoogleSignInClient: GoogleSignInClient
    val RC_SIGN_IN: Int = 1
    lateinit var gso: GoogleSignInOptions
    lateinit var mAuth: FirebaseAuth
    private lateinit var emailET: EditText
    private lateinit var passwordET: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        val root = setContentView(binding.root)
        mAuth = FirebaseAuth.getInstance()
        emailET = binding.editTextEmailAddress
        passwordET = binding.editTextPassword

        createRequest()

        binding.signInButton.setOnClickListener {
            signIn()
        }
        binding.buttonLogin.setOnClickListener {
            logInUsingEmails()
            // registerNewUser()
        }
        binding.textViewRegister.setOnClickListener {


            onRegisterTVClick()


        }
        return root


    }

    private fun onRegisterTVClick() {
        val signUpDialog = Dialog(this)
        signUpDialog.setContentView(R.layout.sign_up_dialog)
        val width: Int = WindowManager.LayoutParams.MATCH_PARENT
        val height: Int = WindowManager.LayoutParams.WRAP_CONTENT
        signUpDialog.window?.setLayout(width, height)
        signUpDialog.show()
        val etEmail: EditText? = signUpDialog.etDialogEmail
        val etPassword: EditText? = signUpDialog.etDialogPassword
        val btnRegister: Button = signUpDialog.btnSignUp

        btnRegister.setOnClickListener {
            val email = etEmail?.text.toString()
            val password = etPassword?.text.toString()
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Fill carefully", Toast.LENGTH_LONG).show()
            } else {
                signUpDialog.dismiss()
                registerNewUser(email, password)
            }
        }

    }

    private fun createRequest() {
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

    }

    private fun registerNewUser(emailAdd: String, password: String) {

        mAuth.createUserWithEmailAndPassword(emailAdd, password).addOnCompleteListener {
            if (it.isSuccessful) {
                val intent = Intent(this, BottomNavigation::class.java)
                startActivity(intent)
                finish()
            }
        }.addOnFailureListener { exception ->
            Toast.makeText(applicationContext, exception.localizedMessage, Toast.LENGTH_LONG).show()
        }

    }

    fun logInUsingEmails() {
        val emailAdd = emailET.text.toString()
        val password = passwordET.text.toString()
        mAuth.signInWithEmailAndPassword(emailAdd, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val intent = Intent(this, BottomNavigation::class.java)
                startActivity(intent)
                finish()
            }
        }.addOnFailureListener { exception ->
            Toast.makeText(applicationContext, exception.localizedMessage, Toast.LENGTH_LONG).show()
        }
    }


    private fun signIn() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)

            try {
                val account = task.getResult(ApiException::class.java)!!
                firebaseAuthWithGoogle(account)
                Log.i("login", "Name - ${account.givenName} Profile Pic URL - ${account.photoUrl}")

                Toast.makeText(this, "Welcome - ${account.displayName}", Toast.LENGTH_LONG).show()
            } catch (e: ApiException) {
                Log.e("login", "${task.exception}")
                Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT)
                    .show()
            }

        }
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)

        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this, BottomNavigation::class.java)
                    startActivity(intent)
                } else {
                    Log.e("login", "${task.exception}")
                    Toast.makeText(this, "Login Failed: ", Toast.LENGTH_SHORT).show()
                }
            }
    }


    override fun onStart() {
        super.onStart()
        val user = mAuth.currentUser
        if (user != null) {
            val intent = Intent(this, BottomNavigation::class.java)
            startActivity(intent)
        }
    }

}