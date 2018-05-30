package edu.eiu.tourist_app;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.Arrays;
import java.util.List;

public class PlacesViewModel extends ViewModel {
    private PlacesRepository placesRepository;

private LiveData<List<WikipediaPage>> touristSitesData;

    public PlacesViewModel() {
//        final MutableLiveData<List<String>> data = new MutableLiveData<>();
//        data.setValue(touristSites);
        placesRepository = new PlacesRepository();
        touristSitesData = placesRepository.getTouristSites();
        }

    public LiveData<List<WikipediaPage>> getTouristSites() {
        return touristSitesData;
    }

}
