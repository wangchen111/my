package path.recordpath3d;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import cn.bluemobi.dylan.step.R;

/**
 * Created by wangchen on 2017/2/17.
 */

public class AnimationActivity extends Activity {
    private TextView textView;
    private Animation animation;
    private int count = 3;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int num = (int) msg.obj;
            if (num == 0) {
                textView.setText("GO");
                Intent intent = new Intent(AnimationActivity.this, PathActivity.class);
                startActivity(intent);
            } else{
                textView.setText(num + "");
                big();
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animation_activity);
        textView = (TextView) findViewById(R.id.text);
        animation = AnimationUtils.loadAnimation(this, R.anim.count_down_exit);
        textView.startAnimation(animation);
        big();
        delayedAnimation();
    }

    private void delayedAnimation() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                int num = --count;
                Message msg = handler.obtainMessage();
                msg.obj = num;
                handler.sendMessage(msg);
                if (num > 0)
                    delayedAnimation();
            }
        }, 1000);
    }


    public void big() {
        animation.reset();
        textView.startAnimation(animation);
    }


}
