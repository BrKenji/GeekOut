package vhirata.com.geekout

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import vhirata.com.geekout.database.UserDBHelper
import vhirata.com.geekout.model.User

class RegisterUserActivity : AppCompatActivity() {

    private lateinit var userET: EditText
    private lateinit var emailET: EditText
    private lateinit var passwordET: EditText
    private lateinit var confirmPasswordET: EditText

    private lateinit var registerBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_user)

        userET = findViewById(R.id.registerUsernameET)
        emailET = findViewById(R.id.registerEmailET)
        passwordET = findViewById(R.id.registerPasswordET)
        confirmPasswordET = findViewById(R.id.registerConfirmPasswordET)

        registerBtn = findViewById(R.id.registerBtn)

        val db = UserDBHelper(this)

        registerBtn.setOnClickListener {

            val username = userET.text.toString()
            val password = passwordET.text.toString()
            val conPassword = confirmPasswordET.text.toString()


            if(username.isNotEmpty() &&
                password.isNotEmpty() &&
                conPassword.isNotEmpty()){

                if (password == conPassword){
                    var checkUser = db.checkUserOnDB(username)
                    if(checkUser){
                        AlertDialog.Builder(this)
                            .setTitle(R.string.warning_title)
                            .setMessage(R.string.user_already_exist)
                            .setNeutralButton(R.string.OK, { dialogInterface: DialogInterface, i: Int -> })
                            .show()
                    } else {
                        var user = User(username, password)
                        val registrationResult = db.insertData(user)

                        if (registrationResult){
                            Toast.makeText(this, "Registration Succeeded", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(applicationContext, MainActivity::class.java))
                        } else {
                            Toast.makeText(this, "Registration Failed", Toast.LENGTH_SHORT).show()
                        }

                    }
                } else {
                    var builder = AlertDialog.Builder(this)
                    builder.setTitle(R.string.warning_title)
                        .setMessage(R.string.password_doesnt_match)
                        .setNeutralButton(R.string.OK, { dialogInterface: DialogInterface, i: Int -> })
                        .show()
                }
            } else {
                Toast.makeText(this, "Please Fill All Fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}