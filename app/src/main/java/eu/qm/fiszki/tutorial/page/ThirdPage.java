package eu.qm.fiszki.tutorial.page;

/**
 * Created by bgood on 2016-04-14.
 */
import com.cleveroad.slidingtutorial.PageFragment;
import com.cleveroad.slidingtutorial.TransformItem;

import eu.qm.fiszki.R;

public class ThirdPage extends PageFragment {

    @Override
    protected int getLayoutResId() {
        return R.layout.tutorial_page_third;
    }

    @Override
    protected TransformItem[] provideTransformItems() {
        return new TransformItem[]{
                new TransformItem(R.id.TutThirdImage, true, 20),
        };
    }
}
