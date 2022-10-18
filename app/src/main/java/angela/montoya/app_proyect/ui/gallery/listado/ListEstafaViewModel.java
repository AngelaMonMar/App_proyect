package angela.montoya.app_proyect.ui.gallery.listado;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class ListEstafaViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ListEstafaViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}