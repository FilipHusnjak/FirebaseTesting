package hr.atoscvc.firebasetesting

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private var mAuthListener: FirebaseAuth.AuthStateListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAuth = FirebaseAuth.getInstance()

        mAuthListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            val user = firebaseAuth.currentUser
            if (user != null) {
                val intent = Intent(this, AddItemsActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Successfully signed out", Toast.LENGTH_SHORT).show()
            }
        }

    }


    fun onLogin(@Suppress("UNUSED_PARAMETER") view: View) {
        val email: String = etEmail.text.toString()
        val password: String = etPassword.text.toString()

        if (email != "" && password != "") {
            Toast.makeText(this, "Signing in", Toast.LENGTH_SHORT).show()
            mAuth.signInWithEmailAndPassword(email, password)
        } else {
            Toast.makeText(this, "Email or password fields cannot be empty", Toast.LENGTH_SHORT).show()
        }

    }


    override fun onResume() {
        super.onResume()
        mAuthListener?.let { mAuth.addAuthStateListener(it) }
    }

    override fun onPause() {
        super.onPause()
        mAuthListener?.let { mAuth.removeAuthStateListener(it) }
    }
}
