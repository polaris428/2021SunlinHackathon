package com.example.a2021sunlinhackathon.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.a2021sunlinhackathon.Data.PostData;
import com.example.a2021sunlinhackathon.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {
    //public
    private ArrayList<PostData> arrayList;
    private Context context;
    public PostAdapter(ArrayList<PostData> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }



    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item, parent, false);
        PostViewHolder holder = new PostViewHolder(view);
        return holder;
    }
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {

        //userProfile => 지금 로그인 된 유저프로필, bestComment => 댓글 있으면 그중에서 좋은 댓글 하나 보여주기
        /*
        Glide.with(holder.itemView).load(arrayList.get(position).getProfileImageUrl()).into(holder.iv_profile);
        Glide.with(holder.itemView).load(arrayList.get(position).getUserUploadImageUrl()).into(holder.iv_userUpload); */
        String useruid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference();
        storageReference.child("userImages/"+arrayList.get(position).getUid()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(context).load(uri).into(holder.iv_profile);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {

            }
        });
        storageReference.child("postImages/"+arrayList.get(position).getPostid()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(context).load(uri).into(holder.iv_userUpload);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {

            }
        });
        storageReference.child("userImages/"+useruid).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(context).load(uri).into(holder.iv_userProfile);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {

            }
        });
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        //DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Posts");
        if(arrayList.get(position).isHeartPushed()) {
            holder.ib_heartBtn.setBackgroundColor(Color.RED);
        }
        if(!arrayList.get(position).isHeartPushed()) {
            holder.ib_heartBtn.setBackgroundColor(Color.GRAY);
        }
        //int cntT = arrayList.get(position).getHeart().size()-1;
        holder.tv_heartCnt.setText("좋아요 "+arrayList.get(position).getCount()+"개");




        holder.tv_username.setText(arrayList.get(position).getName());
        holder.tv_userText.setText(arrayList.get(position).getPost());
        holder.tv_location.setText(arrayList.get(position).getAddars());

        holder.ib_heartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(arrayList.get(position).isHeartPushed) {
                    Toast.makeText(context,"이미 좋아요를 눌렀습니다.",Toast.LENGTH_LONG).show();
                } else {
                    arrayList.get(position).isHeartPushed = true;
                    holder.ib_heartBtn.setBackgroundColor(Color.RED);
                    arrayList.get(position).setCount(arrayList.get(position).getCount() + 1);
                    mDatabase.child("Posts").child(arrayList.get(position).getPostid()).child("heart").child(arrayList.get(position).getCount()+"").setValue(useruid);
                    mDatabase.child("Posts").child(arrayList.get(position).getPostid()).child("count").setValue(arrayList.get(position).getCount());
                    holder.tv_heartCnt.setText("좋아요 "+arrayList.get(position).getCount()+"개");
                    //mDatabase.child("Posts").child(arrayList.get(position).getPostid()).child("heart")
                }
            }
        });

        holder.ib_commentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.btn_sendComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }


    public class PostViewHolder extends RecyclerView.ViewHolder {


        public ImageView iv_profile;
        public TextView tv_username;
        public TextView tv_location;

        public ImageView iv_userUpload;
        public TextView tv_userText;



        public ImageButton ib_heartBtn;
        public TextView tv_heartCnt;
        public ImageButton ib_commentBtn;

        public TextView tv_bestCommentUsername;
        public TextView tv_bestCommentText;

        public ImageView iv_userProfile;
        public EditText et_comment;
        public Button btn_sendComment;



        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_profile = itemView.findViewById(R.id.postProfileImage);
            tv_username = itemView.findViewById(R.id.userNameTextView);
            tv_location = itemView.findViewById(R.id.location);
            iv_userUpload = itemView.findViewById(R.id.postImageView);
            tv_userText = itemView.findViewById(R.id.postText);
            iv_userProfile = itemView.findViewById(R.id.userProfileImage);
            ib_heartBtn = itemView.findViewById(R.id.heartButton);
            ib_commentBtn = itemView.findViewById(R.id.commentButton);
            tv_bestCommentUsername = itemView.findViewById(R.id.bestCommentUsername);
            tv_bestCommentText = itemView.findViewById(R.id.bestComment);
            btn_sendComment = itemView.findViewById(R.id.sendCommentButton);
            et_comment = itemView.findViewById(R.id.userCommentInput);
            tv_heartCnt = itemView.findViewById(R.id.heartCount);

            iv_profile.setBackground(new ShapeDrawable(new OvalShape()));
            iv_profile.setClipToOutline(true);

            iv_userProfile.setBackground(new ShapeDrawable(new OvalShape()));
            iv_userProfile.setClipToOutline(true);

        }
    }
}
