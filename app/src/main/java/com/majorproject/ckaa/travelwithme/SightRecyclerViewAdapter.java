package com.majorproject.ckaa.travelwithme;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ckaa on 8/1/2016.
 */
public class SightRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    public static final int HEADER = 0;
    public static final int CHILD = 1;
    public static final int SightHeader = 2;
    public static final int SightChild = 3;

     static List<GetDataAdapter> getDataAdapter;
    public  static Context context;
    public SightRecyclerViewAdapter(List<GetDataAdapter> getDataAdapter, Context context){
        super();
        this.getDataAdapter = getDataAdapter;
        this.context = context;
        
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int type) {
        //return null; 
        View view = null;
        Context context = parent.getContext();

        if (type == HEADER) {

            LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.sight_header, parent, false);
            ListHeaderViewHolder header = new ListHeaderViewHolder(view);
            return header;
        } else if (type == CHILD) {
            LayoutInflater inflaterr = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflaterr.inflate(R.layout.sight_child, parent, false);
            ListSightChildViewHolder child = new ListSightChildViewHolder(view);
            return child;
        } else if (type == SightHeader) {
            LayoutInflater inflaterr = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflaterr.inflate(R.layout.sight_header, parent, false);
            ListHeaderViewHolder header = new ListHeaderViewHolder(view);
            return header;
        } else {

            LayoutInflater inflaterr = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflaterr.inflate(R.layout.sight_child, parent, false);
            ListChildViewHolder Schild = new ListChildViewHolder(view);
            return Schild;



        }
    }

    
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        final GetDataAdapter getDataAdapter1 = getDataAdapter.get(position);
        //  switch (getDataAdapter1.getType()) {
        if (holder.getItemViewType() == HEADER) {
            final ListHeaderViewHolder itemController = (ListHeaderViewHolder) holder;
            itemController.refferalItem = getDataAdapter1;
            //Glide.with(context).load("http://192.168.0.119/hotel/" + getDataAdapter1.getPhoto()).centerCrop().into(itemController.expand_image);
            itemController.header_title.setText(getDataAdapter1.getStatic_heritage_title());
//
             itemController.header_image.setImageResource(getDataAdapter1.getStatic_heritage_photo());
//                itemController.header_title.setText(item.getItem_name());
            if (getDataAdapter1.invisibleChildren == null) {
                itemController.btn_expand_toggle.setImageResource(R.drawable.circle_minus);
            } else {
                itemController.btn_expand_toggle.setImageResource(R.drawable.circle_plus);
            }
            itemController.btn_expand_toggle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getDataAdapter1.invisibleChildren == null) {
                        getDataAdapter1.invisibleChildren = new ArrayList<>();
                        int count = 0;
                        int pos = getDataAdapter.indexOf(itemController.refferalItem);
                        while (getDataAdapter.size() > pos + 1 && getDataAdapter.get(pos + 1).getType() == CHILD) {
                            getDataAdapter1.invisibleChildren.add(getDataAdapter.remove(pos + 1));
                            count++;
                        }
                        notifyItemRangeRemoved(pos + 1, count);
                        itemController.btn_expand_toggle.setImageResource(R.drawable.circle_plus);
                    } else {
                        int pos = getDataAdapter.indexOf(itemController.refferalItem);
                        int index = pos + 1;
                        for (GetDataAdapter i : getDataAdapter1.invisibleChildren) {
                            getDataAdapter.add(index, i);
                            index++;
                        }
                        notifyItemRangeInserted(pos + 1, index - pos - 1);
                        itemController.btn_expand_toggle.setImageResource(R.drawable.circle_minus);
                        getDataAdapter1.invisibleChildren = null;
                    }
                }
            });
        } else if (holder.getItemViewType() == CHILD) {

            final ListSightChildViewHolder Childitem = (ListSightChildViewHolder) holder;
            Childitem.refferalItem = getDataAdapter1;
           // Glide.with(context).load("http://192.168.0.119/hotel/" + getDataAdapter1.getHeritage_photo()).centerCrop().into(ChilditemController.sight_child_image);

            Childitem.sight_child_image.setImageResource(getDataAdapter1.getStatic_heritage_photo());
            Childitem.sight_child_title.setText(getDataAdapter1.getStatic_heritage_title());
            Childitem.sight_child_address.setText(getDataAdapter1.getStatic_heritage_address());
            Childitem.sight_child_opening.setText("Opening Time:");
            Childitem.sight_child_openingTime.setText(getDataAdapter1.getStatic_heritage_hours());
            Childitem.sight_child_entrance.setText("Entrance fee:");
            Childitem.sight_child_entranceFee.setText(getDataAdapter1.getStatic_heritage_entraceFee());
            Double mlat = getDataAdapter1.getStatic_heritage_lat();
            Double mlong =getDataAdapter1.getStatic_heritage_long();


           // LatLng fromPosition= new LatLng(mlat,mlong);

            LatLng fromPostion = new LatLng(mlat,mlong);
            final Bundle args = new Bundle();
            final String  title = getDataAdapter1.getStatic_heritage_title();

                args.putParcelable("longLat_dataPrivider", fromPostion);

            Childitem.sight_map_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent in = new Intent(context,mapsecondActivity.class);
                    in.putExtras(args);
                    in.putExtra("title",title);
                    context.startActivity(in);

                }
            });
            //ImageView itemImageView = (ImageView)holder.itemView;
//                TextView itemTextView = (TextView) holder.itemView;
//                //itemImageView.setImageResource(ItemList.get(position).getPhoto());
//                itemTextView.setText(ItemList.get(position).getItem_name());


        } else if (holder.getItemViewType() == SightHeader) {
            final ListHeaderViewHolder itemController = (ListHeaderViewHolder) holder;
            itemController.refferalItem = getDataAdapter1;
           // Glide.with(context).load("http://192.168.0.119/hotel/" + getDataAdapter1.getPhoto()).centerCrop().into(itemController.expand_image);
            itemController.header_title.setText(getDataAdapter1.getStatic_heritage_title());
//
              itemController.header_image.setImageResource(getDataAdapter1.getStatic_heritage_photo());
//                itemController.header_title.setText(item.getItem_name());
            if (getDataAdapter1.invisibleChildren == null) {
                itemController.btn_expand_toggle.setImageResource(R.drawable.circle_minus);
            } else {
                itemController.btn_expand_toggle.setImageResource(R.drawable.circle_plus);
            }
            itemController.btn_expand_toggle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getDataAdapter1.invisibleChildren == null) {
                        getDataAdapter1.invisibleChildren = new ArrayList<>();
                        int count = 0;
                        int pos = getDataAdapter.indexOf(itemController.refferalItem);
                        while (getDataAdapter.size() > pos + 1 && getDataAdapter.get(pos + 1).getType() == SightChild) {
                            getDataAdapter1.invisibleChildren.add(getDataAdapter.remove(pos + 1));
                            count++;
                        }
                        notifyItemRangeRemoved(pos + 1, count);
                        itemController.btn_expand_toggle.setImageResource(R.drawable.circle_plus);
                    } else {
                        int pos = getDataAdapter.indexOf(itemController.refferalItem);
                        int index = pos + 1;
                        for (GetDataAdapter i : getDataAdapter1.invisibleChildren) {
                            getDataAdapter.add(index, i);
                            index++;
                        }
                        notifyItemRangeInserted(pos + 1, index - pos - 1);
                        itemController.btn_expand_toggle.setImageResource(R.drawable.circle_minus);
                        getDataAdapter1.invisibleChildren = null;
                    }
                }
            });

        }
        else {


            final ListChildViewHolder ChilditemController = (ListChildViewHolder) holder;
            ChilditemController.refferalItem = getDataAdapter1;
            Glide.with(context).load(Json_Url.ip+"hotel/" + getDataAdapter1.getHeritage_photo()).centerCrop().into(ChilditemController.sight_child_image);

            //ChilditemController.child_image.setImageResource(getDataAdapter1.getPhoto());
            ChilditemController.sight_child_title.setText(getDataAdapter1.getHeritage_title());
            ChilditemController.sight_child_address.setText(getDataAdapter1.getHeritage_address());
            ChilditemController.sight_child_opening.setText("Opening Time:");
            ChilditemController.sight_child_openingTime.setText(getDataAdapter1.getHeritage_hours());
            ChilditemController.sight_child_entrance.setText("Entrance fee:");
            ChilditemController.sight_child_entranceFee.setText(getDataAdapter1.getHeritage_entraceFee());

            Double latitude = getDataAdapter1.getHeritage_lat();
            Double longitude = getDataAdapter1.getHeritage_long();
           final String title = getDataAdapter1.getHeritage_title();
            final Bundle args = new Bundle();
            LatLng fromPosition = new LatLng(latitude,longitude);
           // args.putParcelable("longLat_dataPrivider",fromPosition);
            args.putParcelable("longLat_dataPrivider", fromPosition);
            ChilditemController.sight_map_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context,mapsecondActivity.class);
                    i.putExtras(args);
                    i.putExtra("title",title);
                    context.startActivity(i);
                }
            });


            //Glide.with(context).load("http://192.168.0.119/hotel/" + getDataAdapter1.getPhoto()).centerCrop().into(ChilditemController.child_image);

            //ChilditemController.child_image.setImageResource(getDataAdapter1.getPhoto());
//            SightChilditemController.res_child_title.setText(getDataAdapter1.getResName());
//            SightChilditemController.res_child_address.setText(getDataAdapter1.getResAddress());



        }




    }

   
    @Override
    public int getItemCount() {
        return getDataAdapter.size();
    }
    @Override
    public int getItemViewType(int position) {
        return getDataAdapter.get(position).getType();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView HotelImageView;
        public TextView NameTextView;
        public TextView AddressTextView;
        public RatingBar HotelRatingBar;
        public Button HotelBook;
        public CardView HotelLink;
        public Button SeeMap;


        public ViewHolder(View itemView) {

            super(itemView);
//            itemView.setOnClickListener(this);
//            //IdTextView = (TextView) itemView.findViewById(R.id.textView2) ;
//            HotelImageView = (ImageView) itemView.findViewById(R.id.hotelImage);
//            NameTextView = (TextView) itemView.findViewById(R.id.hotelName) ;
//            AddressTextView = (TextView) itemView.findViewById(R.id.hotelAddress) ;
//            HotelRatingBar = (RatingBar) itemView.findViewById(R.id.ratingBar) ;
//            HotelBook=(Button)itemView.findViewById(R.id.hotel_book);
//            SeeMap=(Button)itemView.findViewById(R.id.Map);
//
//
//        }
//
//        /**
//         * Called when a view has been clicked.
//         *
//         * @param v The view that was clicked.
//         */
//        @Override
//        public void onClick(View v) {
//            GetDataAdapter getDataAdapter1 = getDataAdapter.get(getLayoutPosition());
//            Uri url = Uri.parse(getDataAdapter1.getHotellink());
//            Intent i = new Intent(Intent.ACTION_VIEW,url);
//            context.startActivity(i);

        }
    }


    private static class ListHeaderViewHolder extends RecyclerView.ViewHolder {
        public TextView header_title;
        public ImageView btn_expand_toggle;
        public ImageView header_image;
        public GetDataAdapter refferalItem;

        public ListHeaderViewHolder(View itemView) {
            super(itemView);
            header_image = (ImageView) itemView.findViewById(R.id.sight_header_image);
            header_title = (TextView) itemView.findViewById(R.id.sight_header_tilte);
            btn_expand_toggle = (ImageView) itemView.findViewById(R.id.btn_expand_toggle);
        }
    }

    private static class ListChildViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView sight_child_title;
        private ImageView sight_child_image;
        private TextView sight_child_address;
        private TextView sight_child_opening;
        private TextView sight_child_openingTime;
        private TextView sight_child_entrance;
        private TextView sight_child_entranceFee;
        private Button sight_map_button;
        public GetDataAdapter refferalItem;

        public ListChildViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            sight_child_image = (ImageView) itemView.findViewById(R.id.sight_child_image);
            sight_child_title = (TextView) itemView.findViewById(R.id.sight_child_tilte);
            sight_child_address =(TextView) itemView.findViewById(R.id.sight_child_address);
            sight_child_opening = (TextView) itemView.findViewById(R.id.sight_child_opening);
            sight_child_openingTime= (TextView) itemView.findViewById(R.id.sight_child_openingTime);
            sight_child_entrance = (TextView) itemView.findViewById(R.id.sight_child_entrance);
            sight_child_entranceFee = (TextView) itemView.findViewById(R.id.sight_child_entranceFee);
            sight_map_button = (Button)itemView.findViewById(R.id.sight_map_button);




        }

        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {
        GetDataAdapter getDataAdapter1  = getDataAdapter.get(getLayoutPosition());
            Uri url =Uri.parse(Json_Url.ip + getDataAdapter1.getHeritage_link());
            Intent i  = new Intent(Intent.ACTION_VIEW,url);
            context.startActivity(i);




        }

    }


    private static class ListSightChildViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView sight_child_title;
        private ImageView sight_child_image;
        private TextView sight_child_address;
        private TextView sight_child_opening;
        private TextView sight_child_openingTime;
        private TextView sight_child_entrance;
        private TextView sight_child_entranceFee;
        private Button sight_map_button;
        public GetDataAdapter refferalItem;

        public ListSightChildViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            sight_child_image = (ImageView) itemView.findViewById(R.id.sight_child_image);
            sight_child_title = (TextView) itemView.findViewById(R.id.sight_child_tilte);
            sight_child_address =(TextView) itemView.findViewById(R.id.sight_child_address);
            sight_child_opening = (TextView) itemView.findViewById(R.id.sight_child_opening);
            sight_child_openingTime= (TextView) itemView.findViewById(R.id.sight_child_openingTime);
            sight_child_entrance = (TextView) itemView.findViewById(R.id.sight_child_entrance);
            sight_child_entranceFee = (TextView) itemView.findViewById(R.id.sight_child_entranceFee);
            sight_map_button = (Button)itemView.findViewById(R.id.sight_map_button);




        }

        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {
            GetDataAdapter getDataAdapter1 = getDataAdapter.get(getLayoutPosition());
            Uri url = Uri.parse(getDataAdapter1.getStatic_heritage_link());
            Intent i = new Intent(Intent.ACTION_VIEW, url);
            context.startActivity(i);

        }


        }


    }

