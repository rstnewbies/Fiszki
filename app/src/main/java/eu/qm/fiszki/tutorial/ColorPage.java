package eu.qm.fiszki.tutorial;

/**
 * Created by bgood on 2016-04-14.
 */

import android.content.Intent;
import android.graphics.Color;
import android.provider.CalendarContract;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.cleveroad.slidingtutorial.PageFragment;
import com.cleveroad.slidingtutorial.SimplePagerFragment;

import eu.qm.fiszki.R;
import eu.qm.fiszki.activity.AddWordActivity;
import eu.qm.fiszki.activity.MainActivity;
import eu.qm.fiszki.activity.TutorialActivity;

public class ColorPage extends SimplePagerFragment {

    @Override
    protected int getPagesCount() {
        return 6;
    }

    @Override
    protected PageFragment getPage(int position) {
        position %= 5;
        if (position == 0)
            return new FirstPage();
        if (position == 1)
            return new SecondPage();
        if (position == 2)
            return new ThirdPage();
        if (position == 3)
            return new FourPage();
        if (position == 4)
            return new FivePage();
        throw new IllegalArgumentException("WHOOP WHOOP " + position);
    }

    @ColorInt
    @Override
    protected int getPageColor(int position) {
        if (position == 0)
            return ContextCompat.getColor(getContext(), R.color.yellow);
        if (position == 1)
            return ContextCompat.getColor(getContext(), R.color.pressed_color);
        if (position == 2)
            return ContextCompat.getColor(getContext(), R.color.pistachio);
        if (position == 3)
            return ContextCompat.getColor(getContext(), R.color.patin);
        if (position == 4)
            return ContextCompat.getColor(getContext(), R.color.White);
        if (position == 5){
            getActivity().finish();
        }
        return Color.TRANSPARENT;
    }

    @Override
    protected boolean isInfiniteScrollEnabled() {
        return false;
    }

    @Override
    protected boolean onSkipButtonClicked(View skipButton) {
        getActivity().finish();
        return true;
    }
}
