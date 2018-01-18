package it.almaviva.roomtestapp.viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.DataSource;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.os.AsyncTask;
import android.support.annotation.Nullable;

import it.almaviva.roomtestapp.RemoteTestApp;
import it.almaviva.roomtestapp.dao.MessageDao;
import it.almaviva.roomtestapp.dao.MyDatabase;
import it.almaviva.roomtestapp.entity.Message;

/**
 * Created by m.spalletti on 03/01/2018.
 */

public class MyViewModel extends ViewModel {

	private final MessageDao mMessageDao;
	private final MediatorLiveData<PagedList<Message>> mFilteredMessageList;
	private final MutableLiveData<String> searchText;

	public LiveData<PagedList<Message>> mCurrentFilteredMessagesList;

	public MyViewModel() {
		searchText = new MutableLiveData<>();
		searchText.setValue("");

		mMessageDao = MyDatabase.getInstance(RemoteTestApp.getContext()).messageDao();
		mCurrentFilteredMessagesList = null;
		mFilteredMessageList = new MediatorLiveData<>();
		mFilteredMessageList.setValue(null);

		refreshFilteredMessage();
	}

	public LiveData<PagedList<Message>> getMessageList() {
		return mFilteredMessageList;
	}

	private void refreshFilteredMessage() {
		if (mCurrentFilteredMessagesList != null) {
			mFilteredMessageList.removeSource(mCurrentFilteredMessagesList);
		}
		DataSource.Factory<Integer, Message> dsf = mMessageDao.getFilteredPaged(searchText.getValue());
		mCurrentFilteredMessagesList = new LivePagedListBuilder<>(dsf, 20).build();
		mFilteredMessageList.addSource(mCurrentFilteredMessagesList, new Observer<PagedList<Message>>() {
			@Override
			public void onChanged(@Nullable PagedList<Message> messages) {
				mFilteredMessageList.setValue(messages);
			}
		});

	}

	public final void setSearchString(String searchString) {
		searchText.setValue(searchString);
		refreshFilteredMessage();
	}

	public final LiveData<String> getSearchString() {
		return searchText;
	}

	public void insertMessage(final Message msg) {
		new InsertMessageTask(mMessageDao).execute(msg);
	}

	private static class InsertMessageTask extends AsyncTask<Message, Void, Void> {

		MessageDao mMessageDao;

		public InsertMessageTask(MessageDao messageDao) {
			mMessageDao = messageDao;
		}

		@Override
		protected Void doInBackground(Message... msgs) {
			mMessageDao.insertAll(msgs);
			return null;
		}
	}
}
