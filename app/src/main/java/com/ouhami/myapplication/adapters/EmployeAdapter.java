package com.ouhami.myapplication.adapters;

import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ouhami.myapplication.R;
import com.ouhami.myapplication.beans.Employe;
import com.ouhami.myapplication.beans.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;

// ProfesseurAdapter.java
public class EmployeAdapter extends RecyclerView.Adapter<EmployeAdapter.EmployeViewHolder> {

    private List<Employe> employes;

    public EmployeAdapter(List<Employe> employes) {
        this.employes = employes;
    }

    @NonNull
    @Override
    public EmployeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_employe, parent, false);
        return new EmployeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeViewHolder holder, int position) {
        Employe employe = employes.get(position);
        holder.bind(employe);
    }

    @Override
    public int getItemCount() {
        return employes.size();
    }

    public class EmployeViewHolder extends RecyclerView.ViewHolder {

        private TextView nomTextView;
        private TextView prenomTextView;
        private TextView dateNaissanceTextView;
        private TextView serviceTextView;


        public EmployeViewHolder(@NonNull View itemView) {
            super(itemView);
            nomTextView = itemView.findViewById(R.id.nom);
            prenomTextView = itemView.findViewById(R.id.prenom);
            dateNaissanceTextView = itemView.findViewById(R.id.dateNaissance);
            serviceTextView = itemView.findViewById(R.id.service);
        }

        public void bind(Employe employe) {
            nomTextView.setText(employe.getNom());
            prenomTextView.setText(employe.getPrenom());
            DateTimeFormatter formatter = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                dateNaissanceTextView.setText(employe.getDateNaissance().format(formatter));
            }

            Service service = employe.getService();
            if (service != null) {
                serviceTextView.setText(service.getNom());
            } else {
                serviceTextView.setText("N/A"); // or any default value
            }
            // Bind other attributes
        }
    }
}

