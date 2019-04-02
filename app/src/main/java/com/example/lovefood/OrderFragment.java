package com.example.lovefood;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFragment extends Fragment {
    private View OrderFragmentView;
    private RecyclerView orderList;
    private DatabaseReference FoodRef,UserRef,OrderRef,OrderKeyRef;
    private FirebaseAuth mAuth;
    private String currentUser;
    public OrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        OrderFragmentView =inflater.inflate(R.layout.fragment_order, container, false);
        orderList = OrderFragmentView.findViewById(R.id.orderList);
        orderList.setLayoutManager(new LinearLayoutManager(getContext()));
        return OrderFragmentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FoodRef = FirebaseDatabase.getInstance().getReference().child("Foods");
        OrderRef =FirebaseDatabase.getInstance().getReference().child("Orders");
        mAuth = FirebaseAuth.getInstance();
        UserRef = FirebaseDatabase.getInstance().getReference().child("Users");

        if(mAuth.getCurrentUser() != null)
        {

            currentUser = mAuth.getCurrentUser().getUid();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerOptions options=
                new FirebaseRecyclerOptions.Builder<Foods>()
                .setQuery(FoodRef,Foods.class)
                .build();
                    final FirebaseRecyclerAdapter<Foods,ContactsViewHolder> adapter=
                new FirebaseRecyclerAdapter<Foods, ContactsViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull final ContactsViewHolder holder, final int position, @NonNull Foods model) {
                            final String nameFoods=getRef(position).getKey();
                            holder.etQuantum.setVisibility(View.VISIBLE);
                            FoodRef.child(nameFoods).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(final DataSnapshot dataSnapshot) {
                                     final String imageFood=dataSnapshot.child("link").getValue().toString();
                                     final String nameFood=dataSnapshot.child("name").getValue().toString();
                                     final String priceFood=dataSnapshot.child("price").getValue().toString();

                                     holder.nameFoods.setText(nameFood);
                                     holder.tvPrice.setText(priceFood);
                                     Picasso.get().load(imageFood).into(holder.imageFoods);
                                     holder.btOrder.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             if(Integer.parseInt(holder.etQuantum.getText().toString()) >= 1)
                                             {
                                                 int totalPrice = Integer.parseInt(priceFood) * Integer.parseInt(holder.etQuantum.getText().toString());
                                                 OrderKeyRef = OrderRef.child(currentUser).child("Food Ordered").child(nameFoods);
                                                 OrderKeyRef
                                                         .child("name").setValue(nameFood);
                                                 OrderKeyRef
                                                         .child("link").setValue(imageFood);
                                                 OrderKeyRef
                                                         .child("price").setValue(Integer.toString(totalPrice));
                                                 OrderKeyRef
                                                         .child("quantum").setValue(holder.etQuantum.getText().toString());
                                                 Toast.makeText(getContext(), "Order Successfully.....", Toast.LENGTH_SHORT).show();
                                             }
                                         }
                                     });
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });

                    }

                    @NonNull
                    @Override
                    public ContactsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                        View view =LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.foods_display_list,viewGroup,false);
                        ContactsViewHolder viewHolder = new ContactsViewHolder(view);
                        return viewHolder;
                    }
                };
        orderList.setAdapter(adapter);
        adapter.startListening();


    }
    public static class ContactsViewHolder extends RecyclerView.ViewHolder{
        TextView nameFoods,tvPrice;
        ImageView imageFoods;
        EditText etQuantum;
        Button btOrder;
        public ContactsViewHolder(@NonNull View itemView) {
            super(itemView);
            nameFoods = itemView.findViewById(R.id.nameOfFoods);
            imageFoods = itemView.findViewById(R.id.imageFoods);
            etQuantum = itemView.findViewById(R.id.etQuantum);
            btOrder = itemView.findViewById(R.id.btOrder);
            tvPrice = itemView.findViewById(R.id.tvPrice);
        }
    }
}
