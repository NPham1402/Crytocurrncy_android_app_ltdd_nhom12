package com.example.crytocurrency_ltdt;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class traslate_hiden_scoller_view implements View.OnTouchListener{
    private GestureDetector gestureDetector;

    public traslate_hiden_scoller_view(Context context,View viewanimation){
        gestureDetector=new GestureDetector(context,new simplegesturedetector(viewanimation));
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return gestureDetector.onTouchEvent(motionEvent);
    }

    public class simplegesturedetector extends GestureDetector.SimpleOnGestureListener{
        private View viewanimation;
        private boolean isFishanimation=true;

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if (distanceX>0)
            {
                hideview();
            }
            else {
                showview();
            }
            return super.onScroll(e1, e2, distanceX, distanceY);
        }

        private void showview() {
            if(viewanimation==null||viewanimation.getVisibility()==View.VISIBLE){
                return;
            }
            Animation animationup=AnimationUtils.loadAnimation(viewanimation.getContext(),R.anim.move_down);
            animationup.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    viewanimation.setVisibility(View.VISIBLE);
                    isFishanimation=false;
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    isFishanimation=true;
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            if(isFishanimation){
                viewanimation.startAnimation(animationup);
            }
        }

        private void hideview() {
            if(viewanimation==null||viewanimation.getVisibility()==View.GONE){
                return;
            }
            Animation animationdown=AnimationUtils.loadAnimation(viewanimation.getContext(),R.anim.move_up);
            animationdown.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    viewanimation.setVisibility(View.VISIBLE);
                    isFishanimation=false;
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    viewanimation.setVisibility(View.GONE);
                    isFishanimation=true;
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            if(isFishanimation){
                viewanimation.startAnimation(animationdown);
            }
        }

        public simplegesturedetector(View viewanimation) {
            this.viewanimation = viewanimation;
        }
    }
}
