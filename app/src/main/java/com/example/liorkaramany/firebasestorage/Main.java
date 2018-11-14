package com.example.liorkaramany.firebasestorage;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Main extends AppCompatActivity {

    static final int PICK_IMAGE_REQUEST = 1;

    EditText name;
    ImageView image;

    Uri imageUri;

    StorageReference ref;
    DatabaseReference r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = (EditText) findViewById(R.id.name);
        image = (ImageView) findViewById(R.id.image);

        ref = FirebaseStorage.getInstance().getReference("uploads");
        r = FirebaseDatabase.getInstance().getReference("uploads");
    }

    public void choose(View view) {
        openFileChooser();
    }

    public void push(View view) {
        uploadFile();

    }

    public void next(View view) {
        Intent t = new Intent(this, Images.class);
        startActivity(t);
    }

    private void openFileChooser() {
        Intent t = new Intent();
        t.setType("image/*");
        t.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(t, PICK_IMAGE_REQUEST);
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile() {
        if (imageUri != null && !name.getText().toString().equals("")) {
            final String id = r.push().getKey();
            ref.child(id).putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(Main.this, "Push successful", Toast.LENGTH_SHORT).show();

                    taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Upload upload = new Upload(id, name.getText().toString(), uri.toString());

                            r.child(id).setValue(upload);
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Main.this, "Push failed", Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress  = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                }
            });
        } else {
            Toast.makeText(this, "You didn't enter all the information", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            image.setImageURI(imageUri);
            Toast.makeText(this, "It works!", Toast.LENGTH_SHORT).show();
        }

    }
}
