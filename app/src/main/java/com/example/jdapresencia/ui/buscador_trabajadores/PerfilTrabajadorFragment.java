package com.example.jdapresencia.ui.buscador_trabajadores;

import androidx.lifecycle.ViewModelProviders;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jdapresencia.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class PerfilTrabajadorFragment extends Fragment {

    private BuscadorTrabajadoresViewModel perfilTrabajadorViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        perfilTrabajadorViewModel =
                ViewModelProviders.of(this).get(BuscadorTrabajadoresViewModel.class);
        View root = inflater.inflate(R.layout.fragment_perfil_trabajador, container, false);

        // Inflate the layout for this fragment
        Bundle bundle = this.getArguments();
        int uid = bundle.getInt("uid_key");

        FirebaseDirectory firebaseDirectory = new FirebaseDirectory(String.valueOf(uid));
        firebaseDirectory.execute();

        return root;
    }

    public class FirebaseDirectory extends AsyncTask<String, String, String> {

        String forecastJsonStr = null;
        String idUsuario;

        public FirebaseDirectory(String idUsuario) {
            this.idUsuario = idUsuario;
        }

        @Override
        protected String doInBackground(String... strings) {
            BufferedReader reader = null;
            HttpURLConnection urlConnection = null;
            URL url = null;

            try {
                url = new URL("https://login-baaa3.firebaseio.com/Directory.json");

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            InputStream inputStream = null;
            try {
                inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                try {
                    while ((line = reader.readLine()) != null) {
                        // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                        // But it does make debugging a *lot* easier if you print out the completed
                        // buffer for debugging.
                        buffer.append(line + "\n");
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }

                forecastJsonStr = buffer.toString();

                Log.i("@@@@@@@@@@@@@@@@@@@", forecastJsonStr);
                return forecastJsonStr;

            } catch (IOException e) {
                Log.e("Exception", "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attempting to parse it.
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("Exception", "Error closing stream", e);
                    }
                }
            }

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                // get JSONObject from JSON file
                JSONObject obj = new JSONObject(forecastJsonStr);

                final ImageView imageView = getActivity().findViewById(R.id.imageView);
                TextView upName = getActivity().findViewById(R.id.upName);
                TextView upNumber = getActivity().findViewById(R.id.upNumber);
                TextView upEmail = getActivity().findViewById(R.id.upEmail);

                if (obj.has("id"+idUsuario)) {
                    JSONObject jId = obj.getJSONObject("id"+idUsuario);
                    String email = jId.getString("email");
                    String name = jId.getString("name");
                    String number = jId.getString("number");

                    FirebaseStorage storage = FirebaseStorage.getInstance();
                    StorageReference storageRef = storage.getReference();

                    storageRef.child(idUsuario + ".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
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

                    upName.setText("Nombre: " + name);
                    upNumber.setText("Número: " + number);
                    upEmail.setText("Email: " + email);
                } else {
                    upName.setText("Nombre: ");
                    upNumber.setText("Número: ");
                    upEmail.setText("Email: ");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
