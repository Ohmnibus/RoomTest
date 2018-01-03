package it.almaviva.roomtestapp.viewModel;

import android.arch.lifecycle.ComputableLiveData;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.DataSource;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.LivePagedListProvider;
import android.arch.paging.PagedList;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import it.almaviva.roomtestapp.RemoteTestApp;
import it.almaviva.roomtestapp.dao.MessageDao;
import it.almaviva.roomtestapp.dao.MyDatabase;
import it.almaviva.roomtestapp.entity.Message;

/**
 * Created by m.spalletti on 03/01/2018.
 */

public class MyViewModel extends ViewModel {
	public final LiveData<PagedList<Message>> messagesList;
	public final MutableLiveData<String> searchText;
	public LiveData<PagedList<Message>> oldFilteredMessagesList;
	public LiveData<PagedList<Message>> filteredMessagesList;
	private boolean valid = false;

//	public MyViewModel(MessageDao msgDao) {
//		messagesList = new LivePagedListBuilder<>(
//				msgDao.getAllPaged(), /* page size */ 20).build();
//	}

	public MyViewModel() {
		searchText = new MutableLiveData<>();
		searchText.setValue("");

		MessageDao msgDao = MyDatabase.getInstance(RemoteTestApp.getContext()).messageDao();
		messagesList = new LivePagedListBuilder<>(msgDao.getAllPaged(), 20)
				//.setInitialLoadKey().setBoundaryCallback()
				.build();

		//messagesList.
		//messagesList = msgDao.getAllPagedBis().create(0, 20);
	}

	public final LiveData<PagedList<Message>> getMessageList() {
		if (! valid) {
			oldFilteredMessagesList = filteredMessagesList;
			MessageDao msgDao = MyDatabase.getInstance(RemoteTestApp.getContext()).messageDao();
			DataSource.Factory<Integer, Message> ds = msgDao.getFilteredPaged(searchText.getValue());
			filteredMessagesList = new LivePagedListBuilder<>(ds, 20).build();
			valid = true;
		}
		return filteredMessagesList;
	}

	public final void observeMessageList(LifecycleOwner owner, Observer<PagedList<Message>> observer) {
		getMessageList();
		if (oldFilteredMessagesList != null) {
			oldFilteredMessagesList.removeObservers(owner);
		}
		filteredMessagesList.observe(owner, observer);
	}

	public final void setSearchString(String searchString) {
		valid = false;
		searchText.setValue(searchString);
	}

	public final LiveData<String> getSearchString() {
		return searchText;
	}

}
