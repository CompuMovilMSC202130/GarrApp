// Generated by view binder compiler. Do not edit!
package com.example.garrapp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.garrapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityInstitucionesAliadasBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final LinearLayout barraSuperior;

  @NonNull
  public final BottomNavigationView bottomNav;

  @NonNull
  public final ImageButton imageButton3;

  @NonNull
  public final ImageButton imageSalir;

  @NonNull
  public final TextView textView;

  private ActivityInstitucionesAliadasBinding(@NonNull ConstraintLayout rootView,
      @NonNull LinearLayout barraSuperior, @NonNull BottomNavigationView bottomNav,
      @NonNull ImageButton imageButton3, @NonNull ImageButton imageSalir,
      @NonNull TextView textView) {
    this.rootView = rootView;
    this.barraSuperior = barraSuperior;
    this.bottomNav = bottomNav;
    this.imageButton3 = imageButton3;
    this.imageSalir = imageSalir;
    this.textView = textView;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityInstitucionesAliadasBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityInstitucionesAliadasBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_instituciones_aliadas, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityInstitucionesAliadasBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.barraSuperior;
      LinearLayout barraSuperior = ViewBindings.findChildViewById(rootView, id);
      if (barraSuperior == null) {
        break missingId;
      }

      id = R.id.bottom_nav;
      BottomNavigationView bottomNav = ViewBindings.findChildViewById(rootView, id);
      if (bottomNav == null) {
        break missingId;
      }

      id = R.id.imageButton3;
      ImageButton imageButton3 = ViewBindings.findChildViewById(rootView, id);
      if (imageButton3 == null) {
        break missingId;
      }

      id = R.id.imageSalir;
      ImageButton imageSalir = ViewBindings.findChildViewById(rootView, id);
      if (imageSalir == null) {
        break missingId;
      }

      id = R.id.textView;
      TextView textView = ViewBindings.findChildViewById(rootView, id);
      if (textView == null) {
        break missingId;
      }

      return new ActivityInstitucionesAliadasBinding((ConstraintLayout) rootView, barraSuperior,
          bottomNav, imageButton3, imageSalir, textView);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
