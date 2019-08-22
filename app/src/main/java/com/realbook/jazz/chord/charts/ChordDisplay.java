package com.realbook.jazz.chord.charts;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.ActionBar;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.constraint.Guideline;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class ChordDisplay extends AppCompatActivity {

    int clickCount = 0;

    int width;
    int height;
    String defaultMusicalKey;
    String currentMusicalKey;
    int titleAndAuthorHeight;
    int leftMarginSize;
    int rightMarginSize;
    int numLines;
    int lineHeight;
    int lineTopAndBottomMargins;
    int chordFontSize;

    Guideline A;
    Guideline B;
    Guideline C;
    Guideline D;
    Guideline E;
    Guideline F;
    Guideline G;
    Guideline H;
    Guideline I;
    Guideline J;
    Guideline K;
    Guideline L;
    Guideline M;
    Guideline N;
    Guideline O;
    Guideline P;
    Guideline Q;
    Guideline titleBottom;
    Guideline authorBottom;
    Guideline T;
    Guideline U;
    Guideline V;
    Guideline W;
    Guideline X;
    Guideline Y;
    Guideline Z;
    Guideline AA;
    Guideline AB;
    Guideline AC;
    Guideline AD;
    Guideline AE;
    Guideline AF;
    Guideline AG;
    Guideline AH;
    Guideline AI;
    Guideline AJ;
    Guideline AK;
    Guideline AL;
    Guideline AM;
    Guideline AN;
    Guideline AO;
    Guideline AP;
    Guideline Left;
    Guideline Top;
    Guideline Right;

    Guideline[] lineTops;
    Guideline[] lineBottoms;
    Guideline[] verticalGuidelines;

    int guidelineMarginA;
    int guidelineMarginB;
    int guidelineMarginC;
    int guidelineMarginD;
    int guidelineMarginE;
    int guidelineMarginF;
    int guidelineMarginG;
    int guidelineMarginH;
    int guidelineMarginI;
    int guidelineMarginJ;
    int guidelineMarginK;
    int guidelineMarginL;
    int guidelineMarginM;
    int guidelineMarginN;
    int guidelineMarginO;
    int guidelineMarginP;
    int guidelineMarginQ;
    int guidelineMarginTitleBottom;
    int guidelineMarginAuthorBottom;
    int guidelineMarginT;
    int guidelineMarginU;
    int guidelineMarginV;
    int guidelineMarginW;
    int guidelineMarginX;
    int guidelineMarginY;
    int guidelineMarginZ;
    int guidelineMarginAA;
    int guidelineMarginAB;
    int guidelineMarginAC;
    int guidelineMarginAD;
    int guidelineMarginAE;
    int guidelineMarginAF;
    int guidelineMarginAG;
    int guidelineMarginAH;
    int guidelineMarginAI;
    int guidelineMarginAJ;
    int guidelineMarginAK;
    int guidelineMarginAL;
    int guidelineMarginAM;
    int guidelineMarginAN;
    int guidelineMarginAO;
    int guidelineMarginAP;
    int guidelineMarginLeft;
    int guidelineMarginTop;
    int guidelineMarginRight;

    int[] lineTopMargins;
    int[] lineBottomMargins;
    int[] verticalGuidelineMargins;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chord_display);

        //getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        //getSupportActionBar().setCustomView(R.layout.chord_display_banner);

        String title = getIntent().getStringExtra("title");
        String author = getIntent().getStringExtra("author");
        ArrayList<String> list = getIntent().getStringArrayListExtra("list");

        width = getScreenWidthInPixels();
        height = getScreenHeightInPixels() - getStatusBarHeight();
        numLines = list.size() - 2;
        defaultMusicalKey = list.get(1);
        currentMusicalKey = list.get(1);

        guidelineMarginA = (int) width * 3 / 64;
        guidelineMarginB = guidelineMarginA + (int) ((double)width * 3.7 / 64);
        guidelineMarginC = guidelineMarginA + (int) ((double)width * 3.7 / 64 * 2);
        guidelineMarginD = guidelineMarginA + (int) ((double)width * 3.7 / 64 * 3);
        guidelineMarginE = guidelineMarginA + (int) ((double)width * 3.7 / 64 * 4);
        guidelineMarginF = guidelineMarginA + (int) ((double)width * 3.7 / 64 * 5);
        guidelineMarginG = guidelineMarginA + (int) ((double)width * 3.7 / 64 * 6);
        guidelineMarginH = guidelineMarginA + (int) ((double)width * 3.7 / 64 * 7);
        guidelineMarginI = guidelineMarginA + (int) ((double)width * 3.7 / 64 * 8);
        guidelineMarginJ = guidelineMarginA + (int) ((double)width * 3.7 / 64 * 9);
        guidelineMarginK = guidelineMarginA + (int) ((double)width * 3.7 / 64 * 10);
        guidelineMarginL = guidelineMarginA + (int) ((double)width * 3.7 / 64 * 11);
        guidelineMarginM = guidelineMarginA + (int) ((double)width * 3.7 / 64 * 12);
        guidelineMarginN = guidelineMarginA + (int) ((double)width * 3.7 / 64 * 13);
        guidelineMarginO = guidelineMarginA + (int) ((double)width * 3.7 / 64 * 14);
        guidelineMarginP = guidelineMarginA + (int) ((double)width * 3.7 / 64 * 15);
        guidelineMarginQ = guidelineMarginA + (int) ((double)width * 3.7 / 64 * 16);

        guidelineMarginLeft = 0;
        guidelineMarginTop = 0;
        guidelineMarginRight = width;

        guidelineMarginTitleBottom = width * 4 / 64;
        guidelineMarginAuthorBottom = width * 7 / 64;

        if(numLines <= 6) {
            lineHeight = width * 8 / 64;
        }
        else {
            lineHeight = (int) ((height - guidelineMarginAuthorBottom) / numLines * 2 / 3);
        }
        guidelineMarginU = guidelineMarginAuthorBottom + lineHeight / 4;

        guidelineMarginW = guidelineMarginU + (int) (lineHeight * 3 / 2);
        guidelineMarginY = guidelineMarginU + (int) (lineHeight * 3 / 2 * 2);
        guidelineMarginAA = guidelineMarginU + (int) (lineHeight * 3 / 2 * 3);
        guidelineMarginAC = guidelineMarginU + (int) (lineHeight * 3 / 2 * 4);
        guidelineMarginAE = guidelineMarginU + (int) (lineHeight * 3 / 2 * 5);
        guidelineMarginAG = guidelineMarginU + (int) (lineHeight * 3 / 2 * 6);
        guidelineMarginAI = guidelineMarginU + (int) (lineHeight * 3 / 2 * 7);
        guidelineMarginAK = guidelineMarginU + (int) (lineHeight * 3 / 2 * 8);
        guidelineMarginAM = guidelineMarginU + (int) (lineHeight * 3 / 2 * 9);
        guidelineMarginAO = guidelineMarginU + (int) (lineHeight * 3 / 2 * 10);

        guidelineMarginV = guidelineMarginU + lineHeight;

        guidelineMarginX = guidelineMarginV + (int) (lineHeight * 3 / 2);
        guidelineMarginZ = guidelineMarginV + (int) (lineHeight * 3 / 2 * 2);
        guidelineMarginAB = guidelineMarginV + (int) (lineHeight * 3 / 2 * 3);
        guidelineMarginAD = guidelineMarginV + (int) (lineHeight * 3 / 2 * 4);
        guidelineMarginAF = guidelineMarginV + (int) (lineHeight * 3 / 2 * 5);
        guidelineMarginAH = guidelineMarginV + (int) (lineHeight * 3 / 2 * 6);
        guidelineMarginAJ = guidelineMarginV + (int) (lineHeight * 3 / 2 * 7);
        guidelineMarginAL = guidelineMarginV + (int) (lineHeight * 3 / 2 * 8);
        guidelineMarginAN = guidelineMarginV + (int) (lineHeight * 3 / 2 * 9);
        guidelineMarginAP = guidelineMarginV + (int) (lineHeight * 3 / 2 * 10);

        guidelineMarginT = (int) ((guidelineMarginU + guidelineMarginV) / 2);

        lineTopMargins = new int[]{guidelineMarginU, guidelineMarginW, guidelineMarginY, guidelineMarginAA,
                guidelineMarginAC, guidelineMarginAE, guidelineMarginAG, guidelineMarginAI,
                guidelineMarginAK, guidelineMarginAM, guidelineMarginAO};
        lineBottomMargins = new int[]{guidelineMarginV, guidelineMarginX, guidelineMarginZ, guidelineMarginAB,
                guidelineMarginAD, guidelineMarginAF, guidelineMarginAH, guidelineMarginAJ,
                guidelineMarginAL, guidelineMarginAN, guidelineMarginAP};
        verticalGuidelineMargins = new int[]{guidelineMarginA, guidelineMarginB, guidelineMarginC,
                guidelineMarginD, guidelineMarginE, guidelineMarginF, guidelineMarginG, guidelineMarginH,
                guidelineMarginI, guidelineMarginJ, guidelineMarginK, guidelineMarginL, guidelineMarginM,
                guidelineMarginN, guidelineMarginO,guidelineMarginP, guidelineMarginQ};

        A = findViewById(R.id.A);
        A.setGuidelineBegin(guidelineMarginA);
        B = findViewById(R.id.B);
        B.setGuidelineBegin(guidelineMarginB);
        C = findViewById(R.id.C);
        C.setGuidelineBegin(guidelineMarginC);
        D = findViewById(R.id.D);
        D.setGuidelineBegin(guidelineMarginD);
        E = findViewById(R.id.E);
        E.setGuidelineBegin(guidelineMarginE);
        F = findViewById(R.id.F);
        F.setGuidelineBegin(guidelineMarginF);
        G = findViewById(R.id.G);
        G.setGuidelineBegin(guidelineMarginG);
        H = findViewById(R.id.H);
        H.setGuidelineBegin(guidelineMarginH);
        I = findViewById(R.id.I);
        I.setGuidelineBegin(guidelineMarginI);
        J = findViewById(R.id.J);
        J.setGuidelineBegin(guidelineMarginJ);
        K = findViewById(R.id.K);
        K.setGuidelineBegin(guidelineMarginK);
        L = findViewById(R.id.L);
        L.setGuidelineBegin(guidelineMarginL);
        M = findViewById(R.id.M);
        M.setGuidelineBegin(guidelineMarginM);
        N = findViewById(R.id.N);
        N.setGuidelineBegin(guidelineMarginN);
        O = findViewById(R.id.O);
        O.setGuidelineBegin(guidelineMarginO);
        P = findViewById(R.id.P);
        P.setGuidelineBegin(guidelineMarginP);
        Q = findViewById(R.id.Q);
        Q.setGuidelineBegin(guidelineMarginQ);

        titleBottom = findViewById(R.id.titleBottom);
        titleBottom.setGuidelineBegin(guidelineMarginTitleBottom);
        authorBottom = findViewById(R.id.authorBottom);
        authorBottom.setGuidelineBegin(guidelineMarginAuthorBottom);

        U = findViewById(R.id.U);
        U.setGuidelineBegin(guidelineMarginU);
        V = findViewById(R.id.V);
        V.setGuidelineBegin(guidelineMarginV);
        W = findViewById(R.id.W);
        W.setGuidelineBegin(guidelineMarginW);
        X = findViewById(R.id.X);
        X.setGuidelineBegin(guidelineMarginX);
        Y = findViewById(R.id.Y);
        Y.setGuidelineBegin(guidelineMarginY);
        Z = findViewById(R.id.Z);
        Z.setGuidelineBegin(guidelineMarginZ);
        AA = findViewById(R.id.AA);
        AA.setGuidelineBegin(guidelineMarginAA);
        AB = findViewById(R.id.AB);
        AB.setGuidelineBegin(guidelineMarginAB);
        AC = findViewById(R.id.AC);
        AC.setGuidelineBegin(guidelineMarginAC);
        AD = findViewById(R.id.AD);
        AD.setGuidelineBegin(guidelineMarginAD);
        AE = findViewById(R.id.AE);
        AE.setGuidelineBegin(guidelineMarginAE);
        AF = findViewById(R.id.AF);
        AF.setGuidelineBegin(guidelineMarginAF);
        AG = findViewById(R.id.AG);
        AG.setGuidelineBegin(guidelineMarginAG);
        AH = findViewById(R.id.AH);
        AH.setGuidelineBegin(guidelineMarginAH);
        AI = findViewById(R.id.AI);
        AI.setGuidelineBegin(guidelineMarginAI);
        AJ = findViewById(R.id.AJ);
        AJ.setGuidelineBegin(guidelineMarginAJ);
        AK = findViewById(R.id.AK);
        AK.setGuidelineBegin(guidelineMarginAK);
        AL = findViewById(R.id.AL);
        AL.setGuidelineBegin(guidelineMarginAL);
        AM = findViewById(R.id.AM);
        AM.setGuidelineBegin(guidelineMarginAM);
        AN = findViewById(R.id.AN);
        AN.setGuidelineBegin(guidelineMarginAN);
        AO = findViewById(R.id.AO);
        AO.setGuidelineBegin(guidelineMarginAO);
        AP = findViewById(R.id.AP);
        AP.setGuidelineBegin(guidelineMarginAP);
        Left = findViewById(R.id.Left);
        Left.setGuidelineBegin(guidelineMarginLeft);
        Top = findViewById(R.id.Top);
        Top.setGuidelineBegin(guidelineMarginTop);
        Right = findViewById(R.id.Right);
        Right.setGuidelineBegin(guidelineMarginRight);

        T = findViewById(R.id.T);
        T.setGuidelineBegin(guidelineMarginT);

        lineTops = new Guideline[]{U, W, Y, AA, AC, AE, AG, AI, AK, AM, AO};
        lineBottoms = new  Guideline[]{V, X, Z, AB, AD, AF, AH, AJ, AL, AN, AP};
        verticalGuidelines = new Guideline[]{A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q};

        drawText(titleBottom, Left, Top, Right,
                title, guidelineMarginTitleBottom, true,
                0,0,0,0);
        drawText(authorBottom, Left, titleBottom, Right, author,
                (guidelineMarginAuthorBottom - guidelineMarginTitleBottom), false,
                0,0,0,0);

        for(int i = 1; i < numLines + 1; i++) {
            drawLine(list.get(i+1), i);
        }

        setupSideView();
    }

    private static int getPixelsFromDP(int dp, Context applicationContext) {
        float d = applicationContext.getResources().getDisplayMetrics().density;
        int result = (int)(dp * d); // margin in pixels
        return result;
    }

    public static int getScreenHeightInDPs(Context context){
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(dm);
        int heightInDP = Math.round(dm.heightPixels / dm.density);
        return heightInDP;
    }

    public static int getScreenWidthInDPs(Context context){
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(dm);
        int widthInDP = Math.round(dm.widthPixels / dm.density);
        return widthInDP;
    }

    public int getScreenHeightInPixels(){
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) getApplicationContext().getSystemService(WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

    public int getScreenWidthInPixels(){
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) getApplicationContext().getSystemService(WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public void drawText(Guideline lowerGuideline, Guideline leftGuideline, Guideline
            upperGuideline, String text, int height, boolean bold, int leftMargin,
                         int topMargin, int rightMargin,
                         int bottomMargin) {
        ConstraintLayout constraintLayout = findViewById(R.id.constraintLayout);
        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);

        TextView display= new TextView(getApplicationContext());
        display.setTextSize(TypedValue.COMPLEX_UNIT_PX, height);
        display.setText(text);
        display.setTextColor(Color.parseColor("#000000"));
        if(bold) {
            display.setTypeface(null, Typeface.BOLD);
        }
        //display.setGravity(Gravity.CENTER);
        //display.setBackgroundColor(Color.GREEN);

        params.leftToRight = leftGuideline.getId();
        params.bottomToTop = lowerGuideline.getId();
        params.topToBottom = upperGuideline.getId();
        params.setMargins(leftMargin,topMargin, rightMargin, bottomMargin);

        display.setLayoutParams(params);
        constraintLayout.addView(display);
    }

    public void drawText(Guideline lowerGuideline, Guideline leftGuideline, Guideline
            upperGuideline, Guideline rightGuideline, String text, int height,
                         boolean bold, int leftMargin, int topMargin, int rightMargin,
                         int bottomMargin) {
        ConstraintLayout constraintLayout = findViewById(R.id.constraintLayout);
        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);

        TextView display= new TextView(getApplicationContext());
        display.setTextSize(TypedValue.COMPLEX_UNIT_PX, height);
        display.setText(text);
        display.setTextColor(Color.parseColor("#000000"));
        if(bold) {
            display.setTypeface(null, Typeface.BOLD);
        }
        //display.setGravity(Gravity.CENTER);
        //display.setBackgroundColor(Color.RED);
        if(!(leftGuideline == null)) {params.leftToRight = leftGuideline.getId();}
        if(!(rightGuideline == null)) {params.rightToLeft = rightGuideline.getId();}
        if(!(lowerGuideline == null)) {params.bottomToTop = lowerGuideline.getId();}
        if(!(upperGuideline == null)) {params.topToBottom = upperGuideline.getId();}
        params.setMargins(leftMargin, topMargin, rightMargin, bottomMargin);

        display.setLayoutParams(params);
        constraintLayout.addView(display);
    }

    public void drawALineOfBars(Guideline lowerGuideline, Guideline upperGuideline,
                                Guideline[] leftGuidelines) {
        for(int i = 0; i < leftGuidelines.length; i++) {
            //drawBar(lowerGuideline, upperGuideline, leftGuidelines[i]);
            drawText(lowerGuideline, leftGuidelines[i], upperGuideline, leftGuidelines[i],
                    Character.toString((char) 0xFF5C), lineHeight,
                    false, 0,0,0,0);
        }
    }

    //linenumbers starting at 1
    public void drawLine(String line, int lineNumber) {
        String delim = "[|]";
        String[] stringBars = line.split(delim);
        Bar[] bars = new Bar[stringBars.length];
        for (int j = 0; j < bars.length; j++) {
            bars[j] = new Bar(stringBars[j]);
        }

        Guideline lowerGuideline = lineBottoms[lineNumber - 1];
        Guideline upperGuideline = lineTops[lineNumber - 1];

        boolean eightBars = false;
        if(bars.length == 8) {
            eightBars = true;
        }

        if(!eightBars) {
            Guideline[] leftGuidelineArray = {verticalGuidelines[0], verticalGuidelines[4],
                    verticalGuidelines[8], verticalGuidelines[12], verticalGuidelines[16]};
            drawALineOfBars(lowerGuideline, upperGuideline, leftGuidelineArray);

            for(int i = 0; i < 4; i++) {
                if(bars[i].fourFour || bars[i].threeFour) {
                    String topNum;
                    if(bars[i].fourFour) { topNum = "4";}
                    else { topNum = "3";}

                    drawText(T, Left, lineTops[0],null,
                            topNum, (int)(width * 0.04),
                            true, (int)(width*0.007),0,0,0);
                    drawText(V, Left, T, null, "4",
                            (int)(width * 0.04), true,
                            (int)(width*0.007),0,0,0);
                    drawText(lineBottoms[0], Left, lineTops[0], null, "-",
                            (int)(width * 0.08), true,
                            (int)(width*0.003),0,0,(int)(lineHeight*0.1));
                }

                if(bars[i].leftRepeat) {
                    //String text = new StringBuilder().appendCodePoint(0x1D106).toString();
                    drawText(lowerGuideline, verticalGuidelines[4*i], upperGuideline,
                            null, ":",
                            (int)(lineHeight * 0.7), true,
                            (int)(width * 0.003), 0,0,
                            (int)(lineHeight * 0.1));
                    drawText(lowerGuideline, verticalGuidelines[4*i], upperGuideline,
                            verticalGuidelines[4*i],
                            Character.toString((char) 0x2997), (int)(lineHeight*1.3), true,
                            0,0,0,(int)(lineHeight*0.2));
                }

                if(bars[i].rightRepeat) {
                    System.out.println(i);
                    //String text = new StringBuilder().appendCodePoint(0x1D106).toString();
                    drawText(lowerGuideline, null, upperGuideline,
                            verticalGuidelines[4*i + 4],
                            ":", (int)(lineHeight * 0.7), true,
                            0,0,0,
                            (int)(lineHeight * 0.1));
                    drawText(lowerGuideline, verticalGuidelines[4*i + 4], upperGuideline,
                            verticalGuidelines[4*i + 4],
                            Character.toString((char) 0x2998), (int)(lineHeight*1.3), true,
                            0,0,0,(int)(lineHeight*0.2));

                }
            }
        }

        if(bars.length == 8) {
            Guideline[] leftGuidelineArray = {verticalGuidelines[2], verticalGuidelines[6],
                    verticalGuidelines[10], verticalGuidelines[14]};
            drawALineOfBars(lowerGuideline, upperGuideline, leftGuidelineArray);
        }

    }

    // Screen tapped anywhere
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        // called before OnClickListeners

        final LinearLayout sideView = findViewById(R.id.sideView);
        float density = getResources().getDisplayMetrics().density;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenHeight = (int) (displayMetrics.heightPixels / density); // screenheight in dp

        int clickDistanceFromLeft = (int) (ev.getRawX() / density); // distance in dp
        float widthOfView = sideView.getWidth() / density;

        System.out.println("CLICK DISTANVE");
        System.out.println(clickDistanceFromLeft);



        if (ev.getAction() == MotionEvent.ACTION_DOWN) {

            if (sideView.getVisibility() == View.VISIBLE && clickDistanceFromLeft > widthOfView) {
                clickCount = 0;
                sideView.animate().translationX(-sideView.getWidth()).setDuration(300) // animate left to hide
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        sideView.setVisibility(View.INVISIBLE);
                    }
                });
            } else {
                sideView.bringToFront();
                clickCount += 1;

                if (sideView.getVisibility() == View.INVISIBLE) {

                    sideView.animate().translationX(-sideView.getWidth()).setDuration(0) // first animate left instantly, then animate back out visibly
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    sideView.setVisibility(View.VISIBLE);

                                    sideView.animate().translationX(0).setDuration(300)
                                            .setListener(new AnimatorListenerAdapter() {
                                                @Override
                                                public void onAnimationEnd(Animator animation) {
                                                    super.onAnimationEnd(animation);
                                                }
                                            });
                                }
                            });
                }
            }
        }

        return super.dispatchTouchEvent(ev);
    }

    public void setupSideView() {
        Button backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clickCount > 1) { // user needs to have already clicked twice for button to work. Once to show buttons, and once for the actual click
                    finish();
                }
            }
        });

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenHeight = displayMetrics.heightPixels - getStatusBarHeight(); // screenheight in pixels
        int constraintMarginsHeight = 112; // in pixels

        int heightOfButtons = (screenHeight - constraintMarginsHeight) / 13;

        String text;

        Button CButton = findViewById(R.id.CButton);
        Button DFlat = findViewById(R.id.DFlatButton);
        text = "D" + (char) 0x266D;
        DFlat.setText(text);
        Button DButton = findViewById(R.id.DButton);
        Button EFlatButton = findViewById(R.id.EFlatButton);
        text = "E" + (char) 0x266D;
        EFlatButton.setText(text);
        Button EButton = findViewById(R.id.EButton);
        Button FButton = findViewById(R.id.FButton);
        Button FSharpButton =findViewById(R.id.FSharpButton);
        text = "F" + (char) 0x266F;
        FSharpButton.setText(text);
        Button GButton = findViewById(R.id.GButton);
        Button AFlatButton = findViewById(R.id.AFlatButton);
        text = "A" + (char) 0x266D;
        AFlatButton.setText(text);
        Button AButton = findViewById(R.id.AButton);
        Button BFlatButton = findViewById(R.id.BFlatButton);
        text = "B" + (char) 0x266F;
        BFlatButton.setText(text);
        Button BButton = findViewById(R.id.BButton);

        ArrayList<Button> sideViewButtons = new ArrayList<>();
        sideViewButtons.add(backBtn);
        sideViewButtons.add(CButton);
        sideViewButtons.add(DFlat);
        sideViewButtons.add(EFlatButton);
        sideViewButtons.add(DButton);
        sideViewButtons.add(EFlatButton);
        sideViewButtons.add(EButton);
        sideViewButtons.add(FButton);
        sideViewButtons.add(FSharpButton);
        sideViewButtons.add(GButton);
        sideViewButtons.add(AFlatButton);
        sideViewButtons.add(AButton);
        sideViewButtons.add(BFlatButton);
        sideViewButtons.add(BButton);

        for (Button b : sideViewButtons) {
            int top;
            int bottom;
            if (b.equals(R.id.backBtn)) {
                top = 8;
            } else {
                top = 4;
            }
            if (b.equals(R.id.BButton)) {
                bottom = 8;
            } else {
                bottom = 4;
            }
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, heightOfButtons);
            params.setMargins(8, top, 8, bottom);
            b.setLayoutParams(params);
        }


    }


}

/*
        lineHeight = (height - titleAndAuthorHeight) / numLines * 2 / 3;
        lineTopAndBottomMargins = lineHeight / 4;
        chordFontSize = lineHeight * 2 / 3;
        LinearLayout verticalBarLayout = findViewById(R.id.verticalBarLayout);
        LinearLayout.LayoutParams verticalParams = new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        verticalParams.setMargins(getPixelsFromDP(leftMarginSize, getApplicationContext()),
                getPixelsFromDP(lineTopAndBottomMargins,getApplicationContext()), 0,
                getPixelsFromDP(lineTopAndBottomMargins, getApplicationContext()));
        LinearLayout[] horizontalBarLayouts = new LinearLayout[numLines];
        for (int i = 0; i < numLines; i++) {
            String delim = "[|]";
            String[] stringBars = list.get(i + 2).split(delim);
            Bar[] bars = new Bar[stringBars.length];
            for (int j = 0; j < bars.length; j++) {
                bars[j] = new Bar(stringBars[j]);
            }
            int barWidth = (width - (leftMarginSize + rightMarginSize)) / bars.length;
            horizontalBarLayouts[i] = new LinearLayout(getApplicationContext());
            LinearLayout.LayoutParams horizontalParams = new LinearLayout.LayoutParams(
                    LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            horizontalParams.setMargins(0, 0, getPixelsFromDP(barWidth, getApplicationContext()), 0);
            TextView[] barDisplays = new TextView[bars.length];
            for (int j = 0; j < bars.length; j++) {
                barDisplays[j] = new TextView(getApplicationContext());
                barDisplays[j].setTextSize(TypedValue.COMPLEX_UNIT_PX, lineHeight);
                barDisplays[j].setText("|");
                barDisplays[j].setTextColor(Color.parseColor("#000000"));
                horizontalBarLayouts[i].addView(barDisplays[j], horizontalParams);
            }
            verticalBarLayout.addView(horizontalBarLayouts[i], verticalParams);
        }*/