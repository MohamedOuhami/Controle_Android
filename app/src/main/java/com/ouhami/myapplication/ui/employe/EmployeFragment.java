package com.ouhami.myapplication.ui.employe;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ouhami.myapplication.R;
import com.ouhami.myapplication.adapters.EmployeAdapter;
import com.ouhami.myapplication.beans.Employe;
import com.ouhami.myapplication.beans.Service;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class EmployeFragment extends Fragment {

    public RequestQueue mRequestQueue;
    public StringRequest mStringRequest;
    private String url = "http://192.168.1.158:8080/api/v1/employees";
    private RecyclerView recyclerView;
    private TextView dataText;
    private EmployeAdapter employeAdapter;
    private List<Employe> employes = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_employee, container, false);

        recyclerView = view.findViewById(R.id.employee_recycle_view);
//        dataText = view.findViewById(R.id.dataText);

        // Set up RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
        recyclerView.setLayoutManager(layoutManager);

        employeAdapter = new EmployeAdapter(employes);
        recyclerView.setAdapter(employeAdapter);
        getEmployes();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public void getEmployes() {
        // RequestQueue initialized
        mRequestQueue = Volley.newRequestQueue(requireContext());

        // String Request initialized
        mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONArray jsonArray = null;
                try {
                    // Use normal JSON parsing
                    jsonArray = new JSONArray(response);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Log.d("JSON Employe", jsonObject.toString());

                        // Use normal JSON parsing to create Employe object
                        Employe employe = new Employe();
                        employe.setId(jsonObject.getInt("id"));
                        employe.setNom(jsonObject.getString("nom"));
                        employe.setPrenom(jsonObject.getString("prenom"));

                        // Parse dateNaissance (assuming it's a string in "yyyy-MM-dd" format)
                        // Parse dateNaissance (assuming it's a string in "yyyy-MM-dd'T'HH:mm:ss.SSSXXX" format)
                        if (jsonObject.has("dateNaissance")) {
                            String dateString = jsonObject.getString("dateNaissance");
                            // Parse the date and extract year, month, day
                            LocalDate dateNaissance = null;
                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                                dateNaissance = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX"));
                            }
                            employe.setDateNaissance(dateNaissance); // This will store the date as 'yyyy-MM-dd'
                        }

                        if (jsonObject.has("service")) {
                            JSONObject serviceJson = jsonObject.getJSONObject("service");
                            Service service = new Service();
                            service.setId(serviceJson.getInt("id"));
                            service.setNom(serviceJson.getString("name"));

                            employe.setService(service);
                        }

                        Log.d("Object Employe", employe.toString());

                        employes.add(employe);
                    }

                    // Notify the adapter
                    employeAdapter.notifyDataSetChanged();

                    // Notify the adapter on the main/UI thread
                    requireActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            dataText.setText(response);
                        }
                    });

                    // Show the JSON response in a Toast
                    Toast.makeText(getContext(), "JSON Response:\n" + response, Toast.LENGTH_LONG).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("JSON Length", "The length of your json is " + jsonArray.length());
                Log.d("Employes Array List", employes.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        });

        mRequestQueue.add(mStringRequest);
    }
}
