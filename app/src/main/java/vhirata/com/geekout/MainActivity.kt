package vhirata.com.geekout

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import vhirata.com.geekout.database.UserDBHelper
import vhirata.com.geekout.model.User

class MainActivity : AppCompatActivity() {

    private lateinit var usernameET: EditText
    private lateinit var passwordET: EditText

    private lateinit var loginBtn: TextView
    private lateinit var registerBtn: TextView

    private lateinit var db: UserDBHelper

    override fun onBackPressed() {
        AlertDialog.Builder(this)
            .setTitle(R.string.confirm_phrase)
            .setMessage(R.string.close_app_message)
            .setPositiveButton(R.string.yes) { dialogInterface: DialogInterface, i: Int ->
                finish()
            }
            .setNegativeButton(R.string.no) { dialogInterface: DialogInterface, i: Int -> }
            .show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loginBtn = findViewById(R.id.loginBtn)
        registerBtn = findViewById(R.id.registerNewBtn)

        usernameET = findViewById(R.id.userNameET)
        passwordET = findViewById(R.id.passwordET)

        db = UserDBHelper(this)

        registerBtn.setOnClickListener {
            startActivity(Intent(this@MainActivity, RegisterUserActivity::class.java))
        }

        loginBtn.setOnClickListener {
            val username = usernameET.text.toString()
            val password = passwordET.text.toString()

            if(username.isNotEmpty() && password.isNotEmpty()) {

                var user = User(username, password)

                val verifiedLogin = db.verifyLogin(user)
                val checkUser = db.checkUserOnDB(user.username)

                if (checkUser) {
                    if(verifiedLogin){
                        startActivity(Intent(this@MainActivity, HomeActivity::class.java))
                    } else {
                        Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    AlertDialog.Builder(this)
                        .setTitle(R.string.warning_title)
                        .setMessage(R.string.user_doesnt_exist_message)
                        .setNeutralButton(R.string.OK) { dialogInterface: DialogInterface, i: Int -> }
                        .show()
                }

            } else {
                Toast.makeText(this, "Please Enter All Credentials", Toast.LENGTH_SHORT).show()
            }
        }

    }
}