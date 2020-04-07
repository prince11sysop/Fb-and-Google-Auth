package com.appdeveloper.prince.princenitjsr.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.appdeveloper.prince.princenitjsr.LoginActivity;
import com.appdeveloper.prince.princenitjsr.R;
import com.appdeveloper.prince.princenitjsr.SharedPrefManager;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;

public class HomeFragment extends Fragment {

    Button logout;
    TextView name,email;
    ImageView img;
    FirebaseAuth mAuth;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        logout=root.findViewById(R.id.log_out);
        name=root.findViewById(R.id.name);
        email=root.findViewById(R.id.email);
        img=root.findViewById(R.id.img);

        mAuth = FirebaseAuth.getInstance();

        name.setText(mAuth.getCurrentUser().getDisplayName());
        email.setText(mAuth.getCurrentUser().getEmail());
        Glide.with(getActivity())
                .load(mAuth.getCurrentUser().getPhotoUrl())
                .apply(new RequestOptions()
                        .placeholder(R.mipmap.ic_launcher)
                        .fitCenter())
                .into(img);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                SharedPrefManager sm = new SharedPrefManager(getActivity());
                sm.setIsLoggedIn(false);
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
            }
        });





        return root;
    }
}