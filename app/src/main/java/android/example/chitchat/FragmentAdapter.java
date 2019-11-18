package android.example.chitchat;

//fragment adapter is required with the view pager for swiping

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class FragmentAdapter extends FragmentPagerAdapter {

    HistoryFragment mHistory;

    public FragmentAdapter(FragmentManager manager) {
        super(manager, FragmentAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        mHistory = HistoryFragment.newInstance(1);
    }

    public int getCount() {
        //3 pages to swipe
        return 3;
    }

    public Fragment getItem(int position) {

        Fragment page = null;

        switch (position) {
            case 0:
                page = ChatMessageFragment.newInstance("One", "Two");
                break;
            case 1:
                page = mHistory; //1 col of data
                break;

            case 2:
                page = MembersFragment.newInstance(2); //2 cols of data
                break;

            default:
                page = ChatMessageFragment.newInstance("One", "Two");
                break;
        }
        return page;
    }

    //tabs
    public CharSequence getPageTitle(int position) {

        CharSequence result = "";

        switch (position) {
            case 0:
                result = "Chat";
                break;
            case 1:
                result = "History";
                break;

            case 2:
                result = "Members";
                break;

            default:
                result = "Chat";
                break;
        }

        return result;
    }
}
