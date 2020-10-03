package com.example.foodrunner.CatManagement.Activity;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodrunner.CatManagement.Model.Products;
import com.example.foodrunner.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageAdapter  extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private Context mContext;
    private List<Products> productList;
    private OnItemClickListener mListener;

    public ImageAdapter(Context context , List<Products> products){
        mContext = context;
        productList = products;
    }


    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.list_layout,parent,false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        Products productsCurrent = productList.get(position);

        holder.textViewName.setText(productsCurrent.getName());
        holder.textViewPrice.setText("Rs. "+productsCurrent.getPrice().toString());
       // holder.textViewCategory.setText(productsCurrent.getCategory());
        Picasso.with(mContext).load(productsCurrent.getImageURL()).into(holder.imageViewDb);

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener , View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {

        TextView textViewName;
        TextView textViewPrice;
        TextView textViewCategory;
        ImageView imageViewDb;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
            textViewCategory = itemView.findViewById(R.id.textViewCategory);
            imageViewDb = itemView.findViewById(R.id.imageViewdb);

            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);

        }

        @Override
        public void onClick(View view) {
            if(mListener != null){
                int position = getAdapterPosition();
                if(position != RecyclerView.NO_POSITION){
                    mListener.onItemClick(position);
                }
            }
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.setHeaderTitle("Select an Action");
            MenuItem updatePro = contextMenu.add(Menu.NONE,1,1,"Update Product");
            MenuItem delete = contextMenu.add(Menu.NONE,2,2,"Delete");

            updatePro.setOnMenuItemClickListener(this);
            delete.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            if(mListener != null){
                int position = getAdapterPosition();
                if(position != RecyclerView.NO_POSITION){
                    switch(menuItem.getItemId()){
                        case 1:
                            mListener.onUpdate(position);
                            return true;
                        case 2:
                            mListener.onDeleteClick(position);
                            return true;
                    }
                }
            }
            return false;
        }
    }

    public interface OnItemClickListener{
        void onItemClick(int position);

        void onUpdate(int position);

        void onDeleteClick(int position);

    }
    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;

    }
}
