package com.tchocolat.kaanyagci.perpetual;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends Activity {

    private static ArrayList<TextView> gun_cases = new ArrayList<TextView>();
    private GestureDetector gestureDetector_Takvim;
    private GestureDetector gestureDetector_CurrentDate;
    private GestureDetector gestureDetector_DayName;
    private GestureDetector gestureDetector_Top_Toolbar;
    private static Typeface t;
    private Calendar cal =  Calendar.getInstance();
    private static Toolbar calendar_toolbar;
    private static TextView date_tv,annotation_tv,dayname_tv;
    private static LinearLayout hafta1,hafta2,hafta3,hafta4,hafta5,hafta6;
    private static String URL;


    //Ceci est un commentaire
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getActionBar().hide();
        UserFunctions.setUpDayNames();
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        decorView.setSystemUiVisibility(uiOptions);
        setUpViews();
        setUpTakvim();
        setUpSelectedAndDay();
        setUpGestures();
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

        return super.onOptionsItemSelected(item);
    }

    private  void setUpTakvim() {

        cal = UserFunctions.setCalendarFromString(UserFunctions.current_date);
        for (int i = 0; i < 42; i++) {
            gun_cases.get(i).setTypeface(t);
            gun_cases.get(i).setTextSize(TypedValue.COMPLEX_UNIT_SP, 28);
            gun_cases.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int i = 0; i < gun_cases.size(); i++) {
                        gun_cases.get(i).setTextColor(getResources().getColor(R.color.black));
                        gun_cases.get(i).setBackgroundColor(getResources().getColor(R.color.white));
                    }
                    v.setBackgroundColor(getResources().getColor(R.color.black));
                    ((TextView) v).setTextColor(getResources().getColor(R.color.white));
                    String[] date_t = UserFunctions.current_date.split("\\.");
                    UserFunctions.current_date = ((TextView) v).getText() + "." + date_t[1] + "." + date_t[2];
                    date_tv.setText(UserFunctions.current_date);
                    int id = 0;
                    for (int i = 0; i < gun_cases.size(); i++) {
                        if (gun_cases.get(i).equals(v)) {
                            id = i;
                            break;
                        }
                    }
                    dayname_tv.setText(UserFunctions.day_names.get(id % 7));

                }
            });
            //Get day of the date
        }
        int index = 0;
        //Cal gecerli ayin 1. gunune ayarli
        //index cal'in haftanin hangi gune geldigini gostericek
        index=cal.get(Calendar.DAY_OF_WEEK);
        //US Standartlarinda pazar gunu 1 ile basladigi icin hepsinden 2 cikartiyoruz
        //boylece pazar -1 de cumartesi 5 de kaliyor
        index=index-2;
        //Pazarin cumartesiden sonra gelmesi icin -1 e esit ise 6 ya atiyoruz.
        if (index == -1)
            index=6;
        int num = 1;
        int max = UserFunctions.getMaxDayFromString(UserFunctions.current_date);
        Log.d("Gun cases size :",""+gun_cases.size());
        Log.d("Index :",""+index);
        Log.d("Max :",""+max);
        for (int i = 0; i < gun_cases.size(); i++) {
            if ((i < index) || (i >= (max+index))) {
                gun_cases.get(i).setText("");
                gun_cases.get(i).setClickable(false);
            }
        }
        Log.d("Index",""+index);
        Log.d("index+max",""+(index+max));
        for (int i=index;i<(index+max);i++)
        {
            gun_cases.get(i).setText("" + num);
            gun_cases.get(i).setTextColor(getResources().getColor(R.color.black) );
            gun_cases.get(i).setBackgroundColor(getResources().getColor(R.color.white));
            num++;
        }
    }
    private void setUpSelectedAndDay()
    {
        String[] current_date_table = UserFunctions.current_date.split("\\.");
        int id = 0;
        for (int i=0;i<gun_cases.size();i++)
        {
            if (gun_cases.get(i).getText().equals(current_date_table[0]))
            {
                id = i;
                gun_cases.get(i).setBackgroundColor(getResources().getColor(R.color.black));
                gun_cases.get(i).setTextColor(getResources().getColor(R.color.white));
            }
            else if (!(gun_cases.get(i).equals("")))
            {
                gun_cases.get(i).setBackgroundColor(getResources().getColor(R.color.white));
                gun_cases.get(i).setTextColor(getResources().getColor(R.color.black));
            }
        }
        dayname_tv.setText(UserFunctions.day_names.get(id%7));

    }


    private void setUpGestures () {
        gestureDetector_CurrentDate = new GestureDetector(new GestureDetector.SimpleOnGestureListener() {
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                try {
                    final SwipeDetector detector = new SwipeDetector(e1, e2, velocityX, velocityY);
                    if (detector.isRightSwipe()) {
                        UserFunctions.tommorrow();
                        setUpTakvim();
                        setUpSelectedAndDay();
                        date_tv.setText(UserFunctions.current_date);
                    } else
                    if (detector.isLeftSwipe()) {

                        UserFunctions.yesterday();
                        setUpTakvim();
                        setUpSelectedAndDay();
                        date_tv.setText(UserFunctions.current_date);
                    }
                    else{
                        return false;
                    }
                } catch (Exception e) {
                    // nothing
                }
                return false;
            }
        });
        gestureDetector_DayName = gestureDetector_CurrentDate;
        /** Daha tamamlanmadi */
        gestureDetector_Takvim = new GestureDetector(new GestureDetector.SimpleOnGestureListener() {
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                try {
                    final SwipeDetector detector = new SwipeDetector(e1, e2, velocityX, velocityY);
                    if (detector.isRightSwipe()) {
                        UserFunctions.oneMonthLater();
                        setUpTakvim();
                        setUpSelectedAndDay();
                        date_tv.setText(UserFunctions.current_date);
                    }
                    else {
                        return false;
                    }
                } catch (Exception e) {
                    // nothing
                }
                return false;
            }
        });
        gestureDetector_Top_Toolbar = new GestureDetector(new GestureDetector.SimpleOnGestureListener() {
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                try {
                    final SwipeDetector detector = new SwipeDetector(e1, e2, velocityX, velocityY);
                    if (detector.isUpSwipe()) {
                    }
                    else {
                        return false;
                    }
                } catch (Exception e) {
                    // nothing
                }
                return false;
            }
        });



        date_tv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {}
        });
        date_tv.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector_CurrentDate.onTouchEvent(event);}});

        dayname_tv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {}
        });
        dayname_tv.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector_DayName.onTouchEvent(event);}});

        LinearLayout t = (LinearLayout)findViewById(R.id.takvim_cases);
        t.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {}
        });
        t.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector_Takvim.onTouchEvent(event);}});
        calendar_toolbar.setOnClickListener(new View.OnClickListener(){
            public void onClick(View arg0){}
        });
        calendar_toolbar.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector_Top_Toolbar.onTouchEvent(event);
            }
        });
    }
    private void setUpViews()
    {
        calendar_toolbar = (Toolbar)findViewById(R.id.takvim_day_toolbar);
        date_tv = (TextView)findViewById(R.id.takvim_current_day);
        annotation_tv=(TextView)findViewById(R.id.takvim_annotation_day);
        dayname_tv = (TextView)findViewById(R.id.takvim_dayname_day);
        t = Typeface.createFromAsset(getAssets(), "fonts/BebasNeue Bold.ttf");
        date_tv.setTypeface(t);
        annotation_tv.setTypeface(t);
        dayname_tv.setTypeface(t);
        date_tv.setText(UserFunctions.current_date);
        hafta1 = (LinearLayout) findViewById(R.id.hafta1);
        hafta2 = (LinearLayout) findViewById(R.id.hafta2);
        hafta3 = (LinearLayout) findViewById(R.id.hafta3);
        hafta4 = (LinearLayout) findViewById(R.id.hafta4);
        hafta5 = (LinearLayout) findViewById(R.id.hafta5);
        hafta6 = (LinearLayout) findViewById(R.id.hafta6);

        gun_cases = new ArrayList<TextView>();
        for (int i = 0; i < 42; i++) {
            if (i < 7)
                gun_cases.add((TextView) hafta1.getChildAt(i));
            else if (i < 14)
                gun_cases.add((TextView) hafta2.getChildAt(i % 7));
            else if (i < 21)
                gun_cases.add((TextView) hafta3.getChildAt(i % 7));
            else if (i < 28)
                gun_cases.add((TextView) hafta4.getChildAt(i % 7));
            else if (i < 35)
                gun_cases.add((TextView) hafta5.getChildAt(i % 7));
            else if (i < 42)
                gun_cases.add((TextView)hafta6.getChildAt(i % 7));
        }
    }
}
