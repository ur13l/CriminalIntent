package com.projects.ur13l.criminalintent;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v7.app.ActionBarActivity;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ur13l on 15/12/14.
 */
public class CrimeListFragment extends ListFragment{
    public ArrayList<Crime> mCrimes;
    private Callbacks mCallbacks;
    private boolean mSubtitleVisible;
    private static final String TAG = "CrimeListFragment";
    private static final int REQUEST_CRIME = 1;

    public interface Callbacks {
        void onCrimeSelected(Crime crime);
    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        mCallbacks = (Callbacks)activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        getActivity().setTitle(R.string.crimes_title);
        mCrimes = CrimeLab.get(getActivity()).getCrimes();

        CrimeAdapter adapter = new CrimeAdapter(mCrimes);
        setListAdapter(adapter);
        setRetainInstance(true);
        mSubtitleVisible = true;
    }

   @TargetApi(11)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
        View v;
        v = inflater.inflate(R.layout.fragment_list_crime,parent,false);
        Button mEmptyButton = (Button)v.findViewById(R.id.emptyButton);
        mEmptyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newCrime();
            }
        });

        ListView listView = (ListView)v.findViewById(android.R.id.list);



        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB){
            //Floating ContextMenu instead of ActionBar ContextMenu
            registerForContextMenu(listView);
        }else {
            listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
            listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
                @Override
                public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {

                }

                @Override
                public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                    MenuInflater inflater = mode.getMenuInflater();
                    inflater.inflate(R.menu.crime_list_item_context, menu);
                    return true;
                }

                @Override
                public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                    return false;
                }

                @Override
                public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                    switch(item.getItemId()){
                        case R.id.menu_item_delete_crime:
                            CrimeAdapter adapter = (CrimeAdapter) getListAdapter();
                            CrimeLab crimeLab = CrimeLab.get(getActivity());
                            for(int i = adapter.getCount()-1; i>= 0; i--){
                                if(getListView().isItemChecked(i)) {
                                    crimeLab.deleteCrime(adapter.getItem(i));
                                }
                            }
                            mode.finish();
                            adapter.notifyDataSetChanged();
                            if(getActivity().findViewById(R.id.detailFragmentContainer) != null) {
                                FragmentManager fm = getActivity().getSupportFragmentManager();
                                FragmentTransaction ft = fm.beginTransaction();

                                Fragment detailRemove = fm.findFragmentById(R.id.detailFragmentContainer);
                                if(detailRemove != null){
                                    ft.remove(detailRemove);
                                }
                                ft.commit();
                            }
                            return true;
                        default:
                            return false;
                    }
                }

                @Override
                public void onDestroyActionMode(ActionMode mode) {

                }
            });
        }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
            ((ActionBarActivity)getActivity()).getSupportActionBar().setSubtitle(R.string.subtitle);
        }
        return v;
    }

    @Override
    public void onResume(){
        super.onResume();
        //Guardar información
        ((CrimeAdapter)getListAdapter()).notifyDataSetChanged();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        super.onCreateOptionsMenu(menu,inflater);
        inflater.inflate(R.menu.fragment_crime_list,menu);
        MenuItem showSubtitle = menu.findItem(R.id.menu_item_show_subtitle);
        if(mSubtitleVisible && showSubtitle != null){
            showSubtitle.setTitle(R.string.hide_subtitle);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.menu_item_new_crime:
                newCrime();
                return true;
            case R.id.menu_item_show_subtitle:
                ActionBarActivity activity = (ActionBarActivity)getActivity();
                if(activity.getSupportActionBar().getSubtitle() == null) {
                    activity.getSupportActionBar().setSubtitle(R.string.subtitle);
                    mSubtitleVisible = true;
                    item.setTitle(R.string.hide_subtitle);

                } else {
                    activity.getSupportActionBar().setSubtitle(null);
                    mSubtitleVisible = false;
                    item.setTitle(R.string.show_subtitle);

                }

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        getActivity().getMenuInflater().inflate(R.menu.crime_list_item_context, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = info.position;
        CrimeAdapter adapter = (CrimeAdapter) getListAdapter();
        Crime crime = adapter.getItem(position);

        switch(item.getItemId()){
            case R.id.menu_item_delete_crime:
                CrimeLab.get(getActivity()).deleteCrime(crime);
                adapter.notifyDataSetChanged();
                return true;
        }
        return super.onContextItemSelected(item);

    }



    @Override
    public void onListItemClick(ListView l, View v, int position, long id){
        Crime c = ((CrimeAdapter)getListAdapter()).getItem(position);
        mCallbacks.onCrimeSelected(c);
        if(getActivity().findViewById(R.id.detailFragmentContainer) != null) {
            getActivity().setTitle(c.getTitle());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == REQUEST_CRIME){

        }
    }

    public void newCrime(){
        Crime crime = new Crime();
        CrimeLab.get(getActivity()).addCrime(crime);
        ((CrimeAdapter)getListAdapter()).notifyDataSetChanged();
        mCallbacks.onCrimeSelected(crime);
    }

    public void updateUI() {
        ((CrimeAdapter)getListAdapter()).notifyDataSetChanged();
    }

    private class CrimeAdapter extends ArrayAdapter<Crime>{
        public CrimeAdapter(ArrayList<Crime> crimes){
            super(getActivity(),0,crimes);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            if(convertView==null){
                convertView = getActivity().getLayoutInflater()
                        .inflate(R.layout.list_item_crime, null);
            }

           //Configurar la vista para el crimen
            Crime c = getItem(position);

            TextView titleTextView =
                    (TextView) convertView.findViewById(R.id.crime_list_item_titleTextView);
            titleTextView.setText(c.getTitle());

            TextView dataTextView =
                    (TextView) convertView.findViewById(R.id.crime_list_item_dateTextView);
            dataTextView.setText(c.getDate().toString());

            CheckBox solvedCheckBox =
                    (CheckBox)convertView.findViewById(R.id.crime_list_item_solvedCheckBox);
            solvedCheckBox.setChecked(c.isSolved());

            return convertView;

        }

    }
}

