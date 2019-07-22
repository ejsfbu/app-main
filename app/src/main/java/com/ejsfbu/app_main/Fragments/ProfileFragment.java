package com.ejsfbu.app_main.Fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.ejsfbu.app_main.Activities.LoginActivity;
import com.ejsfbu.app_main.Activities.MainActivity;
import com.ejsfbu.app_main.Adapters.ParentDisplayAdapter;
import com.ejsfbu.app_main.EditFragments.EditEmailDialogFragment;
import com.ejsfbu.app_main.EditFragments.EditNameDialogFragment;
import com.ejsfbu.app_main.EditFragments.EditPasswordDialogFragment;
import com.ejsfbu.app_main.EditFragments.EditProfileImageDialogFragment;
import com.ejsfbu.app_main.EditFragments.EditUsernameDialogFragment;
import com.ejsfbu.app_main.ParseApp;
import com.ejsfbu.app_main.R;
import com.ejsfbu.app_main.models.User;
import com.parse.FindCallback;
import com.parse.ParseFile;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ProfileFragment extends Fragment implements EditEmailDialogFragment.EditEmailDialogListener, EditNameDialogFragment.EditNameDialogListener,
        EditProfileImageDialogFragment.EditProfileImageDialogListener, EditUsernameDialogFragment.EditUserNameDialogListener {

    public static final String TAG = "ProfileFragment";
    public List<User> parents;

    @BindView(R.id.bLogOut) Button bLogOut;
    @BindView(R.id.ibEditName) ImageButton ibName;
    @BindView(R.id.ibEditUserName) ImageButton ibUserName;
    @BindView(R.id.ivProfileImage) ImageView ivProfileImage;

    @BindView(R.id.tv_profile_username) TextView username;
    @BindView(R.id.tv_profile_password) TextView password;
    @BindView(R.id.tv_parent_name) TextView name;
    @BindView(R.id.tv_profile_email)TextView email;
    @BindView(R.id.lvParents) ListView lvParents;


    // Butterknife for fragment
    private Unbinder unbinder;
    private ParseUser user;
    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment98
        context = container.getContext();
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        unbinder = ButterKnife.bind(this, view);
        user = ParseUser.getCurrentUser();
        parents = new ArrayList<>();
        loadProfileData();
    }

    // When change fragment unbind view
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.bLogOut)
    public void onClickLogOut() {
        ParseUser.logOut();

        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    @OnClick(R.id.ibEditName)
    public void onClickEditName() {
        showEditNameDialog();
    }

    @OnClick(R.id.ibEditUserName)
    public void onClickEditUserName() {
        showEditUsernameDialog();
    }

    @OnClick(R.id.ibEditEmail)
    public void onClickEditEmail() {
        showEditEmailDialog();
    }

    @OnClick(R.id.ibEditPassword)
    public void onClickEditPassword() {
        showEditPasswordDialog();
    }

    @OnClick(R.id.ivProfileImage)
    public void onClickEditImage() {
        showEditImageDialog();
    }

    @OnClick(R.id.bBanks)
    public void onClickBanks() {
        Fragment bankFragment = new BankAccountsFragment();
        MainActivity.fragmentManager.beginTransaction().replace(R.id.flContainer, bankFragment).commit();
    }

    private void showEditNameDialog() {
        ///FragmentManager fm = getSupportFragmentManager();
        EditNameDialogFragment editNameDialogFragment = EditNameDialogFragment.newInstance("Edit Name");
        editNameDialogFragment.show(MainActivity.fragmentManager, "fragment_edit_name");
    }

    private void showEditUsernameDialog() {
        ///FragmentManager fm = getSupportFragmentManager();
        EditUsernameDialogFragment editUsernameDialogFragment = EditUsernameDialogFragment.newInstance("Edit Username");
        editUsernameDialogFragment.show(MainActivity.fragmentManager, "fragment_edit_username");
    }

    private void showEditEmailDialog() {
        ///FragmentManager fm = getSupportFragmentManager();
        EditEmailDialogFragment editEmailDialogFragment = EditEmailDialogFragment.newInstance("Edit Email");
        editEmailDialogFragment.show(MainActivity.fragmentManager, "fragment_edit_email");
    }

    private void showEditPasswordDialog() {
        ///FragmentManager fm = getSupportFragmentManager();
        EditPasswordDialogFragment editPasswordDialogFragment = EditPasswordDialogFragment.newInstance("Edit Password");
        editPasswordDialogFragment.show(MainActivity.fragmentManager, "fragment_edit_password");
    }

    private void showEditImageDialog() {
        ///FragmentManager fm = getSupportFragmentManager();
        EditProfileImageDialogFragment editProfileImageDialogFragment = EditProfileImageDialogFragment.newInstance("Edit Password");
        editProfileImageDialogFragment.show(MainActivity.fragmentManager, "fragment_edit_profileimage");
    }

    // Load user data
    private void loadProfileData() {
        ParseFile image = user.getParseFile("profileImage");
        if (image != null) {
            String imageUrl = image.getUrl();
            imageUrl = imageUrl.substring(4);
            imageUrl = "https" + imageUrl;
            RequestOptions options = new RequestOptions();
            options.placeholder(R.drawable.ic_iconfinder_icons_user_1564534)
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .error(R.drawable.ic_iconfinder_icons_user_1564534)
                    .transform(new CenterCrop())
                    .transform(new CircleCrop());
            Glide.with(context)
                    .load(imageUrl)
                    .apply(options) // Extra: round image corners
                    .into(ivProfileImage);
        }
        username.setText(user.getUsername());
        email.setText(user.getEmail());
        name.setText(user.getString("name"));
    }

    //TODO implement when we have parent list
    private void setParentList() {
        //TODO test once we have a list of parents for a child user
        User.Query parentals = new User.Query();
        parentals.whereEqualTo("username", user.getUsername());
        parentals.findInBackground(new FindCallback<User>() {
            @Override
            public void done(List<User> users, com.parse.ParseException e) {
                if (e == null) {
                    parents = users.get(0).getList("parents");
                } else {
                    Log.e("ParentQueryIssue", "Trouble finding parents!");
                    e.printStackTrace();
                }
            }
        });
        ParentDisplayAdapter adapter = new ParentDisplayAdapter(getContext(), parents);
        lvParents.setAdapter(adapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    // This is called when the dialog is completed and the results have been passed
    @Override
    public void onFinishEditDialog() {
        loadProfileData();
    }

}
