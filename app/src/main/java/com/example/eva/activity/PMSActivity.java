package com.example.eva.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.eva.R;
import com.example.eva.adapter.PMSAdapter;
import com.example.eva.adapter.RemindAdapter;
import com.example.eva.model.ItemPMS;

import java.util.ArrayList;
import java.util.List;

public class PMSActivity extends AppCompatActivity implements PMSAdapter.OnItemClickListener {

    public static final int ID_MENSTRUATION=0;
    public static final int ID_MOOD=0;
    public static final int ID_SYMPTOM=1;
    public static final int ID_HEALTHY=2;
    public static final int ID_SPORTY=3;


    List<ItemPMS> mListMens, mListMood, mListSporty, mListMedicine, mListSymptom ;
    RecyclerView mRecyclerMenstruation, mRecyclerMood, mRecyclerSymptom, mRecyclerMedicine, mRecyclerSporty;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p_m_s);

        addData();
        addRecyclerView();

    }

    private void addRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        ((LinearLayoutManager) layoutManager).setOrientation(LinearLayoutManager.HORIZONTAL);

        mRecyclerMenstruation=findViewById(R.id.recycle_menstruation);
        mRecyclerMenstruation.setHasFixedSize(true);
        mRecyclerMenstruation.setLayoutManager(layoutManager);
        PMSAdapter mensAdapter = new PMSAdapter(mListMens, this);
        mRecyclerMenstruation.setAdapter(mensAdapter);

        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(this);
        ((LinearLayoutManager) layoutManager1).setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerMood=findViewById(R.id.recycle_mood);
        mRecyclerMood.setHasFixedSize(true);
        mRecyclerMood.setLayoutManager(layoutManager1);
        PMSAdapter moodAdapter = new PMSAdapter(mListMood, this);
        mRecyclerMood.setAdapter(moodAdapter);

        RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(this);
        ((LinearLayoutManager) layoutManager2).setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerMedicine=findViewById(R.id.recycle_medicine);
        mRecyclerMedicine.setHasFixedSize(true);
        mRecyclerMedicine.setLayoutManager(layoutManager2);
        PMSAdapter medicineAdapter = new PMSAdapter(mListMedicine, this);
        mRecyclerMedicine.setAdapter(medicineAdapter);

        RecyclerView.LayoutManager layoutManager3 = new LinearLayoutManager(this);
        ((LinearLayoutManager) layoutManager3).setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerSymptom=findViewById(R.id.recycle_symptom);
        mRecyclerSymptom.setHasFixedSize(true);
        mRecyclerSymptom.setLayoutManager(layoutManager3);
        PMSAdapter symptomAdapter = new PMSAdapter(mListSymptom, this);
        mRecyclerSymptom.setAdapter(symptomAdapter);

        RecyclerView.LayoutManager layoutManager4 = new LinearLayoutManager(this);
        ((LinearLayoutManager) layoutManager4).setOrientation(LinearLayoutManager.HORIZONTAL);

        mRecyclerSporty=findViewById(R.id.recycle_sporty);
        mRecyclerSporty.setHasFixedSize(true);
        mRecyclerSporty.setLayoutManager(layoutManager4);
        PMSAdapter sportyAdapter = new PMSAdapter(mListSporty, this);
        mRecyclerSporty.setAdapter(sportyAdapter);

    }

    private void addData() {
        mListMens=new ArrayList<>();
        mListMens.add(new ItemPMS(R.drawable.ic_mf_s_l,false,getResources().getString(R.string.menstruation_light)));
        mListMens.add(new ItemPMS(R.drawable.ic_mf_m_l,false,getResources().getString(R.string.menstruation_medium)));
        mListMens.add(new ItemPMS(R.drawable.ic_mf_l_l,false,getResources().getString(R.string.menstruation_heavy)));

        mListMood=new ArrayList<>();
        mListMood.add(new ItemPMS(R.drawable.ic_mood_normal, false,getResources().getString(R.string.mood_normal)));
        mListMood.add(new ItemPMS(R.drawable.ic_mood_happy, false,getResources().getString(R.string.mood_happy)));
        mListMood.add(new ItemPMS(R.drawable.ic_mood_sad, false,getResources().getString(R.string.mood_sad)));
        mListMood.add(new ItemPMS(R.drawable.ic_mood_angry, false,getResources().getString(R.string.mood_angry)));
        mListMood.add(new ItemPMS(R.drawable.ic_mood_anxious, false,getResources().getString(R.string.mood_worry)));
        mListMood.add(new ItemPMS(R.drawable.ic_mood_tired, false,getResources().getString(R.string.mood_tired)));
        mListMood.add(new ItemPMS(R.drawable.ic_mood_panicky, false,getResources().getString(R.string.mood_fear)));

        mListSporty=new ArrayList<>();
        mListSporty.add(new ItemPMS(R.drawable.didnt_exercise,false,getResources().getString(R.string.no_sporty)));
        mListSporty.add(new ItemPMS(R.drawable.running,false,getResources().getString(R.string.running)));
        mListSporty.add(new ItemPMS(R.drawable.cycling,false,getResources().getString(R.string.cycling)));
        mListSporty.add(new ItemPMS(R.drawable.gym,false,getResources().getString(R.string.gym)));
        mListSporty.add(new ItemPMS(R.drawable.swimming,false,getResources().getString(R.string.swimming)));
        mListSporty.add(new ItemPMS(R.drawable.aerobics_dancing,false,getResources().getString(R.string.earobics)));

        mListMedicine=new ArrayList<>();
        mListMedicine.add(new ItemPMS(R.drawable.ic_others_medicine_l, false,getResources().getString(R.string.medicine_title)));

        mListSymptom=new ArrayList<>();
        mListSymptom.add(new ItemPMS(R.drawable.ic_sy_ok, false,getResources().getString(R.string.symptom_good)));
        mListSymptom.add(new ItemPMS(R.drawable.ic_sy_acne, false,getResources().getString(R.string.symptom_acne)));
        mListSymptom.add(new ItemPMS(R.drawable.ic_sy_cramps, false,getResources().getString(R.string.symptom_stomachache)));
        mListSymptom.add(new ItemPMS(R.drawable.ic_sy_headache, false,getResources().getString(R.string.symptom_headache)));
        mListSymptom.add(new ItemPMS(R.drawable.ic_sy_dizziness, false,getResources().getString(R.string.symptom_dizziness)));
        mListSymptom.add(new ItemPMS(R.drawable.ic_sy_bloating, false,getResources().getString(R.string.symptom_bloating)));
        mListSymptom.add(new ItemPMS(R.drawable.ic_sy_backaches, false,getResources().getString(R.string.symptom_backache)));
        mListSymptom.add(new ItemPMS(R.drawable.ic_sy_tender, false,getResources().getString(R.string.symptom_breast_pain)));
        mListSymptom.add(new ItemPMS(R.drawable.ic_sy_nausea, false,getResources().getString(R.string.symptom_nausea)));

    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(this,""+position,Toast.LENGTH_LONG).show();
    }
}