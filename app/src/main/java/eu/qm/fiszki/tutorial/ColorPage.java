package eu.qm.fiszki.tutorial;

/**
 * Created by bgood on 2016-04-14.
 */

import android.content.Intent;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.cleveroad.slidingtutorial.PageFragment;
import com.cleveroad.slidingtutorial.SimplePagerFragment;

import eu.qm.fiszki.R;
import eu.qm.fiszki.activity.MainActivity;

public class ColorPage extends SimplePagerFragment {

    @Override
    protected int getPagesCount() {
        return 4;
    }

    @Override
    protected PageFragment getPage(int position) {
        View bskip = getActivity().findViewById(getButtonSkipResId());
        bskip.setVisibility(View.INVISIBLE);
        position %= 4;
        if (position == 0) {
            return new FirstPage();
        }
        if (position == 1) {
            return new SecondPage();
        }
        if (position == 2) {
            return new ThirdPage();
        }
        if (position == 3) {
            return new FourPage();
        }
        throw new IllegalArgumentException("WHOOP WHOOP " + position);
    }

    @ColorInt
    @Override
    protected int getPageColor(int position) {
        if (position == 0) {
            return ContextCompat.getColor(getContext(), R.color.yellow);
        }
        if (position == 1) {
            return ContextCompat.getColor(getContext(), R.color.pressed_color);
        }
        if (position == 2) {
            return ContextCompat.getColor(getContext(), R.color.pistachio);
        }
        if (position == 3) {
            return ContextCompat.getColor(getContext(), R.color.patin);
        }
        throw new IllegalArgumentException("Wrong position");
    }

    @Override
    protected boolean isInfiniteScrollEnabled() {
        return true;
    }

    @Override
    protected boolean onSkipButtonClicked(View skipButton) {
        Intent myIntent = new Intent(getActivity(), MainActivity.class);
        startActivity(myIntent);
        getActivity().finish();
        return true;
    }
}
