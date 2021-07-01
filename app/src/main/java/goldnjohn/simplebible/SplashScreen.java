package goldnjohn.simplebible;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {

    Animation fade, bottom, out;
    ImageView logo;
    TextView app_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        logo = findViewById(R.id.imageView);
        app_name = findViewById(R.id.textView2);
        fade = AnimationUtils.loadAnimation(this,R.anim.fade);
        out = AnimationUtils.loadAnimation(this,R.anim.fade_out);
        bottom = AnimationUtils.loadAnimation(this,R.anim.slide_in_bottom);
        logo.setAnimation(fade);
        app_name.setAnimation(bottom);
        new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                Intent i = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(i);
                finish();
                }
            }, 3000);
    }
}
