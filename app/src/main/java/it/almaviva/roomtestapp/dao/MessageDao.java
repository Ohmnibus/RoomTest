package it.almaviva.roomtestapp.dao;

import android.arch.lifecycle.LiveData;
import android.arch.paging.DataSource;
import android.arch.paging.LivePagedListProvider;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import it.almaviva.roomtestapp.entity.Message;

/**
 * Created by m.spalletti on 03/01/2018.
 */
@Dao
public abstract class MessageDao {
	@Insert
	public abstract long insert(Message item);

	@Insert
	public abstract void insertAll(Message[] items);

	@Query("Select * From Message Order By timeStamp Desc")
	public abstract DataSource.Factory<Integer, Message> getAllPaged();

	public DataSource.Factory<Integer, Message> getFilteredPaged(String searchText) {
		return _getFilteredPaged("%" + searchText + "%");
	}

	@Query("Select * From Message Where body LIKE :searchText Order By timeStamp Desc")
	protected abstract DataSource.Factory<Integer, Message> _getFilteredPaged(String searchText);
}
