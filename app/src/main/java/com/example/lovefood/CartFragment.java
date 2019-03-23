package com.example.lovefood;


import android.os.Bundle;
import android.support.annotation.NonNull;
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

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends Fragment {
    private View CartFragmentView;
    private RecyclerView cartList;
    private DatabaseReference FoodRef,UserRef,OrderRef,CartKeyRef;
    private FirebaseAuth mAuth;
    private String currentUser;

    public CartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        CartFragmentView = inflater.inflate(R.layout.fragment_cart, container, false);
        FoodRef = FirebaseDatabase.getInstance().getReference().child("Foods");
        cartList = CartFragmentView.findViewById(R.id.cartList);
        cartList.setLayoutManager(new LinearLayoutManager(getContext()));
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser().getUid();
        OrderRef =FirebaseDatabase.getInstance().getReference().child("Orders").child(currentUser).child("Food Ordered");
        UserRef = FirebaseDatabase.getInstance().getReference().child("Users");
        return CartFragmentView;
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerOptions options =
                new FirebaseRecyclerOptions.Builder<FoodOrdered>()
                .setQuery(OrderRef,FoodOrdered.class)
                .build();
        FirebaseRecyclerAdapter<FoodOrdered,CartViewHolder> adapter = new FirebaseRecyclerAdapter<FoodOrdered, CartViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final CartViewHolder holder, int position, @NonNull FoodOrdered model) {
                final String nameFoods=getRef(position).getKey();
                holder.tvQuantum.setVisibility(View.VISIBLE);
                holder.btOrder.setVisibility(View.GONE);
                OrderRef.child(nameFoods).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        final String imageFood=dataSnapshot.child("link").getValue().toString();
                        final String nameFood=dataSnapshot.child("name").getValue().toString();
                        final String quantumFood=dataSnapshot.child("quantum").getValue().toString();
                        final String priceFood=dataSnapshot.child("price").getValue().toString();
                        holder.nameFoods.setText(nameFood);
                        holder.tvQuantum.setText(quantumFood);
                        holder.tvPrice.setText(priceFood);
                        Picasso.get().load(imageFood).into(holder.imageFoods);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view =LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.foods_display_list,viewGroup,false);
                CartFragment.CartViewHolder viewHolder = new CartFragment.CartViewHolder(view);
                return viewHolder;
            }
        };

        cartList.setAdapter(adapter);
        adapter.startListening();
    }
    public static class CartViewHolder extends RecyclerView.ViewHolder{
        TextView nameFoods,tvPrice,tvQuantum;
        ImageView imageFoods;
        EditText etQuantum;
        Button btOrder;
        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            nameFoods = itemView.findViewById(R.id.nameOfFoods);
            imageFoods = itemView.findViewById(R.id.imageFoods);
            etQuantum = itemView.findViewById(R.id.etQuantum);
            btOrder = itemView.findViewById(R.id.btOrder);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvQuantum = itemView.findViewById(R.id.tvQuantum);
        }
    }
}
