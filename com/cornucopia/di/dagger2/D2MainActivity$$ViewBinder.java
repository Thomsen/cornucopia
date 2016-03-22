// Generated code from Butter Knife. Do not modify!
package com.cornucopia.di.dagger2;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class D2MainActivity$$ViewBinder<T extends com.cornucopia.di.dagger2.D2MainActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131296388, "field 'tvTest'");
    target.tvTest = finder.castView(view, 2131296388, "field 'tvTest'");
  }

  @Override public void unbind(T target) {
    target.tvTest = null;
  }
}
