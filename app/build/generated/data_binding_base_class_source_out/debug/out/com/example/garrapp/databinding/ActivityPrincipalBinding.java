// Generated by view binder compiler. Do not edit!
package com.example.garrapp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
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

public final class ActivityPrincipalBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final LinearLayout barraSuperior;

  @NonNull
  public final BottomNavigationView bottomNav;

  @NonNull
  public final Button button;

  @NonNull
  public final Button button2;

  @NonNull
  public final Button button3;

  @NonNull
  public final Button button6;

  @NonNull
  public final Button buttonn3;

  @NonNull
  public final Button buttonn6;

  @NonNull
  public final View divider1;

  @NonNull
  public final View divider2;

  @NonNull
  public final View divider3;

  @NonNull
  public final View divider4;

  @NonNull
  public final ImageButton imageButton3;

  @NonNull
  public final ImageButton imageButton5;

  @NonNull
  public final ImageButton imageButton6;

  @NonNull
  public final ImageButton imageButton7;

  @NonNull
  public final ImageView imageView;

  @NonNull
  public final ImageView imageView1;

  @NonNull
  public final LinearLayout linearLayout2;

  @NonNull
  public final TextView textView;

  @NonNull
  public final TextView textView11;

  @NonNull
  public final TextView textView5;

  @NonNull
  public final TextView textView6;

  @NonNull
  public final TextView textView7;

  @NonNull
  public final TextView textView8;

  @NonNull
  public final TextView textView9;

  private ActivityPrincipalBinding(@NonNull ConstraintLayout rootView,
      @NonNull LinearLayout barraSuperior, @NonNull BottomNavigationView bottomNav,
      @NonNull Button button, @NonNull Button button2, @NonNull Button button3,
      @NonNull Button button6, @NonNull Button buttonn3, @NonNull Button buttonn6,
      @NonNull View divider1, @NonNull View divider2, @NonNull View divider3,
      @NonNull View divider4, @NonNull ImageButton imageButton3, @NonNull ImageButton imageButton5,
      @NonNull ImageButton imageButton6, @NonNull ImageButton imageButton7,
      @NonNull ImageView imageView, @NonNull ImageView imageView1,
      @NonNull LinearLayout linearLayout2, @NonNull TextView textView, @NonNull TextView textView11,
      @NonNull TextView textView5, @NonNull TextView textView6, @NonNull TextView textView7,
      @NonNull TextView textView8, @NonNull TextView textView9) {
    this.rootView = rootView;
    this.barraSuperior = barraSuperior;
    this.bottomNav = bottomNav;
    this.button = button;
    this.button2 = button2;
    this.button3 = button3;
    this.button6 = button6;
    this.buttonn3 = buttonn3;
    this.buttonn6 = buttonn6;
    this.divider1 = divider1;
    this.divider2 = divider2;
    this.divider3 = divider3;
    this.divider4 = divider4;
    this.imageButton3 = imageButton3;
    this.imageButton5 = imageButton5;
    this.imageButton6 = imageButton6;
    this.imageButton7 = imageButton7;
    this.imageView = imageView;
    this.imageView1 = imageView1;
    this.linearLayout2 = linearLayout2;
    this.textView = textView;
    this.textView11 = textView11;
    this.textView5 = textView5;
    this.textView6 = textView6;
    this.textView7 = textView7;
    this.textView8 = textView8;
    this.textView9 = textView9;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityPrincipalBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityPrincipalBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_principal, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityPrincipalBinding bind(@NonNull View rootView) {
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

      id = R.id.button;
      Button button = ViewBindings.findChildViewById(rootView, id);
      if (button == null) {
        break missingId;
      }

      id = R.id.button2;
      Button button2 = ViewBindings.findChildViewById(rootView, id);
      if (button2 == null) {
        break missingId;
      }

      id = R.id.button3;
      Button button3 = ViewBindings.findChildViewById(rootView, id);
      if (button3 == null) {
        break missingId;
      }

      id = R.id.button6;
      Button button6 = ViewBindings.findChildViewById(rootView, id);
      if (button6 == null) {
        break missingId;
      }

      id = R.id.buttonn3;
      Button buttonn3 = ViewBindings.findChildViewById(rootView, id);
      if (buttonn3 == null) {
        break missingId;
      }

      id = R.id.buttonn6;
      Button buttonn6 = ViewBindings.findChildViewById(rootView, id);
      if (buttonn6 == null) {
        break missingId;
      }

      id = R.id.divider1;
      View divider1 = ViewBindings.findChildViewById(rootView, id);
      if (divider1 == null) {
        break missingId;
      }

      id = R.id.divider2;
      View divider2 = ViewBindings.findChildViewById(rootView, id);
      if (divider2 == null) {
        break missingId;
      }

      id = R.id.divider3;
      View divider3 = ViewBindings.findChildViewById(rootView, id);
      if (divider3 == null) {
        break missingId;
      }

      id = R.id.divider4;
      View divider4 = ViewBindings.findChildViewById(rootView, id);
      if (divider4 == null) {
        break missingId;
      }

      id = R.id.imageButton3;
      ImageButton imageButton3 = ViewBindings.findChildViewById(rootView, id);
      if (imageButton3 == null) {
        break missingId;
      }

      id = R.id.imageButton5;
      ImageButton imageButton5 = ViewBindings.findChildViewById(rootView, id);
      if (imageButton5 == null) {
        break missingId;
      }

      id = R.id.imageButton6;
      ImageButton imageButton6 = ViewBindings.findChildViewById(rootView, id);
      if (imageButton6 == null) {
        break missingId;
      }

      id = R.id.imageButton7;
      ImageButton imageButton7 = ViewBindings.findChildViewById(rootView, id);
      if (imageButton7 == null) {
        break missingId;
      }

      id = R.id.imageView;
      ImageView imageView = ViewBindings.findChildViewById(rootView, id);
      if (imageView == null) {
        break missingId;
      }

      id = R.id.imageView1;
      ImageView imageView1 = ViewBindings.findChildViewById(rootView, id);
      if (imageView1 == null) {
        break missingId;
      }

      id = R.id.linearLayout2;
      LinearLayout linearLayout2 = ViewBindings.findChildViewById(rootView, id);
      if (linearLayout2 == null) {
        break missingId;
      }

      id = R.id.textView;
      TextView textView = ViewBindings.findChildViewById(rootView, id);
      if (textView == null) {
        break missingId;
      }

      id = R.id.textView11;
      TextView textView11 = ViewBindings.findChildViewById(rootView, id);
      if (textView11 == null) {
        break missingId;
      }

      id = R.id.textView5;
      TextView textView5 = ViewBindings.findChildViewById(rootView, id);
      if (textView5 == null) {
        break missingId;
      }

      id = R.id.textView6;
      TextView textView6 = ViewBindings.findChildViewById(rootView, id);
      if (textView6 == null) {
        break missingId;
      }

      id = R.id.textView7;
      TextView textView7 = ViewBindings.findChildViewById(rootView, id);
      if (textView7 == null) {
        break missingId;
      }

      id = R.id.textView8;
      TextView textView8 = ViewBindings.findChildViewById(rootView, id);
      if (textView8 == null) {
        break missingId;
      }

      id = R.id.textView9;
      TextView textView9 = ViewBindings.findChildViewById(rootView, id);
      if (textView9 == null) {
        break missingId;
      }

      return new ActivityPrincipalBinding((ConstraintLayout) rootView, barraSuperior, bottomNav,
          button, button2, button3, button6, buttonn3, buttonn6, divider1, divider2, divider3,
          divider4, imageButton3, imageButton5, imageButton6, imageButton7, imageView, imageView1,
          linearLayout2, textView, textView11, textView5, textView6, textView7, textView8,
          textView9);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
