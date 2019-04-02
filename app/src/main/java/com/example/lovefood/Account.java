package com.example.lovefood;


import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class Account extends Fragment {
    private View AccountView;
    private DatabaseReference UserRef;
    private String currentUserID;
    private FirebaseAuth mAuth;
    private CircleImageView imageUser;
    private ProgressDialog loadingBar;
    private TextView Gmail,Phone,Address;
    private static final int MyPick = 2;
    private StorageReference UserProfileImageRef;
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
        return AccountView;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        UserRef = FirebaseDatabase.getInstance().getReference().child("Users");
        UserProfileImageRef= FirebaseStorage.getInstance().getReference().child("Profile Images");
        if(mAuth.getCurrentUser() != null)
        {
            currentUserID = mAuth.getCurrentUser().getUid();

            RetrieveUserInto();
        }
        loadingBar =new ProgressDialog(getContext());

        imageUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, MyPick);
            }
        });
    }

    private void RetrieveUserInto() {
        UserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                        Gmail.setText("Gmail:"+ dataSnapshot.child(currentUserID).child("emailUser").getValue().toString());
                        Phone.setText("Phone:"+dataSnapshot.child(currentUserID).child("phoneNumber").getValue().toString());
                        Address.setText("Address:"+dataSnapshot.child(currentUserID).child("address").getValue().toString());
                        if(dataSnapshot.child(currentUserID).child("image").exists()){
                            String retriveProfileImage = dataSnapshot.child(currentUserID).child("image").getValue().toString();
                            Picasso.get().load(retriveProfileImage).placeholder(R.drawable.account).into(imageUser);
                        }


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
        if(requestCode == MyPick && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            Uri ImageUri = data.getData();
            loadingBar.setTitle("Set Profile Image");
            loadingBar.setMessage("Please wait ,your  profile image is updating....");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
            if (ImageUri != null) {
                StorageReference filePath = UserProfileImageRef.child(currentUserID + ".jpg");

                filePath.putFile(ImageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getContext(), "Profile Image uploaded Successfully...", Toast.LENGTH_SHORT).show();
                            final String downloadUrl = task.getResult().getDownloadUrl().toString();
                            UserRef.child(currentUserID).child("image").setValue(downloadUrl).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getContext(), "Image save in Database Successfully.....", Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();
                                    } else {
                                        String message = task.getException().toString();
                                        Toast.makeText(getContext(), "Error" + message, Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();
                                    }
                                }
                            });
                        } else {
                            String message = task.getException().toString();
                            Toast.makeText(getActivity(), "Error :" + message, Toast.LENGTH_SHORT).show();
                        }
                    }

                });
            }
        }
    }
}
