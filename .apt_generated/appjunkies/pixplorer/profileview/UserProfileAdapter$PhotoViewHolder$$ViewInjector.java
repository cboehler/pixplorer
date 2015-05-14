// Generated code from Butter Knife. Do not modify!
package appjunkies.pixplorer.profileview;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class UserProfileAdapter$PhotoViewHolder$$ViewInjector<T extends appjunkies.pixplorer.profileview.UserProfileAdapter.PhotoViewHolder> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131427449, "field 'ivPhoto'");
    target.ivPhoto = finder.castView(view, 2131427449, "field 'ivPhoto'");
    view = finder.findRequiredView(source, 2131427448, "field 'flRoot'");
    target.flRoot = finder.castView(view, 2131427448, "field 'flRoot'");
  }

  @Override public void reset(T target) {
    target.ivPhoto = null;
    target.flRoot = null;
  }
}
