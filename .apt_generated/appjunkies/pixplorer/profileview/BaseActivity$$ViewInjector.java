// Generated code from Butter Knife. Do not modify!
package appjunkies.pixplorer.profileview;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class BaseActivity$$ViewInjector<T extends appjunkies.pixplorer.profileview.BaseActivity> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findOptionalView(source, 2131427434, null);
    target.toolbar = finder.castView(view, 2131427434, "field 'toolbar'");
    view = finder.findOptionalView(source, 2131427503, null);
    target.ivLogo = finder.castView(view, 2131427503, "field 'ivLogo'");
  }

  @Override public void reset(T target) {
    target.toolbar = null;
    target.ivLogo = null;
  }
}
