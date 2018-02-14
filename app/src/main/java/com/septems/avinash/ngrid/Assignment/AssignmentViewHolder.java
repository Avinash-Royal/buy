package com.septems.avinash.ngrid.Assignment;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.septems.avinash.ngrid.Assignment.models.Assignment;
import com.septems.avinash.ngrid.R;


public class AssignmentViewHolder extends RecyclerView.ViewHolder {
    public TextView Assigment, StartDate, EndDate, Description, imagename, view, download, string;
    public LinearLayout linearLayout;

    public AssignmentViewHolder(View itemView) {
        super(itemView);

        Assigment = itemView.findViewById(R.id.Subject_name);
        StartDate = itemView.findViewById(R.id.Start_Date);
        EndDate = itemView.findViewById(R.id.End_Date);
        Description = itemView.findViewById(R.id.Description);
        imagename = itemView.findViewById(R.id.image_name);
        string = itemView.findViewById(R.id.string);
        view = itemView.findViewById(R.id.view);
        download = itemView.findViewById(R.id.download);
        linearLayout = itemView.findViewById(R.id.image_upload_layout);
    }

    public void bindToPost(Assignment assignment) {
        Assigment.setText(assignment.Subject);
        StartDate.setText(assignment.StartDate);
        EndDate.setText(assignment.EndDate);
        Description.setText(assignment.Description);
        string.setText(assignment.pdf);
        imagename.setText(assignment.Subject.concat(".file"));
    }
}
