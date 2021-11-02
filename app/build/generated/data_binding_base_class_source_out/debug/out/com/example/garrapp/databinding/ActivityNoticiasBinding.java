// Generated by view binder compiler. Do not edit!
package com.example.garrapp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public final class ActivityNoticiasBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final BottomNavigationView bottomNav;

  private ActivityNoticiasBinding(@NonNull ConstraintLayout rootView,
      @NonNull BottomNavigationView bottomNav) {
    this.rootView = rootView;
    this.bottomNav = bottomNav;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityNoticiasBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityNoticiasBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_noticias, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityNoticiasBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.bottom_nav;
      BottomNavigationView bottomNav = ViewBindings.findChildViewById(rootView, id);
      if (bottomNav == null) {
        break missingId;
      }

      return new ActivityNoticiasBinding((ConstraintLayout) rootView, bottomNav);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
