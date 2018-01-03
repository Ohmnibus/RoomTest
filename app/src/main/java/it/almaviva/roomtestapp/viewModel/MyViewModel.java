package it.almaviva.roomtestapp.viewModel;

import android.arch.lifecycle.ComputableLiveData;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.DataSource;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;

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
	public LiveData<PagedList<Message>> filteredMessagesList;

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
		if (filteredMessagesList == null) {
			MessageDao msgDao = MyDatabase.getInstance(RemoteTestApp.getContext()).messageDao();
			filteredMessagesList = new LivePagedListBuilder<>(msgDao.getFilteredPaged(searchText.getValue()), 20).build();

			DataSource.Factory fac = msgDao.getFilteredPaged(searchText.getValue());
			fac.create();
			//ComputableLiveData<PagedList<Message>> asd;// = (ComputableLiveData<PagedList<Message>>)filteredMessagesList;
			//asd.getLiveData().getValue()
			//filteredMessagesList.getValue().detach();
		}
		return filteredMessagesList;
	}

	public final void setSearchString(String searchString) {
		//filteredMessagesList..removeObservers();
		filteredMessagesList = null;
		//filteredMessagesList.getValue().
		searchText.setValue(searchString);
	}

	public final LiveData<String> getSearchString() {
		return searchText;
	}
}
