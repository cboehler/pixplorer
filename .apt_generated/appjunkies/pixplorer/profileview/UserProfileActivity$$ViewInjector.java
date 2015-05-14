// Generated code from Butter Knife. Do not modify!
package appjunkies.pixplorer.profileview;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class UserProfileActivity$$ViewInjector<T extends appjunkies.pixplorer.profileview.UserProfileActivity> extends appjunkies.pixplorer.profileview.BaseActivity$$ViewInjector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    super.inject(finder, target, source);

    View view;
    view = finder.findRequiredView(source, 2131427435, "field 'rvUserProfile'");
    target.rvUserProfile = finder.castView(view, 2131427435, "field 'rvUserProfile'");
    view = finder.findRequiredView(source, 2131427433, "field 'vRevealBackground'");
    target.vRevealBackground = finder.castView(view, 2131427433, "field 'vRevealBackground'");
  }

  @Override public void reset(T target) {
    super.reset(target);

    target.rvUserProfile = null;
    target.vRevealBackground = null;
  }
}
