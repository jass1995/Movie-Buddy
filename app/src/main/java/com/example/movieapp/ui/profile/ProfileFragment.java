package com.example.movieapp.ui.profile;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.movieapp.R;
import com.example.movieapp.database.RealmManager;
import com.example.movieapp.utils.PrefsManager;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.RealmQuery;

public class ProfileFragment extends Fragment {

    private ProfileViewModel homeViewModel;
    private static final String IMAGE_DIRECTORY = "/TheatreList";
    private int GALLERY = 1, CAMERA = 2;
    private String imagePath = null;

    private CircleImageView profileImage;
    private PrefsManager prefsManager;
    private EditText edFirstName, editTextLast;
    private Button btSave;
    private RealmQuery<ProfileDataSave> profileDataSave = null;

    private TextView tvFullName;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Fresco.initialize(getActivity());
        homeViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        RealmManager.open();
        profileImage = root.findViewById(R.id.profileImage);
        edFirstName = root.findViewById(R.id.edFirstName);
        editTextLast = root.findViewById(R.id.editTextLast);
        tvFullName = root.findViewById(R.id.tvFullName);
        prefsManager = new PrefsManager(getActivity());
        requestMultiplePermissions();
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPictureDialog();
            }
        });
        firstLastName();
        btSave = root.findViewById(R.id.btSave);
        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edFirstName.getText().toString().length() == 0) {

                } else {
                    if (edFirstName.getText().toString().equals("First Name")) {

                    } else {
                        prefsManager.savePreferenceString(PrefsManager.KEY_FIRSTNAME, edFirstName.getText().toString());
                        prefsManager.savePreferenceString(PrefsManager.KEY_LASTNAME, editTextLast.getText().toString());
                        ProfileDataSave profile_Data_Save = new ProfileDataSave();
                        profile_Data_Save.setId("1");
                        profile_Data_Save.setmFirstName(edFirstName.getText().toString());
                        profile_Data_Save.setmLastName(editTextLast.getText().toString());
                        if (imagePath != null) {
                            profile_Data_Save.setmImageUrl(imagePath);
                        }
                        saveUpdateProf(profile_Data_Save);
                        Toast.makeText(getActivity(), "Update Successfully", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        profileDataSave = RealmManager.recordsDao().loadProfileData();
        Log.e("COUNT:", "" + profileDataSave.count());

        if (profileDataSave.count() == 1) {
            btSave.setText("Update");
            ProfileDataSave profile = profileDataSave.findFirst();
            Log.e("Id(:", "" + profile.getId());
            Log.e("FirstName:", "" + profile.getmFirstName());
            Log.e("LastName:", "" + profile.getmLastName());
            Log.e("ImageUrl:", "" + profile.getmImageUrl());
            tvFullName.setText(profile.getmFirstName() + " " + profile.getmLastName());
            if (profile.getmImageUrl() != null) {
                Bitmap bitmap = BitmapFactory.decodeFile(profile.getmImageUrl());
                profileImage.setImageBitmap(bitmap);
            }


        } else {
            btSave.setText("Save");
        }


        return root;
    }

    private void saveUpdateProf(ProfileDataSave profileDataSave) {
        RealmManager.recordsDao().updateSaveProfile(profileDataSave);
    }

    @Override
    public void onDestroy() {
        RealmManager.close();
        super.onDestroy();
    }

    public void firstLastName() {
        edFirstName.setText(prefsManager.getPreferenceString(PrefsManager.KEY_FIRSTNAME, "First Name"));
        editTextLast.setText(prefsManager.getPreferenceString(PrefsManager.KEY_LASTNAME, "Last Name"));
    }


    private void requestMultiplePermissions() {
        Dexter.withActivity(getActivity())
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            Toast.makeText(getActivity(), "All permissions are granted by user!", Toast.LENGTH_SHORT).show();
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            //openSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getActivity(), "Some Error! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }

    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(getActivity(),
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }

    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(Objects.requireNonNull(getActivity()).getContentResolver(), contentURI);
                    imagePath = saveImage(bitmap);

                    // Setting Picture Corner
                    profileImage.setImageBitmap(bitmap);


                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");
            profileImage.setImageBitmap(thumbnail);
            assert thumbnail != null;
            imagePath = saveImage(thumbnail);
        }
    }

    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(getActivity());
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }
}