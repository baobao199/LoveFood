package com.example.lovefood;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class Account extends Fragment {
    private View AccountView;
    private DatabaseReference UserRef;
    private FirebaseAuth mAuth;
    private CircleImageView imageUser;
    private TextView Gmail,Phone,Address;
    private static final int GalleryPick = 1;
    public Account() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        AccountView = inflater.inflate(R.layout.fragment_account, container, false);
        // Inflate the layout for this fragment
        imageUser = AccountView.findViewById(R.id.set_profile_image);
        Gmail = AccountView.findViewById(R.id.gmailUser);
        Phone =AccountView.findViewById(R.id.sdt);
        Address =AccountView.findViewById(R.id.address);
        mAuth = FirebaseAuth.getInstance();
        UserRef = FirebaseDatabase.getInstance().getReference().child("Users");
        imageUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GalleryPick);
            }
        });
        return AccountView;
    }

    @Override
    public void onStart() {
        super.onStart();
        RetrieveUserInto();
    }

    private void RetrieveUserInto() {
        UserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                        Gmail.setText("Gmail:"+ dataSnapshot.child("emailUser").getValue().toString());
                        Phone.setText("Phone:"+dataSnapshot.child("phoneNumber").getValue().toString());
                        Address.setText("Address:"+dataSnapshot.child("address").getValue().toString());
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GalleryPick && data != null && data.getData() != null)
        {

        }
    }
}
