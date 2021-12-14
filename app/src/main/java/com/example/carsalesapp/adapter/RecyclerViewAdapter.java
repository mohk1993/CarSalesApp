package com.example.carsalesapp.adapter;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carsalesapp.AddCarActivity;
import com.example.carsalesapp.CurrentUserActivity;
import com.example.carsalesapp.PostDetailsActivity;
import com.example.carsalesapp.R;
import com.example.carsalesapp.converters.Converters;
import com.example.carsalesapp.model.CarEntity;
import com.example.carsalesapp.model.CarInformation;
import com.example.carsalesapp.model.UserInformation;
import com.example.carsalesapp.viewmodel.CarViewModel;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import util.CarApi;

public class RecyclerViewAdapter extends RecyclerView.Adapter <RecyclerViewAdapter.ViewHolder>{
    private List<CarEntity> carList;
    private Context context;
    private CarViewModel carViewModel;
    public static final String CAR_ID = "car_id";
    public RecyclerViewAdapter(List<CarEntity> carList, Context context) {
        this.carList = carList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_row,parent,false);
        return new ViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CarEntity carEntity = Objects.requireNonNull(carList.get(position));
        holder.Model.setText(carEntity.getModel());
        holder.Manufacturer.setText(carEntity.getManufacturer());
        holder.Price.setText(carEntity.getPrice().toString());
        holder.Owner.setText(carEntity.getFK());
        holder.carImage.setImageBitmap(Converters.ByteToBitMap(carEntity.getImage()));
    }

    @Override
    public int getItemCount() {
        return Objects.requireNonNull(carList.size());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView Model;
        public TextView Manufacturer;
        public TextView Price;
        public TextView Owner;
        public TextView Description;
        public ImageView carImage;
        public ImageButton shareButton;
        public ImageButton seeMoreButton;
        public ViewHolder(@NonNull View itemView, Context ctx) {
            super(itemView);
            context = ctx;
            Model = itemView.findViewById(R.id.model);
            Manufacturer = itemView.findViewById(R.id.manufacturer);
            Price = itemView.findViewById(R.id.price);
            Owner = itemView.findViewById(R.id.userName);
            //Description = itemView.findViewById(R.id.descriptionInfoId);
            carImage = itemView.findViewById(R.id.recyclerCarImage);
            shareButton = itemView.findViewById(R.id.shareBtnId);
            seeMoreButton = itemView.findViewById(R.id.seeMoreBtnId);
            shareButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Intent sendIntent = new Intent();
//                    sendIntent.setAction(Intent.ACTION_SEND);
//                    sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
//                    sendIntent.setType("text/plain");
//
//                    Intent shareIntent = Intent.createChooser(sendIntent, null);
//                    context.startActivity(shareIntent);
                    int position = getAdapterPosition();
                    carViewModel = new ViewModelProvider
                            .AndroidViewModelFactory((Application) context.getApplicationContext())
                            .create(CarViewModel.class);

                    //String imageUrl = carList.get(position).getImage().toString();
                    //shareImage(imageUrl, ctx, position, carList);
                    StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                    StrictMode.setVmPolicy(builder.build());
                    if (isExternalStorageWritable()){
                        Intent shareIntent = new Intent(Intent.ACTION_SEND);
                        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "From Car Sales App");
                        shareIntent.putExtra(Intent.EXTRA_TEXT, CarApi.getInstance().getUserName()
                                +", shared with you car offer," +" posted by:"+carList.get(position).getFK()
                                +"\n\n"+"Car manufacture: " +
                                 carList.get(position).getManufacturer() + ", car price: " +
                                 carList.get(position).getPrice());

                        shareIntent.setType("image/*");
                        shareIntent.putExtra(Intent.EXTRA_STREAM, saveImageExternal(Converters.ByteToBitMap(carList.get(position).getImage())));
                        context.startActivity(Intent.createChooser(shareIntent, "Send Image"));
                    }

                }
            });

            seeMoreButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    Intent informationIntent = new Intent(context, PostDetailsActivity.class);
                    informationIntent.putExtra(CAR_ID,carList.get(position).getId());
                    context.startActivity(informationIntent);
                }
            });
        }
    }
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }
    private Uri saveImageExternal(Bitmap image) {
        //TODO - Should be processed in another thread
        Uri uri = null;
        try {
            File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "to-share.png");
            FileOutputStream stream = new FileOutputStream(file);
            image.compress(Bitmap.CompressFormat.PNG, 90, stream);
            stream.close();
            uri = Uri.fromFile(file);
        } catch (IOException e) {
            Log.d("TAG", "IOException while trying to write file for sharing: " + e.getMessage());
        }
        return uri;
    }
//    static public void shareImage(String url, final Context context, final int position, final List<CarEntity>carList) {
//
//        Picasso.get().load(url).into(new Target() {
//            @Override public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
//                Intent shareIntent = new Intent(Intent.ACTION_SEND);
//                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "From Car Sales App");
//                shareIntent.putExtra(Intent.EXTRA_TEXT, carList.get(position).getManufacturer()
//                        + "\n\n" + carList.get(position).getPrice());
//
//                shareIntent.setType("image/*");
//
//                Uri imageUri = getLocalBitmapUri(bitmap, context);
//
//                shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
//                Log.d("TAG", "onBitmapLoaded: "+imageUri.toString());
//                context.startActivity(Intent.createChooser(shareIntent, "Send Image"));
//            }
//
//            @Override
//            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
//
//            }
//            @Override public void onPrepareLoad(Drawable placeHolderDrawable) { }
//        });
//    }
//    static public Uri getLocalBitmapUri(Bitmap bmp, final Context context) {
//        Uri bmpUri = null;
//        try {
//            File file =  new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "share_image_" + System.currentTimeMillis() + ".png");
//            FileOutputStream out = new FileOutputStream(file);
//            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
//            out.close();
//            bmpUri = FileProvider.getUriForFile(context.getApplicationContext(),"com.example.carsalesapp.fileprovider", file);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return bmpUri;
//    }
}

