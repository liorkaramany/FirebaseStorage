package com.example.liorkaramany.firebasestorage;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageAdapter extends ArrayAdapter<Upload> {

    private Activity context;
    private List<Upload> uploadList;

    public ImageAdapter(Activity context, List<Upload> uploadList) {
        super(context, R.layout.image_item, uploadList);

        this.context = context;
        this.uploadList = uploadList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.image_item, null, true);

        TextView name = (TextView) listViewItem.findViewById(R.id.name);
        ImageView image = (ImageView) listViewItem.findViewById(R.id.image);

        Upload upload = uploadList.get(position);

        name.setText(upload.getName());

        Picasso.get().load(upload.getUrl()).fit().centerInside().into(image);

        return listViewItem;
    }
}
