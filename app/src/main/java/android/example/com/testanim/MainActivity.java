package android.example.com.testanim;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;


public class MainActivity extends ActionBarActivity {

    ImageView glass, line;
    private boolean isAnimating;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        glass = (ImageView) findViewById(R.id.pigen_glass);
        glass.setClickable(true);
        glass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateScaleClick(v);
            }
        });

        line = (ImageView) findViewById(R.id.line);
        line.setClickable(true);
        line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animWidtUp(v);
            }
        });

        OvalShape oval = new OvalShape();

        final ShapeDrawable circle = new ShapeDrawable(new OvalShape());
        //Create your shape programatically
        ValueAnimator animation = ValueAnimator.ofFloat(24.0f,12.0f);
        animation.setDuration(1000);
        animation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                circle.getPaint().setStrokeWidth((Float) animation.getAnimatedValue());
            }
        });

        ImageView image = (ImageView)findViewById(R.id.image_shape);
        image.setImageDrawable(circle);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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
        if (id == R.id.action_start) {
            animateScaleUpDown(glass);
            return true;
        }
        if (id == R.id.action_reset) {
            glass.setScaleX(1f);
            glass.setScaleY(1f);
            return true;
        }

        if (id == R.id.action_isanim) {
            Log.d("", "#### anim? " + isAnimating + glass.getScaleX());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    boolean isScaleUp = false;
    private void animateScaleClick(final View view){
        view.clearAnimation();
        isScaleUp = !isScaleUp;
        view.clearAnimation();
        float toScale = isScaleUp ? 1.2f : 1f;
        long time = isScaleUp ? 750l : 2000l;
        Interpolator  i = isScaleUp? new AccelerateInterpolator() : new AccelerateDecelerateInterpolator();

        view.animate().scaleX(toScale).scaleY(toScale).setDuration(time).setInterpolator(i).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                animation.getDuration();
                long newTime = (long)(isScaleUp? ((1.2-view.getScaleX())/.2f)*750  : ((view.getScaleX() -1)/.2f)*2000);
                animation.setDuration(newTime);
                isAnimating = true;
                Log.d("", "### onAnimationStart-----duration - " +  animation.getDuration());
                //  Log.d("", "### onAnimationStart-----time- " + newTime);
                Log.d("", "### onAnimationStart------ " + view.getScaleX() + " " + view.getScaleY());
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Log.d("", "### onAnimationEnd " + view.getScaleX() + " " + view.getScaleY());
                //view.clearAnimation();
                //view.animate().scaleX(1f).scaleY(1f).setStartDelay(1000).setDuration(2500).setInterpolator(new AccelerateDecelerateInterpolator()).setListener(null);
                isAnimating = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                Log.d("", "### onAnimationCancel-----duration - " +  animation.getDuration());
                Log.d("", "### onAnimationCancel!!!!!! " + view.getScaleX() + " " + view.getScaleY());
                isAnimating = false;
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                Log.d("", "### onAnimationRepeat " + view.getScaleX() + " " + view.getScaleY());
            }
        });
    }

    private void animWidtUp(View view){
        view.animate().xBy(50).yBy(70).setDuration(1000);
    }


    private void animateScaleUpDown(final View view){
        view.clearAnimation();

        view.animate().scaleX(1.4f).scaleY(1.4f).setDuration(1000l).setStartDelay(0).setInterpolator(new AccelerateInterpolator()).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                isAnimating = true;
             //   Log.d("", "### onAnimationStart-----duration - " +  animation.getDuration());
              //  Log.d("", "### onAnimationStart-----time- " + newTime);
                Log.d("", "### onAnimationStart animateScaleUpDown ----- " + view.getScaleX() + " " + view.getScaleY());
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Log.d("", "### onAnimationEnd " + view.getScaleX() + " " + view.getScaleY());
                view.clearAnimation();
                view.animate().scaleX(1f).scaleY(1f).setStartDelay(1000).setDuration(2500).setInterpolator(new AccelerateDecelerateInterpolator()).setListener(null);
                isAnimating = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                Log.d("", "### onAnimationCancel!!!!!! " + view.getScaleX() + " " + view.getScaleY());
                isAnimating = false;
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                Log.d("", "### onAnimationRepeat " + view.getScaleX() + " " + view.getScaleY());
            }
        });
    }
}
