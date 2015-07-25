package android.example.com.testanim;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.ArcShape;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import java.io.LineNumberReader;


public class MainActivity3 extends ActionBarActivity {


    ImageView imgCircle, imgLine;
    int end = 0;
    ArcShape arc;
    Rect lineRect;
    Drawable lineDrawble;

    ShapeDrawable shapeCircle;
    ValueAnimator circleAnimation, lineAnimation;

   long duration = 5000;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main3);

        imgCircle = (ImageView) findViewById(R.id.circle_placeholder);


        imgCircle.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onGlobalLayout() {
                imgCircle.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                initShape(imgCircle.getWidth());
            }
        });

        imgLine = (ImageView) findViewById(R.id.line_placeholder);
        imgLine.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onGlobalLayout() {
                imgLine.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                lineDrawble = imgLine.getDrawable();
                lineRect = lineDrawble.getBounds();
                Log.d("", "### line lineRect" + lineRect + " " + lineRect.width() + " " + lineRect.left + " " + lineRect.top + " " + lineRect.height());
                initLineAnim(lineRect.width(), lineRect.centerX());
                lineDrawble.setBounds(lineRect.centerX(), 0, lineRect.centerX(), 100);
                imgLine.invalidate();
            }
        });


        initCircleAnim();

    }

    private void initCircleAnim() {
        circleAnimation = ValueAnimator.ofInt(0, 360);
        circleAnimation.setDuration(duration);
        circleAnimation.setInterpolator(new LinearInterpolator());
        circleAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int end = (int) animation.getAnimatedValue();
                arc = new ArcShape(-90, end);
                shapeCircle.setShape(arc);
            }
        });
    }

    private void initLineAnim(int width, final int centerX) {
        lineAnimation = ValueAnimator.ofInt(0, width/2);
        lineAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int width = (int) animation.getAnimatedValue();
                lineDrawble.setBounds(centerX - width, 0, centerX + width, 100);
            }
        });
    }

    private void initShape(int width) {
        arc = new ArcShape(0, end);
        shapeCircle = new ShapeDrawable(arc);
        shapeCircle.setIntrinsicHeight(width);
        shapeCircle.setIntrinsicWidth(width);
        shapeCircle.getPaint().setColor(Color.YELLOW);
        imgCircle.setImageDrawable(shapeCircle);
    }

    public void reset(View view) {
        end = 0;
        arc = new ArcShape(-90, end);
        shapeCircle.setShape(arc);
    }

    public void animShape(View view){
        circleAnimation.setInterpolator(new LinearInterpolator());
        circleAnimation.start();
    }

    public void animLine(View view){
        lineAnimation.setInterpolator(new BounceInterpolator());
        lineAnimation.setDuration(2500);
        lineAnimation.start();
    }

    public void reverse(View view) {
        circleAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        circleAnimation.reverse();
    }

    public void reverseLine(View view) {
        lineAnimation.setInterpolator(new AccelerateInterpolator());
        lineAnimation.setDuration(1000);
        lineAnimation.reverse();
    }


    public void cancel(View view) {
        circleAnimation.cancel();
    }






    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }


}
