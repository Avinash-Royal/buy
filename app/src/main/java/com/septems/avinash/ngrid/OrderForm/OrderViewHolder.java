package com.septems.avinash.ngrid.OrderForm;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.septems.avinash.ngrid.Assignment.models.Assignment;
import com.septems.avinash.ngrid.OrderForm.Model.Order;
import com.septems.avinash.ngrid.R;


public class OrderViewHolder extends RecyclerView.ViewHolder {
    public TextView Rice, ToorDal, Wheat, Rajama, Choole, Matar;

    public OrderViewHolder(View itemView) {
        super(itemView);

        Rice = itemView.findViewById(R.id.Subject_name);
        ToorDal = itemView.findViewById(R.id.Start_Date);
        Wheat = itemView.findViewById(R.id.End_Date);
        Rajama = itemView.findViewById(R.id.Description);
        Choole = itemView.findViewById(R.id.image_name);
        Matar = itemView.findViewById(R.id.string);
    }

    public void bindToPost(Order order) {
        Rice.setText(order.Rice);
        ToorDal.setText(order.ToorDal);
        Wheat.setText(order.Wheat);
        Rajama.setText(order.Rajama);
        Choole.setText(order.Choole);
        Matar.setText(order.Matar);
    }
}
