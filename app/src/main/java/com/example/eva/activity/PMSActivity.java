package com.example.eva.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.eva.R;
import com.example.eva.adapter.PMSAdapter;
import com.example.eva.callback.OnItemClickListener;
import com.example.eva.data.DBManager;
import com.example.eva.model.ItemPMS;
import com.example.eva.model.PMS;
import com.shawnlin.numberpicker.NumberPicker;

import java.util.ArrayList;
import java.util.List;

public class PMSActivity extends AppCompatActivity implements OnItemClickListener, View.OnClickListener {

    public static final int ID_MENSTRUATION = 0;
    public static final int ID_MOOD = 1;
    public static final int ID_SYMPTOM = 2;
    public static final int ID_SPORTY = 4;
    public static final int ID_MEDICINE = 5;
    public static final int ID_SEX = 6;
    public static final int ID_CHARGE = 7;

    String mPMS = "";
    EditText mEditNote;
    ImageView mImageWeight, mImageTemperature;
    TextView mTextUnit, mTextHealthy, mTextWeight, mTextTemp;
    List<ItemPMS> mListMens, mListMood, mListSporty, mListMedicine, mListSymptom, mListSex, mListCharge;
    PMS mPms;
    RelativeLayout layoutEditHealthy;
    NumberPicker mNumberPicker1, mNumberPicker2;
    boolean mStatusWeight, mStatusTemperature = false, mIsPeriodDay = false;
    Button mButtonSavePMS;
    DBManager mDbManager;

    String mDate;
    float mWeight, mTemperature;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p_m_s);
        addData();
        init();

        addRecyclerView();
        eventNote();
        setDataHeathy();
    }

    private void eventNote() {
        mEditNote.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                mButtonSavePMS.setVisibility(View.VISIBLE);
            }
        });
    }

    private void init() {

//        TextView textTitle = new TextView(this);
//        textTitle.setText(mDate);
//        textTitle.setTextSize(23);
//        textTitle.setTypeface(null, Typeface.BOLD);
//        textTitle.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,
//                LinearLayout.LayoutParams.WRAP_CONTENT));
//        textTitle.setGravity(Gravity.CENTER);
//        textTitle.setTextColor(getResources().getColor(R.color.colorMainPink));
//        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//        getSupportActionBar().setCustomView(textTitle);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_back));
        getSupportActionBar().hide();

        mButtonSavePMS = findViewById(R.id.button_save_pms);
        mButtonSavePMS.setVisibility(View.GONE);
        //healthy
        mNumberPicker1 = findViewById(R.id.number_picker_healthy_1);
        mNumberPicker2 = findViewById(R.id.number_picker_healthy_2);

        layoutEditHealthy = findViewById(R.id.layout_edit_healthy);
        layoutEditHealthy.setVisibility(View.GONE);
        Button buttonSaveHealthy = findViewById(R.id.button_save_healthy);
        Button buttonCancelHealthy = findViewById(R.id.button_cancel_healthy);
        mTextUnit = findViewById(R.id.text_unit);
        mTextHealthy = findViewById(R.id.text_healthy);

        mTextWeight = findViewById(R.id.text_number_weight);
        mTextTemp = findViewById(R.id.text_number_temp);
        TextView textCurrentDay=findViewById(R.id.text_current_day);
        textCurrentDay.setText(mDate);
        mTextWeight.setVisibility(View.GONE);
        mTextTemp.setVisibility(View.GONE);

        FrameLayout layout_weight = findViewById(R.id.layout_weight);
        FrameLayout layout_temperature = findViewById(R.id.layout_temperature);

        mImageWeight = findViewById(R.id.image_weight);
        mImageTemperature = findViewById(R.id.image_temperature);
        ImageView imageBack=findViewById(R.id.image_back);


        //onClick
        mButtonSavePMS.setOnClickListener(this);
        layout_weight.setOnClickListener(this);
        layout_temperature.setOnClickListener(this);
        buttonSaveHealthy.setOnClickListener(this);
        buttonCancelHealthy.setOnClickListener(this);
        imageBack.setOnClickListener(this);
        //setNumberPicker(true);

    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                this.finish();
//                return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    private void setDataHeathy() {
        if (mPms != null) {
            if (mPms.getWeight() != 0.0f) {
                mImageWeight.setImageDrawable(getResources().getDrawable(R.drawable.bg_white_pms));
                mTextWeight.setVisibility(View.VISIBLE);
                mTextWeight.setText(mPms.getWeight() + " Kg");
            }
            if (mPms.getTemperature() != 0.0f) {
                mImageTemperature.setImageDrawable(getResources().getDrawable(R.drawable.bg_white_pms));
                mTextTemp.setVisibility(View.VISIBLE);
                mTextTemp.setText(mPms.getTemperature() + " °C");
            }
        }
    }

    private void setNumberPicker(boolean weight) {
        int maxValue;
        int minValue;
        int value1;
        int value2 = 0;
        int weight1 = 50, weight2 = 0;
        int tem1 = 37, tem2 = 0;
        String[] data = {".0", ".1", ".2", ".3", ".4", ".5", ".6", ".7", ".8", ".9"};

//        if(mWeight!=0.0f){
//            String w=String.valueOf(mWeight);
//            String[] a=w.split("\\.");
//            weight1=Integer.parseInt(a[0]);
//            weight2=Integer.parseInt(a[1]);
//        }
//        if(mTemperature!=0.0f){
//            String t=String.valueOf(mTemperature);
//            String[] a=t.split("\\.");
//            tem1=Integer.parseInt(a[0]);
//            tem2=Integer.parseInt(a[1]);
//
//        }

        if (weight) {
            maxValue = 120;
            minValue = 25;
            value1 = weight1;
            value2 = weight2;
            mTextUnit.setText("kg");
            mTextHealthy.setText(getResources().getString(R.string.weight));

        } else {
            maxValue = 42;
            minValue = 35;
            value1 = tem1;
            value2 = tem2;
            mTextUnit.setText("°C");
            mTextHealthy.setText(getResources().getString(R.string.temperature));
        }
        mNumberPicker1.setMaxValue(maxValue);
        mNumberPicker1.setMinValue(minValue);
        mNumberPicker1.setValue(value1);
        mNumberPicker2.setMinValue(1);
        mNumberPicker2.setMaxValue(data.length);
        mNumberPicker2.setDisplayedValues(data);
        mNumberPicker2.setValue(-value2);
    }

    private void addRecyclerView() {

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        ((LinearLayoutManager) layoutManager).setOrientation(LinearLayoutManager.HORIZONTAL);

        LinearLayout layoutMenstruation = findViewById(R.id.layout_menstruation);

        setupRecyclerView(R.id.recycle_menstruation, ID_MENSTRUATION, (ArrayList<ItemPMS>) mListMens);
        setupRecyclerView(R.id.recycle_mood, ID_MOOD, (ArrayList<ItemPMS>) mListMood);
        setupRecyclerView(R.id.recycle_medicine, ID_MEDICINE, (ArrayList<ItemPMS>) mListMedicine);
        setupRecyclerView(R.id.recycle_symptom, ID_SYMPTOM, (ArrayList<ItemPMS>) mListSymptom);
        setupRecyclerView(R.id.recycle_sporty, ID_SPORTY, (ArrayList<ItemPMS>) mListSporty);
        setupRecyclerView(R.id.recycle_sex, ID_SEX, (ArrayList<ItemPMS>) mListSex);
        setupRecyclerView(R.id.recycle_charge, ID_CHARGE, (ArrayList<ItemPMS>) mListCharge);

        if (!mIsPeriodDay)
            layoutMenstruation.setVisibility(View.GONE);

    }

    private void setupRecyclerView(int idRecyclerView, int ID, ArrayList<ItemPMS> listPMS) {
        RecyclerView.LayoutManager layoutManager5 = new LinearLayoutManager(this);
        ((LinearLayoutManager) layoutManager5).setOrientation(LinearLayoutManager.HORIZONTAL);
        RecyclerView recyclerview = findViewById(idRecyclerView);
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(layoutManager5);
        PMSAdapter adapter = new PMSAdapter(ID, listPMS, this);
        recyclerview.setAdapter(adapter);
    }

    private void addData() {
        mEditNote = findViewById(R.id.edit_note);
        mDate = getIntent().getStringExtra("selectedDate");
        mDbManager = new DBManager(this);
        mPms = mDbManager.getPMS(mDate);
        if (mPms != null) {
            mWeight = mPms.getWeight();
            mTemperature = mPms.getTemperature();
            if (mPms.getNote() != null) {
                mEditNote.setText(mPms.getNote());
            }
        }
        mListMens = (List<ItemPMS>) getIntent().getSerializableExtra("listMens");
        mListMood = (List<ItemPMS>) getIntent().getSerializableExtra("listMood");
        mListSporty = (List<ItemPMS>) getIntent().getSerializableExtra("listSporty");
        mListMedicine = (List<ItemPMS>) getIntent().getSerializableExtra("listMedicine");
        mListSymptom = (List<ItemPMS>) getIntent().getSerializableExtra("listSymptom");
        mListSex = (List<ItemPMS>) getIntent().getSerializableExtra("listSex");
        mListCharge = (List<ItemPMS>) getIntent().getSerializableExtra("listCharge");
        mIsPeriodDay = getIntent().getBooleanExtra("isPeriodDay", false);
    }

    @Override
    public void onItemClick(int position) {
        mButtonSavePMS.setVisibility(View.VISIBLE);
    }

    private void getResult(List<ItemPMS> listItem) {
        for (ItemPMS itemPMS : listItem) {
            if (itemPMS.isStatus()) {
                mPMS += itemPMS.getText() + ",";
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void savePMS() {
        getResult(mListMood);
        getResult(mListMens);
        getResult(mListMedicine);
        getResult(mListSporty);
        getResult(mListSymptom);
        getResult(mListSex);
        getResult(mListCharge);
        String note = mEditNote.getText().toString();

        if (mPms == null) {
            mPms = new PMS();
            mPms.setDate(mDate);
            mPms.setPms(mPMS);
            mPms.setNote(note);
            mDbManager.addPMS(mPms);
        } else {
            mPms.setPms(mPMS);
            mPms.setNote(note);
            mDbManager.updatePMS(mPms);

        }
        Log.d("BacNT", "PMS: " + mPMS);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.layout_weight:
                mStatusWeight = !mStatusWeight;
                mStatusTemperature = false;
                setVisibleLayout(mStatusWeight);
                setNumberPicker(true);
                break;
            case R.id.layout_temperature:
                mStatusTemperature = !mStatusTemperature;
                mStatusWeight = false;
                setVisibleLayout(mStatusTemperature);
                setNumberPicker(false);
                break;
            case R.id.button_cancel_healthy:
                setupStatus();
                setVisibleLayout(false);
                break;
            case R.id.button_save_healthy:
                setVisibleLayout(false);
                saveHealthy(mStatusWeight);
                mButtonSavePMS.setVisibility(View.VISIBLE);
                setupStatus();
                setDataHeathy();
                break;
            case R.id.button_save_pms:
                savePMS();
                finish();
                break;
            case R.id.image_back:
                finish();
        }
    }

    private void setupStatus() {
        mStatusTemperature = false;
        mStatusWeight = false;
    }

    private void saveHealthy(boolean isWeight) {
        String result;
        int value1;
        int value2;
        value1 = mNumberPicker1.getValue();
        value2 = mNumberPicker2.getValue();
        result = value1 + "." + (value2 - 1);
        if (isWeight) {
            mWeight = Float.parseFloat(result);
        } else {
            mTemperature = Float.parseFloat(result);
        }
        if (mPms == null) {
            mPms = new PMS();
            mPms.setDate(mDate);
            mPms.setWeight(mWeight);
            mPms.setTemperature(mTemperature);
            mDbManager.addPMS(mPms);
        } else {
            mPms.setWeight(mWeight);
            mPms.setTemperature(mTemperature);
            mDbManager.updatePMS(mPms);
        }
    }

    private void setVisibleLayout(boolean statusHealthy) {
        if (statusHealthy) {
            layoutEditHealthy.setVisibility(View.VISIBLE);
        } else layoutEditHealthy.setVisibility(View.GONE);
    }
}