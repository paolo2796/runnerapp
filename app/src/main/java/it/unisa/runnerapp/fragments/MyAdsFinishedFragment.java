package it.unisa.runnerapp.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import it.unisa.runnerapp.Dao.Implementation.FinishedRunDaoImpl;
import it.unisa.runnerapp.Dao.Implementation.PActiveRunDaoImpl;
import it.unisa.runnerapp.adapters.AdActiveAdapter;
import it.unisa.runnerapp.adapters.AdFinishedAdapter;
import it.unisa.runnerapp.beans.ActiveRun;
import it.unisa.runnerapp.beans.FinishedRun;
import testapp.com.runnerapp.MainActivityPV;
import testapp.com.runnerapp.R;

/**
 * Created by Paolo on 07/02/2018.
 */

public class MyAdsFinishedFragment extends Fragment implements AdFinishedAdapter.Communicator {


    List<ActiveRun> runsactive;
    ListView listview;
    public AdFinishedAdapter arrayadapter;
    List<FinishedRun> runs = null;

    CommunicatorActivity communicatoractivity;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        runs = new FinishedRunDaoImpl().findByRunnerWithoutMaster(MainActivityPV.userlogged.getNickname(),"data_inizio");
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.adsgen_fragment, container, false);
        listview = (ListView) v.findViewById(R.id.listview);
        arrayadapter = new AdFinishedAdapter(this.getActivity(),R.layout.row_myadsfinished,runs);
        listview.setAdapter(arrayadapter);
        arrayadapter.setCommunicator(this);


        return v;
    }



    public static MyAdsFinishedFragment newInstance(){
        return new MyAdsFinishedFragment();
    }


    @Override
    public void respondDetailRun(int index) {

        communicatoractivity.responMyFinishedDetailRun(index);

    }

    public interface CommunicatorActivity {
        public void responMyFinishedDetailRun(int index);
    }

    public void setCommunicatoractivity(MyAdsFinishedFragment.CommunicatorActivity communicatoractivity){

        this.communicatoractivity=communicatoractivity;
    }
}
