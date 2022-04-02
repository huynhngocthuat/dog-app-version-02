package com.example.lab_05_ver2.view;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

import com.example.lab_05_ver2.MainActivity;
import com.example.lab_05_ver2.R;
import com.example.lab_05_ver2.databinding.ActivityMainBinding;
import com.example.lab_05_ver2.model.DogBreed;
import com.example.lab_05_ver2.viewmodel.DogsApiService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class ListFragment extends Fragment {

    private DogsApiService dogsApiService;
    private DogAdapter dogAdapter;
    private RecyclerView rcvDogBreed;
    private ArrayList<DogBreed> dogBreeds;
    private SearchView searchView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcvDogBreed = view.findViewById(R.id.rcv_dog_breed);
        dogBreeds = new ArrayList<DogBreed>();
        dogAdapter = new DogAdapter(dogBreeds, dogBreeds);
        rcvDogBreed.setAdapter(dogAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(view.getContext(),2);
        rcvDogBreed.setLayoutManager(gridLayoutManager);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();

                switch (direction){
                    case ItemTouchHelper.RIGHT:
                        DogBreed dogBreed = dogBreeds.get(position);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("dogBreed", dogBreed);
                        Navigation.findNavController(view).navigate(R.id.detailsFragment, bundle);
                        break;
                    case ItemTouchHelper.LEFT:
                        break;
                }
            }
        });
        itemTouchHelper.attachToRecyclerView(rcvDogBreed);

        //get data from api
        dogsApiService = new DogsApiService();
        dogsApiService.getDogs()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<DogBreed>>() {
                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull List<DogBreed> dogBreeds1) {
                        for (DogBreed dogBreed : dogBreeds1){
                            dogBreeds.add(dogBreed);
                            dogAdapter.notifyDataSetChanged();
                        }
                    }
                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        Log.d("DEBUG1", e.getMessage());
                    }
                });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String query) {
                dogAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                dogAdapter.getFilter().filter(newText);
                return false;
            }
        });

    }
}