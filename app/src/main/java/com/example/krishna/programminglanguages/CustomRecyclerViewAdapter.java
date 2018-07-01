package com.example.krishna.programminglanguages;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

class SimpleViewHolder extends RecyclerView.ViewHolder{
    TextView language;

    public SimpleViewHolder(View itemView) {
        super(itemView);
        language=(TextView)itemView.findViewById(R.id.lang_textview);
    }
}

public class CustomRecyclerViewAdapter extends RecyclerView.Adapter<SimpleViewHolder> {

    ArrayList<String> lang_name;
    Context mcontext;

    public CustomRecyclerViewAdapter(ArrayList<String> lang_name, Context mcontext) {
        this.mcontext=mcontext;
        this.lang_name = lang_name;
    }

    @NonNull
    @Override
    public SimpleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_design,parent,false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SimpleViewHolder holder, final int position) {

        holder.language.setText(lang_name.get(position));
        if(MainActivity.status==0) {
            holder.language.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mcontext);
                    builder.setMessage("Do you really want to delete?");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String lang = holder.language.getText().toString();
                            new DatabaseHelper(mcontext).deleteLanguage(lang);
                            Log.d("position", String.valueOf(position));
                            Log.d("total", "" + getItemCount());
                            notifyItemRemoved(position);
                            Log.d("total", "" + getItemCount());
                            lang_name.remove(position);
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return lang_name.size();
    }
}
