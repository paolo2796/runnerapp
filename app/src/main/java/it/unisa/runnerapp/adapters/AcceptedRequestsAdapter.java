package it.unisa.runnerapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import it.unisa.runnerapp.beans.Runner;
import it.unisa.runnerapp.utils.CheckUtils;
import it.unisa.runnerapp.utils.LevelMapper;
import testapp.com.runnerapp.R;

public class AcceptedRequestsAdapter extends ArrayAdapter<Runner>
{
    private int            resId;
    private LayoutInflater inflater;

    public AcceptedRequestsAdapter(Context ctx, int resId, List<Runner> list)
    {
        super(ctx,resId,list);
        this.resId=resId;
        inflater=LayoutInflater.from(ctx);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent)
    {
        if (view==null)
            view=inflater.inflate(resId,null);

        Runner runner=getItem(position);
        CircleImageView profileImg=(CircleImageView)view.findViewById(R.id.userImg);
        TextView tvNames=(TextView)view.findViewById(R.id.names);
        TextView tvInfo=(TextView)view.findViewById(R.id.personalInfo);
        ImageButton pathButton=(ImageButton)view.findViewById(R.id.pathButton);

        profileImg.setImageDrawable(runner.getProfileImage());
        tvNames.setText(runner.getName()+" "+runner.getSurname()+","+runner.getNickname());
        tvInfo.setText(CheckUtils.getAge(runner.getBirthDare())+" anni, Livello "+ LevelMapper.getLevelName(runner.getLevel()));

        return view;
    }
}
