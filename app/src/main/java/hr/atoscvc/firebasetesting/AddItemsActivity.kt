package hr.atoscvc.firebasetesting

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_add_items.*

class AddItemsActivity : AppCompatActivity() {

    private lateinit var mFirebaseDatabase: FirebaseDatabase
    private lateinit var mAuth: FirebaseAuth
    private var mAuthListener: FirebaseAuth.AuthStateListener? = null
    private lateinit var mRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_items)

        mFirebaseDatabase = FirebaseDatabase.getInstance()
        mRef = mFirebaseDatabase.reference

        mAuth = FirebaseAuth.getInstance()

        mAuthListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            val user = firebaseAuth.currentUser
            if (user == null) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        mRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
               // val value = dataSnapshot.getValue(String::class.java)

            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }


    fun onAdd(@Suppress("UNUSED_PARAMETER") view: View) {
        val food: String = etFood.text.toString()

        if (food != "") {
            val user: FirebaseUser? = mAuth.currentUser
            val userID: String? = user?.uid
            Toast.makeText(this, user.toString(), Toast.LENGTH_SHORT).show()
            mRef.child(userID.toString()).child("Food").child("Favorite Food").child(food).setValue("true")
        }
    }

    fun onLogout(@Suppress("UNUSED_PARAMETER") view: View) {
        mAuth.signOut()
        Toast.makeText(this, "Signing out", Toast.LENGTH_SHORT).show()
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
