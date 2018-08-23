package jp.co.terraresta.androidlesson

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import jp.co.terraresta.androidlesson.view.activity.common.MainTabActivity
import jp.co.terraresta.androidlesson.view.activity.login.LoginActivity
import jp.co.terraresta.androidlesson.view.activity.sign_up.SignUpActivity
import java.util.prefs.Preferences
import kotlin.math.sign

class MainActivity : AppCompatActivity() {

    var login: Button? = null;
    var signup: Button? = null;
    var pref: jp.co.terraresta.androidlesson.common.Preferences? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setView();
        autoDirectActivity()
    }

    fun autoDirectActivity() {
        pref = jp.co.terraresta.androidlesson.common.Preferences()
        if(pref?.getToken(this)?.length != 0) {
            var homeIntent = Intent(this, MainTabActivity::class.java)
            startActivity(homeIntent)
            finish()
        }
    }

    fun setView(){
        signup = findViewById(R.id.btn_signup) as Button?;
        login = findViewById(R.id.btn_login) as Button?;

        signup?.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java);
            startActivity(intent);
        }

        login?.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java);
            startActivity(intent);
        }

    }

}
