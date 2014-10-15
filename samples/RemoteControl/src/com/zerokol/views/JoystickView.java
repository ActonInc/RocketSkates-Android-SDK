package com.zerokol.views;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class JoystickView extends View {
    // Constants
    private final double RAD = 57.2957795;
    public final static long DEFAULT_LOOP_INTERVAL = 250;
    // Variables
    private OnJoystickMoveListener onJoystickMoveListener; // Listener
    private int xPosition = 0; // Touch x position
    private int yPosition = 0; // Touch y position
    private double centerX = 0; // Center view x position
    private double centerY = 0; // Center view y position
    private Paint mainCircle;
    private Paint secondaryCircle;
    private Paint button;
    private Paint horizontalLine;
    private Paint verticalLine;
    private int joystickRadius;
    private int buttonRadius;
    private int lastAngle = 0;
    private int lastPower = 0;
    
    private boolean initialized = false;
    
    private Timer notifyTimer;
    private TimerTask notifyTask;
    
    public JoystickView(Context context) {
        super(context);
    }

    public JoystickView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initJoystickView();
    }

    public JoystickView(Context context, AttributeSet attrs, int defaultStyle) {
        super(context, attrs, defaultStyle);
        initJoystickView();
    }

    protected void initJoystickView() {
        mainCircle = new Paint(Paint.ANTI_ALIAS_FLAG);
        mainCircle.setColor(Color.WHITE);
        mainCircle.setStyle(Paint.Style.FILL_AND_STROKE);

        secondaryCircle = new Paint();
        secondaryCircle.setColor(Color.GREEN);
        secondaryCircle.setStyle(Paint.Style.STROKE);

        verticalLine = new Paint();
        verticalLine.setStrokeWidth(5);
        verticalLine.setColor(Color.RED);

        horizontalLine = new Paint();
        horizontalLine.setStrokeWidth(2);
        horizontalLine.setColor(Color.BLACK);

        button = new Paint(Paint.ANTI_ALIAS_FLAG);
        button.setColor(Color.RED);
        button.setStyle(Paint.Style.FILL);
        
        notifyTimer = new Timer();
    }

    @Override
    protected void onFinishInflate() {
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // setting the measured values to resize the view to a certain width and
        // height
        int d = Math.min(measure(widthMeasureSpec), measure(heightMeasureSpec));

        setMeasuredDimension(d, d);

        if (!initialized) {
            initialized = true;
            
            xPosition = (int) (d * 0.5);
            yPosition = (int) (d * 0.5);
            
            buttonRadius = (int) (d / 2 * 0.25);
            joystickRadius = (int) (d / 2 * 0.75);
        }
    }

    private int measure(int measureSpec) {
        int result = 0;

        // Decode the measurement specifications.
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.UNSPECIFIED) {
            // Return a default size of 200 if no bounds are specified.
            result = 200;
        } else {
            // As you want to fill the available space
            // always return the full available bounds.
            result = specSize;
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // super.onDraw(canvas);
        centerX = (getWidth()) / 2;
        centerY = (getHeight()) / 2;
        
        // painting the main circle
        canvas.drawCircle((int) centerX, (int) centerY, joystickRadius, mainCircle);
        // painting the secondary circle
        canvas.drawCircle((int) centerX, (int) centerY, joystickRadius / 2, secondaryCircle);
        // paint lines
        canvas.drawLine((float) centerX, (float) centerY, (float) (centerX + joystickRadius), (float) centerY, verticalLine);
        canvas.drawLine((float) (centerX - joystickRadius), (float) centerY, (float) (centerX + joystickRadius), (float) centerY, horizontalLine);
        canvas.drawLine((float) centerX, (float) (centerY + joystickRadius), (float) centerX, (float) (centerY - joystickRadius), horizontalLine);

        // painting the move button
        canvas.drawCircle(xPosition, yPosition, buttonRadius, button);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        xPosition = (int) event.getX();
        yPosition = (int) event.getY();

        double abs = Math.sqrt((xPosition - centerX) * (xPosition - centerX) + (yPosition - centerY) * (yPosition - centerY));
        if (abs > joystickRadius) {
            xPosition = (int) ((xPosition - centerX) * joystickRadius / abs + centerX);
            yPosition = (int) ((yPosition - centerY) * joystickRadius / abs + centerY);
        }

        invalidate();
        
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                notifyTask = new TimerTask() {
                    @Override
                    public void run() {
                        notifyChange();
                    }
                };
                notifyTimer.scheduleAtFixedRate(notifyTask, 0, DEFAULT_LOOP_INTERVAL);
                
                notifyChange();
                break;
            case MotionEvent.ACTION_UP:
                xPosition = (int) centerX;
                yPosition = (int) centerY;
                notifyTask.cancel();
                
                notifyChange();
                break;
        }
        
        return true;
    }

    private int getAngle() {
        if (xPosition > centerX) {
            if (yPosition < centerY) {
                return lastAngle = (int) (Math.atan((yPosition - centerY)
                    / (xPosition - centerX))
                    * RAD + 90);
            } else if (yPosition > centerY) {
                return lastAngle = (int) (Math.atan((yPosition - centerY)
                    / (xPosition - centerX)) * RAD) + 90;
            } else {
                return lastAngle = 90;
            }
        } else if (xPosition < centerX) {
            if (yPosition < centerY) {
                return lastAngle = (int) (Math.atan((yPosition - centerY)
                    / (xPosition - centerX))
                    * RAD - 90);
            } else if (yPosition > centerY) {
                return lastAngle = (int) (Math.atan((yPosition - centerY)
                    / (xPosition - centerX)) * RAD) - 90;
            } else {
                return lastAngle = -90;
            }
        } else {
            if (yPosition <= centerY) {
                return lastAngle = 0;
            } else {
                if (lastAngle < 0) {
                    return lastAngle = -180;
                } else {
                    return lastAngle = 180;
                }
            }
        }
    }

    private int getPower() {
        return (int) (100 * Math.sqrt((xPosition - centerX)
            * (xPosition - centerX) + (yPosition - centerY)
            * (yPosition - centerY)) / joystickRadius);
    }
    
    private void notifyChange() {
        if (this.onJoystickMoveListener != null) {
            onJoystickMoveListener.onValueChanged((450 - getAngle()) % 360, getPower());
        }
    }

    public void setOnJoystickMoveListener(OnJoystickMoveListener listener) {
        this.onJoystickMoveListener = listener;
    }

    public static interface OnJoystickMoveListener {
        public void onValueChanged(int angle, int power);
    }
}