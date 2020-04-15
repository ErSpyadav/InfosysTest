package demo.expresso.infosystest.adapter;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

import demo.expresso.infosystest.R;
import demo.expresso.infosystest.model.Row;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.MyHolder> {

    private List<Row> rowList;
    private HashMap<String, Bitmap> bitmapHashMap;

    public ImageAdapter(List<Row> rows) {
        this.rowList = rows;
        bitmapHashMap = new HashMap<>();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        TextView imageTitle, description;
        ImageView imageView;
        View divider;

        public MyHolder(@NonNull View v) {
            super(v);
            imageTitle = v.findViewById(R.id.title);
            imageView = v.findViewById(R.id.image);
            description = v.findViewById(R.id.description);
            divider = v.findViewById(R.id.divider);

        }
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_image, parent, false);
        return new MyHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, int position) {
        holder.imageTitle.setText(rowList.get(position).getTitle());
        holder.description.setText("Description :\n" + rowList.get(position).getDescription());
        Picasso.get().load(rowList.get(position).getImageHref()).placeholder(R.drawable.ic_empty_image).into(holder.imageView);
        if (position == rowList.size() - 1)
            holder.divider.setVisibility(View.GONE);
        else
            holder.divider.setVisibility(View.VISIBLE);

    }

    @Override
    public int getItemCount() {
        return rowList.size();
    }


}
