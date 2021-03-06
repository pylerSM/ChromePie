package com.jt5.xposed.chromepie.settings.preference;

import android.content.Context;
import android.content.res.TypedArray;
import android.preference.ListPreference;

import com.jt5.xposed.chromepie.R;

public class PieListPreference extends ListPreference {

    public PieListPreference(Context context, int item, int slice) {
        super(context);
        initialise(item, slice);
    }

    @Override
    public void setValue(String value) {
        String oldValue = getValue();
        super.setValue(value);
        if (!value.equals(oldValue)) {
            updateState(value);
            notifyDependencyChange(shouldDisableDependents());
        }
    }

    @Override
    public boolean shouldDisableDependents() {
        boolean shouldDisableDependents = super.shouldDisableDependents();
        String value = getValue();
        return shouldDisableDependents || value == null || value.equals("none");
    }

    private void initialise(int item, int slice) {
        setKey("slice_" + slice + "_item_" + item);
        setTitle(getContext().getResources().getString(R.string.pie_item) + " " + item);
        if (item == slice) {
            setTitle(getTitle() + " (" + getContext().getResources().getString(R.string.main_item) + ")");
        }
        setEntries(R.array.pie_item_entries);
        setEntryValues(R.array.pie_item_values);
        setIcon(R.drawable.null_icon);
        setDefaultValue("none");
        setOrder(item);
    }

    private void updateState(String newValue) {
        final int index = findIndexOfValue(newValue);
        setSummary(getEntry());
        final TypedArray drawables = getContext().getResources().obtainTypedArray(R.array.pie_item_dark_drawables);
        setIcon(drawables.getResourceId(index, 0));
        drawables.recycle();
    }

}
