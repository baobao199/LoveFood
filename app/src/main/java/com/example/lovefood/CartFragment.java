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
        cartList = CartFragmentView.findViewById(R.id.cartList);
        cartList.setLayoutManager(new LinearLayoutManager(getContext()));

        return CartFragmentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FoodRef = FirebaseDatabase.getInstance().getReference().child("Foods");
        UserRef = FirebaseDatabase.getInstance().getReference().child("Users");
        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() != null)
        {

            currentUser = mAuth.getCurrentUser().getUid();
            OrderRef =FirebaseDatabase.getInstance().getReference().child("Orders").child(currentUser).child("Food Ordered");
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        if(OrderRef!=null) {
            FirebaseRecyclerOptions options =
                    new FirebaseRecyclerOptions.Builder<FoodOrdered>()
                            .setQuery(OrderRef, FoodOrdered.class)
                            .build();
            FirebaseRecyclerAdapter<FoodOrdered, CartViewHolder> adapter = new FirebaseRecyclerAdapter<FoodOrdered, CartViewHolder>(options) {
                @Override
                protected void onBindViewHolder(@NonNull final CartViewHolder holder, int position, @NonNull FoodOrdered model) {
                    final String nameFoods = getRef(position).getKey();
                    holder.tvQuantum.setVisibility(View.VISIBLE);
                    holder.btOrder.setVisibility(View.GONE);
                    OrderRef.child(nameFoods).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                final String imageFood = dataSnapshot.child("link").getValue().toString();
                                final String nameFood = dataSnapshot.child("name").getValue().toString();
                                final String quantumFood = dataSnapshot.child("quantum").getValue().toString();
                                final String priceFood = dataSnapshot.child("price").getValue().toString();
                                holder.nameFoods.setText(nameFood);
                                holder.tvQuantum.setText(quantumFood);
                                holder.tvPrice.setText(priceFood);
                                holder.tvCount.setText(Integer.parseInt(holder.tvCount.getText().toString())+Integer.parseInt(quantumFood));
                                Picasso.get().load(imageFood).placeholder(R.drawable.account3).into(holder.imageFoods);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }

                @NonNull
                @Override
                public CartViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                    View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.foods_display_list, viewGroup, false);
                    CartFragment.CartViewHolder viewHolder = new CartFragment.CartViewHolder(view);
                    return viewHolder;
                }
            };

            cartList.setAdapter(adapter);
            adapter.startListening();
        }
    }
    public static class CartViewHolder extends RecyclerView.ViewHolder{
        TextView nameFoods,tvPrice,tvQuantum;
        ImageView imageFoods;
        EditText etQuantum;
        Button btOrder;
        TextView tvCount;
        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            nameFoods = itemView.findViewById(R.id.nameOfFoods);
            imageFoods = itemView.findViewById(R.id.imageFoods);
            etQuantum = itemView.findViewById(R.id.etQuantum);
            btOrder = itemView.findViewById(R.id.btOrder);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvQuantum = itemView.findViewById(R.id.tvQuantum);
           tvCount = itemView.findViewById(R.id.tvCount);
        }
    }
}
