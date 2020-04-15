package demo.expresso.infosystest.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import demo.expresso.infosystest.R;
import demo.expresso.infosystest.model.Row;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.MovieViewHolder> {
private List<Row> imageList;
private int rowLayout;
private Context context;
    public ImageLoader imageLoader;

    public ImageAdapter(List<Row> movies, Context context) {
        this.imageList = movies;
        this.rowLayout = rowLayout;
        this.context = context;
        imageLoader=new ImageLoader(context);
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder{
        TextView imageTitle,description;
       ImageView imageView;
        View divider;
        public MovieViewHolder(@NonNull View v) {
            super(v);
            imageTitle = (TextView) v.findViewById(R.id.title);
            imageView = (ImageView) v.findViewById(R.id.image);
            description = (TextView) v.findViewById(R.id.description);
            divider =(View)v.findViewById(R.id.view);

        }
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_movie,parent,false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieViewHolder holder, int position) {
        holder.imageTitle.setText(imageList.get(position).getTitle());
        holder.description.setText("Description :\n"+imageList.get(position).getDescription());
        Picasso.get().load(imageList.get(position).getImageHref()).into(holder.imageView);
        if(position==imageList.size()-1)
            holder.divider.setVisibility(View.GONE);
        else
            holder.divider.setVisibility(View.VISIBLE);

    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }


}
