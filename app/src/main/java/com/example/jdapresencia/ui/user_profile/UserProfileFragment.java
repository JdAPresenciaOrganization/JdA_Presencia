package com.example.jdapresencia.ui.user_profile;

import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.jdapresencia.R;
import com.example.jdapresencia.model.Directory;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

import static android.app.Activity.RESULT_OK;

public class UserProfileFragment extends Fragment {

    private UserProfileViewModel userProfileViewModel;

    ImageView imageView;
    ImageButton bUpload, bFoto;
    Button bEditData;
    EditText upName, upNumber, upEmail;

    static final int REQUEST_IMAGE_CAPTURE = 1;

    Bitmap imageBitmap = null;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        userProfileViewModel =
                ViewModelProviders.of(this).get(UserProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_user_profile, container, false);

        //Captura el id del usuario logueado enviado del MainActivity
        SharedPreferences pref = getActivity().getPreferences(getActivity().MODE_PRIVATE);
        final String idSession = pref.getString("sessionUserId_key", "");

        imageView = root.findViewById(R.id.imageView);
        bUpload = root.findViewById(R.id.uploadImageButton);
        bFoto = root.findViewById(R.id.fotoImageButton);

        bFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
                }
            }
        });

        bUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fileUploader(idSession);
            }
        });

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        storageRef.child(idSession + ".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Got the download URL for 'users/me/profile.png'
                // Pass it to Picasso to download, show in ImageView and caching
                Picasso.get().load(uri).into(imageView);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
                Log.i("INFO", "No user image");
            }
        });

        //Datos usuario, por añadir o editar
        bEditData = root.findViewById(R.id.button_userModify);
        upName = root.findViewById(R.id.upName);
        upNumber = root.findViewById(R.id.upNumber);
        upEmail = root.findViewById(R.id.upEmail);

        //Se setean los campos en caso de que el usuario tenga ya datos registrados
        final DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = database.child("Directory");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Directory directory = dataSnapshot.child("id"+idSession).getValue(Directory.class);

                if (directory != null) {
                    upName.setText(directory.getName());
                    upNumber.setText(directory.getNumber());
                    upEmail.setText(directory.getEmail());
                } else {
                    Log.e("@@@", "dataSnapshot no data");
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("@@@", "onCancelled", databaseError.toException());
            }
        });

        bEditData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userProfileViewModel.userProfileData(upName.getText().toString(), upNumber.getText().toString(),
                        upEmail.getText().toString(), idSession);
            }
        });

        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
        }

        if (imageBitmap != null) {
            imageView.setImageBitmap(imageBitmap);
        }
    }

    private void fileUploader(String idSession) {
        // Get the data from an ImageView as bytes
        imageView.setDrawingCacheEnabled(true);
        imageView.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        FirebaseStorage storage = FirebaseStorage.getInstance();
        // Create a storage reference from our app
        StorageReference storageRef = storage.getReference();
        // Create a reference to "mountains.jpg"
        StorageReference mountainsRef = storageRef.child(idSession + ".jpg");

        UploadTask uploadTask = mountainsRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                Toast.makeText(getContext(), "Image not uploaded", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                Toast.makeText(getContext(), "Image uploaded", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
