package it.unisa.runnerapp.Dao.Implementation;

import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import it.unisa.runnerapp.beans.Run;
import it.unisa.runnerapp.beans.Runner;
import it.unisa.runnerapp.Dao.Interf.RunDao;
import it.unisa.runnerapp.utils.ConnectionUtil;

/**
 * Created by Paolo on 27/01/2018.
 */

public class RunDaoImpl implements RunDao {


    @Override
    public void createRun(final Run run) {
        try {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground( final Void ... params ) {
                    PreparedStatement ps = null;
                    try {

                        String sql = "INSERT INTO Corse "
                                + " (punto_ritrovo_lat, punto_ritrovo_lng, data_inizio, master)" +
                                "    VALUES (?, ?, ?, ?)";

                        ps = ConnectionUtil.getConnection().prepareStatement(sql);

                        ps.setDouble(1,run.getMeetingPoint().latitude);
                        ps.setDouble(2,run.getMeetingPoint().longitude);
                        ps.setTimestamp(3,new Timestamp(run.getStartDate().getTime()));
                        ps.setString(4,run.getMaster().getNickname());

                        int result = ps.executeUpdate();




                    }

                    catch (SQLException e) {
                        Log.e("SQLException",Log.getStackTraceString(e));
                    }

                    return null;


                }



            }.execute().get();
        }

        catch (Exception e) {
            Log.e("Exception",Log.getStackTraceString(e));
        }
    }

    @Override
    public void updateRun(final Run run) {


        try {

            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground( final Void ... params ) {
                    PreparedStatement ps = null;
                    String sql = "UPDATE Utenti SET Corse.punto_ritrovo_lat = ?, Corse.punto_ritrovo_lng= ? , Corse.data_inizio= ?";

                    try {

                        ps = ConnectionUtil.getConnection().prepareStatement(sql);
                        ps.setDouble(1, run.getMeetingPoint().latitude);
                        ps.setDouble(2,run.getMeetingPoint().longitude);
                        ps.setTimestamp(3,new Timestamp(run.getStartDate().getTime()));
                        int result = ps.executeUpdate();

                    }

                    catch (SQLException e) {
                        Log.e("Exception",Log.getStackTraceString(e));
                    }
                    return null;
                }



            }.execute().get();

        }

        catch (Exception e) {
            Log.e("Exception",Log.getStackTraceString(e));
        }


    }

    @Override
    public void deleteRun(final int idrun) {

        try {

            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground( final Void ... params ) {
                    PreparedStatement ps = null;
                    String sql = "DELETE FROM Corse WHERE Corse.id ='" + idrun + "'";

                    try {

                        ps = ConnectionUtil.getConnection().prepareStatement(sql);
                        int result = ps.executeUpdate();

                    }

                    catch (SQLException e) {
                        Log.e("Exception",Log.getStackTraceString(e));
                    }
                    return null;
                }



            }.execute().get();

        }

        catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public Run findByID(final int idrun) {

        try {

            return  new AsyncTask<Void, Void, Run>() {
                @Override
                protected Run doInBackground( final Void ... params ) {
                    ResultSet rs =null;
                    PreparedStatement ps = null;
                    Run run = null;
                    try {

                        ps = ConnectionUtil.getConnection().prepareStatement("select * from Corse join Utenti on Utenti.nickname=Corse.master WHERE Corse.id =" + idrun);
                        rs = ps.executeQuery();

                        rs.next();


                        run = new Run();

                        run.setId(rs.getInt("id"));
                        LatLng latLng = new LatLng(rs.getDouble("punto_ritrovo_lat"),rs.getDouble("punto_ritrovo_lng"));
                        run.setMeetingPoint(latLng);

                        run.setStartDate((java.util.Date) rs.getDate("data_inizio"));

                        Runner runner = new Runner();
                        runner.setNickname(rs.getString("nickname"));
                        runner.setPassword(rs.getString("password"));
                        runner.setName(rs.getString("nome"));
                        runner.setSurname(rs.getString("cognome"));
                        runner.setBirthDate(rs.getDate("data_nascita"));
                        runner.setWeight(rs.getDouble("peso"));
                        runner.setLevel(rs.getShort("livello"));
                        runner.setTraveledKilometers(rs.getDouble("km_percorsi"));

                        run.setMaster(runner);



                    }


                    catch (SQLException e) {
                        Log.e("SQLException",Log.getStackTraceString(e));
                    }
                    return run;
                }

                @Override
                protected void onPostExecute( Run result ) {
                    super.onPostExecute(result);
                }
            }.execute().get();
        }
        catch (Exception e) {
            Log.e("Exception",Log.getStackTraceString(e));
        }

        return null;



    }

    @Override
    public List<Run> getAllRuns() {
        List<Run> run = null;
        try {

            run = new AsyncTask<Void, Void, List<Run>>() {
                @Override
                protected List<Run> doInBackground( final Void ... params ) {
                    ResultSet rs =null;
                    List<Run> runs = new ArrayList<Run>();
                    PreparedStatement ps = null;
                    try {


                        ps = ConnectionUtil.getConnection().prepareStatement("select * from Corse");
                        rs = ps.executeQuery();

                        while (rs.next()) {
                            Run run = new Run();
                            run.setId(rs.getInt("id"));
                            LatLng latLng = new LatLng(rs.getDouble("punto_ritrovo_lat"),rs.getDouble("punto_ritrovo_lng"));
                            run.setMeetingPoint(latLng);

                            run.setStartDate(rs.getTimestamp("data_inizio"));

                            Runner runner = new Runner();
                            runner.setNickname(rs.getString("master"));
                            run.setMaster(runner);

                            runs.add(run);

                        }

                    }


                    catch (SQLException e) {
                        Log.e("SQLException",Log.getStackTraceString(e));
                    }
                    return runs;
                }

                @Override
                protected void onPostExecute( List<Run> result ) {
                    super.onPostExecute(result);
                }
            }.execute().get();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return run;
    }
}
