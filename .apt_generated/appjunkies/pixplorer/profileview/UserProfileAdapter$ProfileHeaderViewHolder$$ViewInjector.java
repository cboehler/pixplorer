// Generated code from Butter Knife. Do not modify!
package appjunkies.pixplorer.profileview;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class UserProfileAdapter$ProfileHeaderViewHolder$$ViewInjector<T extends appjunkies.pixplorer.profileview.UserProfileAdapter.ProfileHeaderViewHolder> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131427504, "field 'vUserProfileRoot'");
    target.vUserProfileRoot = view;
    view = finder.findRequiredView(source, 2131427511, "field 'vUserStats'");
    target.vUserStats = view;
    view = finder.findRequiredView(source, 2131427505, "field 'ivUserProfilePhoto'");
    target.ivUserProfilePhoto = finder.castView(view, 2131427505, "field 'ivUserProfilePhoto'");
    view = finder.findRequiredView(source, 2131427509, "field 'userGroup'");
    target.userGroup = finder.castView(view, 2131427509, "field 'userGroup'");
    view = finder.findRequiredView(source, 2131427422, "field 'container'");
    target.container = view;
    view = finder.findRequiredView(source, 2131427506, "field 'vUserDetails'");
    target.vUserDetails = view;
    view = finder.findRequiredView(source, 2131427512, "field 'places'");
    target.places = finder.castView(view, 2131427512, "field 'places'");
    view = finder.findRequiredView(source, 2131427508, "field 'userEmail'");
    target.userEmail = finder.castView(view, 2131427508, "field 'userEmail'");
    view = finder.findRequiredView(source, 2131427513, "field 'searching'");
    target.searching = finder.castView(view, 2131427513, "field 'searching'");
    view = finder.findRequiredView(source, 2131427507, "field 'userName'");
    target.userName = finder.castView(view, 2131427507, "field 'userName'");
    view = finder.findRequiredView(source, 2131427510, "field 'btnHighscore'");
    target.btnHighscore = finder.castView(view, 2131427510, "field 'btnHighscore'");
    view = finder.findRequiredView(source, 2131427514, "field 'found'");
    target.found = finder.castView(view, 2131427514, "field 'found'");
  }

  @Override public void reset(T target) {
    target.vUserProfileRoot = null;
    target.vUserStats = null;
    target.ivUserProfilePhoto = null;
    target.userGroup = null;
    target.container = null;
    target.vUserDetails = null;
    target.places = null;
    target.userEmail = null;
    target.searching = null;
    target.userName = null;
    target.btnHighscore = null;
    target.found = null;
  }
}
